package com.ys.intelligentlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.CustomizedView.WheelView;
import com.ys.intelligentlock.JsonData.AuthorizationUserData;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2016/12/14.
 */

public class UserDetailActivity extends Activity implements View.OnClickListener{
    private ImageButton back;
    private TextView textView_detailUserName;
    private TextView textView_detailUserCardNumb;
    private TextView textView_detailUserDnCode;
    private LinearLayout linearLayout_user_startTime;
    private LinearLayout linearLayout_user_endTime;
    private TextView textView_startTime;
    private TextView textView_endTime;

    private LinearLayout linearLayout_cancelAuthorization;

    private String phoneNumber;
    private String gatewayCode;
    private String lockCode;
    private String serviceNumb;

    private Handler handler;
    private final int CancelAuthorization=0x11;

    private AuthorizationUserData.UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        back = (ImageButton) findViewById(R.id.backOnUserDetail);
        textView_detailUserName=(TextView)findViewById(R.id.textView_detailUserName);
        textView_detailUserCardNumb=(TextView)findViewById(R.id.textView_detailUserCardNumb);
        textView_detailUserDnCode=(TextView)findViewById(R.id.textView_detailUserDnCode);

        linearLayout_user_startTime = (LinearLayout) findViewById(R.id.linearLayout_modify_user_startTime);
        linearLayout_user_endTime = (LinearLayout) findViewById(R.id.linearLayout_modify_user_endTime);
        linearLayout_cancelAuthorization = (LinearLayout) findViewById(R.id.linearLayout_cancelAuthorization);
        textView_startTime = (TextView) findViewById(R.id.textView_detail_user_startTime);
        textView_endTime = (TextView) findViewById(R.id.textView_detail_user_endTime);

        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("phoneNumber");
        gatewayCode=intent.getStringExtra("gatewayCode");
        lockCode=intent.getStringExtra("lockCode");
        userInfo=(AuthorizationUserData.UserInfo)intent.getSerializableExtra("userInfo");

        serviceNumb=userInfo.serviceNumb;

        textView_detailUserName.setText(userInfo.name);
        textView_detailUserCardNumb.setText(userInfo.cardNumb);
        textView_detailUserDnCode.setText(userInfo.dnCode);
        textView_startTime.setText(timeShow(userInfo.startTime));
        textView_endTime.setText(timeShow(userInfo.endTime));

        back.setOnClickListener(this);
        linearLayout_user_startTime.setOnClickListener(this);
        linearLayout_user_endTime.setOnClickListener(this);
        linearLayout_cancelAuthorization.setOnClickListener(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    wheelStartDay.invalidate();
                }
                if(msg.what==CancelAuthorization){
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(UserDetailActivity.this,"连接服务器失败！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            Toast.makeText(UserDetailActivity.this,"取消授权成功！",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else if(result==1){
                            Toast.makeText(UserDetailActivity.this,"取消授权失败！",Toast.LENGTH_SHORT).show();
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
        switch(v.getId()){
            case R.id.backOnUserDetail:
                finish();
                break;
            /**
            case R.id.linearLayout_modify_user_startTime:
                showPopupWindow(v,textView_startTime,textView_endTime);
                break;
            case R.id.linearLayout_modify_user_endTime:
                showPopupWindow(v,textView_startTime,textView_endTime);
                break;
             **/
            case R.id.linearLayout_cancelAuthorization:
                LayoutInflater inflater=LayoutInflater.from(this);
                View view=inflater.inflate(R.layout.alert_dialog,null);
                TextView cancel=(TextView)view.findViewById(R.id.cancel_alertDialog);
                TextView confirm=(TextView)view.findViewById(R.id.deleteAuthorization);
                TextView alert_message=(TextView)view.findViewById(R.id.alert_message);
                alert_message.setText("确定删除该授权用户?");
                final PopupWindow alertWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
                alertWindow.setAnimationStyle(R.style.leftToRight);
                alertWindow.setOutsideTouchable(true);
                alertWindow.showAtLocation(back, Gravity.CENTER,0,0);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.3f;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
                alertWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp =getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertWindow.dismiss();
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertWindow.dismiss();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonRequest=new JSONObject();
                                try {
                                    jsonRequest.put("sign",19);
                                    jsonRequest.put("ownerPhoneNumber",phoneNumber);
                                    jsonRequest.put("lockCode",lockCode);
                                    jsonRequest.put("cardNumb",userInfo.cardNumb);
                                    jsonRequest.put("serviceNumb",userInfo.serviceNumb);
                                    jsonRequest.put("timetag",new TimeUtil().getTimetag());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("jsonRequest  "+jsonRequest);
                                String gatewayIp=((LockApplication)getApplication()).getGatewayIp(gatewayCode);

                                String consequence= HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                                System.out.println("consequence  "+consequence);
                                Message message=new Message();
                                message.obj=consequence;
                                message.what=CancelAuthorization;
                                handler.sendMessage(message);

                            }
                        }).start();

                    }
                });

                break;

        }
    }

    private WheelView wheelStartYear;
    private WheelView wheelStartMonth;
    private WheelView wheelStartDay;

    private void showPopupWindow(final View view, final TextView startView, final TextView endView){
        View contentView= LayoutInflater.from(this).inflate(R.layout.start_data_time_picker,null,false);
        final PopupWindow popupWindow=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.animation);

        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(back, Gravity.BOTTOM,0,0);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.3f;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);     //解决华为手机  PopupWindow 不变暗
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
        if(view.getId()==R.id.linearLayout_modify_user_startTime){
            timePickerTitle.setText("修改授权起始时间");
        }
        else if(view.getId()==R.id.linearLayout_modify_user_endTime){
            timePickerTitle.setText("修改授权结束时间");
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
                if(view.getId()==R.id.linearLayout_modify_user_startTime){
                    startView.setText(wheelStartYear.getSelectedText()+wheelStartMonth.getSelectedText()+ wheelStartDay.getSelectedText()+wheelStartHour.getSelectedText()+wheelStartMinute.getSelectedText());

                }
                else if(view.getId()==R.id.linearLayout_modify_user_endTime){
                    endView.setText(wheelStartYear.getSelectedText()+wheelStartMonth.getSelectedText()+ wheelStartDay.getSelectedText()+wheelStartHour.getSelectedText()+wheelStartMinute.getSelectedText());

                }
            }
        });
    }

    private String formatting(String original){
        String s=original.replace("年","-").replace("月","-").replace("日","-").replace("时","-").replace("分","-");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        Date date;
        String consequence="";
        try {
            date=simpleDateFormat.parse(s);
            consequence=simpleDateFormat.format(date).replace("-","");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return consequence;
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

    private CurrentTime getCurrentTime(){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        currentTime=new CurrentTime();
        currentTime.setYear(year);
        currentTime.setMonth(month);
        currentTime.setDay(day);
        currentTime.setHour(hour);
        currentTime.setMinute(minute);
        return currentTime;
    }

    private CurrentTime currentTime;

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

    public String timeShow(String original){
        String consequence=original.substring(0,4)+"年"+original.substring(4,6)+"月"+original.substring(6,8)+"日"
                +original.substring(8,10)+"时"+original.substring(10,12)+"分";
        return consequence;
    }

}
