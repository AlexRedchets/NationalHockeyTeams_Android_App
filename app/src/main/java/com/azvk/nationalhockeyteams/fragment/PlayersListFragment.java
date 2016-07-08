package com.azvk.nationalhockeyteams.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.azvk.nationalhockeyteams.Generator;
import com.azvk.nationalhockeyteams.adapter.PlayerAdapter;
import com.azvk.nationalhockeyteams.R;
import com.azvk.nationalhockeyteams.client.PlayerClient;
import com.azvk.nationalhockeyteams.model.Player;
import com.azvk.nationalhockeyteams.model.Team;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersListFragment extends Fragment {

    public static PlayersListFragment newInstance (){
        return new PlayersListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.players_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Realm realm;
        RealmConfiguration realmConfig;

        final PlayerAdapter mPlayerAdapter = new PlayerAdapter(getActivity());

        final ListView mListView = (ListView)getActivity().findViewById(R.id.playersList);
        if (mListView != null) {
            mListView.setAdapter(mPlayerAdapter);
        }

        assert mListView != null;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //send players info to the dialogFragment using Bundle
                Bundle args = new Bundle();
                args.putString("name", mPlayerAdapter.getItem(position).getName());
                args.putString("birthdate", mPlayerAdapter.getItem(position).getBirthdate());
                args.putString("birthplace", mPlayerAdapter.getItem(position).getBirthplace());
                args.putString("team", mPlayerAdapter.getItem(position).getTeam());
                args.putString("position", mPlayerAdapter.getItem(position).getPosition());
                args.putInt("number", mPlayerAdapter.getItem(position).getNumber());
                args.putInt("weight", mPlayerAdapter.getItem(position).getWeight());
                args.putInt("height", mPlayerAdapter.getItem(position).getHeight());
                args.putString("imgUrl", mPlayerAdapter.getItem(position).getImgRes());

                FragmentManager fm = getActivity().getSupportFragmentManager();
                PlayerInfoDialog playerInfoDialog = new PlayerInfoDialog();
                playerInfoDialog.setArguments(args);
                playerInfoDialog.show(fm, "Dialog");

            }
        });

        realmConfig = new RealmConfiguration.Builder(getContext()).deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfig);
        Team fav_team = realm.where(Team.class).findFirst();

        //get players information from server
        PlayerClient client = Generator.createService(PlayerClient.class);

        final Call<List<Player>> call = client.player(fav_team.getName());
            call.enqueue(new Callback<List<Player>>() {
                    @Override
                    public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                        if (response.isSuccessful()) {
                            mPlayerAdapter.updateAdapter(response.body());
                            System.out.println("Getting players info: SUCCESS");
                        } else {
                            System.out.println("Getting players info: ERROR");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Player>> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
    }
}
