package com.ys.intelligentlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.SPUtils;
import com.ys.intelligentlock.gesturelock.LockPatternView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by admin on 2017/7/20.
 */

public class GestureEnterActivity extends Activity {

    private TextView textView_message;
    private LockPatternView lockPatternView;
    private Button button_forgetGesture;
    private ImageButton back;
    private String phoneNumber;
    private String gesturePassword;
    private String gesturePasswordFromSP;
    private static final long DELAYTIME = 2000;
    private int sign;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_enter);
        back=(ImageButton)findViewById(R.id.backOnGestureEnter);
        textView_message=(TextView)findViewById(R.id.textView_message);
        lockPatternView=(LockPatternView)findViewById(R.id.lockPatternView);
        button_forgetGesture=(Button)findViewById(R.id.button_forgetGesture);
        ((LockApplication)getApplication()).addActivity(this);
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        sign=getIntent().getIntExtra("sign",0);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_forgetGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GestureEnterActivity.this,SMSVerificationActivity.class);
                intent.putExtra("phoneNumber",phoneNumber);
                startActivity(intent);
                finish();
            }
        });

        gesturePasswordFromSP=getGesturePasswordFromSP();
        System.out.println("gesturePasswordFromSP   "+gesturePasswordFromSP);
    }

    private void init() {
        lockPatternView.setOnPatternListener(patternListener);
        updateStatus(GestureEnterActivity.Status.DEFAULT);
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
        SPUtils spUtils=new SPUtils(GestureEnterActivity.this);
        return spUtils.getGesturePassword();
    }

    private void saveGesturePasswordToSP(String gesturePassword){
        new SPUtils(GestureEnterActivity.this).saveGesturePassword(gesturePassword);
    }


    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if(pattern != null){
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
                        if(sign==1){
                            Intent addIntent=new Intent(GestureEnterActivity.this,AddUserActivity.class);
                            addIntent.putExtra("phoneNumber",phoneNumber);
                            Intent intent=getIntent();
                            String gatewayCode=intent.getStringExtra("gatewayCode");
                            String lockCode=intent.getStringExtra("lockCode");
                            addIntent.putExtra("gatewayCode",gatewayCode);
                            addIntent.putExtra("lockCode",lockCode);
                            startActivity(addIntent);
                            finish();
                        }
                        else if(sign==2){
                            Intent addIntent=new Intent(GestureEnterActivity.this,SetPasswordActivity.class);
                            addIntent.putExtra("phoneNumber",phoneNumber);
                            Intent intent=getIntent();
                            String gatewayCode=intent.getStringExtra("gatewayCode");
                            String lockCode=intent.getStringExtra("lockCode");
                            addIntent.putExtra("gatewayCode",gatewayCode);
                            addIntent.putExtra("lockCode",lockCode);
                            startActivity(addIntent);
                            finish();
                        }
                    } else {
                        textView_message.setText("手势密码错误,请重试！");
                        lockPatternView.postClearPatternRunnable(DELAYTIME);
                        return;
                    }
            }
        }
    };

    /**
     * 更新状态
     * @param status
     */

    private void updateStatus(GestureEnterActivity.Status status){
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
                Toast.makeText(GestureEnterActivity.this, "手势密码设置成功！", Toast.LENGTH_SHORT).show();
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
