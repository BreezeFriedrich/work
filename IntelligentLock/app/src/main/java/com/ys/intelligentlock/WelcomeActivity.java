package com.ys.intelligentlock;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2017/2/16.
 */

public class WelcomeActivity extends Activity {
    private ImageView imageView_welcome;
    private AnimationSet animationSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        imageView_welcome=(ImageView)findViewById(R.id.imageView_welcome);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memorySize = activityManager.getMemoryClass();
        System.out.println("maxMemory   "+memorySize);
         **/
        /**
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.welcome);
        imageView_welcome.setImageBitmap(bitmap);
         **/
        imageView_welcome.setImageResource(R.drawable.welcome);
        animationSet=new AnimationSet(true);
        AlphaAnimation alphaAnimation=new AlphaAnimation((float)1,(float)0.2);
        animationSet.setDuration(1500);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView_welcome.setImageDrawable(null);
                Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView_welcome.setAnimation(animationSet);
        imageView_welcome.startAnimation(animationSet);
    }

}
