package com.example.azvk.nationalhockeyteams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;

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
        usernameText = (EditText)findViewById(R.id.loginUsername);
        passwordText = (EditText)findViewById(R.id.loginPassword);

        final User user = new User(usernameText.getText().toString(), passwordText.getText().toString());
        LoginService loginService = Generator.createService(LoginService.class);
        Call<User> call = loginService.userLogin(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });/*
        Intent i = new Intent(this, ApplicationActivity.class);
        startActivity(i);*/
    }
}
