package com.azvk.nationalhockeyteams.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.azvk.nationalhockeyteams.R;
import com.azvk.nationalhockeyteams.adapter.ViewPagerAdapter;
import com.azvk.nationalhockeyteams.model.Team;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class ApplicationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        FragmentPagerAdapter fragmentPagerAdapter;
        Realm realm;
        RealmConfiguration realmConfig;
        ImageView header_pic;

        //Insert toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Insert navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        //Activate viewPager
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        assert viewPager != null;
        viewPager.setAdapter(fragmentPagerAdapter);

        //upload background image from realm and set it using Picasso
        header_pic = (ImageView)findViewById(R.id.header_pic);
        realmConfig = new RealmConfiguration.Builder(getApplicationContext()).deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfig);
        Team fav_team = realm.where(Team.class).findFirst();

        Picasso.with(getApplicationContext())
                .load(fav_team.getImgResHeader())
                .resize(400, 150)
                .centerCrop()
                .into(header_pic);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.application, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_teams) {
            Intent i = new Intent(ApplicationActivity.this, TeamActivity.class);
            finish();
            startActivity(i);
        }
        else if (id == R.id.nav_search){
            Intent i = new Intent(ApplicationActivity.this, SearchActivity.class);
            finish();
            startActivity(i);
        }
        else if (id == R.id.nav_app){
            Intent i = new Intent(ApplicationActivity.this, AboutAppActivity.class);
            finish();
            startActivity(i);
        }
        else if (id == R.id.nav_credits){
            Intent i = new Intent(ApplicationActivity.this, CreditsActivity.class);
            finish();
            startActivity(i);
        }
        else if (id == R.id.nav_logout){
            new AlertDialog.Builder(ApplicationActivity.this)
                    .setMessage("Do you want to log out?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            sharedPreferences.edit().clear().apply();
                            dialog.cancel();
                            Intent i = new Intent(ApplicationActivity.this, LoginActivity.class);
                            finish();
                            startActivity(i);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}