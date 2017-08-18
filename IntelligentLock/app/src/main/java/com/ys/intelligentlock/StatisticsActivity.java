package com.ys.intelligentlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ys.intelligentlock.CustomizedView.WheelView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.JsonData.GatewayIpData;
import com.ys.intelligentlock.JsonData.RecordData;
import com.ys.intelligentlock.Utils.DataBaseUtil;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.TimeUtil;

/**
 * Created by Administrator on 2016/12/1.
 */
public class StatisticsActivity extends Activity implements View.OnClickListener{
    private ImageButton backOnStatistics;
    private LinearLayout linearLayout_statisticsStartTime;
    private LinearLayout linearLayout_statisticsEndTime;
    private TextView textView_statisticsStartTime;
    private TextView textView_statisticsEndTime;
    private Button button_query;
    private Handler handler;

    private String phoneNumber;
    private List<GatewayIpData.GatewayIpInfo> gatewayIpList;

    private final int GetRecordData=0x11;
    private final int NoNetWork=0x22;
    private final int GetGatewayIpFiled=0x33;
    private final int GetRecordDataFinish=0x44;

    private RecordData recordData;
    private List<RecordData.RecordInfo> recordList;
    public boolean connectServerAgain=true;  // if get Data from Server failed,delete ip and try again
    public boolean netWork=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        backOnStatistics=(ImageButton)findViewById(R.id.backOnStatistics);
        linearLayout_statisticsStartTime=(LinearLayout)findViewById(R.id.linearLayout_statisticsStartTime);
        linearLayout_statisticsEndTime=(LinearLayout)findViewById(R.id.linearLayout_statisticsEndTime);
        textView_statisticsStartTime=(TextView)findViewById(R.id.textView_statisticsStartTime);
        textView_statisticsEndTime=(TextView)findViewById(R.id.textView_statisticsEndTime);
        button_query=(Button)findViewById(R.id.button_query);

