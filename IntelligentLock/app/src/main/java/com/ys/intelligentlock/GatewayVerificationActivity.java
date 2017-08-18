package com.ys.intelligentlock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.result.GeoResultHandler;
import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.LanUtil.UDPSend;
import com.ys.intelligentlock.Utils.DataBaseUtil;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.google.zxing.client.android.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Created by admin on 2016/12/26.
 */

public class GatewayVerificationActivity extends Activity implements View.OnClickListener{
    private ImageButton backOnGatewayVerification;
    private RadioGroup gatewayRadioGroup;
    private RadioButton scanGatewayCode;
    private RadioButton handGatewayCode;
    private LinearLayout linearLayout_scanAddGatewayCode;
    private EditText editText_gatewayCode;
    private Button button_search;
    private Button button_update;
    private Button button_next;
    private TextView textView_gatewayVerificationCode;
    private LinearLayout linearLayout_GatewayVerificationCode;

    private TextView textView_gatewayLanIp;
    private String phoneNumber;
    private Handler handler;
    private final int NoNetwork=0x55;
    private final int GatewayNotUse=0x11;
    private final int judgeGatewayCodeUnique=0x22;
    private final int judgeVerifyCode=0x33;
    private final int GetGatewayVerificationCode=0x44;
    private final int NoGatewayOnLan=0x66;
    private final int GatewayCodeFalse=0x77;
    private final int UpdateGatewayCode=0x88;
    private String gatewayIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gateway_verification);
        backOnGatewayVerification=(ImageButton)findViewById(R.id.backOnGatewayVerification);
        gatewayRadioGroup=(RadioGroup)findViewById(R.id.gatewayRadioGroup);
        scanGatewayCode=(RadioButton)findViewById(R.id.scanGatewayCode);
        handGatewayCode=(RadioButton)findViewById(R.id.handGatewayCode);
        linearLayout_scanAddGatewayCode=(LinearLayout)findViewById(R.id.linearLayout_scanAddGatewayCode);
        editText_gatewayCode=(EditText)findViewById(R.id.editText_gatewayCode);
        button_search=(Button)findViewById(R.id.button_search);
        button_update=(Button)findViewById(R.id.button_update);
        button_next=(Button)findViewById(R.id.button_next);
        textView_gatewayVerificationCode=(TextView)findViewById(R.id.textView_gatewayVerificationCode);
        textView_gatewayLanIp=(TextView)findViewById(R.id.textView_gatewayLanIp);
        linearLayout_GatewayVerificationCode=(LinearLayout)findViewById(R.id.linearLayout_GatewayVerificationCode);
        linearLayout_GatewayVerificationCode.setVisibility(View.GONE);

        phoneNumber=getIntent().getStringExtra("phoneNumber");
        scanGatewayCode.setChecked(true);
        handGatewayCode.setChecked(false);
        editText_gatewayCode.setEnabled(false);
        gatewayRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.scanGatewayCode){
                    scanGatewayCode.setChecked(true);
                    handGatewayCode.setChecked(false);
                    linearLayout_scanAddGatewayCode.setVisibility(View.VISIBLE);
                    editText_gatewayCode.setEnabled(false);
                }
                else if(checkedId==R.id.handGatewayCode){
                    scanGatewayCode.setChecked(false);
                    handGatewayCode.setChecked(true);
                    linearLayout_scanAddGatewayCode.setVisibility(View.GONE);
                    editText_gatewayCode.setEnabled(true);
                }
            }
        });
        backOnGatewayVerification.setOnClickListener(this);
        linearLayout_scanAddGatewayCode.setOnClickListener(this);
        button_search.setOnClickListener(this);
        button_update.setOnClickListener(this);
        button_next.setOnClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ip       "+getLocalIp());
            }
        }).start();

        if(!inquireWifiState()){
            Toast.makeText(this,"当前Wifi未连接！",Toast.LENGTH_SHORT).show();
        }

        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==NoGatewayOnLan){
                    waitView.dismiss();
                    new AlertDialog.Builder(GatewayVerificationActivity.this).setTitle("注意").setMessage("当前局域网内没有亿数网关！")
                            .setPositiveButton("确定",null).show();
                    return;
                }
                else if(msg.what==GatewayCodeFalse){
                    waitView.dismiss();
                    new AlertDialog.Builder(GatewayVerificationActivity.this).setTitle("注意").setMessage("网关条码不匹配！")
                            .setPositiveButton("确定",null).show();
                    return;
                }
                else if(msg.what== GetGatewayVerificationCode){
                    textView_gatewayVerificationCode.setText((String)msg.obj);
                    textView_gatewayLanIp.setText(msg.getData().getString("gatewayLanIp"));
                    return;
                }
                else if(msg.what==NoNetwork){
                    waitView.dismiss();
                    Toast.makeText(GatewayVerificationActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(msg.what==GatewayNotUse){
                    waitView.dismiss();
                    Toast.makeText(GatewayVerificationActivity.this,"该网关未连接服务器！",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(msg.what==judgeGatewayCodeUnique){
                    waitView.dismiss();

                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(GatewayVerificationActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            Toast.makeText(GatewayVerificationActivity.this,"该网关条码可用！",Toast.LENGTH_SHORT).show();
                            linearLayout_GatewayVerificationCode.setVisibility(View.VISIBLE);
                            editText_gatewayCode.setEnabled(false);
                            gatewayRadioGroup.setVisibility(View.GONE);
                        }
                        else if(result==1){
                            String alreadyPhoneNumber=jsonResponse.getString("alreadyPhoneNumber");
                            showPopupWindowExceptionAlert(alreadyPhoneNumber);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(msg.what==judgeVerifyCode){
                    waitView.dismiss();

                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(GatewayVerificationActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==-1){
                            Toast.makeText(GatewayVerificationActivity.this,"该网关未曾连接网络！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(result==0){
                            Toast.makeText(GatewayVerificationActivity.this,"该网关验证码不存在！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(result==2){
                            Toast.makeText(GatewayVerificationActivity.this,"网关验证码匹配失败！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(result==1){
                            Toast.makeText(GatewayVerificationActivity.this,"网关验证码匹配成功！",Toast.LENGTH_SHORT).show();
                            Intent nextIntent=new Intent(GatewayVerificationActivity.this,AddGatewayActivity.class) ;
                            nextIntent.putExtra("phoneNumber",phoneNumber);
                            nextIntent.putExtra("opCode",textView_gatewayVerificationCode.getText().toString());
                            nextIntent.putExtra("gatewayCode",editText_gatewayCode.getText().toString());
                            nextIntent.putExtra("opCode",textView_gatewayVerificationCode.getText().toString());
                            startActivity(nextIntent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(msg.what==UpdateGatewayCode){
                    textView_gatewayVerificationCode.setText((String)msg.obj);
                }
            }
        };


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.backOnGatewayVerification:
                finish();
                break;
            case R.id.linearLayout_scanAddGatewayCode:
                Intent scanIntent=new Intent(GatewayVerificationActivity.this,CaptureActivity.class);
                startActivityForResult(scanIntent,0);
                break;
            case R.id.button_search:
                if(editText_gatewayCode.getText().toString().equals("")){
                    Toast.makeText(GatewayVerificationActivity.this,"网关条码不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                if(editText_gatewayCode.getText().toString().trim().length()!=32){
                    Toast.makeText(GatewayVerificationActivity.this,"网关条码只能为32位！",Toast.LENGTH_SHORT).show();
                    return;
                }
                 **/

                showWait();

                // 局域网连接网关
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        broadcastGateway();
                    }
                }).start();

                break;
            case R.id.button_update:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        updateVerifyCode();
                    }
                }).start();

                break;
            case R.id.button_next:
                showWait();
                String gatewayVerificationCode=textView_gatewayVerificationCode.getText().toString();
                if(gatewayVerificationCode.length()==0){
                    Toast.makeText(this,"网关验证码不能为空！",Toast.LENGTH_SHORT).show();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonRequest=new JSONObject();
                        try {
                            jsonRequest.put("sign",7);
                            jsonRequest.put("ownerPhoneNumber",phoneNumber);
                            jsonRequest.put("gatewayCode",editText_gatewayCode.getText().toString());
                            jsonRequest.put("opCode",textView_gatewayVerificationCode.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("jsonRequest  "+jsonRequest);
                        String consequence=HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                        System.out.println("consequence  "+consequence);
                        Message message=new Message();
                        message.obj=consequence;
                        message.what=judgeVerifyCode;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==0){
                Toast.makeText(this,"扫描成功！",Toast.LENGTH_SHORT).show();
                String result=data.getExtras().getString("result");
                editText_gatewayCode.setText(result);
            }
        }
    }

    public boolean inquireWifiState(){
        WifiManager manager=(WifiManager)getApplicationContext().getSystemService(this.WIFI_SERVICE);
        return manager.isWifiEnabled();
    }

    public String getLocalIp(){
        String ip=null;
        try {
            ip=InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public void showPopupWindowWifiAlert(){
        View view=View.inflate(GatewayVerificationActivity.this,R.layout.nowifi_alert,null);
        TextView confirm=(TextView)view.findViewById(R.id.confirm_noWifi);
        final PopupWindow alertWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        alertWindow.setAnimationStyle(R.style.animation);
        alertWindow.setOutsideTouchable(true);
        alertWindow.showAtLocation(backOnGatewayVerification, Gravity.CENTER,0,0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        alertWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp =getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertWindow.dismiss();
            }
        });

    }

    public void showPopupWindowExceptionAlert(String alreadyPhoneNumber){
        View view=View.inflate(GatewayVerificationActivity.this,R.layout.exception_alert,null);
        TextView confirm=(TextView)view.findViewById(R.id.confirm_exception);
        TextView textView_alreadyPhoneNumber=(TextView)view.findViewById(R.id.textView_alreadyPhoneNumber);
        final PopupWindow alertWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        alertWindow.setAnimationStyle(R.style.animation);
        alertWindow.setOutsideTouchable(true);
        alertWindow.showAtLocation(backOnGatewayVerification, Gravity.CENTER,0,0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        alertWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp =getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertWindow.dismiss();
            }
        });
        textView_alreadyPhoneNumber.setText(alreadyPhoneNumber);

    }

    private PopupWindow waitView;
    public void showWait(){
        View contentView= LayoutInflater.from(this).inflate(R.layout.wait,null,false);
        waitView=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT,true);
        waitView.setAnimationStyle(R.style.animation);
        waitView.setOutsideTouchable(true);
        waitView.showAtLocation(backOnGatewayVerification, Gravity.CENTER,0,0);
    }

    public void broadcastGateway(){
        String gatewayCode=addZero(editText_gatewayCode.getText().toString());
        System.out.println("gatewayCode     ");

        /**
        System.out.println(" encryptprocessout  "+new String(PasswordEncrypt.encryptprocessout(gatewayCode,32)));
        System.out.println("decrypt   "+new String(PasswordEncrypt.decryptprocessout(PasswordEncrypt.encryptprocessout(gatewayCode,32),32)));
        System.out.println(gatewayCode.substring(0,32).equals(new String(PasswordEncrypt.decryptprocessout(PasswordEncrypt.encryptprocessout(gatewayCode,32),32)).substring(0,32)));
        byte[] b=new String(PasswordEncrypt.decryptprocessout(PasswordEncrypt.encryptprocessout(gatewayCode,32),32)).getBytes();
        for(int i=0;i<b.length;i++){
            System.out.println(b[i]);
        }
         **/

        Map<String,Object> map=PasswordEncrypt.gwcom(1,gatewayCode);
        int backret=(int)map.get("backret");
        String recvmessage=(String)map.get("recvmessage");

        if(backret!=1){
            handler.sendEmptyMessage(NoGatewayOnLan);
            return;
        }
        System.out.println("gatewayCode   "+gatewayCode);
        System.out.println("recvmessage   "+recvmessage.substring(0,32));

        if(!recvmessage.substring(0,32).equals(gatewayCode)){
            handler.sendEmptyMessage(GatewayCodeFalse);
            return;
        }
        getVerifyCodeFromGateway(gatewayCode);
    }

    public void getVerifyCodeFromGateway(String gatewayCode){
        Map<String,Object> map=PasswordEncrypt.gwcom(3,gatewayCode);
        int backret=(int)map.get("backret");
        String recvmessage=(String)map.get("recvmessage");
        String gatewayLanIp=(String)map.get("gatewayLanIp");
        System.out.println("recvmessage2   "+recvmessage);
        Message message=new Message();
        message.what=GetGatewayVerificationCode;
        message.obj=recvmessage;
        Bundle bundle=message.getData();
        bundle.putString("gatewayLanIp",gatewayLanIp);
        handler.sendMessage(message);

        judgeGatewayUseful();
    }

    public void updateVerifyCode(){
        String gatewayCode=addZero(editText_gatewayCode.getText().toString());
        Map<String,Object> map=PasswordEncrypt.gwcom(5,gatewayCode);
        int backret=(int)map.get("backret");
        String recvmessage=(String)map.get("recvmessage");
        Message message=new Message();
        message.what=UpdateGatewayCode;
        message.obj=recvmessage;
        handler.sendMessage(message);
    }

    public void judgeGatewayUseful(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("udp send");
                new UDPSend().sendData();

                JSONObject jsonRequest=new JSONObject();
                try {
                    jsonRequest.put("sign",5);
                    jsonRequest.put("ownerPhoneNumber",phoneNumber);
                    jsonRequest.put("gatewayCode",editText_gatewayCode.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("jsonRequest  "+jsonRequest);

                String baseIp=HttpUtil.getIp(HttpUtil.hostName);
                String consequence=HttpUtil.postData(jsonRequest.toString(),baseIp);
                System.out.println("consequence  "+consequence);
                if(consequence==null){
                    Message message=new Message();
                    message.obj=consequence;
                    message.what=NoNetwork;
                    handler.sendMessage(message);
                    return;
                }
                try {
                    JSONObject jsonResponse=new JSONObject(consequence);
                    int result=jsonResponse.getInt("result");
                    if(result==1){
                        Message message=new Message();
                        message.obj=consequence;
                        message.what=GatewayNotUse;
                        handler.sendMessage(message);
                        return;
                    }
                    else if(result==0){
                        gatewayIp=jsonResponse.getString("gatewayIp");
                        DataBaseUtil dataBaseUtil=new DataBaseUtil(GatewayVerificationActivity.this);
                        dataBaseUtil.putGatewayIp(phoneNumber,editText_gatewayCode.getText().toString(),gatewayIp);
                        JSONObject jsonRequest2=new JSONObject();
                        try {
                            jsonRequest2.put("sign",6);
                            jsonRequest2.put("ownerPhoneNumber",phoneNumber);
                            jsonRequest2.put("gatewayCode",editText_gatewayCode.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("jsonRequest  "+jsonRequest2);
                        String consequence2=HttpUtil.postData(jsonRequest2.toString(),gatewayIp);
                        System.out.println("consequence2  "+consequence2);
                        Message message=new Message();
                        message.obj=consequence2;
                        message.what=judgeGatewayCodeUnique;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static String addZero(String original){
        if(original.length()<32){
            byte[] o= new byte[0];
            try {
                o = original.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println(o.length);
            byte[] p=new byte[32];
            for(int i=0;i<o.length;i++){
                p[i]=o[i];
            }
            for(int i=o.length;i<32;i++){
                p[i]=0x00;
            }
            try {
                return new String(p,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
    private String addZero(String original){
        StringBuffer stringBuffer=new StringBuffer(original);
        if(original.length()<32){
            int n=32-original.length();
            byte[] b=new byte[n];
            for(int i=0;i<n;i++){
                b[i]=0x00;
            }
            stringBuffer.append(new String(b));
            return stringBuffer.toString();
        }
        return original;
    }

     **/
}
