package vn.asiantech.internship.footballmanager.ui.league;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.model.League;
import vn.asiantech.internship.footballmanager.widget.CircleImageView;

/**
 *  Created by sunday on 21/10/2015.
 */
public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>{
    private List<League> mLeagues;
    private OnItemViewListener mOnItemViewListener;
    private boolean mIsRemoved;

    public LeagueAdapter(List<League> leagues, boolean isRemoved) {
        this.mLeagues = leagues;
        this.mIsRemoved = isRemoved;
    }

    public void setIsRemoved(boolean isRemoved){
        this.mIsRemoved = isRemoved;
    }

    public boolean getIsRemoved(){
    return mIsRemoved;
}
    public class LeagueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mImgLogoLeague;
        private TextView mTvLeague;
        private CheckBox mchkDelete;

        public LeagueViewHolder(View itemView) {
            super(itemView);
            mImgLogoLeague = (CircleImageView) itemView.findViewById(R.id.imgLogoLeague);
            mTvLeague = (TextView) itemView.findViewById(R.id.tvLeague);
            mchkDelete = (CheckBox) itemView.findViewById(R.id.chkDelete);
            mchkDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (mchkDelete.isChecked() && mOnItemViewListener != null) {
                        mOnItemViewListener.onItemCheckboxChecked(getLayoutPosition());
                    }else if(!mchkDelete.isChecked() && mOnItemViewListener != null){
                        mOnItemViewListener.onItemCheckboxUnchecked(getLayoutPosition());
                    }
                }
            });
            if (!mIsRemoved) {
                mchkDelete.setVisibility(View.VISIBLE);
            } else {
                itemView.setOnClickListener(this);
                mchkDelete.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            if (mOnItemViewListener != null) {
                mOnItemViewListener.onItemLeagueClick(getAdapterPosition());
            }
        }
    }
    public void setmOnItemViewListener(OnItemViewListener mOnItemViewListener) {
        this.mOnItemViewListener = mOnItemViewListener;
    }

    @Override
    public LeagueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_league, parent,false);
        return new LeagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeagueViewHolder holder, int position) {
        if(mLeagues == null || mLeagues.isEmpty()){
            return;
        }
        holder.mTvLeague.setText(mLeagues.get(position).getName());
        holder.mImgLogoLeague.setImageBitmap(BitmapFactory.decodeFile(mLeagues.get(position).getLogo()));
    }

    @Override
    public int getItemCount() {
        if(mLeagues == null || mLeagues.isEmpty()){
           return 0;
        }
        return mLeagues.size();
    }
    public interface OnItemViewListener{
        void onItemLeagueClick(int position);
        void onItemCheckboxChecked(int position);
        void onItemCheckboxUnchecked(int position);
    }
}
