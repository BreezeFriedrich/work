package com.ys.intelligentlock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.SPUtils;
import com.ys.intelligentlock.Utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;


/**
 * Created by Administrator on 2016/11/29.
 */

public class RegisterActivity extends Activity {
    public static String APPKEY="1b8629da5b8dc";
    public static String APPSECRET="b8dc90eb5a96ea45416fbcb65115722e";

    private ImageButton backOnRegister;
    private Button getMobileVerificationCode;
    private Button verificationCodeConfirm;
    private Button registerConfirm;
    private EditText et_register_phoneNumber;
    private EditText et_verificationCode;

    private EditText et_register_name;
    private EditText et_register_password;
    private EditText et_confirm_password;
    private String phoneNumber;
    private String verificationCode;
    private String name;
    private String password;
    private String confirm_password;

    private LinearLayout linearLayout_verificationCode;
    private LinearLayout specificRegister;
    private TimerCount timerCount;
    private Handler myHandler;

    private final int NoNetWork=0x11;
    private final int PhoneNumberAlreadyExixt=0x22;
    private final int GetOwnerIp=0x33;
    private final int Register=0x44;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.register);
        System.out.println("register");

        backOnRegister=(ImageButton)findViewById(R.id.backOnRegister);
        et_register_phoneNumber=(EditText)findViewById(R.id.register_phoneNumber);
        et_verificationCode=(EditText)findViewById(R.id.verificationCode);
        getMobileVerificationCode=(Button)findViewById(R.id.getMobileVerificationCode);
        verificationCodeConfirm=(Button)findViewById(R.id.verificationCodeConfirm);
        registerConfirm=(Button)findViewById(R.id.register_confirm);
        et_register_name=(EditText)findViewById(R.id.register_name);
        et_register_password=((EditText)findViewById(R.id.register_password));
        et_confirm_password=(EditText)findViewById(R.id.confirm_password);
        linearLayout_verificationCode=(LinearLayout)findViewById(R.id.linearLayout_verificationCode);
        specificRegister=(LinearLayout)findViewById(R.id.specific_register);
        linearLayout_verificationCode.setVisibility(View.VISIBLE);
        specificRegister.setVisibility(View.INVISIBLE);

        verificationCodeConfirm.setEnabled(false);
        timerCount=new TimerCount(90000,1000);

        backOnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getMobileVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=et_register_phoneNumber.getText().toString();
                System.out.println("电话号码"+phoneNumber);
                if(phoneNumber.length()==11){
                    SMSSDK.getVerificationCode("86",phoneNumber);   //
                    timerCount.start();

                    verificationCodeConfirm.setEnabled(true);
                    et_verificationCode.requestFocus();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                }
            }
        });

        verificationCodeConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                verificationCode=et_verificationCode.getText().toString();
                if(!verificationCode.equals("")){
                    SMSSDK.submitVerificationCode("86",phoneNumber,verificationCode);   //
                }
                else{
                    Toast.makeText(RegisterActivity.this,"验证码不能为空", Toast.LENGTH_LONG).show();
                }
            }
        });

        registerConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWait();
                name=et_register_name.getText().toString();
                password=et_register_password.getText().toString();
                confirm_password=et_confirm_password.getText().toString();
                System.out.println(name);
                System.out.println(password);
                System.out.println(confirm_password);
                if(!name.equals("") && !password.equals("") && !confirm_password.equals("")) {
                    if(password.length()<=12 && password.length()>=4){
                        if(password.equals(confirm_password)){

                            new Thread(new Runnable() {    // 获取新分配ownerIp
                                @Override
                                public void run() {
                                    JSONObject jsonRequest=new JSONObject();
                                    try {
                                        jsonRequest.put("sign",1);
                                        jsonRequest.put("ownerPhoneNumber",phoneNumber);
                                        jsonRequest.put("timetag",new TimeUtil().getTimetag());
                                        String baseIp=HttpUtil.getIp(HttpUtil.hostName);
                                        System.out.println("jsonRequest "+jsonRequest.toString());
                                        String consequence=HttpUtil.postData(jsonRequest.toString(),baseIp);
                                        System.out.println("consequence "+consequence);

                                        if(consequence==null){
                                            Message message=new Message();
                                            message.what=NoNetWork;
                                            myHandler.sendMessage(message);
                                        }
                                        else if(consequence!=null){
                                            JSONObject jsonResponse=new JSONObject(consequence);
                                            int result=jsonResponse.getInt("result");
                                            if(result==1){
                                                Message message=new Message();
                                                message.what=PhoneNumberAlreadyExixt;
                                                myHandler.sendMessage(message);
                                            }
                                            else if(result==0){
                                                String ownerIp=jsonResponse.getString("ownerIp");
                                                JSONObject jsonObject=new JSONObject();
                                                try {
                                                    jsonObject.put("sign",2);
                                                    jsonObject.put("ownerName",name);
                                                    jsonObject.put("ownerPhoneNumber",phoneNumber);
                                                    String hexPassword=printHexString(PasswordEncrypt.encryptPassword(password,16)).replaceAll(" ","");
                                                    System.out.println("hexPassword  "+hexPassword);
                                                    //jsonRequest.put("ownerPassword",new String(PasswordEncrypt.encryptPassword(password,16)));
                                                    //jsonRequest.put("ownerPassword",password);
                                                    jsonObject.put("ownerPassword",hexPassword);

                                                    //jsonObject.put("ownerPassword",new String(PasswordEncrypt.encryptPassword(password,16)));
                                                    //jsonObject.put("newPassword",password);
                                                    jsonObject.put("timetag",new TimeUtil().getTimetag());
                                                    jsonObject.put("imei",getImei());
                                                    System.out.println("jsonRequest  "+jsonObject);

                                                    String consequenceRegister=HttpUtil.postData(jsonObject.toString(),ownerIp);
                                                    System.out.println("consequenceRegister  "+consequenceRegister);
                                                    Message message=new Message();
                                                    message.obj=consequenceRegister;
                                                    message.what=Register;
                                                    myHandler.sendMessage(message);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"两次输入密码不一致！",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"密码只能由 4到12位数字字母组成",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this,"用户名、密码、确认密码不能为空",Toast.LENGTH_LONG).show();
                }
            }
        });

        myHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(waitView!=null){
                    waitView.dismiss();
                }
                if(msg.what==NoNetWork){
                    Toast.makeText(RegisterActivity.this,"当前网络不通，请检查网络设置！",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(msg.what==PhoneNumberAlreadyExixt){
                    Toast.makeText(RegisterActivity.this,"该号码已经被注册！",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(msg.what==Register){
                    String consequence= (String) msg.obj;
                    if(consequence==null){
                        Toast.makeText(RegisterActivity.this,"当前网络不通，请检查网络设置！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    try {
                        JSONObject jsonObject=new JSONObject(consequence);
                        int i=(int)jsonObject.get("result");
                        if(i==0){
                            Toast.makeText(RegisterActivity.this,"注册成功！现转入主界面！",Toast.LENGTH_LONG).show();
                            new SPUtils(RegisterActivity.this).putLoginInfo(phoneNumber,password);     //保存用户注册信息，方便下次自动登录
                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                            intent.putExtra("ownerName",name);
                            intent.putExtra("phoneNumber",phoneNumber);
                            startActivity(intent);
                        }
                        else if(i==1){
                            Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_LONG).show();
                        }
                        else if(i==2){
                            Toast.makeText(RegisterActivity.this,"该号码已经被注册！",Toast.LENGTH_LONG).show();
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
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
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

            if(result==SMSSDK.RESULT_COMPLETE){
                if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    Toast.makeText(RegisterActivity.this,"验证码已经发送，请注意查收!",Toast.LENGTH_LONG).show();

                    //new AlertDialog.Builder(RegisterActivity.this).setMessage("验证码已经发送，请注意查收!").setPositiveButton("确定",null).show();
                }
                else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    Toast.makeText(RegisterActivity.this,"验证码成功!",Toast.LENGTH_LONG).show();

                    // new AlertDialog.Builder(RegisterActivity.this).setMessage("验证码成功!").setPositiveButton("确定",null).show();
                    linearLayout_verificationCode.setVisibility(View.GONE);
                    specificRegister.setVisibility(View.VISIBLE);
                    et_register_name.requestFocus();
                    et_register_phoneNumber.setEnabled(false);

                }
            }
            else {
                Toast.makeText(RegisterActivity.this,"请检查网络设置！",Toast.LENGTH_LONG).show();
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(RegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }
    };

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
            getMobileVerificationCode.setTextSize(12);
            getMobileVerificationCode.setText(millisUntilFinished/1000+"秒后可重新发送");
            getMobileVerificationCode.setBackgroundResource(R.drawable.button_gray);
            getMobileVerificationCode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            getMobileVerificationCode.setEnabled(true);
            getMobileVerificationCode.setBackgroundResource(R.drawable.button);
            getMobileVerificationCode.setTextSize(15);
            getMobileVerificationCode.setText("重新获取");
        }
    }

    public String getImei(){
        TelephonyManager manager=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
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

    private PopupWindow waitView;
    public void showWait(){
        View contentView= LayoutInflater.from(this).inflate(R.layout.wait,null,false);
        waitView=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT,true);
        waitView.setAnimationStyle(R.style.animation);
        waitView.setOutsideTouchable(true);
        waitView.showAtLocation(backOnRegister, Gravity.CENTER,0,0);
        //WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        // lp.alpha = 0.4f;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //this.getWindow().setAttributes(lp);
        waitView.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //   WindowManager.LayoutParams lp =getWindow().getAttributes();
                //  lp.alpha = 1f;
                //  getWindow().setAttributes(lp);
            }
        });
    }

}
