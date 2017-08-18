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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.TimeUtil;
import com.google.zxing.client.android.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/9/12.
 */
public class AddLockActivity extends Activity {
    private LinearLayout linearLayout_scan;
    private EditText editText_lockName;
    private EditText editText_lockLocation;
    private EditText editText_lockComment;

    private EditText editText_lockCode;
    private RadioGroup addLockRadioGroup;
    private RadioButton scanAdd;
    private RadioButton handAdd;
    private ImageButton backOnAddLock;
    private Button saveLockData;

    private String gatewayCode;
    private String phoneNumber;
    private String lockCode;
    private Handler handler;
    private final int scanLockCode=0x11;
    private final int JudgeLockCode=0x22;
    private final int AddLock=0x33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lock);
        addLockRadioGroup=(RadioGroup)findViewById(R.id.addLockRadioGroup);
        scanAdd=(RadioButton)findViewById(R.id.scanAdd);
        handAdd=(RadioButton)findViewById(R.id.handAdd);
        backOnAddLock=(ImageButton)findViewById(R.id.backOnAddLock);
        linearLayout_scan=(LinearLayout)findViewById(R.id.linearLayout_scanAddLockCode);
        saveLockData=(Button)findViewById(R.id.saveLockData);
        editText_lockName=(EditText)findViewById(R.id.editText_lockName);
        editText_lockLocation=(EditText)findViewById(R.id.editText_lockLocation);
        editText_lockComment=(EditText)findViewById(R.id.editText_lockComment);
        editText_lockCode=(EditText)findViewById(R.id.editText_lockCode);
        editText_lockCode.setEnabled(false);
        MyOnClickListener listener=new MyOnClickListener();
        backOnAddLock.setOnClickListener(listener);
        linearLayout_scan.setVisibility(View.VISIBLE);
        linearLayout_scan.setOnClickListener(listener);
        saveLockData.setOnClickListener(listener);

        scanAdd.setChecked(true);
        addLockRadioGroup.setOnCheckedChangeListener(new MyOnCheckedChangedListener());

        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("phoneNumber");
        gatewayCode=intent.getStringExtra("gatewayCode");

        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==JudgeLockCode){
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(AddLockActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            addLock();
                        }
                        else if(result==1){
                            waitView.dismiss();
                            String alreadyPhoneNumber=jsonResponse.getString("alreadyPhoneNumber");
                            showPopupWindowExceptionAlert(alreadyPhoneNumber);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(msg.what==AddLock){
                    waitView.dismiss();
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(AddLockActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            Toast.makeText(AddLockActivity.this,"添加门锁成功！",Toast.LENGTH_SHORT).show();

                            Timer timer=new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            },1000);
                        }
                        else if(result==1){
                            Toast.makeText(AddLockActivity.this,"添加门锁失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==scanLockCode){
                Toast.makeText(this,"扫描成功！",Toast.LENGTH_SHORT).show();
                String result=data.getExtras().getString("result");
                editText_lockCode.setText(result);
            }
        }
    }

    class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.backOnAddLock:
                    finish();
                    break;
                case R.id.linearLayout_scanAddLockCode:
                    Intent scanIntent=new Intent(AddLockActivity.this,CaptureActivity.class);
                    startActivityForResult(scanIntent,scanLockCode);
                    break;
                case R.id.saveLockData:
                    lockCode=editText_lockCode.getText().toString();
                    if(lockCode.equals("")){
                        Toast.makeText(AddLockActivity.this,"门锁条码不能为空！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(editText_lockName.getText().toString().equals("")){
                        Toast.makeText(AddLockActivity.this,"门锁名称不能为空！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    /**
                    if(lockCode.length()!=32){
                        Toast.makeText(AddLockActivity.this,"门锁条码必须为32位！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                     **/
                    showWait();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonRequest=new JSONObject();
                            try {
                                jsonRequest.put("sign",11);
                                jsonRequest.put("ownerPhoneNumber",phoneNumber);
                                jsonRequest.put("lockCode",lockCode);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("jsonRequest  "+jsonRequest);
                            String gatewayIp=((LockApplication)getApplication()).getGatewayIp(gatewayCode);

                            String consequence= HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                            System.out.println("consequence  "+consequence);
                            Message message=new Message();
                            message.obj=consequence;
                            message.what=JudgeLockCode;
                            handler.sendMessage(message);
                        }
                    }).start();

                    break;
                default:
                    break;
            }
        }
    }

    class MyOnCheckedChangedListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId==R.id.scanAdd){
                editText_lockCode.setEnabled(false);
                linearLayout_scan.setVisibility(View.VISIBLE);
                scanAdd.setChecked(true);
                handAdd.setChecked(false);
            }
            else if(checkedId==R.id.handAdd){
                editText_lockCode.setEnabled(true);
                linearLayout_scan.setVisibility(View.GONE);
                scanAdd.setChecked(false);
                handAdd.setChecked(true);
            }
        }
    }

    private PopupWindow waitView;
    public void showWait(){
        View contentView= LayoutInflater.from(this).inflate(R.layout.wait,null,false);
        waitView=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT,true);
        waitView.setAnimationStyle(R.style.animation);
        waitView.setOutsideTouchable(true);
        waitView.showAtLocation(backOnAddLock, Gravity.CENTER,0,0);
    }

    public void showPopupWindowExceptionAlert(String alreadyPhoneNumber){
        View view=View.inflate(AddLockActivity.this,R.layout.exception_alert,null);
        TextView confirm=(TextView)view.findViewById(R.id.confirm_exception);
        TextView textView_alreadyPhoneNumber=(TextView)view.findViewById(R.id.textView_alreadyPhoneNumber);
        final PopupWindow alertWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        alertWindow.setAnimationStyle(R.style.animation);
        alertWindow.setOutsideTouchable(true);
        alertWindow.showAtLocation(backOnAddLock, Gravity.CENTER,0,0);
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

    public void addLock(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonRequest=new JSONObject();
                try {
                    jsonRequest.put("sign",12);
                    jsonRequest.put("ownerPhoneNumber",phoneNumber);
                    jsonRequest.put("lockCode",lockCode);
                    jsonRequest.put("gatewayCode",gatewayCode);
                    jsonRequest.put("timetag",new TimeUtil().getTimetag());
                    jsonRequest.put("lockName",editText_lockName.getText().toString());
                    jsonRequest.put("lockLocation",editText_lockLocation.getText().toString());
                    jsonRequest.put("lockComment",editText_lockComment.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("jsonRequest  "+jsonRequest);
                String gatewayIp=((LockApplication)getApplication()).getGatewayIp(gatewayCode);
                String consequence= HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                System.out.println("consequence  "+consequence);
                Message message=new Message();
                message.obj=consequence;
                message.what=AddLock;
                handler.sendMessage(message);
            }
        }).start();
    }


}
