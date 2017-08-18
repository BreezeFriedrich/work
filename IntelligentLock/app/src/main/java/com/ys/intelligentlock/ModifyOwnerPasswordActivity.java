package com.ys.intelligentlock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.SPUtils;
import com.ys.intelligentlock.Utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * Created by Administrator on 2016/4/19.
 */
public class ModifyOwnerPasswordActivity extends Activity implements View.OnClickListener{
    public static String APPKEY="1b8629da5b8dc";
    public static String APPSECRET="b8dc90eb5a96ea45416fbcb65115722e";

    private ImageButton backOnModifyPassword;
    private EditText editText_phoneNumber;
    private EditText editText_verificationCode;
    private Button button_getVerificationCode;
    private Button button_verificationCodeConfirm;
    private LinearLayout linearLayout_verifyCode;
    private LinearLayout resetPassword_specific;
    private EditText editText_reset_newPassword;
    private EditText editText_reset_confirmPassword;
    private Button button_resetConfirm;

    private String phoneNumber;
    private String verificationCode;
    private String newPassword;
    private String confirmPassword;
    private TimerCount timerCount;

    private Handler myHandler;
    private final int ModifyPassword=0x33;
    private final int NoNetwork=0x11;
    private final int NoOwner=0x22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password);
        backOnModifyPassword=(ImageButton)findViewById(R.id.backOnModifyPassword);
        editText_phoneNumber=(EditText)findViewById(R.id.editText_phoneNumber);
        editText_verificationCode=(EditText)findViewById(R.id.editText_verificationCode);
        button_getVerificationCode=(Button)findViewById(R.id.button_getVerificationCode);
        button_verificationCodeConfirm=(Button)findViewById(R.id.button_verificationCodeConfirm);
        resetPassword_specific=(LinearLayout)findViewById(R.id.resetPassword_specific);
        linearLayout_verifyCode=(LinearLayout)findViewById(R.id.linearLayout_verifyCode);
        editText_reset_newPassword=(EditText)findViewById(R.id.editText_reset_newPassword);
        editText_reset_confirmPassword=(EditText)findViewById(R.id.editText_reset_confirmPassword);
        button_resetConfirm=(Button)findViewById(R.id.button_resetConfirm);

        resetPassword_specific.setVisibility(View.INVISIBLE);
        button_verificationCodeConfirm.setEnabled(false);
        backOnModifyPassword.setOnClickListener(this);
        button_getVerificationCode.setOnClickListener(this);
        button_verificationCodeConfirm.setOnClickListener(this);
        button_resetConfirm.setOnClickListener(this);
        SMSSDK.initSDK(this,APPKEY,APPSECRET,true);
        EventHandler eventHandler=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg=new Message();
                msg.arg1=event;
                msg.arg2=result;
                msg.obj=data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        timerCount=new TimerCount(90000,1000);

        myHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                button_resetConfirm.setClickable(true);
                if(msg.what==NoNetwork){
                    Toast.makeText(ModifyOwnerPasswordActivity.this,"当前网络不通，请检查网络设置！",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(msg.what==NoOwner){
                    Toast.makeText(ModifyOwnerPasswordActivity.this,"不存在该用户！",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(msg.what==ModifyPassword){
                    String consequence= (String) msg.obj;
                    if(consequence==null){
                        Toast.makeText(ModifyOwnerPasswordActivity.this,"当前网络不通，请检查网络设置！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    try {
                        JSONObject jsonObject=new JSONObject(consequence);
                        int i=(int)jsonObject.get("result");
                        if(i==0){
                            //new AlertDialog.Builder(ModifyOwnerPasswordActivity.this).setTitle("注意").
                              //      setMessage("修改密码成功！").setPositiveButton("确定",null).show();
                            Toast.makeText(ModifyOwnerPasswordActivity.this,"修改密码成功！",Toast.LENGTH_LONG).show();
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    SPUtils spUtils=new SPUtils(ModifyOwnerPasswordActivity.this);
                                    spUtils.deleteLoginInfo();
                                    spUtils.putLoginInfo(phoneNumber,editText_reset_newPassword.getText().toString());
                                    finish();
                                }
                            },2000);

                        }
                        else if(i==1){
                            Toast.makeText(ModifyOwnerPasswordActivity.this,"修改密码失败！",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backOnModifyPassword:
                finish();
                break;
            case R.id.button_getVerificationCode:
                phoneNumber=editText_phoneNumber.getText().toString();
                if(!phoneNumber.equals("")){
                    if(phoneNumber.length()==11){
                        SMSSDK.getVerificationCode("86",phoneNumber);   //
                        timerCount.start();
                        button_verificationCodeConfirm.setEnabled(true);
                        editText_verificationCode.requestFocus();
                    }
                    else {
                        showToast("请输入正确的手机号码！");
                    }
                }
                else{
                    showToast("手机号码不能为空！");
                }
                break;
            case R.id.button_verificationCodeConfirm:
                verificationCode=editText_verificationCode.getText().toString();
                if(!verificationCode.equals("")){
                    SMSSDK.submitVerificationCode("86",phoneNumber,verificationCode);   //
                }
                else {
                    showToast("验证码不能为空！");
                }
                break;
            case R.id.button_resetConfirm:
                button_resetConfirm.setClickable(false);
                newPassword=editText_reset_newPassword.getText().toString();
                confirmPassword=editText_reset_confirmPassword.getText().toString();
                if (!newPassword.equals("") || !confirmPassword.equals("")){
                    if(newPassword.equals(confirmPassword)){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SPUtils spUtils=new SPUtils(ModifyOwnerPasswordActivity.this);
                                String ownerIp=spUtils.getOwnerIp(phoneNumber);
                                if(ownerIp==null){
                                    getOwnerIpFromServer(ownerIp);
                                }
                                else {
                                    modifyPasswordFromServer();
                                }
                            }
                        }).start();

                    }
                    else {
                        showToast("两次输入密码不同");
                    }
                }
                else {
                    showToast("新密码和确认密码不能为空！");
                }
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event=msg.arg1;
            int result=msg.arg2;
            Object data=msg.obj;
            Log.e("result","result"+result);
            Log.e("event", "event="+event);

            if(result== SMSSDK.RESULT_COMPLETE){
                if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    Toast.makeText(ModifyOwnerPasswordActivity.this,"验证码已经发送，请注意查收!",Toast.LENGTH_LONG).show();

                    //new AlertDialog.Builder(ModifyOwnerPasswordActivity.this).setMessage("验证码已经发送，请注意查收!").setPositiveButton("确定",null).show();
                }
                else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    Toast.makeText(ModifyOwnerPasswordActivity.this,"验证码成功!",Toast.LENGTH_LONG).show();

                   // new AlertDialog.Builder(ModifyOwnerPasswordActivity.this)
                   //         .setMessage("验证码成功!").setPositiveButton("确定",null).show();
                    resetPassword_specific.setVisibility(View.VISIBLE);
                    linearLayout_verifyCode.setVisibility(View.GONE);
                    editText_reset_newPassword.requestFocus();
                    editText_phoneNumber.setEnabled(false);
                }
            }
            else {
                Toast.makeText(ModifyOwnerPasswordActivity.this,"验证码发送失败！", Toast.LENGTH_LONG).show();
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(ModifyOwnerPasswordActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }
    };


    public void showToast(String message){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }


    class TimerCount extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimerCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            button_getVerificationCode.setTextSize(12);
            button_getVerificationCode.setText(millisUntilFinished/1000+"秒后可重新发送");
            button_getVerificationCode.setBackgroundResource(R.drawable.button_gray);
            button_getVerificationCode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            button_getVerificationCode.setEnabled(true);
            button_getVerificationCode.setBackgroundResource(R.drawable.button);
            button_getVerificationCode.setTextSize(15);
            button_getVerificationCode.setText("重新获取");
        }
    }
    public static String printHexString( byte[] b)
    {
        String bstr="";
        for (int i = 0; i < 16; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            bstr=bstr+" "+hex.toUpperCase();
        }
        return bstr;
    }

    public void getOwnerIpFromServer(String ownerIp){
        JSONObject jsonObject=new JSONObject();
        phoneNumber=editText_phoneNumber.getText().toString();
        SPUtils spUtils=new SPUtils(ModifyOwnerPasswordActivity.this);
        try {
            jsonObject.put("sign", 3);                               // get ownerIp
            jsonObject.put("ownerPhoneNumber", phoneNumber);
            System.out.println("jsonObject      " + jsonObject.toString());
            String baseIp = HttpUtil.getIp(HttpUtil.hostName);
            System.out.println("baseIp      " + baseIp);
            String consequence = HttpUtil.postData(jsonObject.toString(), baseIp);
            System.out.println("consequence      " + consequence);
            if (consequence == null) {
                spUtils.deleteOwnerIp(phoneNumber);
                Message message = new Message();
                message.what = NoNetwork;
                handler.sendMessage(message);
                return;
            }
            JSONObject jsonResponse = new JSONObject(consequence);
            int i = jsonResponse.getInt("result");
            if (i == 0) {
                ownerIp = jsonResponse.getString("ownerIp");
                if (ownerIp == null) {
                    Message message = new Message();
                    message.what = NoOwner;
                    handler.sendMessage(message);
                    return;
                }
                spUtils.putOwnerIp(phoneNumber, ownerIp);
                ((LockApplication) getApplication()).setOwnerIp(ownerIp);
                modifyPasswordFromServer();
            }
        }catch(JSONException e) {
            e.printStackTrace();
        }

        }

    public void modifyPasswordFromServer(){
        JSONObject jsonObject=new JSONObject();
        phoneNumber=editText_phoneNumber.getText().toString();
        try {
            jsonObject.put("sign",23);
            phoneNumber=editText_phoneNumber.getText().toString();
            jsonObject.put("ownerPhoneNumber",phoneNumber);
            String hexPassword=printHexString(PasswordEncrypt.encryptPassword(newPassword,16)).replaceAll(" ","");
            System.out.println("hexPassword  "+hexPassword);
            jsonObject.put("newPassword",hexPassword);

            //jsonObject.put("newPassword",new String(PasswordEncrypt.encryptPassword(newPassword,16)));
            //jsonObject.put("newPassword",newPassword);

            jsonObject.put("timetag",new TimeUtil().getTimetag());
            System.out.println("jsonObject      "+jsonObject.toString());
            String ownerIp=((LockApplication)getApplication()).getOwnerIp();
            String result= HttpUtil.postData(jsonObject.toString(),ownerIp);
            System.out.println("result      "+result);

            Message message=new Message();
            message.obj=result;
            message.what=ModifyPassword;
            myHandler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
