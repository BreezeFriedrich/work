package com.ys.intelligentlock.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by admin on 2017/1/11.
 */

public class TimeUtil {

    public String getTimetag(){
        Calendar now=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(now.getTimeInMillis());
    }

    // servicenumb 32 =timetag 14    phoneNumber 11   random  7
    public String getServiceNumb(String phoneNumber){
        Random random=new Random();
        int randomInt=random.nextInt(9999999);
        String serviceNumb=getTimetag()+phoneNumber+String.valueOf(randomInt);
        if(serviceNumb.length()>32){
            return serviceNumb.substring(0,32);
        }
        else if(serviceNumb.length()<32){
            int x=32-serviceNumb.length();
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<x;i++){
                stringBuffer.append("0");
            }
            return serviceNumb+stringBuffer.toString();
        }
        return serviceNumb;
    }


}
