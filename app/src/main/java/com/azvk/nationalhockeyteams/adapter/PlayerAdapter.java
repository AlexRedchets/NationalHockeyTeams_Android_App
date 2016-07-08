package com.azvk.nationalhockeyteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.azvk.nationalhockeyteams.R;
import com.azvk.nationalhockeyteams.model.Player;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayerAdapter extends BaseAdapter {


    private List<Player> playerList;
    private LayoutInflater inflater;
    private Context context;

    public PlayerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public void updateAdapter(List<Player> player){
        playerList = player;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return playerList != null ? playerList.size() : 0;
    }

    @Override
    public Player getItem(int position) {
        return playerList != null ? playerList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return playerList != null ? playerList.get(position).hashCode() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder;

        if(convertView==null){
            convertView = inflater.inflate(R.layout.custom_row_players_list, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }
        else{
            mViewHolder = (ViewHolder)convertView.getTag();
        }

        Player currentPlayerData = getItem(position);
        mViewHolder.playerName.setText(currentPlayerData.getName());
        Picasso.with(context).load(currentPlayerData.getImgRes()).into(mViewHolder.playerImage);

        return convertView;
    }

    private class ViewHolder {

        TextView playerName;
        ImageView playerImage;

        public ViewHolder(View view){
            playerName = (TextView) view.findViewById(R.id.playerName);
            playerImage = (ImageView)view.findViewById(R.id.playerImage);
        }
    }
}