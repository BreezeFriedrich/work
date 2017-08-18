package com.ys.intelligentlock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.JsonData.AbnormalDeviceData;
import com.ys.intelligentlock.JsonData.DeviceData;
import com.ys.intelligentlock.JsonData.GatewayIpData;
import com.ys.intelligentlock.Service.LockService;
import com.ys.intelligentlock.Utils.DataBaseUtil;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.google.gson.Gson;
import com.ys.intelligentlock.Utils.SPUtils;
import com.ys.intelligentlock.Utils.Tea;
import com.ys.intelligentlock.Utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {
    private DrawerLayout drawerLayout;
    private FrameLayout contentFrame;
    private LinearLayout left_menu;
    private ListView leftListView;
    private String[] leftText;
    private int[] imageViewId;
    private  LeftListViewAdapter leftListViewAdapter;

    private ImageView imageView_ownerImage;
    private TextView textView_ownerName;
    private TextView textView_phoneNumber;
    private String ownerName;
    private String phoneNumber;
    private String imei;

    private ImageButton imageView_openLeft;
    private RecyclerView devicesRecyclerView;

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "head.jpg";

    private Handler handler;
    private final int GetDeviceData=0x55;
    private final int GetDataFinish=0x66;

    private final int NoNetWork=0x111;
    private final int GetGatewayIpFiled=0x222;

    private DeviceData deviceData;
    private List<DeviceData.DeviceInfo> devices;

    private GatewayIpData gatewayIpData;
    private List<GatewayIpData.GatewayIpInfo> gatewayIpList;

    public AbnormalDeviceData abnormalDeviceData;
    public List<AbnormalDeviceData.AbnormalDeviceInfo> abnormalDeviceLists;
    public int sendNotification=1;   // 1 send 0 already send
    public int showAlertDialog=1;   // 1 send 0 already send


    public boolean connectServerAgain=true;  // if get Data from Server failed,delete ip and try again
    public boolean netWork=true;

    private boolean showOtherLogin=true;
    private boolean startService=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imei=getIntent().getStringExtra("imei");
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        ownerName=getIntent().getStringExtra("ownerName");

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        contentFrame=(FrameLayout)findViewById(R.id.content_frame);
        left_menu=(LinearLayout)findViewById(R.id.left_menu);
        leftListView=(ListView)findViewById(R.id.leftListView);
        imageView_ownerImage=(ImageView)findViewById(R.id.ownerImage);
        textView_ownerName=(TextView)findViewById(R.id.textView_ownerName);
        textView_phoneNumber=(TextView)findViewById(R.id.textView_phoneNumber);
        imageView_openLeft=(ImageButton) findViewById(R.id.imageButton_openLeft);

        if(imei!=null){
            if(showOtherLogin){
                if(!judgeImei(imei)){
                    new AlertDialog.Builder(this).setTitle("注意").setMessage("该账号上次在另一台设备登录！").setPositiveButton("确定",null).show();
                }
                showOtherLogin=false;
            }
        }
        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        netWork=true;

        abnormalDeviceData=new AbnormalDeviceData();
        abnormalDeviceLists=new ArrayList<>();

        textView_ownerName.setText(ownerName);
        textView_phoneNumber.setText(phoneNumber);
        deviceData=null;
        devices=new ArrayList<>();
        //gatewayIpList=new ArrayList<>();
        if(devicesRecyclerView!=null){
            devicesRecyclerView.removeAllViews();
        }

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==NoNetWork){
                    Toast.makeText(MainActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(msg.what==GetGatewayIpFiled){
                    Toast.makeText(MainActivity.this,"获取数据失败！",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(msg.what==GetDeviceData){
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        if(connectServerAgain){
                            connectServerAgain=false;
                            DataBaseUtil dataBaseUtil=new DataBaseUtil(MainActivity.this);
                            dataBaseUtil.deleteAllGatewayIp();
                            getDevicesDataFromServer();
                            return;
                        }
                        else{
                            Toast.makeText(MainActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                            netWork=false;
                            return;
                        }
                    }
                    Gson gson=new Gson();
                    deviceData=gson.fromJson(consequence,DeviceData.class);

                    if(deviceData.result==1){
                        Toast.makeText(MainActivity.this,"获取设备数据失败！",Toast.LENGTH_SHORT).show();
                    }
                    else if(deviceData.result==0){
                        if(deviceData.devices!=null){
                            devices.addAll(deviceData.devices);  // 拼接
                        }
                    }
                }
                else if(msg.what==GetDataFinish){
                    if(devicesRecyclerView!=null){
                        devicesRecyclerView.removeAllViews();
                    }
                    if(!netWork){
                        return;
                    }
                    if(devices.size()==0){
                        Toast.makeText(MainActivity.this,"当前无网关，请先添加网关！",Toast.LENGTH_SHORT).show();
                    }
                    devicesRecyclerView=(RecyclerView)findViewById(R.id.devicesRecyclerView);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    devicesRecyclerView.setLayoutManager(linearLayoutManager);

                    RecyclerView.Adapter adapter=new RecyclerAdapter();
                    devicesRecyclerView.setAdapter(adapter);
                    System.out.println("devices.size()    "+devices.size());
                    devicesRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        private Drawable mDrawable;
                        @Override
                        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                            super.onDraw(c, parent, state);
                            mDrawable=MainActivity.this.getResources().getDrawable(R.drawable.segmentation);
                            final int left=parent.getLeft();
                            final int right=parent.getRight();
                            int childCount=parent.getChildCount();
                            for(int i=0;i<childCount-1;i++){
                                View child=parent.getChildAt(i);
                                int top=child.getBottom();
                                int bottom=top+mDrawable.getIntrinsicHeight();
                                mDrawable.setBounds(left,top,right,bottom);
                                mDrawable.draw(c);
                            }
                        }
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);
                            mDrawable=MainActivity.this.getResources().getDrawable(R.drawable.segmentation);
                            outRect.set(0,0,0,0);
                        }
                    });

                    for(int i=0;i<devices.size();i++){
                        int gatewayStatus=devices.get(i).gatewayStatus;
                        if(gatewayStatus!=4){
                            AbnormalDeviceData.AbnormalDeviceInfo abnormalDeviceInfo=abnormalDeviceData.new AbnormalDeviceInfo();
                            abnormalDeviceInfo.abnormalDeviceName=devices.get(i).gatewayName;
                            abnormalDeviceInfo.abnormalDeviceCode=devices.get(i).gatewayCode;
                            abnormalDeviceInfo.abnormalDeviceComment=devices.get(i).gatewayComment;
                            abnormalDeviceInfo.abnormalDeviceLocation=devices.get(i).gatewayLocation;
                            abnormalDeviceInfo.type=1;
                            abnormalDeviceInfo.abnormalDeviceStatus=gatewayStatus;
                            abnormalDeviceLists.add(abnormalDeviceInfo);
                        }
                        for(int j=0;j<devices.get(i).lockLists.size();j++){
                            DeviceData.DeviceInfo.LockInfo lockInfo=devices.get(i).lockLists.get(j);
                            if(lockInfo.lockStatus==2 || lockInfo.lockStatus==3){
                                AbnormalDeviceData.AbnormalDeviceInfo abnormalDeviceInfo=abnormalDeviceData.new AbnormalDeviceInfo();
                                abnormalDeviceInfo.abnormalDeviceName=lockInfo.lockName;
                                abnormalDeviceInfo.abnormalDeviceCode=lockInfo.lockCode;
                                abnormalDeviceInfo.abnormalDeviceComment=lockInfo.lockComment;
                                abnormalDeviceInfo.abnormalDeviceLocation=lockInfo.lockLocation;
                                abnormalDeviceInfo.type=2;
                                abnormalDeviceInfo.abnormalDeviceStatus=lockInfo.lockStatus;
                                abnormalDeviceLists.add(abnormalDeviceInfo);
                            }
                            if(lockInfo.lockPower==1){
                                AbnormalDeviceData.AbnormalDeviceInfo abnormalDeviceInfo=abnormalDeviceData.new AbnormalDeviceInfo();
                                abnormalDeviceInfo.abnormalDeviceName=lockInfo.lockName;
                                abnormalDeviceInfo.abnormalDeviceCode=lockInfo.lockCode;
                                abnormalDeviceInfo.abnormalDeviceComment=lockInfo.lockComment;
                                abnormalDeviceInfo.abnormalDeviceLocation=lockInfo.lockLocation;
                                abnormalDeviceInfo.type=2;
                                abnormalDeviceInfo.abnormalDeviceStatus=lockInfo.lockStatus;
                                abnormalDeviceInfo.lockPower=lockInfo.lockPower;              // lock power not enough
                                abnormalDeviceLists.add(abnormalDeviceInfo);
                            }
                        }
                    }
                    if(abnormalDeviceLists.size()!=0  && sendNotification==1){
                        if(new SPUtils(MainActivity.this).getIsAlertPush()){
                            notificationCall();
                        }
                    }
                    if(abnormalDeviceLists.size()!=0 && showAlertDialog==1){
                        new AlertDialog.Builder(MainActivity.this).setTitle("注意").setMessage("存在异常设备！").setNegativeButton("确定",null)
                                .setPositiveButton("查看", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent alertIntent=new Intent(MainActivity.this,AlertActivity.class);
                                        alertIntent.putExtra("abnormalDeviceData",abnormalDeviceData);
                                        startActivity(alertIntent);
                                    }
                                }).show();
                        showAlertDialog=0;
                    }
                }
            }
        };

        abnormalDeviceData.abnormalDeviceLists=abnormalDeviceLists;

        imageView_ownerImage.setImageResource(R.drawable.username);
        leftText=new String[]{"添加网关","查询统计","异常警示","系统设置"};
        imageViewId=new int[]{R.drawable.add_gateway,
                R.drawable.statistics,R.drawable.alert,R.drawable.setting};
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                leftListView.setItemsCanFocus(true);
                contentFrame.setFocusable(false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                leftListView.setItemsCanFocus(false);
                contentFrame.setFocusable(true);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        leftListViewAdapter=new LeftListViewAdapter();
        leftListView.setAdapter(leftListViewAdapter);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        Intent addGatewayIntent=new Intent(MainActivity.this,GatewayVerificationActivity.class);
                        addGatewayIntent.putExtra("phoneNumber",phoneNumber);
                        startActivity(addGatewayIntent);
                        break;
                    case 1:
                        Intent statisticsIntent=new Intent(MainActivity.this,StatisticsActivity.class);
                        statisticsIntent.putExtra("phoneNumber",phoneNumber);
                        startActivity(statisticsIntent);
                        break;
                    case 2:
                        Intent alertIntent=new Intent(MainActivity.this,AlertActivity.class);
                        alertIntent.putExtra("abnormalDeviceData",abnormalDeviceData);
                        startActivity(alertIntent);
                        break;
                    case 3:
                        Intent settingIntent=new Intent(MainActivity.this,SettingActivity.class);
                        settingIntent.putExtra("phoneNumber",phoneNumber);
                        startActivity(settingIntent);
                        break;
                    default:
                        break;
                }
            }
        });

        imageView_openLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(left_menu);
            }
        });

        Bitmap photo= BitmapFactory.decodeFile("sdcard/IntelligentLock/photo.png");
        if (photo == null) {
            imageView_ownerImage.setImageResource(R.drawable.username_white);
        }
        else{
            imageView_ownerImage.setImageBitmap(photo);
        }

        imageView_ownerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        getDevicesDataFromServer();

        getGesturePasswordFromServer();

        if(startService){
            Intent serviceIntent=new Intent(this, LockService.class);
            serviceIntent.putExtra("phoneNumber",phoneNumber);
            serviceIntent.putExtra("imei",getImei());
            startService(serviceIntent);
            startService=false;
        }


    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.main_item,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final String gatewayName=devices.get(position).gatewayName;
            final String gatewayCode=devices.get(position).gatewayCode;
            final String gatewayLocation=devices.get(position).gatewayLocation;
            final String gatewayComment=devices.get(position).gatewayComment;
            int gatewayStatus=devices.get(position).gatewayStatus;

            holder.textView_deviceName.setText(gatewayName);
            holder.textView_deviceLocation.setText(gatewayLocation);
            holder.textView_deviceComment.setText(gatewayComment);
            if(gatewayStatus==4){
                holder.textView_deviceState.setText("工作正常");
                holder.image_gateway.setImageResource(R.drawable.gateway_green);
            }
            else if(gatewayStatus==5){
                holder.textView_deviceState.setText("工作异常");
                holder.image_gateway.setImageResource(R.drawable.caution);
            }
            else if(gatewayStatus==6){
                holder.textView_deviceState.setText("网关失联");
                holder.image_gateway.setImageResource(R.drawable.caution);
            }

            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.lockRecyclerView.setLayoutManager(linearLayoutManager);
            LockRecyclerAdapter adapter=new LockRecyclerAdapter(position,gatewayCode);
            holder.lockRecyclerView.setAdapter(adapter);
            holder.lockRecyclerView.setVisibility(View.GONE);
            holder.lockRecyclerView.setBackgroundColor(getResources().getColor(R.color.slightGray));
            holder.lockRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                private Drawable mDrawable;
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    super.onDraw(c, parent, state);
                    mDrawable=MainActivity.this.getResources().getDrawable(R.drawable.segmentation_locklist);
                    final int left=parent.getLeft();
                    final int right=parent.getRight();
                    int childCount=parent.getChildCount();
                    for(int i=0;i<childCount-1;i++){
                        View child=parent.getChildAt(i);
                        int top=child.getBottom();
                        int bottom=top+mDrawable.getIntrinsicHeight();
                        mDrawable.setBounds(left,top,right,bottom);
                        mDrawable.draw(c);
                    }
                }
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(0,0,0,0);
                }

            });

            holder.imageView_Expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.lockRecyclerView.isShown()){
                        holder.lockRecyclerView.setVisibility(View.GONE);
                    }
                    else {
                        holder.lockRecyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
            holder.linearLayout_upperItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this,GatewayDetailActivity.class);
                    intent.putExtra("phoneNumber",phoneNumber);
                    intent.putExtra("gatewayName",gatewayName);
                    intent.putExtra("gatewayCode",gatewayCode);
                    intent.putExtra("gatewayLocation",gatewayLocation);
                    intent.putExtra("gatewayComment",gatewayComment);
                    intent.putExtra("DeviceInfo", devices.get(position));
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return devices.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder{

            public ImageView image_gateway;
            public TextView textView_deviceName;
            public TextView textView_deviceLocation;
            public TextView textView_deviceComment;
            public TextView textView_deviceState;
            public RecyclerView lockRecyclerView;
            public ImageView imageView_Expand;
            public LinearLayout linearLayout_upperItem;
            public MyViewHolder(View itemView) {
                super(itemView);
                image_gateway=(ImageView)itemView.findViewById(R.id.image_gateway);
                textView_deviceName=(TextView)itemView.findViewById(R.id.textView_deviceName);
                textView_deviceLocation=(TextView)itemView.findViewById(R.id.textView_deviceLocation);
                textView_deviceComment=(TextView)itemView.findViewById(R.id.textView_deviceComment);
                textView_deviceState=(TextView)itemView.findViewById(R.id.textView_deviceState);
                lockRecyclerView=(RecyclerView)itemView.findViewById(R.id.lockRecyclerView);
                imageView_Expand=(ImageView)itemView.findViewById(R.id.imageView_expand);
                linearLayout_upperItem=(LinearLayout)itemView.findViewById(R.id.linearLayout_upperItem);
            }
           }
    }

    class LeftListViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return leftText.length;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.left_list_view_item,parent,false);
            TextView textView_left=(TextView)view.findViewById(R.id.textView_left);
            ImageView icon_left=(ImageView)view.findViewById(R.id.icon_left);
            textView_left.setText(leftText[position]);
            icon_left.setImageResource(imageViewId[position]);
            return view;
        }
    }


    class LockRecyclerAdapter extends RecyclerView.Adapter<LockRecyclerAdapter.MyLockViewHolder>{
        public int gatewayPosition;
        public String bindGatewayCode;
        public LockRecyclerAdapter(int position,String gatewayCode){
            gatewayPosition=position;
            bindGatewayCode=gatewayCode;
        }

        @Override
        public LockRecyclerAdapter.MyLockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.main_lock_item,parent,false);
            MyLockViewHolder holder=new MyLockViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(LockRecyclerAdapter.MyLockViewHolder holder, int position) {

            final DeviceData.DeviceInfo.LockInfo lockInfo=devices.get(gatewayPosition).lockLists.get(position);
            holder.textView_lockName.setText(lockInfo.lockName);
            holder.textView_lockLocation.setText(lockInfo.lockLocation);
            holder.textView_lockComment.setText(lockInfo.lockComment);
            if(lockInfo.lockStatus==1){
                holder.textView_lockState.setText("工作正常");
                holder.textView_lockState.setTextColor(getResources().getColor(R.color.green));
                holder.lock_image.setImageResource(R.drawable.lock_green);
            }
            else if(lockInfo.lockStatus==2){
                holder.textView_lockState.setText("工作异常");
                holder.textView_lockState.setTextColor(Color.RED);
                holder.lock_image.setImageResource(R.drawable.caution);
            }
            else if(lockInfo.lockStatus==3){
                holder.textView_lockState.setText("门锁失联");
                holder.textView_lockState.setTextColor(Color.RED);
                holder.lock_image.setImageResource(R.drawable.caution);
            }
            if(lockInfo.lockPower==1){
                holder.image_battery.setImageResource(R.drawable.battery20);
                holder.textView_batteryStatus.setText("门锁电量不足!");
                holder.textView_lockState.setTextColor(Color.RED);
                holder.lock_image.setImageResource(R.drawable.caution);
            }
            else if(lockInfo.lockPower==2){
                holder.image_battery.setImageResource(R.drawable.battery40);
            }
            else if(lockInfo.lockPower==3){
                holder.image_battery.setImageResource(R.drawable.battery60);
            }
            else if(lockInfo.lockPower==4){
                holder.image_battery.setImageResource(R.drawable.battery80);
            }
            else if(lockInfo.lockPower==5){
                holder.image_battery.setImageResource(R.drawable.battery100);
            }

            holder.linearLayout_lockItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this,LockDetailActivity.class);
                    intent.putExtra("lockInfo",lockInfo);
                    intent.putExtra("phoneNumber",phoneNumber);
                    intent.putExtra("gatewayCode",bindGatewayCode);
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            if(devices.get(gatewayPosition).lockLists!=null){
                return devices.get(gatewayPosition).lockLists.size();
            }
            else {
                return 0;
            }
        }

        class MyLockViewHolder extends RecyclerView.ViewHolder{

            public ImageView lock_image;
            public TextView textView_lockName;
            public TextView textView_lockLocation;
            public TextView textView_lockComment;
            public TextView textView_lockState;
            public ImageView image_battery;
            public TextView textView_batteryStatus;
            public LinearLayout linearLayout_lockItem;
            public MyLockViewHolder(View itemView) {
                super(itemView);
                lock_image=(ImageView)itemView.findViewById(R.id.lock_image);
                textView_lockName=(TextView)itemView.findViewById(R.id.textView_lockName);
                textView_lockLocation=(TextView)itemView.findViewById(R.id.textView_lockLocation);
                textView_lockComment=(TextView)itemView.findViewById(R.id.textView_lockComment);
                textView_lockState=(TextView)itemView.findViewById(R.id.textView_lockState);
                image_battery=(ImageView)itemView.findViewById(R.id.image_battery);
                textView_batteryStatus=(TextView)itemView.findViewById(R.id.textView_batteryStatus);
                linearLayout_lockItem=(LinearLayout)itemView.findViewById(R.id.linearLayout_lockItem);
            }
        }
    }


    public void showPopupWindow(){
        View contentView=LayoutInflater.from(this).inflate(R.layout.head_image_choice,null,false);
        final PopupWindow popupWindow=new PopupWindow(contentView,ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setAnimationStyle(R.style.animation);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(imageView_openLeft, Gravity.BOTTOM,0,0);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.3f;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);     //解决华为手机  PopupWindow 不变暗
        this.getWindow().setAttributes(lp);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        TextView text_gallery=(TextView)contentView.findViewById(R.id.choiceFromGallery);
        text_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImgIntent = new Intent(Intent.ACTION_PICK, null);
                pickImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickImgIntent,IMAGE_REQUEST_CODE);
                popupWindow.dismiss();

            }
        });
        TextView text_camera=(TextView)contentView.findViewById(R.id.choiceFromCamera);
        text_camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (hasSDCard()) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImgUri());
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "没有找到SD卡", Toast.LENGTH_SHORT).show();
                }
                popupWindow.dismiss();
            }
        });

    }

    private Uri getImgUri()
    {
        return  Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME));
    }
    private boolean hasSDCard()
    {
        String _state = Environment.getExternalStorageState();
        if (_state.equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void resizeImage(Uri uri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        //width : height
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //real values of the outputs
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void showResizeImage(Intent data)
    {
        Bundle extras = data.getExtras();
        if (extras != null)
        {
            Bitmap photo = (Bitmap) extras.get("data");
            saveBitmap(photo);
            imageView_ownerImage.setImageBitmap(photo);
        }
    }

    private void saveBitmap(Bitmap photo){
        File file=new File("/sdcard/IntelligentLock/","photo.png");
        file.mkdirs();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.PNG,99,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        else
        {
            switch (requestCode)
            {
                case IMAGE_REQUEST_CODE :
                {
                    resizeImage(data.getData());
                    break;
                }
                case CAMERA_REQUEST_CODE :
                {
                    if (hasSDCard())
                    {
                        resizeImage(getImgUri());
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case RESIZE_REQUEST_CODE :
                {
                    if (data != null)
                    {
                        showResizeImage(data);
                    }
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    // double back key exit application
    private boolean isExit=false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            if(isExit==false){
                isExit=true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit=false;
                    }
                },2000);
            }
            else{
                ((LockApplication)getApplication()).exitApp();
            }
        }
        return false;
    }


    public String getImei(){
        TelephonyManager manager=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public boolean judgeImei(String imei){
        if(getImei().equals(imei)){
            return true;
        }
        else{
            return false;
        }
    }

    public void notificationCall(){
        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent=new Intent(this,AlertActivity.class);
        intent.putExtra("abnormalDeviceData",abnormalDeviceData);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);

        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentTitle("警告");
        builder.setContentText("存在状态异常设备!");
        builder.setTicker("警告");
        builder.setSmallIcon(R.drawable.caution);
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        builder.setContentIntent(pendingIntent);
        Notification notification=builder.build();
        manager.notify(1,notification);
        sendNotification=0;
    }

    public void getDevicesDataFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("phoneNumber"+phoneNumber);
                if(phoneNumber==null){
                    phoneNumber=getIntent().getStringExtra("phoneNumber");
                }
                gatewayIpList=new DataBaseUtil(MainActivity.this).getAllGatewayIp(phoneNumber);

                System.out.println("gatewayIpList   "+gatewayIpList.size());
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
                    String consequence=HttpUtil.postData(jsonGetGatewayIp.toString(),ownerIp);
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
                        DataBaseUtil dataBaseUtil=new DataBaseUtil(MainActivity.this);
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
                                jsonRequest.put("sign",16);
                                jsonRequest.put("ownerPhoneNumber",phoneNumber);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("jsonRequest  "+jsonRequest);

                            String consequenceGetDeviceData=HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                            System.out.println("consequenceGetDeviceData  "+consequenceGetDeviceData);
                            Message message=new Message();
                            message.obj=consequenceGetDeviceData;
                            message.what=GetDeviceData;
                            handler.sendMessage(message);
                        }
                        Message message=new Message();
                        message.what=GetDataFinish;
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
                            jsonRequest.put("sign",16);
                            jsonRequest.put("ownerPhoneNumber",phoneNumber);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("jsonRequest  "+jsonRequest);

                        String consequence=HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                        System.out.println("consequence  "+consequence);
                        Message message=new Message();
                        message.obj=consequence;
                        message.what=GetDeviceData;
                        handler.sendMessage(message);
                    }
                    Message message=new Message();
                    message.what=GetDataFinish;
                    handler.sendMessage(message);
                }
            }
        }).start();

    }


    public void modifyPasswordFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("sign",27);
                    jsonObject.put("ownerPhoneNumber",phoneNumber);
                    String password=new Tea().encryptByTea("123456");
                    System.out.println("password  "+password);
                    jsonObject.put("ownerPassword",password);

                    //jsonObject.put("newPassword",new String(PasswordEncrypt.encryptPassword(newPassword,16)));
                    //jsonObject.put("newPassword",newPassword);

                    jsonObject.put("timetag",new TimeUtil().getTimetag());
                    System.out.println("jsonObject      "+jsonObject.toString());
                    String ownerIp=((LockApplication)getApplication()).getOwnerIp();
                    String result= HttpUtil.postData(jsonObject.toString(),ownerIp);
                    System.out.println("result 27     "+result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    private void getGesturePasswordFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonRequest=new JSONObject();
                try {
                    jsonRequest.put("sign",28);
                    jsonRequest.put("ownerPhoneNumber",phoneNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("jsonRequest  "+jsonRequest);
                String ownerIp=((LockApplication)getApplication()).getOwnerIp();
                System.out.println("sign 28   "+ownerIp);
                String consequence= HttpUtil.postData(jsonRequest.toString(),ownerIp);
                System.out.println("getGesturePasswordFromServer  consequence  "+consequence);

                if(consequence==null){
                    System.out.println("consequence null");

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"连接服务器失败，请检查网络！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        final int result=jsonResponse.getInt("result");

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(result==0){
                                    //Toast.makeText(MainActivity.this,"手势密码获取成功！",Toast.LENGTH_SHORT).show();
                                }
                                else if(result==1){
                                    Toast.makeText(MainActivity.this,"手势密码获取失败！",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        if(result==2){
                            new SPUtils(MainActivity.this).saveGesturePassword("");
                        }
                        else if(result==0){
                            String gesturePassword=jsonResponse.getString("gesturePassword");
                            new SPUtils(MainActivity.this).saveGesturePassword(gesturePassword);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
