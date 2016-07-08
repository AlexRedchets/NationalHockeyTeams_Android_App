package com.azvk.nationalhockeyteams.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.azvk.nationalhockeyteams.Activity.ApplicationActivity;
import com.azvk.nationalhockeyteams.Generator;
import com.azvk.nationalhockeyteams.R;
import com.azvk.nationalhockeyteams.adapter.TeamAdapter;
import com.azvk.nationalhockeyteams.client.LoginService;
import com.azvk.nationalhockeyteams.model.Team;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamListFragment extends Fragment {

    private Realm realm;
    private RealmConfiguration realmConfig;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_team, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TeamAdapter mTeamAdapter = new TeamAdapter(getActivity());

        ListView mListView = (ListView)getActivity().findViewById(R.id.favorite_teamList);
        if (mListView != null) {
            mListView.setAdapter(mTeamAdapter);
        }

        assert mListView != null;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //save team information to realm
                realmConfig = new RealmConfiguration.Builder(getContext()).deleteRealmIfMigrationNeeded().build();
                realm = Realm.getInstance(realmConfig);
                realm.beginTransaction();
                if (realm!=null){
                    realm.deleteAll();
                }
                realm.copyToRealmOrUpdate(mTeamAdapter.getItem(position));
                realm.commitTransaction();

                Intent i = new Intent(getActivity(), ApplicationActivity.class);
                getActivity().finish();
                startActivity(i);
            }
        });

        //get team information from server
        LoginService loginService = Generator.createService(LoginService.class);
        final Call<List<Team>> call = loginService.teamInfo();
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if (response.isSuccessful()) {
                    mTeamAdapter.updateAdapter(response.body());
                    System.out.println("Getting team info: SUCCESS");
                } else {
                    System.out.println("Getting team info: ERROR");
                }
            }
            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
