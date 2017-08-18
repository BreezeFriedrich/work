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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.CustomizedView.SwitchButton;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.SPUtils;
import com.ys.intelligentlock.Utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/1.
 */
public class SettingActivity extends Activity implements View.OnClickListener{
    private ImageButton backOnSetting;
    private LinearLayout linearLayout_modifyPassword;
    private LinearLayout linearLayout_modifyName;
    private LinearLayout linearLayout_support;
    private LinearLayout linearLayout_currentVersion;
    private LinearLayout linearLayout_modifyGesture;
    private SwitchButton switchButton_push;
    private SwitchButton switchButton_autoCheck;
    private SwitchButton switchButton_autoLogin;
    private Button button_exit;
    private SPUtils spUtils;

    private String phoneNumber;

    private Handler handler;
    private final int ModifyOwnerName=0x11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        backOnSetting=(ImageButton)findViewById(R.id.backOnSetting);
        linearLayout_modifyPassword=(LinearLayout)findViewById(R.id.linearLayout_modifyPassword);
        linearLayout_modifyName=(LinearLayout)findViewById(R.id.linearLayout_modifyName);
        linearLayout_support=(LinearLayout)findViewById(R.id.linearLayout_support);
        linearLayout_currentVersion=(LinearLayout)findViewById(R.id.linearLayout_currentVersion);
        linearLayout_modifyGesture=(LinearLayout)findViewById(R.id.linearLayout_modifyGesture);
        switchButton_push=(SwitchButton)findViewById(R.id.switchButton_push);
        switchButton_autoCheck=(SwitchButton)findViewById(R.id.switchButton_autoCheck);
        switchButton_autoLogin=(SwitchButton)findViewById(R.id.switchButton_autoLogin);
        button_exit=(Button)findViewById(R.id.button_exit);

        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==ModifyOwnerName){
                    modifyNameWindow.dismiss();
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(SettingActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            Toast.makeText(SettingActivity.this,"修改用户名成功！",Toast.LENGTH_SHORT).show();
                        }
                        else if(result==1){
                            Toast.makeText(SettingActivity.this,"修改用户名失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        backOnSetting.setOnClickListener(this);
        linearLayout_modifyPassword.setOnClickListener(this);
        linearLayout_modifyName.setOnClickListener(this);
        linearLayout_support.setOnClickListener(this);
        linearLayout_currentVersion.setOnClickListener(this);
        button_exit.setOnClickListener(this);
        linearLayout_modifyGesture.setOnClickListener(this);
        spUtils=new SPUtils(this);

        switchButton_push.setChecked(spUtils.getIsAlertPush());
        switchButton_autoCheck.setChecked(spUtils.getIsAutoCheckVersionUpdate());
        switchButton_autoLogin.setChecked(spUtils.getIsAutologin());
        MyCheckedListener checkedListener=new MyCheckedListener();
        switchButton_push.setOnCheckedChangeListener(checkedListener);
        switchButton_autoCheck.setOnCheckedChangeListener(checkedListener);
        switchButton_autoLogin.setOnCheckedChangeListener(checkedListener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backOnSetting:
                finish();
                break;
            case R.id.linearLayout_modifyPassword:
                Intent modifyIntent=new Intent(SettingActivity.this,ModifyOwnerPasswordActivity.class);
                startActivity(modifyIntent);
                break;
            case R.id.linearLayout_modifyName:
                showModifyPopupWindow();
                break;
            case R.id.linearLayout_support:
                Intent supportIntent=new Intent(SettingActivity.this,SupportActivity.class);
                startActivity(supportIntent);
                break;
            case R.id.linearLayout_currentVersion:
                break;
            case R.id.button_exit:
                new SPUtils(SettingActivity.this).deleteLoginInfo();
                Intent loginIntent=new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                ((LockApplication)getApplication()).clearActivity();
                break;
            case R.id.linearLayout_modifyGesture:
                Intent gestureIntent=new Intent(SettingActivity.this,GestureSettingActivity.class);
                gestureIntent.putExtra("phoneNumber",phoneNumber);
                startActivity(gestureIntent);
                break;
        }
    }

    class MyCheckedListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView.getId()==R.id.switchButton_push){
                spUtils.setIsAlertPush(isChecked);
            }
            else if(buttonView.getId()==R.id.switchButton_autoCheck){
                spUtils.setIsAutoCheckVersionUpdate(isChecked);
            }
            else if(buttonView.getId()==R.id.switchButton_autoLogin){
                spUtils.setIsAutoLogin(isChecked);
            }
        }
    }

    public PopupWindow modifyNameWindow;
    public void showModifyPopupWindow(){
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.modify_name,null);
        TextView cancel=(TextView)view.findViewById(R.id.cancel_modifyOwnerName);
        TextView confirm=(TextView)view.findViewById(R.id.confirm_modifyOwnerName);
        final TextView editText_newOwnerName=(TextView)view.findViewById(R.id.editText_newOwnerName);
        modifyNameWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        modifyNameWindow.setAnimationStyle(R.style.leftToRight);
        modifyNameWindow.setOutsideTouchable(true);
        modifyNameWindow.showAtLocation(backOnSetting, Gravity.CENTER,0,0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        modifyNameWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
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
                modifyNameWindow.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonRequest=new JSONObject();
                        try {
                            jsonRequest.put("sign",24);
                            jsonRequest.put("ownerPhoneNumber",phoneNumber);
                            jsonRequest.put("newName",editText_newOwnerName.getText().toString());
                            jsonRequest.put("timetag",new TimeUtil().getTimetag());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("jsonRequest  "+jsonRequest);
                        String ownerIp=((LockApplication)getApplication()).getOwnerIp();
                        System.out.println("ownerIp   "+ownerIp);
                        String consequence= HttpUtil.postData(jsonRequest.toString(),ownerIp);
                        System.out.println("consequence  "+consequence);
                        Message message=new Message();
                        message.obj=consequence;
                        message.what=ModifyOwnerName;
                        handler.sendMessage(message);

                    }
                }).start();

            }
        });

    }
}
