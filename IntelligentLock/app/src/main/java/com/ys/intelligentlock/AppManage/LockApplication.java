package com.ys.intelligentlock.AppManage;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import com.ys.intelligentlock.JsonData.GatewayIpData;
import com.ys.intelligentlock.Utils.SPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/1/19.
 */

public class LockApplication extends Application {

    public String ownerIp;
    private List<GatewayIpData.GatewayIpInfo> gatewayIpList;
    private Service lockService;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setOwnerIp(String ownerIp){
        this.ownerIp=ownerIp;
    }

    public String getOwnerIp(){
        return ownerIp;
    }

    public void setGatewayIpList(List<GatewayIpData.GatewayIpInfo> gatewayIpList){
        this.gatewayIpList=gatewayIpList;
    }

    public List<GatewayIpData.GatewayIpInfo> getGatewayIpList(){
        return gatewayIpList;
    }

    public String getGatewayIp(String gatewayCode){
        for(GatewayIpData.GatewayIpInfo gatewayIpInfo:gatewayIpList){
            if(gatewayIpInfo.gatewayCode.equals(gatewayCode)){
                return gatewayIpInfo.gatewayIp;
            }
        }
        return null;
    }

    public List<Activity> activityList=new ArrayList<>();

    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    public void clearActivity(){
        for(Activity activity:activityList){
            if(activity!=null){
                activity.finish();
            }
        }
    }

    public void addService(Service service){
        lockService=service;
    }

    public void stopService(){
        lockService.stopSelf();
    }

    public void exitApp(){
        clearActivity();
        stopService();
        System.exit(0);
    }
}
