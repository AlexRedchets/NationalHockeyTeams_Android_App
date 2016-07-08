package com.azvk.nationalhockeyteams.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.azvk.nationalhockeyteams.R;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        Button button = (Button)findViewById(R.id.aboutAppButton);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutAppActivity.this, ApplicationActivity.class);
                finish();
                startActivity(i);
            }
        });
    }
}
