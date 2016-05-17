package com.example.azvk.nationalhockeyteams.fragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.azvk.nationalhockeyteams.ApplicationActivity;
import com.example.azvk.nationalhockeyteams.Generator;
import com.example.azvk.nationalhockeyteams.adapter.PlayerAdapter;
import com.example.azvk.nationalhockeyteams.R;
import com.example.azvk.nationalhockeyteams.client.PlayerClient;
import com.example.azvk.nationalhockeyteams.model.Player;
import com.example.azvk.nationalhockeyteams.model.Team;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersListFragment extends Fragment {

    DialogFragment dialog;
    private Realm realm;
    private RealmConfiguration realmConfig;

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
                Toast.makeText(getContext(), "You chose: " + mPlayerAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                PlayerInfoDialog playerInfoDialog = new PlayerInfoDialog();
                playerInfoDialog.show(fm, "Dialog");

                /*TextView playerName = (TextView)view.findViewById(R.id.playerNameDialogInput);
                TextView playerPosition = (TextView)view.findViewById(R.id.playerPositionDialogInput);
                TextView playerBirthdate = (TextView)view.findViewById(R.id.playerBirthdateDialogInput);
                TextView playerBirthplace = (TextView)view.findViewById(R.id.playerBirthplaceDialogInput);
                TextView playerNumber = (TextView)view.findViewById(R.id.playerNumberDialogInput);
                TextView playerHeight = (TextView)view.findViewById(R.id.playerHeightDialogInput);
                TextView playerWeight = (TextView)view.findViewById(R.id.playerWeightDialogInput);
                TextView playerTeam = (TextView)view.findViewById(R.id.playerTeamtDialogInput);
                ImageView playerImage = (ImageView)view.findViewById(R.id.playerImageDialog);

                playerName.setText(mPlayerAdapter.getItem(position).getName());
                playerPosition.setText(mPlayerAdapter.getItem(position).getPosition());
                playerBirthdate.setText(mPlayerAdapter.getItem(position).getBirthdate());
                playerBirthplace.setText(mPlayerAdapter.getItem(position).getBirthplace());
                playerNumber.setText("" + mPlayerAdapter.getItem(position).getNumber());
                playerHeight.setText("" + mPlayerAdapter.getItem(position).getHeight());
                playerWeight.setText("" + mPlayerAdapter.getItem(position).getWeight());
                playerTeam.setText(mPlayerAdapter.getItem(position).getTeam());
                Picasso.with(getContext()).load(mPlayerAdapter.getItem(position).getImgRes()).into(playerImage);*/




                /*dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                dialog.setContentView(R.layout.player_info);
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(layoutParams);


                playerName.setText(mPlayerAdapter.getItem(position).getName());
                playerPosition.setText(mPlayerAdapter.getItem(position).getPosition());
                playerBirthdate.setText(mPlayerAdapter.getItem(position).getBirthdate());
                playerBirthplace.setText(mPlayerAdapter.getItem(position).getBirthplace());
                playerNumber.setText("" + mPlayerAdapter.getItem(position).getNumber());
                playerHeight.setText("" + mPlayerAdapter.getItem(position).getHeight());
                playerWeight.setText("" + mPlayerAdapter.getItem(position).getWeight());
                playerTeam.setText(mPlayerAdapter.getItem(position).getTeam());
                Picasso.with(getContext()).load(mPlayerAdapter.getItem(position).getImgRes()).into(playerImage);*/

            }
        });

        ////get players information from server
        PlayerClient client = Generator.createService(PlayerClient.class);

        realmConfig = new RealmConfiguration.Builder(getContext()).deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfig);
        Team fav_team = realm.where(Team.class).findFirst();

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
