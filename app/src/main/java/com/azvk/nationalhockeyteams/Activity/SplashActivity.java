package com.azvk.nationalhockeyteams.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.azvk.nationalhockeyteams.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //running time of splash activity
        int splash_time_out = 3000;

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //checking if sharedPreferences file exists
                if (sharedPreferences.contains("username") && (sharedPreferences.contains("password"))) {
                    Intent i = new Intent(SplashActivity.this, TeamActivity.class);
                    finish();
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        }, splash_time_out);
    }
}
