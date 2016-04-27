package com.example.azvk.nationalhockeyteams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    Button loginButton;
    Button signinButton;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = null;

        loginButton = (Button)findViewById(R.id.loginButton);
        signinButton = (Button)findViewById(R.id.signinButton);

        if (loginButton == null) throw new AssertionError();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClicked();
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInDialog();
            }
        });
    }

    private void loginButtonClicked(){
        usernameText = (EditText)findViewById(R.id.loginUsername);
        passwordText = (EditText)findViewById(R.id.loginPassword);

        if (isEmpty(usernameText) || isEmpty(passwordText)){
            Toast.makeText(LoginActivity.this, "Enter username/password", Toast.LENGTH_LONG).show();
        }
        else {
            final User user = new User(usernameText.getText().toString(), passwordText.getText().toString());
            LoginService loginService = Generator.createService(LoginService.class);
            Call<User> call = loginService.userLogin(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body().username != null) {
                        System.out.println("SUCCESS");
                        ifUserExists();
                    } else {
                        System.out.println("unSUCCESS");
                        Toast.makeText(LoginActivity.this, "Incorrect username/password information", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        }
    }

    private void ifUserExists(){
        dialog.dismiss();
        Intent i = new Intent(this, ApplicationActivity.class);
        finish();
        startActivity(i);
    }

    private void signInDialog(){

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.registration_form);
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(layoutParams);

        final EditText registration_username = (EditText)dialog.findViewById(R.id.registration_username);
        final EditText registration_password = (EditText)dialog.findViewById(R.id.registration_password);
        final EditText registration_reenter_password = (EditText)dialog.findViewById(R.id.registration_reenter_password);

        Button createAccountButton = (Button)dialog.findViewById(R.id.createAccountButton);
        Button cancelButton = (Button)dialog.findViewById(R.id.registrationCancelButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(registration_username) || isEmpty(registration_password) || isEmpty(registration_reenter_password)) {
                    Toast.makeText(LoginActivity.this, "Enter all information", Toast.LENGTH_LONG).show();
                } else {
                    if (registration_password.getText().toString().equals(registration_reenter_password.getText().toString())) {
                        registerNewUser(registration_username.getText().toString(), registration_password.getText().toString());
                    }
                    else {
                            Toast.makeText(LoginActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void registerNewUser(final String username, final String password){

        User user = new User(username, password);
        LoginService loginService = Generator.createService(LoginService.class);
        Call<User> call = loginService.userRegistration(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().username != null) {
                    System.out.println("SUCCESS");
                    saveUserAlertDialog("Success", "Account was created successfully. Do you want to save your information to login automatically?", username, password);
                    //Toast.makeText(LoginActivity.this, "Account was created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("unSUCCESS");
                    alertDialog("Error", "This username already exists");
                    //Toast.makeText(LoginActivity.this, "This username already exists", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("unSUCCESS");
                System.out.println(t.getMessage());
                alertDialog("Error", "Network error. Please< try again later");
                //Toast.makeText(LoginActivity.this, "Error. Try again later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveUserAlertDialog(String title, String message, final String username, final String password){
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveLoginInfo(username, password);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ifUserExists();
                    }
                })
                .show();
    }

    private void alertDialog(String title, String message){
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void saveLoginInfo(String username, String password){
        dialog.cancel();

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        ifUserExists();
    }

    private boolean isEmpty(EditText editText){
        return editText.getText().toString().trim().length() == 0;
    }
}
