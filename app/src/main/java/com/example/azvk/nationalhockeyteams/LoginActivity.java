package com.example.azvk.nationalhockeyteams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton;

        loginButton = (Button)findViewById(R.id.loginButton);

        if (loginButton == null) throw new AssertionError();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClicked();
            }
        });
    }

    public void loginButtonClicked(){
        Intent i = new Intent(this, ApplicationActivity.class);
        startActivity(i);
    }
}
