package vn.asiantech.internship.footballmanager.ui.player;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.model.Player;
import vn.asiantech.internship.footballmanager.model.Team;
import vn.asiantech.internship.footballmanager.ui.core.BaseAppCompatActivity;
import vn.asiantech.internship.footballmanager.util.Common;
import vn.asiantech.internship.footballmanager.widget.CircleImageView;
import vn.asiantech.internship.footballmanager.widget.DeleteItemDialog;
import vn.asiantech.internship.footballmanager.widget.HeaderBar;

/**
 *  Created by sunday on 22/10/2015.
 */
@EActivity(R.layout.activity_player)
public class PlayerActivity extends BaseAppCompatActivity implements PlayerAdapter.OnItemViewListener, DeleteItemDialog.OnConfirmDialogListener{
    private List<Player> mPlayers;
    private PlayerAdapter mAdapter;
    private Team mTeam;
    private int mPositionSelelect = -1;

    @Extra(Common.EXTRA_TEAM_ID)
    long mTeamId;
    @ViewById
    HeaderBar mHeaderBarPlayer;
    @ViewById
    EditText mEdtTeamDetail;
    @ViewById
    CircleImageView mImgLogoTeamDetail;
    @ViewById
    RecyclerView mRecyclerViewPlayer;
    @ViewById
    ImageButton mImgBtnEditTeam;
    @ViewById
    ImageButton mImgBtnSaveTeamInfo;
    @ViewById
    FloatingActionButton mFabAddPlayer;

    @Override
    public void afterView() {
        Log.e("Team", mTeamId + "");
        mHeaderBarPlayer.setTitle(getResources().getString(R.string.header_bar_title_player));
        mTeam = Team.findById(Team.class, mTeamId);
        if(mTeam != null){
            mImgLogoTeamDetail.setImageResource(mTeam.getLogo());
            mEdtTeamDetail.setText(mTeam.getInformation());
            mPlayers = Player.getPlayerByTeamId(mTeamId);
            mAdapter = new PlayerAdapter(mPlayers);
            mAdapter.setmOnItemViewListener(this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerViewPlayer.setLayoutManager(linearLayoutManager);
            mRecyclerViewPlayer.setAdapter(mAdapter);
            mFabAddPlayer.attachToRecyclerView(mRecyclerViewPlayer);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListPlayer();
    }

    @Override
    public void onItemPlayerClick(int position) {
        long id = mPlayers.get(position).getId();
        mPositionSelelect = position;
        DetailPlayerActivity_.intent(this).mPlayerId(id).start();
    }

    @Override
    public void onDeletePlayerClick(int position) {
        DeleteItemDialog deleteItemDialog = new DeleteItemDialog();
        deleteItemDialog.setOnfirmDialogListener(this);
        deleteItemDialog.showDialog(this, position, "player");
    }
    @Override
    public void onConfirmDialog(int position) {
        Player.deletePlayer(mPlayers.get(position).getId());
        mPlayers.remove(position);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getApplication(), R.string.text_notice_add_new_success, Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.mImgBtnEditTeam)
    void onButtonEditTeamClick(){
        mEdtTeamDetail.setEnabled(true);
        mImgBtnEditTeam.setVisibility(View.INVISIBLE);
        mImgBtnSaveTeamInfo.setVisibility(View.VISIBLE);
    }
    @Click(R.id.mImgBtnSaveTeamInfo)
    void onButtonSaveTeamInfo(){
        String name = mEdtTeamDetail.getText().toString();
        if(name.equals(mTeam.getInformation())){
            Toast.makeText(this, R.string.text_notice_change_information, Toast.LENGTH_SHORT).show();
            mImgBtnSaveTeamInfo.setVisibility(View.INVISIBLE);
            mEdtTeamDetail.setEnabled(false);
            mImgBtnEditTeam.setVisibility(View.VISIBLE);
        }
        else if(name.equals("")){
            mEdtTeamDetail.setError(getResources().getString(R.string.text_notice_field_add_new));
        }else{
            Toast.makeText(this, R.string.text_notice_add_new_success, Toast.LENGTH_SHORT).show();
            mTeam.setInformation(name);
            Team.updateTeam(mTeam);
            mEdtTeamDetail.setEnabled(false);
            mImgBtnSaveTeamInfo.setVisibility(View.INVISIBLE);
            mImgBtnEditTeam.setVisibility(View.VISIBLE);
        }
    }
    @Click(R.id.mFabAddPlayer)
    void onButtonAddPlayerClick(){
        long id = mTeam.getId();
        Log.e("ID", id  + "");
        DetailPlayerActivity_.intent(this).mPlayerId(-1).mTeamId(id).startForResult(Common.REQUEST_CODE_ADD);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Common.REQUEST_CODE_ADD) {
            long teamId = data.getLongExtra(Common.EXTRA_TEAM_ID, -1);
            Log.e("ADD", teamId + "");
            Player player = Player.getPlayerById(teamId);
            if(player != null){
                mPlayers.add(player);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateListPlayer(){
        if(mPositionSelelect >= 0 && mPositionSelelect < mPlayers.size()){
            Player player = Player.findById(Player.class, mPlayers.get(mPositionSelelect).getId());
            if(player.getTeam_id() == mTeam.getId()){
                mPlayers.set(mPositionSelelect, player);
            }else {
                mPlayers.remove(mPositionSelelect);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
