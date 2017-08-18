package com.ys.intelligentlock;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.JsonData.AuthorizationUserData;
import com.ys.intelligentlock.JsonData.DeviceData;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/12.
 */

public class LockDetailActivity extends Activity implements View.OnClickListener{
    private ImageButton backOnLockDetail;
    private ImageButton imageButton_lockSetting;
    private TextView textView_detailLockName;
    private TextView textView_detailLockLocation;
    private TextView textView_detailLockComment;
    private TextView textView_detailLockCode;
    private TextView textView_bindGatewayName;
    private LinearLayout linearLayout_passwordSetting;
    private LinearLayout linearLayout_addAuthorizedUser;
    private RecyclerView userRecyclerView;
    //private List<Map<String,String>> userList;

    private String gatewayCode;
    private String phoneNumber;

    private String lockName;
    private String lockLocation;
    private String lockComment;
    private String lockCode;
    private Handler handler;
    private final int GetUser=0x11;
    private AuthorizationUserData authorizationUserData;
    private List<AuthorizationUserData.UserInfo> userList;
    private final int ModifyLockSuccess=0x33;
    private final int DeleteLockSuccess=0x44;
    private final int LOCKSETTING=0x55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_detail);
        backOnLockDetail=(ImageButton)findViewById(R.id.backOnLockDetail);
        imageButton_lockSetting=(ImageButton)findViewById(R.id.imageButton_lockSetting);
        textView_detailLockName=(TextView)findViewById(R.id.textView_detailLockName);
        textView_detailLockLocation=(TextView)findViewById(R.id.textView_detailLockLocation);
        textView_detailLockCode=(TextView)findViewById(R.id.textView_detailLockCode);
        textView_detailLockComment=(TextView)findViewById(R.id.textView_detailLockComment);
        textView_bindGatewayName=(TextView)findViewById(R.id.textView_bindGatewayName);

        Intent intent=getIntent();
        phoneNumber=intent.getStringExtra("phoneNumber");
        gatewayCode=intent.getStringExtra("gatewayCode");
        DeviceData.DeviceInfo.LockInfo lockInfo= (DeviceData.DeviceInfo.LockInfo) intent.getSerializableExtra("lockInfo");

        lockName=lockInfo.lockName;
        lockLocation=lockInfo.lockLocation;
        lockComment=lockInfo.lockComment;
        lockCode=lockInfo.lockCode;

        textView_detailLockName.setText(lockName);
        textView_detailLockLocation.setText(lockLocation);
        textView_detailLockComment.setText(lockComment);
        textView_detailLockCode.setText(lockCode);
        textView_bindGatewayName.setText(gatewayCode);

        linearLayout_passwordSetting=(LinearLayout)findViewById(R.id.linearLayout_passwordSetting);
        linearLayout_addAuthorizedUser=(LinearLayout)findViewById(R.id.linearLayout_addAuthorizedUser);
        userRecyclerView=(RecyclerView)findViewById(R.id.userRecyclerView);
        linearLayout_addAuthorizedUser.setOnClickListener(this);
        linearLayout_passwordSetting.setOnClickListener(this);
        backOnLockDetail.setOnClickListener(this);
        imageButton_lockSetting.setOnClickListener(this);

        ((LockApplication)getApplication()).addActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==GetUser){
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(LockDetailActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Gson gson=new Gson();
                    authorizationUserData=gson.fromJson(consequence,AuthorizationUserData.class);
                    userList=authorizationUserData.userList;
                    if(authorizationUserData.result==1){
                        Toast.makeText(LockDetailActivity.this,"当前无授权开锁人！",Toast.LENGTH_SHORT).show();
                        UserRecyclerViewAdapter adapter=new UserRecyclerViewAdapter();
                        userRecyclerView.setAdapter(adapter);
                    }
                    else if(authorizationUserData.result==0){
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(LockDetailActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        userRecyclerView.setLayoutManager(linearLayoutManager);
                        UserRecyclerViewAdapter adapter=new UserRecyclerViewAdapter();
                        userRecyclerView.setAdapter(adapter);
                        userRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                            private Drawable mDrawable;
                            @Override
                            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                                super.onDraw(c, parent, state);
                                mDrawable=LockDetailActivity.this.getResources().getDrawable(R.drawable.segmentation_locklist);
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
                    }
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonRequest=new JSONObject();
                try {
                    jsonRequest.put("sign",17);
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
                message.what=GetUser;
                handler.sendMessage(message);

            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linearLayout_addAuthorizedUser:
                String gesturePassword=getGesturePasswordFromSP();
                if(gesturePassword.equals("")){
                    new AlertDialog.Builder(LockDetailActivity.this).setTitle("提示").setMessage("授权手势密码未设置！").
                            setPositiveButton("设置手势密码", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent gestureIntent=new Intent(LockDetailActivity.this,GestureSettingActivity.class);
                            gestureIntent.putExtra("phoneNumber",phoneNumber);
                            gestureIntent.putExtra("gatewayCode",gatewayCode);
                            gestureIntent.putExtra("lockCode",lockCode);
                            startActivity(gestureIntent);
                        }
                    }).setNegativeButton("取消",null).show();
                }
                else {
                    Intent addIntent=new Intent(LockDetailActivity.this,GestureEnterActivity.class);
                    addIntent.putExtra("sign",1);
                    addIntent.putExtra("phoneNumber",phoneNumber);
                    addIntent.putExtra("gatewayCode",gatewayCode);
                    addIntent.putExtra("lockCode",lockCode);
                    startActivity(addIntent);
                }
                break;
            case R.id.linearLayout_passwordSetting:
                String gesturePassword2=getGesturePasswordFromSP();
                if(gesturePassword2.equals("")){
                    new AlertDialog.Builder(LockDetailActivity.this).setTitle("提示").setMessage("授权手势密码未设置！").
                            setPositiveButton("设置手势密码", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent gestureIntent=new Intent(LockDetailActivity.this,GestureSettingActivity.class);
                                    gestureIntent.putExtra("phoneNumber",phoneNumber);
                                    gestureIntent.putExtra("gatewayCode",gatewayCode);
                                    gestureIntent.putExtra("lockCode",lockCode);
                                    startActivity(gestureIntent);
                                }
                            }).setNegativeButton("取消",null).show();
                }
                else {
                    Intent passwordIntent=new Intent(LockDetailActivity.this,GestureEnterActivity.class);
                    passwordIntent.putExtra("sign",2);
                    passwordIntent.putExtra("phoneNumber",phoneNumber);
                    passwordIntent.putExtra("gatewayCode",gatewayCode);
                    passwordIntent.putExtra("lockCode",lockCode);
                    startActivity(passwordIntent);
                }

                break;
            case R.id.backOnLockDetail:
                finish();
                break;
            case R.id.imageButton_lockSetting:
                lockName=textView_detailLockName.getText().toString();
                lockLocation=textView_detailLockLocation.getText().toString();
                lockComment=textView_detailLockComment.getText().toString();
                Intent settingIntent=new Intent(LockDetailActivity.this,LockSettingActivity.class);
                settingIntent.putExtra("phoneNumber",phoneNumber);
                settingIntent.putExtra("gatewayCode",gatewayCode);
                settingIntent.putExtra("lockCode",lockCode);
                settingIntent.putExtra("lockName",lockName);
                settingIntent.putExtra("lockLocation",lockLocation);
                settingIntent.putExtra("lockComment",lockComment);
                startActivityForResult(settingIntent,LOCKSETTING);
                //startActivity(settingIntent);
                break;

        }
    }

    private String getGesturePasswordFromSP(){
        SPUtils spUtils=new SPUtils(LockDetailActivity.this);
        return spUtils.getGesturePassword();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==LOCKSETTING){
            if(resultCode==ModifyLockSuccess){
                textView_detailLockName.setText(data.getStringExtra("lockName"));
                textView_detailLockLocation.setText(data.getStringExtra("lockLocation"));
                textView_detailLockComment.setText(data.getStringExtra("lockComment"));
            }
            else if(resultCode==DeleteLockSuccess){
                finish();
            }
        }
    }

    class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserItemViewHolder>{
        @Override
        public UserItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(LockDetailActivity.this).inflate(R.layout.user_item,parent,false);
            UserItemViewHolder holder=new UserItemViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(UserItemViewHolder holder, int position) {
            final AuthorizationUserData.UserInfo userInfo=userList.get(position);
            holder.textView_authorizedUserName.setText(userInfo.name);
            holder.textView_authorizedUserCardNumber.setText(userInfo.cardNumb);
            holder.textView_userStartTime.setText(timeShow(userInfo.startTime));
            holder.textView_userEndTime.setText(timeShow(userInfo.endTime));
            holder.linearLayout_userItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent userDetailIntent=new Intent(LockDetailActivity.this,UserDetailActivity.class);
                    userDetailIntent.putExtra("userInfo",userInfo);
                    userDetailIntent.putExtra("phoneNumber",phoneNumber);
                    userDetailIntent.putExtra("lockCode",lockCode);
                    userDetailIntent.putExtra("gatewayCode",gatewayCode);
                    startActivity(userDetailIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            if(userList!=null){
                return userList.size();
            }
            else return 0;
        }

        class UserItemViewHolder extends RecyclerView.ViewHolder{
            public TextView textView_authorizedUserName;
            public TextView textView_authorizedUserCardNumber;
            public TextView textView_userStartTime;
            public TextView textView_userEndTime;
            public LinearLayout linearLayout_userItem;

            public UserItemViewHolder(View itemView) {
                super(itemView);
                textView_authorizedUserName=(TextView)itemView.findViewById(R.id.textView_authorizedUserName);
                textView_authorizedUserCardNumber=(TextView)itemView.findViewById(R.id.textView_authorizedUserCardNumber);
                textView_userStartTime=(TextView)itemView.findViewById(R.id.textView_user_startTime);
                textView_userEndTime=(TextView)itemView.findViewById(R.id.textView_user_endTime);
                linearLayout_userItem=(LinearLayout)itemView.findViewById(R.id.linearLayout_userItem);
            }
        }
    }

    public String timeShow(String original){
        String consequence=original.substring(0,4)+"年"+original.substring(4,6)+"月"+original.substring(6,8)+"日"
                +original.substring(8,10)+"时"+original.substring(10,12)+"分";
        return consequence;
    }
}
