package com.example.azvk.nationalhockeyteams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private static int splash_time_out = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //checking if sharedPreferences file exists
                if(sharedPreferences.contains("firstname") && (sharedPreferences.contains("lastname"))){
                    Intent i = new Intent(SplashActivity.this, ApplicationActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        }, splash_time_out);
    }
}
