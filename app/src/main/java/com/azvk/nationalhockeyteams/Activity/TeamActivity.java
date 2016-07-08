package com.azvk.nationalhockeyteams.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.azvk.nationalhockeyteams.R;
import com.azvk.nationalhockeyteams.fragment.TeamListFragment;

public class TeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_activity_test);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TeamListFragment teamListFragment = new TeamListFragment();
        fragmentTransaction.add(R.id.container_for_test, teamListFragment);
        fragmentTransaction.commit();
    }
}
