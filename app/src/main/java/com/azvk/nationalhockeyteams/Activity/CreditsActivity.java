package com.azvk.nationalhockeyteams.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.azvk.nationalhockeyteams.R;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        Button button = (Button)findViewById(R.id.creditsButton);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreditsActivity.this, ApplicationActivity.class);
                finish();
                startActivity(i);
            }
        });
    }
}
