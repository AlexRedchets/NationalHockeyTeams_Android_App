package com.azvk.nationalhockeyteams.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azvk.nationalhockeyteams.Generator;
import com.azvk.nationalhockeyteams.R;
import com.azvk.nationalhockeyteams.client.PlayerSearchClient;
import com.azvk.nationalhockeyteams.model.Player;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button cancelButton;
        Button searchButton;

        searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPlayer();
            }
        });

        cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSearchActivity();
            }
        });
    }

    private void searchPlayer(){
        EditText playerName;

        playerName = (EditText)findViewById(R.id.searchPlayer);
        if (playerName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter player's name", Toast.LENGTH_SHORT).show();
        }
        else{

            Player player = new Player(capitalizeWords(playerName.getText().toString()));
            //get players information from server
            PlayerSearchClient client = Generator.createService(PlayerSearchClient.class);

            final Call<Player> call = client.searchPlayer(player);
            call.enqueue(new Callback<Player>() {
                @Override
                public void onResponse(Call<Player> call, Response<Player> response) {
                    if (response.body().getName()!= null) {
                        System.out.println("Searching player info: SUCCESS");
                        showPlayer(response.body());
                    } else {
                        System.out.println("Searching player info: ERROR");
                        new AlertDialog.Builder(SearchActivity.this)
                                .setMessage("No players found")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .show();
                    }
                }
                @Override
                public void onFailure(Call<Player> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });

        }
    }
    private void cancelSearchActivity(){
        Intent i = new Intent(SearchActivity.this, ApplicationActivity.class);
        finish();
        startActivity(i);
    }

    private void showPlayer(Player player){
        TextView playerName;
        TextView playerPosition;
        TextView playerTeam;
        TextView playerWeight;
        TextView playerHeight;
        TextView playerBirthdate;
        TextView playerBirthplace;
        ImageView playerImg;

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.player_dialog);
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(layoutParams);

        playerName = (TextView)dialog.findViewById(R.id.playerDialogName);
        playerPosition = (TextView)dialog.findViewById(R.id.playerDialogPosition);
        playerTeam = (TextView)dialog.findViewById(R.id.playerDialogTeamInput);
        playerWeight = (TextView)dialog.findViewById(R.id.playerDialogWeightInput);
        playerHeight = (TextView)dialog.findViewById(R.id.playerDialogHeightInput);
        playerBirthdate = (TextView)dialog.findViewById(R.id.playerDialogBirthdateInput);
        playerBirthplace = (TextView)dialog.findViewById(R.id.playerDialogBirthplaceInput);
        playerImg = (ImageView)dialog.findViewById(R.id.playerDialogImg);

        playerName.setText(player.getName());
        playerPosition.setText(player.getPosition());
        playerTeam.setText(player.getTeam());
        playerWeight.setText(String.valueOf(player.getWeight()));
        playerHeight.setText(String.valueOf(player.getHeight()));
        playerBirthdate.setText(player.getBirthdate());
        playerBirthplace.setText(player.getBirthplace());
        Picasso.with(this).load(player.getImgRes()).into(playerImg);

        dialog.show();
    }

    private String capitalizeWords(String string){
        String[] strArr = string.split(" ");
        StringBuilder ret = new StringBuilder();
        for(int i=0; i< strArr.length; i++){
            ret.append(Character.toUpperCase(strArr[i].charAt(0)));
            ret.append(strArr[i].substring(1));
            if (i < strArr.length - 1){
                ret.append(' ');
            }
        }
        return ret.toString();
    }
}
