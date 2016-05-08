package com.example.azvk.nationalhockeyteams.fragment;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.azvk.nationalhockeyteams.Generator;
import com.example.azvk.nationalhockeyteams.adapter.PlayerAdapter;
import com.example.azvk.nationalhockeyteams.R;
import com.example.azvk.nationalhockeyteams.client.PlayerClient;
import com.example.azvk.nationalhockeyteams.model.Player;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersListFragment extends Fragment {

    Dialog dialog;


    public static PlayersListFragment newInstance (){
        PlayersListFragment playersListFragment = new PlayersListFragment();
        return playersListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        dialog = null;
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

        final PlayerAdapter mPlayerAdapter = new PlayerAdapter(getActivity());

        final ListView mListView = (ListView)getActivity().findViewById(R.id.playersList);
        if (mListView != null) {
            mListView.setAdapter(mPlayerAdapter);
        }

        assert mListView != null;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "You pressed " + mPlayerAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        ////get players information from server
        PlayerClient client = Generator.createService(PlayerClient.class);
        final Call<List<Player>> call = client.player();
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