        ((LockApplication)getApplication()).addActivity(this);
        Calendar now=Calendar.getInstance();
        textView_statisticsEndTime.setText(now.get(Calendar.YEAR)+"年"+(now.get(Calendar.MONTH)+1)+"月"+now.get(Calendar.DAY_OF_MONTH)+"日"+
                now.get(Calendar.HOUR_OF_DAY)+"时"+now.get(Calendar.MINUTE)+"分");
        //Date start=new Date(now.getTimeInMillis()-7*24*3600*1000);
        //now.setTime(start);
        now.add(Calendar.DAY_OF_MONTH, -7);
        textView_statisticsStartTime.setText(now.get(Calendar.YEAR)+"年"+(now.get(Calendar.MONTH)+1)+"月"+now.get(Calendar.DAY_OF_MONTH)+"日"+
                now.get(Calendar.HOUR_OF_DAY)+"时"+now.get(Calendar.MINUTE)+"分");

    }

    @Override
    protected void onResume() {
        super.onResume();
        recordList=new ArrayList<>();
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        backOnStatistics.setOnClickListener(this);
        linearLayout_statisticsStartTime.setOnClickListener(this);
        linearLayout_statisticsEndTime.setOnClickListener(this);
        button_query.setOnClickListener(this);

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    wheelStartDay.invalidate();
                }
                else if(msg.what==NoNetWork){
                    waitView.dismiss();
                    Toast.makeText(StatisticsActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(msg.what==GetGatewayIpFiled){
                    waitView.dismiss();
                    Toast.makeText(StatisticsActivity.this,"获取数据失败！",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(msg.what==GetRecordData){
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        waitView.dismiss();
                        if(connectServerAgain){
                            connectServerAgain=false;
                            DataBaseUtil dataBaseUtil=new DataBaseUtil(StatisticsActivity.this);
                            dataBaseUtil.deleteAllGatewayIp();
                            getRecordDataFromServer();
                            return;
                        }
                        else{
                            Toast.makeText(StatisticsActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                            netWork=false;
                            return;
                        }
                    }
                    Gson gson=new Gson();
                    recordData=gson.fromJson(consequence,RecordData.class);
                    if(recordData.result==0){
                        if(recordData.recordList!=null){
                            recordList.addAll(recordData.recordList);
                        }
                    }
                    else if(recordData.result==1){
                        waitView.dismiss();
                        Toast.makeText(StatisticsActivity.this,"获取设备数据失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else if(msg.what==GetRecordDataFinish){
                    waitView.dismiss();
                    if(!netWork){
                        return;
                    }
                    if(recordList.size()==0){
                        Toast.makeText(StatisticsActivity.this,"当前时间段内无开锁记录！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent=new Intent(StatisticsActivity.this,RecordDetailActivity.class);
                    RecordData recordDataTotal=new RecordData();
                    recordDataTotal.recordList=recordList;
                    intent.putExtra("recordDataTotal",recordDataTotal);
                    intent.putExtra("startTime",textView_statisticsStartTime.getText().toString());
                    intent.putExtra("endTime",textView_statisticsEndTime.getText().toString());
                    startActivity(intent);
                }

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backOnStatistics:
                finish();
                break;
            case R.id.linearLayout_statisticsStartTime:
                showPopupWindow(v,textView_statisticsStartTime,textView_statisticsEndTime);
                break;
            case R.id.linearLayout_statisticsEndTime:
                showPopupWindow(v,textView_statisticsStartTime,textView_statisticsEndTime);
                break;
            case R.id.button_query:
                if(textView_statisticsStartTime.getText().toString().equals("") || textView_statisticsEndTime.getText().toString().equals("")){
                    Toast.makeText(StatisticsActivity.this,"授权起始时间与授权结束时间不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                String startTime=formatting(textView_statisticsStartTime.getText().toString());
                String endTime=formatting(textView_statisticsEndTime.getText().toString());
                System.out.println("s7"+textView_statisticsEndTime.getText().toString());
                System.out.println("sss"+formatting(textView_statisticsStartTime.getText().toString()));

                if(Long.valueOf(startTime) >=Long.valueOf(endTime)){
                    Toast.makeText(StatisticsActivity.this,"授权起始时间不能晚于授权结束时间！",Toast.LENGTH_LONG).show();
                    return;
                }
                getRecordDataFromServer();
                break;
        }
    }

    public void getRecordDataFromServer(){
        showWait();
        new Thread(new Runnable() {
            @Override
            public void run() {
                gatewayIpList=new DataBaseUtil(StatisticsActivity.this).getAllGatewayIp(phoneNumber);

                if(gatewayIpList.size()==0){                  // 获取gatewayIp
                    JSONObject jsonGetGatewayIp=new JSONObject();
                    try {
                        jsonGetGatewayIp.put("sign",15);
                        jsonGetGatewayIp.put("ownerPhoneNumber",phoneNumber);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("jsonGetGatewayIp  "+jsonGetGatewayIp);
                    String ownerIp=((LockApplication)getApplication()).getOwnerIp();
                    String consequence= HttpUtil.postData(jsonGetGatewayIp.toString(),ownerIp);
                    System.out.println("consequence  "+consequence);
                    if(consequence==null){
                        Message message=new Message();
                        message.what=NoNetWork;
                        handler.sendMessage(message);
                        return;
                    }
                    Gson gson=new Gson();
                    GatewayIpData gatewayIpData=gson.fromJson(consequence, GatewayIpData.class);
                    int result=gatewayIpData.result;
                    if(result==1){
                        Message message=new Message();
                        message.what=GetGatewayIpFiled;
                        handler.sendMessage(message);
                    }
                    else if(result==0){
                        gatewayIpList=gatewayIpData.gatewayIpList;
                        DataBaseUtil dataBaseUtil=new DataBaseUtil(StatisticsActivity.this);
                        dataBaseUtil.putAllGatewayIp(phoneNumber,gatewayIpList);
                        ((LockApplication)getApplication()).setGatewayIpList(gatewayIpList);

                        List<String> noRepeatGatewayIpList=new ArrayList<>();
                        for(GatewayIpData.GatewayIpInfo gatewayIpInfo:gatewayIpList){
                            if(!noRepeatGatewayIpList.contains(gatewayIpInfo.gatewayIp)){
                                noRepeatGatewayIpList.add(gatewayIpInfo.gatewayIp);
                            }
                        }
                        System.out.println("noRepeatGatewayIpList  "+noRepeatGatewayIpList);

                        for(String gatewayIp:noRepeatGatewayIpList){
                            JSONObject jsonRequest=new JSONObject();
                            try {
                                String startTime=formatting(textView_statisticsStartTime.getText().toString())+"00";
                                String endTime=formatting(textView_statisticsEndTime.getText().toString())+"59";
                                jsonRequest.put("sign",26);
                                jsonRequest.put("ownerPhoneNumber",phoneNumber);
                                jsonRequest.put("startTime",startTime);
                                jsonRequest.put("endTime",endTime);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("jsonRequest  "+jsonRequest);

                            String consequenceGetDeviceData=HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                            System.out.println("consequenceGetDeviceData  "+consequenceGetDeviceData);
                            Message message=new Message();
                            message.obj=consequenceGetDeviceData;
                            message.what=GetRecordData;
                            handler.sendMessage(message);
                        }
                        Message message=new Message();
                        message.what=GetRecordDataFinish;
                        handler.sendMessage(message);
                    }
                }
                else{
                    ((LockApplication)getApplication()).setGatewayIpList(gatewayIpList);
                    List<String> noRepeatGatewayIpList=new ArrayList<>();
                    for(GatewayIpData.GatewayIpInfo gatewayIpInfo:gatewayIpList){
                        if(!noRepeatGatewayIpList.contains(gatewayIpInfo.gatewayIp)){
                            noRepeatGatewayIpList.add(gatewayIpInfo.gatewayIp);
                        }
                    }
                    System.out.println("noRepeatGatewayIpList2  "+noRepeatGatewayIpList);

                    for(String gatewayIp:noRepeatGatewayIpList){
                        JSONObject jsonRequest=new JSONObject();
                        try {
                            String startTime=formatting(textView_statisticsStartTime.getText().toString())+"00";
                            String endTime=formatting(textView_statisticsEndTime.getText().toString())+"59";
                            System.out.println("startTime     "+startTime);
                            System.out.println("endTime     "+endTime);

                            jsonRequest.put("sign",26);
                            jsonRequest.put("ownerPhoneNumber",phoneNumber);
                            jsonRequest.put("startTime",startTime);
                            jsonRequest.put("endTime",endTime);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("jsonRequest  "+jsonRequest);

                        String consequence=HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                        System.out.println("consequence  "+consequence);
                        Message message=new Message();
                        message.obj=consequence;
                        message.what=GetRecordData;
                        handler.sendMessage(message);
                    }
                    Message message=new Message();
                    message.what=GetRecordDataFinish;
                    handler.sendMessage(message);
                }


            }
        }).start();
    }

    private WheelView wheelStartYear;
    private WheelView wheelStartMonth;
    private WheelView wheelStartDay;

    private void showPopupWindow(final View view, final TextView startView, final TextView endView){
        View contentView= LayoutInflater.from(this).inflate(R.layout.start_data_time_picker,null,false);
        final PopupWindow popupWindow=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.animation);

        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(backOnStatistics, Gravity.BOTTOM,0,0);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.3f;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp =getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        final TextView timePickerTitle=(TextView)contentView.findViewById(R.id.timePickerTitle);
        ImageButton cancel_startTime=(ImageButton)contentView.findViewById(R.id.cancel_startTime);
        ImageButton confirm_startTime=(ImageButton)contentView.findViewById(R.id.confirm_startTime);
        wheelStartYear=(WheelView)contentView.findViewById(R.id.startYear);
        wheelStartMonth=(WheelView)contentView.findViewById(R.id.startMonth);
        wheelStartDay=(WheelView)contentView.findViewById(R.id.startDay);
        final WheelView wheelStartHour=(WheelView)contentView.findViewById(R.id.startHour);
        final WheelView wheelStartMinute=(WheelView)contentView.findViewById(R.id.startMinute);
        if(view.getId()==R.id.linearLayout_statisticsStartTime){
            timePickerTitle.setText("选择起始时间");
        }
        else if(view.getId()==R.id.linearLayout_statisticsEndTime){
            timePickerTitle.setText("选择授权结束时间");
        }

        currentTime=getCurrentTime();

        ArrayList<String> yearList=getYearData();
        int yearIndex=yearList.indexOf(currentTime.getYear()+"年");
        wheelStartYear.setData(yearList);
        wheelStartYear.setDefault(yearIndex);

        ArrayList<String> monthList=getMonthData();
        int monthIndex=monthList.indexOf(currentTime.getMonth()+"月");
        wheelStartMonth.setData(monthList);
        wheelStartMonth.setDefault(monthIndex);

        String yearMonth=wheelStartYear.getSelectedText().replace("年","")+"-"+wheelStartMonth.getSelectedText().replace("月","");
        ArrayList<String> dayList=getDayData(yearMonth);
        final int dayIndex=dayList.indexOf(currentTime.getDay()+"日");
        wheelStartDay.setData(dayList);
        wheelStartDay.setDefault(dayIndex);

        ArrayList<String> hourList=getHourData();
        int hourIndex=hourList.indexOf(currentTime.getHour()+"时");
        wheelStartHour.setData(hourList);
        wheelStartHour.setDefault(hourIndex);

        ArrayList<String> minuteList=getMinuteData();
        int minuteIndex=minuteList.indexOf(currentTime.getMinute()+"分");
        wheelStartMinute.setData(minuteList);
        wheelStartMinute.setDefault(minuteIndex);

        wheelStartYear.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                String yearMonth=text.replace("年","")+"-"+wheelStartMonth.getSelectedText().replace("月","");
                wheelStartDay.setData(getDayData(yearMonth));
                handler.sendEmptyMessage(1);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        wheelStartMonth.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                String yearMonth=wheelStartYear.getSelectedText().replace("年","")+"-"+text.replace("月","");
                wheelStartDay.setData(getDayData(yearMonth));
                handler.sendEmptyMessage(1);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        cancel_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        confirm_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if(view.getId()==R.id.linearLayout_statisticsStartTime){
                    startView.setText(wheelStartYear.getSelectedText()+wheelStartMonth.getSelectedText()+ wheelStartDay.getSelectedText()+wheelStartHour.getSelectedText()+wheelStartMinute.getSelectedText());

                }
                else if(view.getId()==R.id.linearLayout_statisticsEndTime){
                    endView.setText(wheelStartYear.getSelectedText()+wheelStartMonth.getSelectedText()+ wheelStartDay.getSelectedText()+wheelStartHour.getSelectedText()+wheelStartMinute.getSelectedText());

                }
            }
        });
    }

    private static String formatting(String original){
        String s=original.replace("年"," ").replace("月"," ").replace("日"," ").replace("时"," ").replace("分"," ");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy MM dd HH mm");
        Date date;
        String consequence="";
        try {
            date=simpleDateFormat.parse(s);
            consequence=simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return consequence.replaceAll(" ", "");
    }


    private ArrayList<String> getYearData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 2000; i<=2100; i++) {
            list.add(String.valueOf(i)+"年");
        }
        return list;
    }

    private ArrayList<String> getMonthData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.valueOf(i)+"月");
        }
        return list;
    }

    private ArrayList<String> getDayData(String yearMonth) {
        //  January  March  May July August October December 31     April June September November 30    February
        ArrayList<String> list = new ArrayList<>();
        int max=judgeMonths(yearMonth);
        for (int i = 1; i <=max; i++) {
            list.add(String.valueOf(i)+"日");
        }
        return list;
    }

    private ArrayList<String> getHourData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            list.add(String.valueOf(i)+"时");
        }
        return list;
    }

    private ArrayList<String> getMinuteData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <=59; i++) {
            list.add(String.valueOf(i)+"分");
        }
        return list;
    }

    private StatisticsActivity.CurrentTime getCurrentTime(){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        currentTime=new StatisticsActivity.CurrentTime();
        currentTime.setYear(year);
        currentTime.setMonth(month);
        currentTime.setDay(day);
        currentTime.setHour(hour);
        currentTime.setMinute(minute);
        return currentTime;
    }

    private StatisticsActivity.CurrentTime currentTime;

    class CurrentTime{
        public int year;
        public int month;
        public int day;
        public int hour;
        public int minute;
        public void setYear(int year){
            this.year=year;
        }
        public void setMonth(int month){
            this.month=month;
        }
        public void setDay(int day){
            this.day=day;
        }
        public void setHour(int hour){
            this.hour=hour;
        }
        public void setMinute(int minute){
            this.minute=minute;
        }
        public int getYear(){
            return year;
        }
        public int getMonth(){
            return month;
        }
        public int getDay(){
            return day;
        }
        public int getHour(){
            return hour;
        }
        public int getMinute(){
            return minute;
        }
    }

    public int judgeMonths(String yearMonth){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM");
        try {
            Date date=simpleDateFormat.parse(yearMonth);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date);
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 30;
    }


    private PopupWindow waitView;
    public void showWait(){
        View contentView= LayoutInflater.from(this).inflate(R.layout.wait,null,false);
        waitView=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT,true);
        waitView.setAnimationStyle(R.style.animation);
        waitView.setOutsideTouchable(true);
        waitView.showAtLocation(backOnStatistics, Gravity.CENTER,0,0);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
         lp.alpha = 0.4f;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
        waitView.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                   WindowManager.LayoutParams lp =getWindow().getAttributes();
                  lp.alpha = 1f;
                  getWindow().setAttributes(lp);
            }
        });
    }

}
