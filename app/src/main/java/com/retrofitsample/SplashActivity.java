package com.retrofitsample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.google.firebase.iid.FirebaseInstanceId;

public class SplashActivity extends BaseActivity{
    private String deviceToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gatAppName();



    }

    private void gatAppName() {
        deviceToken = FirebaseInstanceId.getInstance().getToken();
        if (deviceToken!=null){
            preference.setDeviceToken(deviceToken);
        }
        callNewScreen();

    }

    private void callNewScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preference.getEmail()!=null&&preference.getEmail().length()>0){
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(SplashActivity.this, SignInNewActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 3000);
    }


}
