package com.ys.intelligentlock;

import android.app.Activity;
import android.content.Intent;
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
import com.ys.intelligentlock.Utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * Created by admin on 2017/7/25.
 */

public class SMSVerificationActivity extends Activity implements View.OnClickListener{
    public static String APPKEY="1b8629da5b8dc";
    public static String APPSECRET="b8dc90eb5a96ea45416fbcb65115722e";
    private ImageButton backOnModifyPassword;
    private EditText editText_phoneNumber;
    private EditText editText_verificationCode;
    private Button button_getVerificationCode;
    private Button button_verificationCodeConfirm;
    private TimerCount timerCount;
    private String phoneNumber;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_verification);
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        backOnModifyPassword = (ImageButton) findViewById(R.id.backOnSmsVerification);
        editText_phoneNumber = (EditText) findViewById(R.id.editText_phoneNumber);
        editText_verificationCode = (EditText) findViewById(R.id.editText_verificationCode);
        button_getVerificationCode = (Button) findViewById(R.id.button_getVerificationCode);
        button_verificationCodeConfirm = (Button) findViewById(R.id.button_verificationCodeConfirm);
        button_verificationCodeConfirm.setEnabled(false);
        backOnModifyPassword.setOnClickListener(this);
        button_getVerificationCode.setOnClickListener(this);
        button_verificationCodeConfirm.setOnClickListener(this);

        SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        timerCount = new TimerCount(90000, 1000);

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
                    Toast.makeText(SMSVerificationActivity.this,"验证码已经发送，请注意查收!",Toast.LENGTH_LONG).show();

                    //new AlertDialog.Builder(ModifyOwnerPasswordActivity.this).setMessage("验证码已经发送，请注意查收!").setPositiveButton("确定",null).show();
                }
                else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    Toast.makeText(SMSVerificationActivity.this,"验证码成功!",Toast.LENGTH_LONG).show();

                    // new AlertDialog.Builder(ModifyOwnerPasswordActivity.this)
                    //         .setMessage("验证码成功!").setPositiveButton("确定",null).show();
                    editText_phoneNumber.setEnabled(false);
                    new SPUtils(SMSVerificationActivity.this).saveGesturePassword("");
                    Intent intent=new Intent(SMSVerificationActivity.this,GestureSettingActivity.class);
                    intent.putExtra("phoneNumber",phoneNumber);
                    startActivity(intent);
                    finish();
                }
            }
            else {
                Toast.makeText(SMSVerificationActivity.this,"验证码发送失败！", Toast.LENGTH_LONG).show();
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(SMSVerificationActivity.this, des, Toast.LENGTH_SHORT).show();
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
}
