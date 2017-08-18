package com.ys.intelligentlock;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ys.intelligentlock.AppManage.LockApplication;


/**
 * Created by Administrator on 2016/4/25.
 */
public class SupportActivity extends Activity implements View.OnClickListener {
    private ImageButton backOnSupport;
    private TextView textView_phone1;
    private TextView textView_phone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support);

        backOnSupport=(ImageButton)findViewById(R.id.backOnSupport);
        textView_phone1 = (TextView) findViewById(R.id.textView_phone1);
        textView_phone2 = (TextView) findViewById(R.id.textView_phone2);
        backOnSupport.setOnClickListener(this);
        textView_phone1.setOnClickListener(this);
        textView_phone2.setOnClickListener(this);

        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backOnSupport:
                finish();
                break;
            case R.id.textView_phone1:
                new AlertDialog.Builder(this).setMessage("是否拨打该客服电话?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String number1 = textView_phone1.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + number1);
                        intent.setData(data);
                        if (ActivityCompat.checkSelfPermission(SupportActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    }
                }).setNegativeButton("取消",null).show();
                break;
            case R.id.textView_phone2:
                new AlertDialog.Builder(this).setMessage("是否拨打该客服电话?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String number2=textView_phone2.getText().toString();
                        Intent intent2 = new Intent(Intent.ACTION_CALL);
                        Uri data2 = Uri.parse("tel:" + number2);
                        intent2.setData(data2);
                        if (ActivityCompat.checkSelfPermission(SupportActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent2);
                    }
                }).setNegativeButton("取消",null).show();
                break;
        }
    }
}
