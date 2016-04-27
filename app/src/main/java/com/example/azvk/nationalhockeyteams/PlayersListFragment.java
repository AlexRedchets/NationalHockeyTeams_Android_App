package com.example.azvk.nationalhockeyteams;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersListFragment extends Fragment {


    static PlayersListFragment newInstance (){
        PlayersListFragment playersListFragment = new PlayersListFragment();
        return playersListFragment;
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

        final PlayerAdapter mPlayerAdapter = new PlayerAdapter(getActivity());

        ListView mListView = (ListView)getActivity().findViewById(R.id.playersList);
        if (mListView != null) {
            mListView.setAdapter(mPlayerAdapter);
        }

        PlayerClient client = Generator.createService(PlayerClient.class);
        final Call<List<Player>> call = client.player();
            call.enqueue(new Callback<List<Player>>() {
                    @Override
                    public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                        if (response.isSuccessful()) {
                            mPlayerAdapter.updateAdapter(response.body());
                            System.out.println("SUCCESS");
                        } else {
                            System.out.println("ERROR");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Player>> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
    }

}
