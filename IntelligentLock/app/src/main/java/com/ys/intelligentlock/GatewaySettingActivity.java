package com.ys.intelligentlock;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.JsonData.PasswordData;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by admin on 2016/12/15.
 */

public class GatewaySettingActivity extends Activity {
    private ImageButton backOnGatewaySetting;
    private EditText editText_modifyGatewayName;
    private EditText editText_modifyGatewayLocation;
    private EditText editText_modifyGatewayComment;
    private Button saveGatewayModify;
    private Button deleteGateway;

    private String gatewayName;
    private String gatewayLocation;
    private String gatewayComment;
    private String gatewayCode;
    private String phoneNumber;

    private Handler handler;
    private final int ModifyGateway=0x11;
    private final int DeleteGateway=0x22;
    private final int ModifyGatewaySuccess=0x33;
    private final int DeleteGatewaySuccess=0x44;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gateway_setting);
        backOnGatewaySetting=(ImageButton)findViewById(R.id.backOnGatewaySetting);
        editText_modifyGatewayName=(EditText)findViewById(R.id.editText_modifyGatewayName);
        editText_modifyGatewayLocation=(EditText)findViewById(R.id.editText_modifyGatewayLocation);
        editText_modifyGatewayComment=(EditText)findViewById(R.id.editText_modifyGatewayComment);
        saveGatewayModify=(Button)findViewById(R.id.saveGatewayModify);
        deleteGateway=(Button)findViewById(R.id.deleteGateway);

        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("phoneNumber");
        gatewayName=intent.getStringExtra("gatewayName");
        gatewayLocation=intent.getStringExtra("gatewayLocation");
        gatewayComment=intent.getStringExtra("gatewayComment");
        gatewayCode=intent.getStringExtra("gatewayCode");

        editText_modifyGatewayName.setText(gatewayName);
        editText_modifyGatewayLocation.setText(gatewayLocation);
        editText_modifyGatewayComment.setText(gatewayComment);

        backOnGatewaySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveGatewayModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGatewayModify.setClickable(false);
                gatewayName=editText_modifyGatewayName.getText().toString();
                gatewayLocation=editText_modifyGatewayLocation.getText().toString();
                gatewayComment=editText_modifyGatewayComment.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonRequest=new JSONObject();
                        try {
                            jsonRequest.put("sign",9);
                            jsonRequest.put("ownerPhoneNumber",phoneNumber);
                            jsonRequest.put("gatewayCode",gatewayCode);
                            jsonRequest.put("gatewayName",gatewayName);
                            jsonRequest.put("gatewayLocation",gatewayLocation);
                            jsonRequest.put("gatewayComment",gatewayComment);
                            jsonRequest.put("timetag",new TimeUtil().getTimetag());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("jsonRequest  "+jsonRequest);
                        String gatewayIp=((LockApplication)getApplication()).getGatewayIp(gatewayCode);
                        String consequence= HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                        System.out.println("consequence  "+consequence);
                        Message message=new Message();
                        message.obj=consequence;
                        message.what=ModifyGateway;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        deleteGateway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmAlert(1);
            }
        });

        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==ModifyGateway){
                    saveGatewayModify.setClickable(true);
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(GatewaySettingActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            Toast.makeText(GatewaySettingActivity.this,"修改网关成功！",Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent();
                            gatewayName=editText_modifyGatewayName.getText().toString();
                            gatewayLocation=editText_modifyGatewayLocation.getText().toString();
                            gatewayComment=editText_modifyGatewayComment.getText().toString();

                            intent.putExtra("gatewayName",gatewayName);
                            intent.putExtra("gatewayLocation",gatewayLocation);
                            intent.putExtra("gatewayComment",gatewayComment);
                            setResult(ModifyGatewaySuccess,intent);
                            finish();
                        }
                        else if(result==1){
                            Toast.makeText(GatewaySettingActivity.this,"修改网关失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(msg.what==DeleteGateway){
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(GatewaySettingActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            Toast.makeText(GatewaySettingActivity.this,"删除网关成功！",Toast.LENGTH_SHORT).show();
                            setResult(DeleteGatewaySuccess);
                            finish();
                        }
                        else if(result==1){
                            Toast.makeText(GatewaySettingActivity.this,"删除网关失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }

    public void showConfirmAlert(final int order){
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.alert_dialog,null);
        TextView cancel=(TextView)view.findViewById(R.id.cancel_alertDialog);
        TextView confirm=(TextView)view.findViewById(R.id.deleteAuthorization);
        TextView alert_message=(TextView)view.findViewById(R.id.alert_message);
        if(order==1){
            alert_message.setText("删除网关将导致该网关所联结的门锁、开锁授权全部失效！");
        }
        else if(order==2){
            alert_message.setText("请再次确认！");
        }
        final PopupWindow alertWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        alertWindow.setAnimationStyle(R.style.leftToRight);
        alertWindow.setOutsideTouchable(true);
        alertWindow.showAtLocation(backOnGatewaySetting, Gravity.CENTER,0,0);
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
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertWindow.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertWindow.dismiss();
                if(order==1){
                    showConfirmAlert(2);
                }
                else if(order==2){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonRequest=new JSONObject();
                            try {
                                jsonRequest.put("sign",10);
                                jsonRequest.put("ownerPhoneNumber",phoneNumber);
                                jsonRequest.put("gatewayCode",gatewayCode);
                                jsonRequest.put("timetag",new TimeUtil().getTimetag());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("jsonRequest  "+jsonRequest);
                            String gatewayIp=((LockApplication)getApplication()).getGatewayIp(gatewayCode);
                            String consequence= HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                            System.out.println("consequence  "+consequence);
                            Message message=new Message();
                            message.obj=consequence;
                            message.what=DeleteGateway;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
            }
        });

    }

}
