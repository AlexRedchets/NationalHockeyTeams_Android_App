package com.azvk.nationalhockeyteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.azvk.nationalhockeyteams.R;
import com.azvk.nationalhockeyteams.model.Team;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TeamAdapter extends BaseAdapter{

        private List<Team> teamList;
        private LayoutInflater inflater;
        private Context context;


        public TeamAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        public void updateAdapter(List<Team> team){
            teamList = team;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return teamList != null ? teamList.size() : 0;
        }

        @Override
        public Team getItem(int position) {
            return teamList != null ? teamList.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return teamList != null ? teamList.get(position).hashCode() : 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder mViewHolder;

            if(convertView==null){
                convertView = inflater.inflate(R.layout.custom_row_team_list, parent, false);
                mViewHolder = new ViewHolder(convertView);
                convertView.setTag(mViewHolder);
            }
            else{
                mViewHolder = (ViewHolder)convertView.getTag();
            }

            Team currentTeamData = getItem(position);
            mViewHolder.team_name.setText(currentTeamData.getName());
            Picasso.with(context).load(currentTeamData.getImgRes()).into(mViewHolder.team_image);
            return convertView;
        }

        private class ViewHolder {

            TextView team_name;
            ImageView team_image;

            public ViewHolder(View view){
                team_name = (TextView) view.findViewById(R.id.team_name);
                team_image = (ImageView)view.findViewById(R.id.team_flag);
            }
        }
}
