package com.ys.intelligentlock;


/**
 * Created by admin on 2016/12/13.
 */
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcB;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.CustomizedView.WheelView;
import com.ys.intelligentlock.JsonData.DeviceData;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.TimeUtil;
import com.google.gson.Gson;
import com.otg.idcard.OTGReadCardAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/31.
 */
public class AddUserActivity extends Activity implements View.OnClickListener{

    private final int TRANSFER_RESULT=0x333;
    private RadioGroup radioGroup;
    private RadioButton nfcAdd;
    private ImageButton back;
    private RadioButton manualAdd;
    private EditText editText_name;
    private EditText editText_number;
    private TextView textView_dnCode;
    private LinearLayout dnCodeLinearLayout;
    private TextView reminder;
    private LinearLayout linearLayout_userStartTime;
    private LinearLayout linearLayout_userEndTime;
    private TextView textView_userStartTime;
    private TextView textView_userEndTime;
    private Button saveUserDate;
    private String name;
    private String number;
    private String dnCode="";

    private TextView reading;

    private static final int SETTING_SERVER_IP = 11;
    public static String remoteIPA="";
    public static String remoteIPB="";
    public static String remoteIPC="";

    private NfcAdapter mAdapter=null;
    private OTGReadCardAPI ReadCardAPI;
    private PendingIntent pi = null;
    //滤掉组件无法响应和处理的Intent
    private IntentFilter tagDetected = null;
    private String[][] mTechLists;
    private Intent inintent=null;

    private static final int REQUEST_ENABLE_BT = 2;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    public static String addressmac="";

    public static final int MESSAGE_VALID_OTGBUTTON=15;
    public static final int MESSAGE_VALID_NFCBUTTON=16;
    public static final int MESSAGE_VALID_BTBUTTON=17;
    public static final int MESSAGE_VALID_PROCESS=1001;

    private String gatewayCode;
    private String phoneNumber;

    private String lockCode;
    private Handler handler;
    private final int AddUser=0x11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("phoneNumber");
        gatewayCode=intent.getStringExtra("gatewayCode");
        lockCode=intent.getStringExtra("lockCode");

        radioGroup=(RadioGroup)findViewById(R.id.addLockRadioGroup);
        nfcAdd=(RadioButton)findViewById(R.id.nfcAdd);
        back=(ImageButton)findViewById(R.id.backOnAddUser);
        manualAdd=(RadioButton)findViewById(R.id.manualAdd);
        reminder=(TextView)findViewById(R.id.reminder);
        editText_name=(EditText)findViewById(R.id.editText_name);
        editText_number=(EditText)findViewById(R.id.editText_number);
        textView_dnCode=(TextView)findViewById(R.id.textView_dnCode);
        dnCodeLinearLayout=(LinearLayout)findViewById(R.id.dnCodeLinearLayout);
        linearLayout_userStartTime=(LinearLayout)findViewById(R.id.linearLayout_userStartTime);
        linearLayout_userEndTime=(LinearLayout)findViewById(R.id.linearLayout_userEndTime);
        textView_userStartTime=(TextView)findViewById(R.id.textView_userStartTime);
        textView_userEndTime=(TextView)findViewById(R.id.textView_userEndTime);
        saveUserDate=(Button)findViewById(R.id.saveUserData);
        reading=(TextView)findViewById(R.id.reading);
        reading.setVisibility(View.INVISIBLE);

        nfcAdd.setChecked(true);
        editText_name.setEnabled(false);
        editText_number.setEnabled(false);
        radioGroup.setOnCheckedChangeListener(new CheckedListener());


        linearLayout_userStartTime.setOnClickListener(this);
        linearLayout_userEndTime.setOnClickListener(this);

        ClickListener listener=new ClickListener();
        back.setOnClickListener(listener);
        dnCodeLinearLayout.setVisibility(View.VISIBLE);
        saveUserDate.setOnClickListener(listener);

