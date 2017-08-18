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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.CustomizedView.WheelView;
import com.ys.intelligentlock.JsonData.PasswordData;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class SetPasswordActivity extends Activity implements View.OnClickListener{
    private ListView passwordListView;
    private ImageButton back;
    private Button addPassword;
    private LinearLayout linearLayout_addPassword;
    private LinearLayout linearLayout_passwordList;
    private EditText editText_newPassword;
    private LinearLayout linearLayout_password_startTime;
    private LinearLayout linearLayout_password_endTime;
    private TextView textView_password_startTime;
    private TextView textView_password_endTime;
    private TextView cancel_addPassword;
    private TextView confirm_addPassword;
    private PasswordListViewAdapter adapter;

    private String phoneNumber;
    private String lockCode;
    private String gatewayCode;

    private Handler handler;
    private final int AddPassword=0x11;
    private final int GetPasswordList=0x22;
    private final int CancelPassword=0x33;
    private PasswordData passwordData;
    private List<PasswordData.PasswordInfo> passwordList;
    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_password);
        passwordListView=(ListView)findViewById(R.id.passwordListView);
        back=(ImageButton) findViewById(R.id.backReturnLockParticular);
        addPassword=(Button)findViewById(R.id.addPassword);
        linearLayout_addPassword=(LinearLayout)findViewById(R.id.linearLayout_addPassword);
        linearLayout_passwordList=(LinearLayout)findViewById(R.id.linearLayout_passwordList);
        linearLayout_addPassword.setVisibility(View.GONE);
        editText_newPassword=(EditText)findViewById(R.id.editText_newPassword);
        linearLayout_password_startTime=(LinearLayout)findViewById(R.id.linearLayout_password_startTime);
        linearLayout_password_endTime=(LinearLayout)findViewById(R.id.linearLayout_password_endTime);
        textView_password_startTime=(TextView)findViewById(R.id.textView_password_startTime);
        textView_password_endTime=(TextView)findViewById(R.id.textView_password_endTime);
        cancel_addPassword=(TextView)findViewById(R.id.cancel_addPassword);
        confirm_addPassword=(TextView)findViewById(R.id.confirm_addPassword);

        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("phoneNumber");
        gatewayCode=intent.getStringExtra("gatewayCode");
        lockCode=intent.getStringExtra("lockCode");

        back.setOnClickListener(this);
        addPassword.setOnClickListener(this);
        linearLayout_password_startTime.setOnClickListener(this);
        linearLayout_password_endTime.setOnClickListener(this);
        cancel_addPassword.setOnClickListener(this);
        confirm_addPassword.setOnClickListener(this);

        ((LockApplication)getApplication()).addActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getPassword();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==5){
                    wheelStartDay.invalidate();
                }
                if(msg.what==AddPassword){
                    //waitView.dismiss();
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(SetPasswordActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            count++;
                            editText_newPassword.setText("");
                            textView_password_startTime.setText("");
                            textView_password_endTime.setText("");
                            Toast.makeText(SetPasswordActivity.this,"添加密码成功！",Toast.LENGTH_SHORT).show();
                        }
                        else if(result==1){
                            Toast.makeText(SetPasswordActivity.this,"添加密码失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(msg.what==GetPasswordList){
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(SetPasswordActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            passwordList=new ArrayList<>();
                            Gson gson=new Gson();
                            passwordData=gson.fromJson(consequence, PasswordData.class);
                            passwordList=passwordData.passwordList;
                            System.out.println(passwordList);
                            adapter=new PasswordListViewAdapter();
                            passwordListView.setAdapter(adapter);

                        }
                        else if(result==1){
                           // Toast.makeText(SetPasswordActivity.this,"获取密码失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if(msg.what==CancelPassword){
                    waitView.dismiss();
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(SetPasswordActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            Toast.makeText(SetPasswordActivity.this,"取消密码成功！",Toast.LENGTH_SHORT).show();
                            getPassword();

                        }
                        else if(result==1){
                            Toast.makeText(SetPasswordActivity.this,"取消密码失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        getPassword();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.backReturnLockParticular:
                finish();
                break;
            case R.id.addPassword:
                if(passwordList==null){
                    return;
                }
                count=countValidPassword();
                if(count>=2){
                    Toast.makeText(SetPasswordActivity.this,"门锁有效密码不能超过两组！",Toast.LENGTH_SHORT).show();
                    return;
                }
                linearLayout_addPassword.setVisibility(View.VISIBLE);
                linearLayout_passwordList.setVisibility(View.GONE);
                break;
            case R.id.linearLayout_password_startTime:
                showPopupWindow(linearLayout_password_startTime,textView_password_startTime,textView_password_endTime);
                break;
            case R.id.linearLayout_password_endTime:
                showPopupWindow(linearLayout_password_endTime,textView_password_startTime,textView_password_endTime);
                break;
            case R.id.cancel_addPassword:
                linearLayout_addPassword.setVisibility(View.GONE);
                linearLayout_passwordList.setVisibility(View.VISIBLE);
                getPassword();
                break;
            case R.id.confirm_addPassword:
                if(count>=2){
                    Toast.makeText(SetPasswordActivity.this,"门锁有效密码不能超过两组！",Toast.LENGTH_SHORT).show();
                    return;
                }

                final String password=editText_newPassword.getText().toString();
                if(textView_password_startTime.getText().toString().equals("") || textView_password_endTime.getText().toString().equals("")){
                    showToast("密码授权时间不能为空！");
                    return;
                }
                if(password.equals("")){
                    showToast("新密码不能为空！");
                    return;
                }
                final String startTime=formatting(textView_password_startTime.getText().toString());
                final String endTime=formatting(textView_password_endTime.getText().toString());
                if(Long.valueOf(startTime.toString())> Long.valueOf(endTime.toString())){
                    showToast("密码截止时间不能早于起始时间！");
                    return;
                }
                if(password.length()>=4  && password.length()<=12){

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonRequest=new JSONObject();
                            try {
                                jsonRequest.put("sign",21);
                                jsonRequest.put("ownerPhoneNumber",phoneNumber);
                                jsonRequest.put("lockCode",lockCode);
                                jsonRequest.put("gatewayCode",gatewayCode);
                                jsonRequest.put("serviceNumb",new TimeUtil().getServiceNumb(phoneNumber));
                                jsonRequest.put("timetag", new TimeUtil().getTimetag());
                                jsonRequest.put("startTime",startTime);
                                jsonRequest.put("endTime",endTime);
                                jsonRequest.put("password",password);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("jsonRequest  "+jsonRequest);
                            String gatewayIp=((LockApplication)getApplication()).getGatewayIp(gatewayCode);
                            String consequence= HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                            System.out.println("consequence  "+consequence);
                            Message message=new Message();
                            message.obj=consequence;
                            message.what=AddPassword;
                            handler.sendMessage(message);
                        }
                    }).start();

                }
                else{
                    showToast("密码只能是4到8位数字！");
                    return;
                }
                break;
            default:
                break;
        }
    }

    public void getPassword(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonRequest=new JSONObject();
                try {
                    jsonRequest.put("sign",20);
                    jsonRequest.put("ownerPhoneNumber",phoneNumber);
                    jsonRequest.put("lockCode",lockCode);
                    jsonRequest.put("gatewayCode",gatewayCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("jsonRequest  "+jsonRequest);
                String gatewayIp=((LockApplication)getApplication()).getGatewayIp(gatewayCode);
                String consequence= HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                System.out.println("consequence  "+consequence);
                Message message=new Message();
                message.obj=consequence;
                message.what=GetPasswordList;
                handler.sendMessage(message);

            }
        }).start();

    }

    public int countValidPassword(){
        int count=0;
        System.out.println("passwordList"+passwordList.size());
        if(passwordList.size()==0){
            return count;
        }
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmm");
        String now=simpleDateFormat.format(calendar.getTime());

        for(int i=0;i<passwordList.size();i++){
            if(Long.parseLong(passwordList.get(i).endTime)>Long.parseLong(now)){
                count++;
            }
        }
        return count;
    }

    class PasswordListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if(passwordList==null){
                return 0;
            }
            return passwordList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view= View.inflate(SetPasswordActivity.this,R.layout.password_item,null);
            TextView textView_password=(TextView)view.findViewById(R.id.textView_password);
            TextView textView_password_startTime=(TextView)view.findViewById(R.id.textView_password_startTime);
            TextView textView_password_endTime=(TextView)view.findViewById(R.id.textView_password_endTime);
            final LinearLayout linearLayout_deletePassword=(LinearLayout)view.findViewById(R.id.linearLayout_deletePassword);
            final String password=passwordList.get(position).password;
            String startTime=passwordList.get(position).startTime;
            String endTime=passwordList.get(position).endTime;
            textView_password.setText(password);
            textView_password_startTime.setText(startTime.substring(0,4)+"年"+startTime.substring(4,6)+"月"+startTime.substring(6,8)+"日"+startTime.substring(8,10)+"时"+startTime.substring(10,12)+"分");
            textView_password_endTime.setText(endTime.substring(0,4)+"年"+endTime.substring(4,6)+"月"+endTime.substring(6,8)+"日"+endTime.substring(8,10)+"时"+endTime.substring(10,12)+"分");

            linearLayout_deletePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater= LayoutInflater.from(SetPasswordActivity.this);
                    View alertView=inflater.inflate(R.layout.alert_dialog,null,false);
                    TextView alertMessage=(TextView)alertView.findViewById(R.id.alert_message);
                    TextView cancel=(TextView)alertView.findViewById(R.id.cancel_alertDialog);
                    TextView confirm=(TextView)alertView.findViewById(R.id.deleteAuthorization);
                    alertMessage.setText("确定删除该门锁密码?");
                    final PopupWindow alertWindow=new PopupWindow(alertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
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
                            showWait();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject jsonRequest=new JSONObject();
                                    try {
                                        jsonRequest.put("sign",22);
                                        jsonRequest.put("ownerPhoneNumber",phoneNumber);
                                        jsonRequest.put("lockCode",lockCode);
                                        jsonRequest.put("gatewayCode",gatewayCode);
                                        jsonRequest.put("serviceNumb",passwordList.get(position).serviceNumb);
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
                                    message.what=CancelPassword;
                                    handler.sendMessage(message);
                                }
                            }).start();

                        }
                    });

                }
            });
            return view;
        }
    }

    private PopupWindow waitView;
    public void showWait(){
        View contentView= LayoutInflater.from(this).inflate(R.layout.wait,null,false);
        waitView=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT,true);
        waitView.setAnimationStyle(R.style.animation);
        waitView.setOutsideTouchable(true);
        waitView.showAtLocation(addPassword, Gravity.CENTER,0,0);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.2f;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);     //解决华为手机  PopupWindow 不变暗
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

    private void showToast(String content){
        Toast.makeText(this,content, Toast.LENGTH_SHORT).show();
    }

    //private PopupWindow popupWindow;
    private WheelView wheelStartYear;
    private WheelView wheelStartMonth;
    private WheelView wheelStartDay;

    private void showPopupWindow(final View view, final TextView startView, final TextView endView){
        View contentView= LayoutInflater.from(this).inflate(R.layout.start_data_time_picker,null,false);
        final PopupWindow popupWindow=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.animation);

        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(addPassword, Gravity.BOTTOM,0,0);
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
        if(view.getId()==R.id.linearLayout_password_startTime){
            timePickerTitle.setText("选择密码起始时间");
        }
        else if(view.getId()==R.id.linearLayout_password_endTime){
            timePickerTitle.setText("选择密码结束时间");
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
                handler.sendEmptyMessage(5);
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
                handler.sendEmptyMessage(5);
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
                if(view.getId()==R.id.linearLayout_password_startTime){
                    startView.setText(wheelStartYear.getSelectedText()+wheelStartMonth.getSelectedText()+ wheelStartDay.getSelectedText()+wheelStartHour.getSelectedText()+wheelStartMinute.getSelectedText());
                }
                else if(view.getId()==R.id.linearLayout_password_endTime){
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
        //for (int i = 1; i <= 9; i++) {
       //     list.add("0"+String.valueOf(i)+"月");
       // }
        for(int i=1;i<=12;i++){
            list.add(String.valueOf(i)+"月");
        }
        return list;
    }

    private ArrayList<String> getDayData(String yearMonth) {
        //  January  March  May July August October December 31     April June September November 30    February
        ArrayList<String> list = new ArrayList<>();
        int max=judgeMonths(yearMonth);
        //for (int i = 1; i <= 9; i++) {
        //    list.add("0"+String.valueOf(i)+"日");
        //}
        for (int i = 1; i <=max; i++) {
            list.add(String.valueOf(i)+"日");
        }
        return list;
    }

    private ArrayList<String> getHourData() {
        ArrayList<String> list = new ArrayList<>();
        //for (int i = 1; i <= 9; i++) {
        //    list.add("0"+String.valueOf(i)+"时");
        //}
        for (int i = 1; i <= 23; i++) {
            list.add(String.valueOf(i)+"时");
        }
        return list;
    }
    private ArrayList<String> getMinuteData() {
        ArrayList<String> list = new ArrayList<>();
        //for (int i = 1; i <= 9; i++) {
         //   list.add("0"+String.valueOf(i)+"分");
        //}
        for (int i = 1; i <=59; i++) {
            list.add(String.valueOf(i)+"分");
        }
        return list;
    }

    private CurrentTime getCurrentTime(){
        Calendar calendar= Calendar.getInstance();
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
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(date);
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 30;
    }

}

