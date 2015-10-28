package vn.asiantech.internship.footballmanager.ui.team;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import vn.asiantech.internship.footballmanager.model.League;
import vn.asiantech.internship.footballmanager.model.Team;
import vn.asiantech.internship.footballmanager.ui.core.BaseAppCompatActivity;
import vn.asiantech.internship.footballmanager.ui.player.PlayerActivity_;
import vn.asiantech.internship.footballmanager.util.Common;
import vn.asiantech.internship.footballmanager.widget.CircleImageView;
import vn.asiantech.internship.footballmanager.widget.DeleteItemDialog;
import vn.asiantech.internship.footballmanager.widget.HeaderBar;

/**
 *  Created by sunday on 21/10/2015.
 */
@EActivity(R.layout.activity_team)
public class TeamActivity extends BaseAppCompatActivity implements TeamAdapter.OnItemViewListener, DeleteItemDialog.OnConfirmDialogListener{
    private List<Team> mTeams;
    private League mLeague;
    private TeamAdapter mAdapter;
    private int mPositionSelelect = -1;

    @Extra(Common.EXTRA_LEAGUE_ID)
    long mLeagueId;
    @ViewById
    HeaderBar mHeaderBarTeam;
    @ViewById
    EditText mEdtLeagueDetail;
    @ViewById
    CircleImageView mImgLogoLeagueDetail;
    @ViewById
    RecyclerView mRecyclerViewTeam;
    @ViewById
    ImageButton mImgBtnEditLeague;
    @ViewById
    ImageButton mImgBtnSaveLeagueInfo;
    @ViewById
    FloatingActionButton mFabAddTeam;

    @Override
    public void afterView() {
        mHeaderBarTeam.setTitle(getResources().getString(R.string.header_bar_title_team));
        Log.e("League", mLeagueId + "");
        mLeague = League.findById(League.class, mLeagueId);
        if (mLeague != null) {
            mEdtLeagueDetail.setText(mLeague.getInformation());
            mImgLogoLeagueDetail.setImageBitmap(BitmapFactory.decodeFile(mLeague.getLogo()));
            mTeams = Team.getTeamByLeagueId(mLeagueId);
            mAdapter = new TeamAdapter(mTeams);
            mAdapter.setmOnItemViewListener(this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerViewTeam.setLayoutManager(linearLayoutManager);
            mRecyclerViewTeam.setAdapter(mAdapter);
            mFabAddTeam.attachToRecyclerView(mRecyclerViewTeam);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListTeam();
    }

    @Override
    public void onItemClick(int position) {
        long id = mTeams.get(position).getId();
        PlayerActivity_.intent(this).mTeamId(id).start();
    }

    @Override
    public void onItemDeleteTeamClick(int position) {
        DeleteItemDialog deleteItemDialog = new DeleteItemDialog();
        deleteItemDialog.setOnfirmDialogListener(this);
        deleteItemDialog.showDialog(this, position, "team");
    }

    @Override
    public void onConfirmDialog(int position) {
        Team.deleteTeam(mTeams.get(position).getId());
        mTeams.remove(position);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getApplication(), R.string.text_notice_add_new_success, Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.mFabAddTeam)
    void OnButtonAddTeamClick() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_add);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtNameAdd);
        final EditText edtInfor = (EditText) dialog.findViewById(R.id.edtInforAdd);
        ImageButton imgBtnUploadPhoto = (ImageButton) dialog.findViewById(R.id.imgBtnUploadPhoto);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        imgBtnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO upload Image
                Toast.makeText(getBaseContext(), "Upload Image", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String infor = edtInfor.getText().toString().trim();
                Log.e("NAME", name);
                Log.e("Infor", infor);
                if (name.equals("") || infor.equals("")) {
                    Toast.makeText(getApplication(), R.string.text_notice_field_add_new, Toast.LENGTH_SHORT).show();
                } else if (!checkTeamName(name)) {
                    Team team = new Team();
                    team.setLeague_id(mLeagueId);
                    team.setName(name);
                    team.setLogo(R.drawable.ic_logo_arsenal);
                    team.setInformation(infor);
                    team.save();
                    mTeams.add(team);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplication(), R.string.text_notice_add_new_success, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    edtName.setError(getResources().getString(R.string.text_notice_exist_name));
                }
            }
        });
        dialog.show();
    }
    @Click(R.id.mImgBtnEditLeague)
    void OnImageButtonEditLeague(){
        mEdtLeagueDetail.setEnabled(true);
        mImgBtnEditLeague.setVisibility(View.INVISIBLE);
        mImgBtnSaveLeagueInfo.setVisibility(View.VISIBLE); String name = mEdtLeagueDetail.getText().toString();
        if(name.equals(mLeague.getInformation())){
            Toast.makeText(this, R.string.text_notice_change_information, Toast.LENGTH_SHORT).show();
            mImgBtnSaveLeagueInfo.setVisibility(View.INVISIBLE);
            mEdtLeagueDetail.setEnabled(false);
            mImgBtnEditLeague.setVisibility(View.VISIBLE);
        }
        else if(name.equals("")){
            mEdtLeagueDetail.setError(getResources().getString(R.string.text_notice_field_add_new));
        }else{
            Toast.makeText(this, R.string.text_notice_add_new_success, Toast.LENGTH_SHORT).show();
            mLeague.setInformation(name);
            League.updateLeague(mLeague);
            mEdtLeagueDetail.setEnabled(false);
            mImgBtnSaveLeagueInfo.setVisibility(View.INVISIBLE);
            mImgBtnEditLeague.setVisibility(View.VISIBLE);
        }
    }
    @Click(R.id.mImgBtnSaveLeagueInfo)
    void OnImageButtonSaveLeagueInfo(){

    }
    private boolean checkTeamName(String name){
        List<Team> teams = Team.getTeamByName(name);
        return (teams != null && teams.size() > 0);
    }
    private void updateListTeam(){
        if(mPositionSelelect >= 0 && mPositionSelelect < mTeams.size()){
            Team team = Team.findById(Team.class, mTeams.get(mPositionSelelect).getId());
            if(team.getLeague_id() == mLeague.getId()){
                mTeams.set(mPositionSelelect, team);
            }else {
                mTeams.remove(mPositionSelelect);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
