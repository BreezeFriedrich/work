package com.ys.intelligentlock.Service;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.LoginActivity;
import com.ys.intelligentlock.Utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by admin on 2017/1/22.
 */

public class LockService extends Service {
    private String phoneNumber;
    private String imei;
    private boolean isConnectServer;

    @Override
    public void onCreate() {
        super.onCreate();
        ((LockApplication)getApplication()).addService(this);
        isConnectServer=true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        phoneNumber=intent.getStringExtra("phoneNumber");
        imei=intent.getStringExtra("imei");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("start");
                if(isConnectServer){
                    JSONObject jsonRequest=new JSONObject();
                    try {
                        jsonRequest.put("sign",25);
                        jsonRequest.put("ownerPhoneNumber",phoneNumber);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("jsonRequest  "+jsonRequest);
                    String ownerIp=((LockApplication)getApplication()).getOwnerIp();
                    String consequence= HttpUtil.postData(jsonRequest.toString(),ownerIp);

                    System.out.println("consequence  "+consequence);
                    if(consequence!=null){
                        try {
                            JSONObject jsonResponse=new JSONObject(consequence);
                            int result=jsonResponse.getInt("result");
                            if(result==0){
                                String imeiFromServer=jsonResponse.getString("imei");
                                System.out.println("imeiFromServer"+imeiFromServer);
                                System.out.println("imei"+imei);

                                System.out.println(imeiFromServer.equals(imei));
                                if(!(imeiFromServer.equals(imei))){   // 被其他设备登录
                                    Intent loginIntent=new Intent(LockService.this,LoginActivity.class);
                                    loginIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                    boolean otherLogin=true;
                                    loginIntent.putExtra("otherLogin",otherLogin);
                                    startActivity(loginIntent);
                                    isConnectServer=false;
                                    ((LockApplication)getApplication()).clearActivity();
                                    stopSelf();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },10000,60000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
