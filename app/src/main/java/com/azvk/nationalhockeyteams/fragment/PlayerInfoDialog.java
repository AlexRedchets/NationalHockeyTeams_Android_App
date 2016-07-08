package com.azvk.nationalhockeyteams.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azvk.nationalhockeyteams.R;
import com.squareup.picasso.Picasso;

public class PlayerInfoDialog extends DialogFragment {

    private String name, birthdate, birthplace, team, imgUrl, position;
    private Integer number, height, weight;

    public PlayerInfoDialog() {
    }

    /*public static PlayerInfoDialog newInstance(String title) {
        return new PlayerInfoDialog();
    }
*/
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //get passed info from the PlayerListFragment
        Bundle mArgs = getArguments();
        name = mArgs.getString("name");
        birthdate = mArgs.getString("birthdate");
        birthplace = mArgs.getString("birthplace");
        team = mArgs.getString("team");
        position = mArgs.getString("position");
        imgUrl = mArgs.getString("imgUrl");
        number = mArgs.getInt("number");
        height = mArgs.getInt("height");
        weight = mArgs.getInt("weight");

        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.player_info, container, false);

        //set up widgets in the dialog
        TextView playerName = (TextView)view.findViewById(R.id.playerNameDialogInput);
        TextView playerPosition = (TextView)view.findViewById(R.id.playerPositionDialogInput);
        TextView playerBirthdate = (TextView)view.findViewById(R.id.playerBirthdateDialogInput);
        TextView playerBirthplace = (TextView)view.findViewById(R.id.playerBirthplaceDialogInput);
        TextView playerNumber = (TextView)view.findViewById(R.id.playerNumberDialogInput);
        TextView playerHeight = (TextView)view.findViewById(R.id.playerHeightDialogInput);
        TextView playerWeight = (TextView)view.findViewById(R.id.playerWeightDialogInput);
        TextView playerTeam = (TextView)view.findViewById(R.id.playerTeamtDialogInput);
        ImageView playerImage = (ImageView)view.findViewById(R.id.playerImageDialog);

        //set values to widgets
        playerName.setText(name);
        playerPosition.setText(position);
        playerBirthdate.setText(birthdate);
        playerBirthplace.setText(birthplace);
        playerNumber.setText(String.valueOf(number));
        playerHeight.setText(String.valueOf(height));
        playerWeight.setText(String.valueOf(weight));
        playerTeam.setText(team);
        Picasso.with(getContext()).load(imgUrl).into(playerImage);

        return view;
    }
}
