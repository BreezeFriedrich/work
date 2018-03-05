package com.yishu;

import com.yishu.pojo.UnlockAuthorization;
import com.yishu.service.IUnlockService;
import com.yishu.service.impl.UnlockServiceImpl;
import com.yishu.util.DateUtil;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by WindSpring on 2018/1/23.
 */
public class MAIN {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MAIN.class);
    public static void main(String[] args) throws ParseException {
//        IUnlockService unlockService=new UnlockServiceImpl();
//        UnlockAuthorization unlockAuthorization=unlockService.getUnlockAuthorization("18255683932","GWH0081702000003","LCN0011721000001");
//        System.out.println("unlockAuthorization:"+unlockAuthorization);

//        String startTime="1516690521839";
//        String endTime="1517744770921";
        String ZERO_TIME=DateUtil.yyyy_MM_dd0HH$mm$ss.format(0);
        System.out.println("ZERO_TIME:"+ZERO_TIME);
        long startTag=0;
        long endTag=0;

        String startTime="20180103111606";
        String timestamp="20180105120000";
        String endTime  ="20180107152556";

        long startTimeL=DateUtil.yyyyMMddHHmmss.parse(startTime).getTime();
        long endTimeL= DateUtil.yyyyMMddHHmmss.parse(endTime).getTime();
        long timestampL= DateUtil.yyyyMMddHHmmss.parse(timestamp).getTime();

        System.out.println("startTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(startTimeL));
        System.out.println("endTime  :"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(endTimeL));
        int boundary= (int) (timestampL%86400000);
        System.out.println("timestamp-取余数-boundary:"+boundary);

        int start_remainder= (int) (startTimeL%86400000);
        System.out.println("start_remainder:"+start_remainder);
        int start_round= (int) (startTimeL/86400000);
        System.out.println("start_round:"+start_round);
        if (start_remainder>=boundary){
            startTag=start_round*1L*86400000+boundary;
        }else {
            startTag=(start_round-1)*1L*86400000+boundary;
        }
        System.out.println("startTag:"+startTag);
        System.out.println("startTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(startTag));

        int end_remainder= (int) (endTimeL%86400000);
        System.out.println("end_remainder:"+end_remainder);
        int end_round= (int) (endTimeL/86400000);
        System.out.println("end_round:"+end_round);
        if (end_remainder<=boundary){
            endTag=end_round*1L*86400000+boundary;
        }else {
            endTag=(end_round+1)*1L*86400000+boundary;
        }
        System.out.println("endTag:"+endTag);
        System.out.println("endTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(endTag));
        int size= (int) ((endTag-startTag)/86400000);
        System.out.println("size:"+size);
    }
}
