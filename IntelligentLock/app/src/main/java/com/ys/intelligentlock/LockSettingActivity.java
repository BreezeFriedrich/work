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

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016/12/26.
 */

public class LockSettingActivity extends Activity {
    private ImageButton backOnLockSetting;
    private EditText editText_modifyLockName;
    private EditText editText_modifyLockLocation;
    private EditText editText_modifyLockComment;
    private Button saveLockModify;
    private Button deleteLock;

    private String gatewayCode;
    private String lockName;
    private String lockLocation;
    private String lockComment;
    private String lockCode;
    private String phoneNumber;

    private Handler handler;
    private final int ModifyLock=0x11;
    private final int DeleteLock=0x22;
    private final int ModifyLockSuccess=0x33;
    private final int DeleteLockSuccess=0x44;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_setting);
        backOnLockSetting=(ImageButton)findViewById(R.id.backOnLockSetting);
        backOnLockSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText_modifyLockName=(EditText)findViewById(R.id.editText_modifyLockName);
        editText_modifyLockLocation=(EditText)findViewById(R.id.editText_modifyLockLocation);
        editText_modifyLockComment=(EditText)findViewById(R.id.editText_modifyLockComment);
        saveLockModify=(Button)findViewById(R.id.saveLockModify);
        deleteLock=(Button)findViewById(R.id.deleteLock);

        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("phoneNumber");
        gatewayCode=intent.getStringExtra("gatewayCode");
        lockName=intent.getStringExtra("lockName");
        lockLocation=intent.getStringExtra("lockLocation");
        lockComment=intent.getStringExtra("lockComment");
        lockCode=intent.getStringExtra("lockCode");

        editText_modifyLockName.setText(lockName);
        editText_modifyLockLocation.setText(lockLocation);
        editText_modifyLockComment.setText(lockComment);


        saveLockModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLockModify.setClickable(false);
                lockName=editText_modifyLockName.getText().toString();
                lockLocation=editText_modifyLockLocation.getText().toString();
                lockComment=editText_modifyLockComment.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonRequest=new JSONObject();
                        try {
                            jsonRequest.put("sign",13);
                            jsonRequest.put("ownerPhoneNumber",phoneNumber);
                            jsonRequest.put("lockCode",lockCode);
                            jsonRequest.put("lockName",lockName);
                            jsonRequest.put("lockLocation",lockLocation);
                            jsonRequest.put("lockComment",lockComment);
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
                        message.what=ModifyLock;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        deleteLock.setOnClickListener(new View.OnClickListener() {
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
                if(msg.what==ModifyLock){
                    saveLockModify.setClickable(true);
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(LockSettingActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            Toast.makeText(LockSettingActivity.this,"修改门锁成功！",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            intent.putExtra("lockName",lockName);
                            intent.putExtra("lockLocation",lockLocation);
                            intent.putExtra("lockComment",lockComment);
                            setResult(ModifyLockSuccess,intent);
                            finish();
                        }
                        else if(result==1){
                            Toast.makeText(LockSettingActivity.this,"修改门锁失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(msg.what==DeleteLock){
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(LockSettingActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            Toast.makeText(LockSettingActivity.this,"删除门锁成功！",Toast.LENGTH_SHORT).show();
                            setResult(DeleteLockSuccess);
                            finish();
                        }
                        else if(result==1){
                            Toast.makeText(LockSettingActivity.this,"删除门锁失败！",Toast.LENGTH_SHORT).show();
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
        final TextView confirm=(TextView)view.findViewById(R.id.deleteAuthorization);
        TextView alert_message=(TextView)view.findViewById(R.id.alert_message);
        if(order==1){
            alert_message.setText("删除门锁将导致该门锁所联结的开锁授权、门锁密码全部失效！");
        }
        else if(order==2){
            alert_message.setText("请再次确认！");
        }
        final PopupWindow alertWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        alertWindow.setAnimationStyle(R.style.leftToRight);
        alertWindow.setOutsideTouchable(true);
        alertWindow.showAtLocation(backOnLockSetting, Gravity.CENTER,0,0);
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
                    confirm.setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonRequest=new JSONObject();
                            try {
                                jsonRequest.put("sign",14);
                                jsonRequest.put("ownerPhoneNumber",phoneNumber);
                                jsonRequest.put("lockCode",lockCode);
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
                            message.what=DeleteLock;
                            handler.sendMessage(message);
                        }
                    }).start();

                }
            }
        });
    }

}

