package vn.asiantech.internship.footballmanager.ui.team;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.model.Team;
import vn.asiantech.internship.footballmanager.widget.CircleImageView;

/**
 *  Created by sunday on 21/10/2015.
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private List<Team> mTeams;
    private OnItemViewListener mOnItemViewListener;

    public TeamAdapter(List<Team> mTeams) {
        this.mTeams = mTeams;
    }

    public void setmOnItemViewListener(OnItemViewListener mOnItemViewListener) {
        this.mOnItemViewListener = mOnItemViewListener;
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private CircleImageView mImgLogoTeam;
        private TextView mTvTeam;
        private ImageButton mImgDeleteTeam;
        public TeamViewHolder(View itemView) {
            super(itemView);
            mImgLogoTeam = (CircleImageView) itemView.findViewById(R.id.imgLogoTeam);
            mTvTeam = (TextView) itemView.findViewById(R.id.tvTeam);
            mImgDeleteTeam = (ImageButton) itemView.findViewById(R.id.imgBtnDeleteTeam);
            itemView.setOnClickListener(this);
            mImgDeleteTeam.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(v == itemView){
                if(mOnItemViewListener != null){
                    mOnItemViewListener.onItemClick(getAdapterPosition());
                }
            }else if(v.getId() == R.id.imgBtnDeleteTeam && mOnItemViewListener != null){
                mOnItemViewListener.onItemDeleteTeamClick(getAdapterPosition());
            }
        }
    }
    @Override
    public TeamAdapter.TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamAdapter.TeamViewHolder holder, int position) {
        if(mTeams == null || mTeams.isEmpty()){
            return;
        }
        holder.mTvTeam.setText(mTeams.get(position).getName());
        holder.mImgLogoTeam.setImageResource(mTeams.get(position).getLogo());
    }

    @Override
    public int getItemCount() {
        if(mTeams == null || mTeams.isEmpty()){
            return 0;
        }
        return mTeams.size();
    }

    protected interface OnItemViewListener{
        void onItemClick(int position);
        void onItemDeleteTeamClick(int position);
    }

}
