package com.ys.intelligentlock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.SPUtils;
import com.ys.intelligentlock.Utils.Tea;
import com.ys.intelligentlock.Utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/11/24.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText editText_phoneNumber;
    private EditText editText_password;
    private Button button_login;
    private Button button_register;
    private TextView textView_forgetPassword;
    private String phoneNumber;
    private String password;
    public Handler handler;

    private TextView textView_autoLoginNotify;
    private final int NoNetwork=0x11;
    private final int NoOwner=0x22;
    private final int JudgeOwner=0x33;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        editText_phoneNumber=(EditText)findViewById(R.id.editText_phoneNumber);
        editText_password=(EditText)findViewById(R.id.editText_password);
        button_login=(Button) findViewById(R.id.button_login);
        button_register=(Button)findViewById(R.id.button_register);
        textView_forgetPassword=(TextView) findViewById(R.id.textView_forgetPassword);
        textView_autoLoginNotify=(TextView)findViewById(R.id.textView_autoLoginNotify);
        textView_autoLoginNotify.setVisibility(View.GONE);
        button_login.setOnClickListener(this);
        button_register.setOnClickListener(this);
        textView_forgetPassword.setOnClickListener(this);
        editText_phoneNumber.setText(new SPUtils(this).getLoginPhoneNumber());
        if(!isNetworkAvailable()){
            new AlertDialog.Builder(this).setTitle("注意").setMessage("当前网络未连接！").setPositiveButton("确定",null).show();
        }
        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean otherLogin=getIntent().getBooleanExtra("otherLogin",false);
        if(otherLogin){
            new AlertDialog.Builder(this).setTitle("注意").setMessage("该账号已经在其他设备设备登录！").setPositiveButton("确定",null).show();
        }
        else{
            if(new SPUtils(this).getIsAutologin()){
                autoLogin();
            }
        }
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                textView_autoLoginNotify.setVisibility(View.GONE);
                if(waitView!=null){
                    waitView.dismiss();
                }
                if(msg.what==NoNetwork){
                    Toast.makeText(LoginActivity.this,"当前网络故障！",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(msg.what==NoOwner){
                    Toast.makeText(LoginActivity.this,"不存在该用户！",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(msg.what==JudgeOwner){
                    String result= (String) msg.obj;
                    if(result==null){
                        Toast.makeText(LoginActivity.this,"当前网络不通，请检查网络设置！ ",Toast.LENGTH_LONG).show();
                        return;
                    }
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        int i=(int)jsonObject.get("result");
                        if(i==0){
                            new SPUtils(LoginActivity.this).putLoginInfo(phoneNumber,password);     //保存用户注册信息，方便下次自动登录
                            String ownerName=(String)jsonObject.get("ownerName");
                            String imei=(String)jsonObject.get("imei");
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("phoneNumber",phoneNumber);
                            intent.putExtra("ownerName",ownerName);
                            intent.putExtra("imei",imei);
                            startActivity(intent);
                            finish();
                        } 
                        else if(i==1){
                            Toast.makeText(LoginActivity.this,"登录失败！用户名密码错误！",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                phoneNumber=editText_phoneNumber.getText().toString();
                password=editText_password.getText().toString();
                if(phoneNumber.equals("")){
                    showToast("手机号码不能为空！");
                    return;
                }
                if(phoneNumber.length()!=11){
                    showToast("请输入正确的手机号！");
                    return;
                }
                if(password.equals("")){
                    showToast("密码不能为空!");
                    return;
                }
                if(password.length()>12 || password.length()<4){
                    showToast("密码只能由4到12位数字字母组成！");
                    return;
                }
                showWait();
                login();
                break;
            case R.id.button_register:
                Intent registerIntent=new Intent(this,RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.textView_forgetPassword:
                Intent forgetPasswordIntent=new Intent(this,ModifyOwnerPasswordActivity.class);
                startActivity(forgetPasswordIntent);
                break;
            default:
                break;
        }
    }

    public void showToast(String toast){
        Toast.makeText(LoginActivity.this,toast,Toast.LENGTH_SHORT).show();
    }

    //check is network available
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null){
            return networkInfo.isAvailable();
        }
        else{
            return false;
        }
    }

    private PopupWindow waitView;
    public void showWait(){
        View contentView= LayoutInflater.from(this).inflate(R.layout.wait,null,false);
        waitView=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT,true);
        waitView.setAnimationStyle(R.style.animation);
        waitView.setOutsideTouchable(true);
        waitView.showAtLocation(button_login, Gravity.CENTER,0,0);
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

    public String getImei(){
        TelephonyManager manager=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public void autoLogin(){
        SPUtils spUtils=new SPUtils(LoginActivity.this);
        phoneNumber=spUtils.getLoginPhoneNumber();
        password=spUtils.getLoginPassword();
        if(phoneNumber.equals("") || password.equals("")){
            return;
        }
        textView_autoLoginNotify.setVisibility(View.VISIBLE);
        login();
    }

    public void login(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SPUtils spUtils=new SPUtils(LoginActivity.this);
                String ownerIp=spUtils.getOwnerIp(phoneNumber);

                if(ownerIp==null){
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("sign",3);                               // get ownerIp
                        jsonObject.put("ownerPhoneNumber",phoneNumber);
                        System.out.println("jsonObject      "+jsonObject.toString());
                        String baseIp=HttpUtil.getIp(HttpUtil.hostName);
                        System.out.println("baseIp      "+baseIp);
                        String consequence=HttpUtil.postData(jsonObject.toString(),baseIp);
                        System.out.println("consequence      "+consequence);
                        if(consequence==null){
                            spUtils.deleteOwnerIp(phoneNumber);
                            Message message=new Message();
                            message.what=NoNetwork;
                            handler.sendMessage(message);
                            return;
                        }
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int i=jsonResponse.getInt("result");
                        if(i==0){
                            ownerIp=jsonResponse.getString("ownerIp");
                            if(ownerIp==null){
                                Message message=new Message();
                                message.what=NoOwner;
                                handler.sendMessage(message);
                                return;
                            }
                            spUtils.putOwnerIp(phoneNumber,ownerIp);
                            ((LockApplication)getApplication()).setOwnerIp(ownerIp);

                            JSONObject jsonRequest=new JSONObject();
                            try {
                                jsonRequest.put("sign",4);
                                jsonRequest.put("ownerPhoneNumber",phoneNumber);
                                String hexPassword=printHexString(PasswordEncrypt.encryptPassword(password,16)).replaceAll(" ","");
                                System.out.println("hexPassword  "+hexPassword);
                                //jsonRequest.put("ownerPassword",new String(PasswordEncrypt.encryptPassword(password,16)));
                                //jsonRequest.put("ownerPassword",password);
                                jsonRequest.put("ownerPassword",hexPassword);
                                jsonRequest.put("imei",getImei());
                                jsonRequest.put("timetag",new TimeUtil().getTimetag());
                                System.out.println("jsonRequest      "+jsonRequest.toString());

                                String result=HttpUtil.postData(jsonRequest.toString(),ownerIp);
                                System.out.println("result      "+result);

                                Message message=new Message();
                                message.obj=result;
                                message.what=JudgeOwner;
                                handler.sendMessage(message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else if(i==1){
                            Message message=new Message();
                            message.what=NoOwner;
                            handler.sendMessage(message);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    ((LockApplication)getApplication()).setOwnerIp(ownerIp);

                    JSONObject jsonRequest=new JSONObject();
                    try {
                        jsonRequest.put("sign",4);
                        jsonRequest.put("ownerPhoneNumber",phoneNumber);
                        String hexPassword=printHexString(PasswordEncrypt.encryptPassword(password,16)).replaceAll(" ","");
                        System.out.println("hexPassword  "+hexPassword);
                        //jsonRequest.put("ownerPassword",new String(PasswordEncrypt.encryptPassword(password,16)));
                        //jsonRequest.put("ownerPassword",password);
                        jsonRequest.put("ownerPassword",hexPassword);
                        jsonRequest.put("imei",getImei());
                        jsonRequest.put("timetag",new TimeUtil().getTimetag());
                        System.out.println("jsonRequest      "+jsonRequest.toString());

                        System.out.println("ownerIp     "+ownerIp);
                        String result=HttpUtil.postData(jsonRequest.toString(),ownerIp);
                        System.out.println("result      "+result);

                        if(result==null){
                            spUtils.deleteOwnerIp(phoneNumber);
                            login();
                            return;
                        }
                        Message message=new Message();
                        message.obj=result;
                        message.what=JudgeOwner;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.left_in, R.anim.right_out);
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

}
