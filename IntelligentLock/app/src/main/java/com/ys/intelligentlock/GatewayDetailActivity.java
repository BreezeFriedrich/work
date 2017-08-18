package com.ys.intelligentlock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.JsonData.DeviceData;
import com.ys.intelligentlock.Utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/12.
 */

public class GatewayDetailActivity extends Activity implements View.OnClickListener{
    private ImageButton backOnGatewayDetail;
    private ImageButton imageButton_gatewaySetting;
    private TextView textView_gatewayName;
    private TextView textView_gatewayLocation;
    private TextView textView_gatewayComment;
    private TextView textView_gatewayCode;
    private String gatewayName;
    private String gatewayLocation;
    private String gatewayComment;
    private String gatewayCode;
    private String phoneNumber;

    private LinearLayout linearLayout_addConnectLock;
    private RecyclerView lockRecycler;
    private LockRecyclerAdapter adapter;

    public List<DeviceData.DeviceInfo.LockInfo> lockLists;

    public final int GatewaySetting=0x11;
    private final int ModifyGatewaySuccess=0x33;
    private final int DeleteGatewaySuccess=0x44;

    private final int ModifyLock=0x55;
    private final int AddLock=0x44;
    private Handler handler;
    private final int GetDeviceData=0x99;
    private DeviceData deviceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gateway_detail);
        backOnGatewayDetail=(ImageButton)findViewById(R.id.backOnGatewayDetail);
        imageButton_gatewaySetting=(ImageButton)findViewById(R.id.imageButton_gatewaySetting);
        textView_gatewayName=(TextView)findViewById(R.id.textView_gatewayName);
        textView_gatewayLocation=(TextView)findViewById(R.id.textView_gatewayLocation);
        textView_gatewayComment=(TextView)findViewById(R.id.textView_gatewayComment);
        textView_gatewayCode=(TextView)findViewById(R.id.textView_gatewayCode);
        linearLayout_addConnectLock=(LinearLayout)findViewById(R.id.linearLayout_addConnectLock);
        lockRecycler=(RecyclerView)findViewById(R.id.lockRecyclerViewOnGatewayDetail);
        ((LockApplication)getApplication()).addActivity(this);

        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("phoneNumber");
        gatewayName=intent.getStringExtra("gatewayName");
        gatewayLocation=intent.getStringExtra("gatewayLocation");
        gatewayComment=intent.getStringExtra("gatewayComment");
        gatewayCode=intent.getStringExtra("gatewayCode");
        DeviceData.DeviceInfo deviceInfo= (DeviceData.DeviceInfo) intent.getSerializableExtra("DeviceInfo");
        lockLists=deviceInfo.lockLists;
        textView_gatewayName.setText(gatewayName);
        textView_gatewayLocation.setText(gatewayLocation);
        textView_gatewayComment.setText(gatewayComment);
        textView_gatewayCode.setText(gatewayCode);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backOnGatewayDetail.setOnClickListener(this);
        imageButton_gatewaySetting.setOnClickListener(this);
        linearLayout_addConnectLock.setOnClickListener(this);

        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lockRecycler.setLayoutManager(manager);
        adapter=new LockRecyclerAdapter();
        lockRecycler.setAdapter(adapter);
        lockRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            private Drawable mDrawable;
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                mDrawable=GatewayDetailActivity.this.getResources().getDrawable(R.drawable.segmentation);
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
                mDrawable=GatewayDetailActivity.this.getResources().getDrawable(R.drawable.segmentation);
                outRect.set(0,0,0,0);
            }
        });

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==GetDeviceData){
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(GatewayDetailActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Gson gson=new Gson();
                    deviceData=gson.fromJson(consequence,DeviceData.class);

                    if(deviceData.result==1){
                        Toast.makeText(GatewayDetailActivity.this,"获取设备数据失败！",Toast.LENGTH_SHORT).show();
                    }
                    else if(deviceData.result==0){
                        List<DeviceData.DeviceInfo> devices=deviceData.devices;
                        for(DeviceData.DeviceInfo deviceInfo:devices){
                            if(deviceInfo.gatewayCode.equals(gatewayCode)){
                                System.out.println("update data");
                                lockLists=deviceInfo.lockLists;
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.backOnGatewayDetail:
                finish();
                break;
            case R.id.imageButton_gatewaySetting:
                gatewayName=textView_gatewayName.getText().toString();
                gatewayLocation=textView_gatewayLocation.getText().toString();
                gatewayComment=textView_gatewayComment.getText().toString();
                Intent gatewaySettingIntent=new Intent(GatewayDetailActivity.this,GatewaySettingActivity.class);
                gatewaySettingIntent.putExtra("phoneNumber",phoneNumber);
                gatewaySettingIntent.putExtra("gatewayCode",gatewayCode);
                gatewaySettingIntent.putExtra("gatewayName",gatewayName);
                gatewaySettingIntent.putExtra("gatewayLocation",gatewayLocation);
                gatewaySettingIntent.putExtra("gatewayComment",gatewayComment);

                startActivityForResult(gatewaySettingIntent,GatewaySetting);
                break;
            case R.id.linearLayout_addConnectLock:
                Intent addConnectLockIntent=new Intent(GatewayDetailActivity.this,AddLockActivity.class);
                addConnectLockIntent.putExtra("phoneNumber",phoneNumber);
                addConnectLockIntent.putExtra("gatewayCode",gatewayCode);
                startActivityForResult(addConnectLockIntent,AddLock);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GatewaySetting){
            if(resultCode==ModifyGatewaySuccess){
                System.out.println("ModifyGatewaySuccess"+data.getStringExtra("gatewayComment"));
                textView_gatewayName.setText(data.getStringExtra("gatewayName"));
                textView_gatewayLocation.setText(data.getStringExtra("gatewayLocation"));
                textView_gatewayComment.setText(data.getStringExtra("gatewayComment"));
            }
            else if(resultCode==DeleteGatewaySuccess){
                System.out.println("gateway finish");
                finish();
            }
        }
        else if(requestCode==AddLock || requestCode==ModifyLock){
            getLockDataFromServer();
        }
    }

    class LockRecyclerAdapter extends RecyclerView.Adapter<LockRecyclerAdapter.MyLockViewHolder>{

        @Override
        public LockRecyclerAdapter.MyLockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(GatewayDetailActivity.this).inflate(R.layout.main_lock_item,parent,false);
            MyLockViewHolder holder=new MyLockViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(LockRecyclerAdapter.MyLockViewHolder holder, int position) {
            final DeviceData.DeviceInfo.LockInfo lockInfo=lockLists.get(position);
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
                    Intent intent=new Intent(GatewayDetailActivity.this,LockDetailActivity.class);
                    intent.putExtra("lockInfo",lockInfo);
                    intent.putExtra("phoneNumber",phoneNumber);
                    intent.putExtra("gatewayCode",gatewayCode);
                    startActivityForResult(intent,ModifyLock);
                }
            });
        }

        @Override
        public int getItemCount() {
            if(lockLists!=null){
                return lockLists.size();
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

    public void getLockDataFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String gatewayIp=((LockApplication)getApplication()).getGatewayIp(gatewayCode);
                JSONObject jsonRequest=new JSONObject();
                try {
                    jsonRequest.put("sign",16);
                    jsonRequest.put("ownerPhoneNumber",phoneNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("jsonRequest  "+jsonRequest);

                String consequenceGetDeviceData= HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                System.out.println("consequenceGetDeviceData  "+consequenceGetDeviceData);
                Message message=new Message();
                message.obj=consequenceGetDeviceData;
                message.what=GetDeviceData;
                handler.sendMessage(message);
            }
        }).start();
    }

}
