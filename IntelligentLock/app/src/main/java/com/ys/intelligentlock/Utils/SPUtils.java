package com.ys.intelligentlock.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/4/15.
 */
public class SPUtils {
    private Context context;

    public SPUtils(Context context) {
        this.context = context;
    }

    public void putOwnerIp(String phoneNumber,String Ip){
        SharedPreferences.Editor editor=context.getSharedPreferences("OwnerIpSave", Context.MODE_PRIVATE).edit();
        editor.putString(phoneNumber,Ip);
        editor.commit();
    }

    public String getOwnerIp(String phoneNumber){
        SharedPreferences sharedPreferences=context.getSharedPreferences("OwnerIpSave", Context.MODE_PRIVATE);
        return sharedPreferences.getString(phoneNumber,null);
    }

    public void deleteOwnerIp(String phoneNumber){
        SharedPreferences.Editor editor=context.getSharedPreferences("OwnerIpSave", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }


//put person user login information
    public void putLoginInfo(String phoneNumber, String password){
        SharedPreferences.Editor editor=context.getSharedPreferences("loginSave", Context.MODE_PRIVATE).edit();
        editor.putString("phoneNumber",phoneNumber);
        editor.putString("password",password);
        editor.commit();
    }

    public String getLoginPhoneNumber(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("loginSave", Context.MODE_PRIVATE);
        return sharedPreferences.getString("phoneNumber","");
    }

    public String getLoginPassword(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("loginSave", Context.MODE_PRIVATE);
        return sharedPreferences.getString("password","");
    }

    public void deleteLoginInfo(){
        SharedPreferences.Editor editor=context.getSharedPreferences("loginSave", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    public void putAutoLoginTime(String autoLoginTime){
        SharedPreferences.Editor editor=context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE).edit();
        editor.putString("autoLoginTime",autoLoginTime);
        editor.commit();
    }

    public String getAutoLoginTime(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        return sharedPreferences.getString("autoLoginTime","");
    }

    public void deleteAutoLoginTime(){
        SharedPreferences.Editor editor=context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    public void setIsAlertPush(boolean b){
        SharedPreferences.Editor editor=context.getSharedPreferences("IsAlertPush", Context.MODE_PRIVATE).edit();
        editor.putBoolean("IsAlertPush",b);
        editor.commit();
    }

    public boolean getIsAlertPush(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("IsAlertPush", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsAlertPush",true);
    }


    public void setIsAutoLogin(boolean b){
        SharedPreferences.Editor editor=context.getSharedPreferences("IsAutoLogin", Context.MODE_PRIVATE).edit();
        editor.putBoolean("IsAutoLogin",b);
        editor.commit();
    }

    public boolean getIsAutologin(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("IsAutoLogin", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsAutoLogin",true);
    }

    public void setIsAutoCheckVersionUpdate(boolean b){
        SharedPreferences.Editor editor=context.getSharedPreferences("autoCheckVersionUpdate", Context.MODE_PRIVATE).edit();
        editor.putBoolean("IsAutoCheckVersionUpdate",b);
        editor.commit();
    }

    public boolean getIsAutoCheckVersionUpdate(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("autoCheckVersionUpdate", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsAutoCheckVersionUpdate",true);
    }


    public void saveGesturePassword(String gesturePassword){
        SharedPreferences.Editor editor=context.getSharedPreferences("gesturePasswordInfo", Context.MODE_PRIVATE).edit();
        editor.putString("gesturePassword",gesturePassword);
        editor.commit();
    }

    public String getGesturePassword(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("gesturePasswordInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("gesturePassword","");
    }

}