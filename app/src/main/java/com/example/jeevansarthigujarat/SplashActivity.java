package com.example.jeevansarthigujarat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    ImageView iv;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);
        getSupportActionBar().hide();
        iv=findViewById(R.id.splash_logo);

        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(2700);
        iv.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sp.getString(ConstantSp.USERID,"").equalsIgnoreCase("")){
                    new ToastIntentClass(SplashActivity.this,LoginActivity.class);
                    finish();
                }
                else {
                    if(sp.getString(ConstantSp.TYPE,"").equals("1")) {
                        new ToastIntentClass(SplashActivity.this, HospitalRegisterActivity.class);
                        finish();
                    }
                    else if(sp.getString(ConstantSp.TYPE,"").equals("2")) {
                        new ToastIntentClass(SplashActivity.this, AdminHomeActivity.class);
                        finish();
                    }
                    else{
                        new ToastIntentClass(SplashActivity.this, BottomTabActivity.class);
                        finish();
                    }
                }

            }
        }, 2000);
    }
}