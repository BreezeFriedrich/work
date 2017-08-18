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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.Utils.DataBaseUtil;
import com.ys.intelligentlock.Utils.HttpUtil;
import com.ys.intelligentlock.Utils.TimeUtil;
import com.google.zxing.client.android.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/1.
 */
public class AddGatewayActivity extends Activity implements View.OnClickListener{
    private EditText editText_gatewayName;
    private EditText editText_gatewayLocation;
    private EditText editText_gatewayComment;
    private TextView textView_gatewayCode;
    private Button button_saveGateway;
    private ImageButton backOnAddGateway;

    private String gatewayCode;
    private String phoneNumber;
    private String opCode;

    private Handler handler;
    private final int addGateway=0x33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_gateway);
        editText_gatewayName=(EditText)findViewById(R.id.editText_gatewayName);
        editText_gatewayLocation=(EditText)findViewById(R.id.editText_gatewayLocation);
        editText_gatewayComment=(EditText)findViewById(R.id.editText_gatewayComment);
        textView_gatewayCode=(TextView)findViewById(R.id.textView_gatewayCode);
        button_saveGateway=(Button)findViewById(R.id.button_saveGateway);
        backOnAddGateway=(ImageButton) findViewById(R.id.backOnAddGateway);

        phoneNumber=getIntent().getStringExtra("phoneNumber");
        gatewayCode=getIntent().getStringExtra("gatewayCode");
        opCode=getIntent().getStringExtra("opCode");
        textView_gatewayCode.setText(gatewayCode);
        backOnAddGateway.setOnClickListener(this);
        button_saveGateway.setOnClickListener(this);

        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==addGateway){
                    waitView.dismiss();
                    String consequence=(String)msg.obj;
                    if(consequence==null){
                        Toast.makeText(AddGatewayActivity.this,"连接服务器失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONObject jsonResponse=new JSONObject(consequence);
                        int result=jsonResponse.getInt("result");
                        if(result==0){
                            Toast.makeText(AddGatewayActivity.this,"添加网关成功！",Toast.LENGTH_SHORT).show();
                            Intent mainIntent=new Intent(AddGatewayActivity.this,MainActivity.class);
                            mainIntent.putExtra("phoneNumber",phoneNumber);
                            startActivity(mainIntent);
                            finish();
                        }
                        else if(result==1){
                            Toast.makeText(AddGatewayActivity.this,"添加网关失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backOnAddGateway:
                finish();
                break;
            case R.id.button_saveGateway:
                showWait();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonRequest=new JSONObject();
                        try {
                            jsonRequest.put("sign",8);
                            jsonRequest.put("ownerPhoneNumber",phoneNumber);
                            jsonRequest.put("gatewayName",editText_gatewayName.getText().toString());
                            jsonRequest.put("gatewayCode",gatewayCode);
                            jsonRequest.put("gatewayLocation",editText_gatewayLocation.getText().toString());
                            jsonRequest.put("gatewayComment",editText_gatewayComment.getText().toString());
                            jsonRequest.put("opCode",opCode);
                            jsonRequest.put("timetag", new TimeUtil().getTimetag());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("jsonRequest  "+jsonRequest);
                        String gatewayIp=new DataBaseUtil(AddGatewayActivity.this).getGatewayIp(phoneNumber,gatewayCode);
                        System.out.println("gatewayIp    "+gatewayIp);
                        String consequence=HttpUtil.postData(jsonRequest.toString(),gatewayIp);
                        System.out.println("consequence  "+consequence);
                        Message message=new Message();
                        message.obj=consequence;
                        message.what=addGateway;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
        }
    }

    private PopupWindow waitView;
    public void showWait(){
        View contentView= LayoutInflater.from(this).inflate(R.layout.wait,null,false);
        waitView=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT,true);
        waitView.setAnimationStyle(R.style.animation);
        waitView.setOutsideTouchable(true);
        waitView.showAtLocation(backOnAddGateway, Gravity.CENTER,0,0);
    }

    /**
        房地产与阶层固化

     */
}