        ArrayList<String> IPArray = new ArrayList<String>();
        IPArray.add("103.21.119.78");    //无锡  IP
      //  IPArray.add("103.36.132.157");

        System.out.println("OTGReadCardAPI    ");

        ReadCardAPI=new OTGReadCardAPI(this);
        //ReadCardAPI.setuserid("123456");
        ReadCardAPI.setlogflag(0);

        mAdapter = NfcAdapter.getDefaultAdapter(this);

        testNFC();
        if (mAdapter!=null)
        {
            init_NFC();
        }

        ((LockApplication)getApplication()).addActivity(this);
    }


    class CheckedListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId==R.id.nfcAdd){
                dnCodeLinearLayout.setVisibility(View.VISIBLE);
                reminder.setVisibility(View.VISIBLE);
                editText_name.setEnabled(false);
                editText_number.setEnabled(false);
            }
            else if(checkedId==R.id.manualAdd){
                dnCodeLinearLayout.setVisibility(View.GONE);
                reminder.setVisibility(View.INVISIBLE);
                editText_name.setEnabled(true);
                editText_number.setEnabled(true);
                editText_name.setText("");
                editText_number.setText("");
                textView_dnCode.setText("");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backOnAddUser:
                finish();
                break;
            case R.id.linearLayout_userStartTime:
                showPopupWindow(v, textView_userStartTime, textView_userEndTime);
                break;
            case R.id.linearLayout_userEndTime:
                showPopupWindow(v, textView_userStartTime, textView_userEndTime);
                break;
        }

        }

    class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.backOnAddUser:
                    finish();
                    break;
                case R.id.saveUserData:
                    name=editText_name.getText().toString().trim();
                    number=lowerToUpper(editText_number.getText().toString());   //身份证字母小写转化为大写
                    dnCode=textView_dnCode.getText().toString();
                    if(name.equals("") || number.equals("")){
                        Toast.makeText(AddUserActivity.this,"姓名和身份证号码不能为空！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(number.length()!=18){
                        Toast.makeText(AddUserActivity.this,"身份证号码有误！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    final String startTime=formatting(textView_userStartTime.getText().toString());
                    final String endTime=formatting(textView_userEndTime.getText().toString());

                    System.out.println("startTime"+startTime);
                    System.out.println("endTime"+endTime);

                    if(startTime.equals("") || endTime.equals("")){
                        Toast.makeText(AddUserActivity.this,"授权起始时间与授权结束时间不能为空！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(Long.valueOf(startTime) >=Long.valueOf(endTime)){
                        Toast.makeText(AddUserActivity.this,"授权起始时间不能晚于授权结束时间！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    saveUserDate.setClickable(false);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonRequest=new JSONObject();
                            try {
                                jsonRequest.put("sign",18);
                                jsonRequest.put("ownerPhoneNumber",phoneNumber);
                                jsonRequest.put("lockCode",lockCode);
                                jsonRequest.put("gatewayCode",gatewayCode);
                                jsonRequest.put("serviceNumb",new TimeUtil().getServiceNumb(phoneNumber));
                                jsonRequest.put("name",name);
                                jsonRequest.put("cardNumb",number);
                                jsonRequest.put("dnCode",dnCode);
                                jsonRequest.put("startTime",startTime);
                                jsonRequest.put("endTime",endTime);
                                jsonRequest.put("timetag",new TimeUtil().getTimetag());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("jsonRequest  "+jsonRequest);
                            System.out.println("dnCode  length"+dnCode.length());

                            String gatewayIp=((LockApplication)getApplication()).getGatewayIp(gatewayCode);
                            String consequence= HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                            System.out.println("consequence  "+consequence);
                            Message message=new Message();
                            message.obj=consequence;
                            message.what=AddUser;
                            handler.sendMessage(message);
                        }
                    }).start();
                    break;
                default:
                    break;
            }
        }
    }

    private PopupWindow waitView;
    public void showWait(){
        View contentView= LayoutInflater.from(this).inflate(R.layout.wait,null,false);
        waitView=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT,true);
        waitView.setAnimationStyle(R.style.animation);
        waitView.setOutsideTouchable(true);
        waitView.showAtLocation(back, Gravity.CENTER,0,0);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.2f;
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


    public void testNFC()
    {
        if (mAdapter == null)
        {
            Toast.makeText(this, "设备不支持NFC功能！",Toast.LENGTH_LONG).show();
            return;
        }
        if (!mAdapter.isEnabled())
        {
            Toast.makeText(this, "NFC功能未启用，请先启用！", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        inintent = intent;
        editText_name.setText("");
        editText_number.setText("");
        textView_dnCode.setText("");
        reading.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(MESSAGE_VALID_NFCBUTTON, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
//		mAdapter.disableForegroundDispatch(this);
        if (mAdapter!=null)	stopNFC_Listener();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter!=null) startNFC_Listener();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    wheelStartDay.invalidate();
                }
                if(msg.what==AddUser){
                    saveUserDate.setClickable(true);
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(AddUserActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            editText_name.setText("");
                            editText_number.setText("");
                            textView_dnCode.setText("");
                            textView_userStartTime.setText("");
                            textView_userEndTime.setText("");
                            Toast.makeText(AddUserActivity.this,"添加授权用户成功！",Toast.LENGTH_SHORT).show();
                        }
                        else if(result==1){
                            Toast.makeText(AddUserActivity.this,"添加授权用户失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

    }
    private void startNFC_Listener() {
        mAdapter.enableForegroundDispatch(this, pi, new IntentFilter[] { tagDetected }, mTechLists);

    }

    private void stopNFC_Listener() {
        mAdapter.disableForegroundDispatch(this);
    }

    private void init_NFC() {
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);//.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        mTechLists = new String[][] { new String[] { NfcB.class.getName() } };
    }

    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int tt;
            switch (msg.what) {
                case MESSAGE_VALID_NFCBUTTON:
                    reading.setVisibility(View.INVISIBLE);
                    tt = ReadCardAPI.NfcReadCard(inintent);
                    Log.e("For Test", " ReadCard TT=" + tt);
                    if (tt == 2) {
                        new AlertDialog.Builder(AddUserActivity.this)
                                .setTitle("提示").setMessage("接收数据超时！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }
                    if (tt == 41) {
                        new AlertDialog.Builder(AddUserActivity.this)
                                .setTitle("提示").setMessage("读卡失败！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }
                    if (tt == 42) {
                        new AlertDialog.Builder(AddUserActivity.this)
                                .setTitle("提示").setMessage("没有找到服务器！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();

                    }
                    if (tt == 43) {
                        new AlertDialog.Builder(AddUserActivity.this)
                                .setTitle("提示").setMessage("服务器忙！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }
                    if (tt == 90) {
                        editText_name.setText(ReadCardAPI.Name());
                        editText_number.setText(ReadCardAPI.CardNo());
                        textView_dnCode.setText(ReadCardAPI.DNcode().replace(" ",""));
                        reading.setVisibility(View.INVISIBLE);
                    }
            }
        }
    };


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
        if(view.getId()==R.id.linearLayout_userStartTime){
            timePickerTitle.setText("选择授权起始时间");
        }
        else if(view.getId()==R.id.linearLayout_userEndTime){
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
                if(view.getId()==R.id.linearLayout_userStartTime){
                    startView.setText(wheelStartYear.getSelectedText()+wheelStartMonth.getSelectedText()+ wheelStartDay.getSelectedText()+wheelStartHour.getSelectedText()+wheelStartMinute.getSelectedText());

                }
                else if(view.getId()==R.id.linearLayout_userEndTime){
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

    private AddUserActivity.CurrentTime currentTime;

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


    public  String lowerToUpper(String source){
        for(int i=0;i<source.length();i++){
            if(Character.isLowerCase(source.charAt(i))){
                char a=Character.toUpperCase(source.charAt(i));
                source=source.replace(source.charAt(i), a);
            }
        }
        return source;
    }
}
