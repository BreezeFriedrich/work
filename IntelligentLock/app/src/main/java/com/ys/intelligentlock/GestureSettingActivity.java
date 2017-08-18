package com.ys.intelligentlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.SPUtils;
import com.ys.intelligentlock.Utils.TimeUtil;
import com.ys.intelligentlock.gesturelock.ACache;
import com.ys.intelligentlock.gesturelock.LockPatternUtil;
import com.ys.intelligentlock.gesturelock.LockPatternView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2017/7/10.
 */

public class GestureSettingActivity extends Activity{
    private TextView textView_message;
    private LockPatternView lockPatternView;
    private Button button_forgetGesture;
    private ImageButton back;
    private String phoneNumber;
    private TextView textView_title;
    private String gesturePassword;
    private String gesturePasswordFromSP;
    private static final long DELAYTIME = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_setting);
        back=(ImageButton)findViewById(R.id.backOnGestureSetting);
        textView_title=(TextView)findViewById(R.id.textView_title);
        textView_message=(TextView)findViewById(R.id.textView_message);
        lockPatternView=(LockPatternView)findViewById(R.id.lockPatternView);
        button_forgetGesture=(Button)findViewById(R.id.button_forgetGesture);
        ((LockApplication)getApplication()).addActivity(this);
        phoneNumber=getIntent().getStringExtra("phoneNumber");

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        textView_title.setText("请输入原手势密码");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gesturePasswordFromSP=getGesturePasswordFromSP();
        System.out.println("gesturePasswordFromSP   "+gesturePasswordFromSP);
        if(gesturePasswordFromSP.equals("")){
            times=2;
            textView_title.setText("请设置新手势密码！");
            button_forgetGesture.setVisibility(View.GONE);
        }
        else{
            times=1;
            textView_title.setText("请输入原手势密码");
            button_forgetGesture.setVisibility(View.VISIBLE);
        }
        System.out.println("times   "+times);
        button_forgetGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GestureSettingActivity.this,SMSVerificationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void init() {
        lockPatternView.setOnPatternListener(patternListener);
        updateStatus(Status.DEFAULT);
    }


    private String getGesturePasswordFromUI(List<LockPatternView.Cell> cells){
        StringBuffer stringBuffer=new StringBuffer();
        for(int i=0;i<cells.size();i++){
            int index=cells.get(i).getIndex();
            stringBuffer.append(index);
        }
        String gesturePassword=stringBuffer.toString();
        return gesturePassword;
    }

    private String getGesturePasswordFromSP(){
        SPUtils spUtils=new SPUtils(GestureSettingActivity.this);
        return spUtils.getGesturePassword();
    }

    private void saveGesturePasswordToSP(String gesturePassword){
        new SPUtils(GestureSettingActivity.this).saveGesturePassword(gesturePassword);
    }

    public void saveGesturePasswordToServer(final String gesturePassword){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonRequest=new JSONObject();
                try {
                    jsonRequest.put("sign",29);
                    jsonRequest.put("ownerPhoneNumber",phoneNumber);
                    jsonRequest.put("gesturePassword",gesturePassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("jsonRequest  "+jsonRequest);
                String ownerIp=((LockApplication)getApplication()).getOwnerIp();
                String consequence= HttpUtil.postData(jsonRequest.toString(),ownerIp);
                System.out.println("consequence  "+consequence);

                if(consequence==null){
                    GestureSettingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GestureSettingActivity.this,"连接服务器失败，请检查网络！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        final int result=jsonResponse.getInt("result");
                        GestureSettingActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(result==0){
                                    Toast.makeText(GestureSettingActivity.this,"手势密码上传成功！",Toast.LENGTH_SHORT).show();
                                    textView_title.setText("设置手势密码成功！");
                                    lockPatternView.setVisibility(View.INVISIBLE);
                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    },1000);
                                }
                                else if(result==1){
                                    Toast.makeText(GestureSettingActivity.this,"手势密码上传失败！",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private int times=1;

    private String bufferGestureString;

    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if(pattern != null){
                if(times==1){
                    String gesturePasswordFromUI=getGesturePasswordFromUI(pattern);
                    System.out.println("gesturePasswordFromUI    "+gesturePasswordFromUI);

                    if(gesturePasswordFromUI.equals("")){
                        textView_message.setText("手势密码不能为空！");
                        return;
                    }
                    if(gesturePasswordFromUI.length()<4){
                        textView_message.setText("手势密码不能小于4个点！");
                        lockPatternView.postClearPatternRunnable(DELAYTIME);
                        return;
                    }
                    if(gesturePasswordFromUI.equals(gesturePasswordFromSP)){
                        textView_message.setText("原手势密码正确");
                        lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                        times=2;
                        textView_title.setText("请设置新手势密码！");
                    } else {
                        textView_message.setText("手势密码错误,请重试！");
                        lockPatternView.postClearPatternRunnable(DELAYTIME);
                        return;
                    }
                }
                else if(times==2){
                    String gesturePasswordFromUI=getGesturePasswordFromUI(pattern);
                    if(gesturePasswordFromUI.equals("")){
                        textView_message.setText("手势密码不能为空！");
                        return;
                    }
                    if(gesturePasswordFromUI.length()<4){
                        textView_message.setText("手势密码不能小于4个点！");
                        lockPatternView.postClearPatternRunnable(DELAYTIME);
                        return;
                    }
                    bufferGestureString=gesturePasswordFromUI;
                    lockPatternView.postClearPatternRunnable(DELAYTIME);
                    times=3;
                    textView_title.setText("请再次输入刚才的手势密码！");
                }
                else if(times==3){
                    String gesturePasswordFromUI=getGesturePasswordFromUI(pattern);
                    if(gesturePasswordFromUI.equals(bufferGestureString)){
                        textView_message.setText("两次手势密码一致");
                        lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                        textView_title.setText(" ");
                        saveGesturePasswordToSP(gesturePasswordFromUI);
                        saveGesturePasswordToServer(gesturePasswordFromUI);
                    }
                    else {
                        textView_message.setText("两次手势密码不一致！");
                        lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                        lockPatternView.postClearPatternRunnable(DELAYTIME);
                        times=2;
                    }
                }
            }
        }
    };

    /**
     * 更新状态
     * @param status
     */

    private void updateStatus(Status status){
        textView_message.setText(status.strId);
        textView_message.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case LESSERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case CONFIRMERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case FIRSTCORRECT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case CONFIRMCORRECT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                Toast.makeText(GestureSettingActivity.this, "手势密码设置成功！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private enum Status {
        //默认的状态，刚开始的时候（初始化状态）
        DEFAULT(R.string.create_gesture_default, R.color.grey_a5a5a5),
        //第一次记录成功
        FIRSTCORRECT(R.string.create_gesture_correct, R.color.grey_a5a5a5),
        //连接的点数小于4（二次确认的时候就不再提示连接的点数小于4，而是提示确认错误）
        LESSERROR(R.string.create_gesture_less_error, R.color.red_f4333c),
        //二次确认错误
        CONFIRMERROR(R.string.create_gesture_confirm_error, R.color.red_f4333c),
        //二次确认正确
        CONFIRMCORRECT(R.string.create_gesture_confirm_correct, R.color.grey_a5a5a5);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }
        private int strId;
        private int colorId;
    }


}
