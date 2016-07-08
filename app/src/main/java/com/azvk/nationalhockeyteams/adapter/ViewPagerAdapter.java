package com.azvk.nationalhockeyteams.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.azvk.nationalhockeyteams.fragment.PlayersListFragment;
import com.azvk.nationalhockeyteams.fragment.TeamInfoFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0: return  TeamInfoFragment.newInstance();
            case 1: return PlayersListFragment.newInstance();
            default: return null;
        }

    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0)
            return "INFORMATION";
        else
            return "PLAYERS";
    }
}