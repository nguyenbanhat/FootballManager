package vn.asiantech.internship.footballmanager.ui.player;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.model.Player;
import vn.asiantech.internship.footballmanager.widget.CircleImageView;

/**
 *  Created by sunday on 22/10/2015.
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>{
    private List<Player> mPlayers;
    private OnItemViewListener mOnItemViewListener;

    public PlayerAdapter(List<Player> mPlayers) {
        this.mPlayers = mPlayers;
    }

    public void setmOnItemViewListener(OnItemViewListener mOnItemViewListener) {
        this.mOnItemViewListener = mOnItemViewListener;
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTvPlayer;
        private CircleImageView mImgAvatar;
        private ImageButton mImgBtnDeletePlayer;
        public PlayerViewHolder(View itemView) {
            super(itemView);
            mTvPlayer = (TextView) itemView.findViewById(R.id.tvPlayer);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            mImgBtnDeletePlayer = (ImageButton) itemView.findViewById(R.id.imgBtnDeletePlayer);
            itemView.setOnClickListener(this);
            mImgBtnDeletePlayer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v == itemView){
                if(mOnItemViewListener != null){
                    mOnItemViewListener.onItemPlayerClick(getAdapterPosition());
                }
            }else if(v.getId() == R.id.imgBtnDeleteTeam && mOnItemViewListener != null){
                mOnItemViewListener.onDeletePlayerClick(getAdapterPosition());
            }
        }
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        if(mPlayers == null || mPlayers.isEmpty()){
            return;
        }
        holder.mTvPlayer.setText(mPlayers.get(position).getName());
        holder.mImgAvatar.setImageResource(mPlayers.get(position).getAvatarId());
    }

    @Override
    public int getItemCount() {
        if(mPlayers == null || mPlayers.isEmpty()){
            return 0;
        }
        return mPlayers.size();
    }

    protected interface OnItemViewListener{
        void onItemPlayerClick(int position);
        void onDeletePlayerClick(int position);
    }
}
