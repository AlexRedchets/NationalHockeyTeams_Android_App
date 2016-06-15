package com.example.azvk.nationalhockeyteams.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azvk.nationalhockeyteams.R;
import com.example.azvk.nationalhockeyteams.model.Team;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.internal.Context;

public class TeamInfoFragment extends Fragment {

    private Realm realm;
    private RealmConfiguration realmConfig;

    public static TeamInfoFragment newInstance(){
        return new TeamInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.team_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView flag;
        TextView teamName;
        TextView headCoach;
        TextView captain;

        //upload background image from realm and set it using Picasso
        flag = (ImageView)view.findViewById(R.id.infoFlag);
        realmConfig = new RealmConfiguration.Builder(getContext()).deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfig);
        Team fav_team = realm.where(Team.class).findFirst();

        Picasso.with(getContext()).load(fav_team.getImgRes()).into(flag);

        teamName = (TextView)view.findViewById(R.id.infoName);
        headCoach = (TextView)view.findViewById(R.id.headCoachInput);
        captain = (TextView)view.findViewById(R.id.captainInput);

        teamName.setText(fav_team.getName());
        headCoach.setText(fav_team.getHead_coach());
        captain.setText(fav_team.getCaptain());
    }
}
