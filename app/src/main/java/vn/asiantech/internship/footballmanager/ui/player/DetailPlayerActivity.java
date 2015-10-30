package vn.asiantech.internship.footballmanager.ui.player;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 *  Created by sunday on 27/10/2015.
 */
@EActivity(R.layout.activity_detail_player)
public class DetailPlayerActivity extends BaseAppCompatActivity {
    private Player mPlayer;
    private Team team;

    @Extra(Common.EXTRA_PLAYER_ID)
    long mPlayerId;
    @Extra(Common.EXTRA_TEAM_ID)
    long mTeamId;
    @ViewById
    EditText mEdtNamePlayer;
    @ViewById
    EditText mEdtAge;
    @ViewById
    EditText mEdtTeamPlayer;
    @ViewById
    EditText mEdtHeight;
    @ViewById
    EditText mEdtWeight;
    @ViewById
    EditText mEdtNumber;
    @ViewById
    EditText mEdtCountry;
    @ViewById
    EditText mEdtPosition;
    @ViewById
    TextView mTvTeamPlayer;
    @ViewById
    ImageButton mImgBtnEditPlayer;
    @ViewById
    Button mImgBtnSavePlayer;
    @ViewById
    ImageView mImgAvatarPlayer;

    @Override
    public void afterView() {
        Log.e("Player", mPlayerId + "");
        mPlayer = Player.findById(Player.class, mPlayerId);
        team = Team.getTeamById(mTeamId);
        Log.e("TEAM ID", mTeamId+ "");
        if(mPlayer != null){
           setValuePlayer();
        }else{
           showAddingPlayerView();
        }
    }
    @Click(R.id.mImgBtnEditPlayer)
    void onImageButtonEditPlayerClick(){
        mImgBtnEditPlayer.setVisibility(View.INVISIBLE);
        mImgBtnSavePlayer.setVisibility(View.VISIBLE);
        mEdtNamePlayer.setEnabled(true);
        mEdtAge.setEnabled(true);
        mEdtHeight.setEnabled(true);
        mEdtWeight.setEnabled(true);
        mEdtNumber.setEnabled(true);
        mEdtCountry.setEnabled(true);
        mEdtTeamPlayer.setVisibility(View.GONE);
        mTvTeamPlayer.setVisibility(View.GONE);
        mEdtPosition.setEnabled(true);
    }
    @Click(R.id.mImgBtnSavePlayer)
    void onButtonSavePlayerClick(){
    if(mPlayer != null){
        editPlayer();
        }else {
        addNewPlayer();
    }
    }

    private void addNewPlayer(){
        String name = mEdtNamePlayer.getText().toString().trim();
        String country = mEdtCountry.getText().toString();
        String position = mEdtPosition.getText().toString();
        String sttNumber = mEdtNumber.getText() + "";
        String sttHeight = mEdtHeight.getText() + "";
        String sttWeight = mEdtWeight.getText() + "";
        int number;
        int height;
        int weight;
        try {
            number = Integer.parseInt(sttNumber);
            height = Integer.parseInt(sttHeight);
            weight = Integer.parseInt(sttWeight);
        }catch (NumberFormatException e) {
            return;
        }
        if(name.equals("") || country.equals("") || sttHeight.equals("") || position.equals("") ||
                sttNumber.equals("") || sttWeight.equals("")){
            Toast.makeText(this, R.string.text_notice_field_add_new, Toast.LENGTH_SHORT).show();
        }else if(number <= 0 || height < 155 || weight < 50){
            Toast.makeText(this, "Please check your form again! ", Toast.LENGTH_SHORT).show();
        }
        else if(!checkPlayerName(name, number)){
            Player player = new Player();
            player.setName(name);
            player.setCountry(country);
            player.setPosition(position);
            player.setNumber(number);
            player.setHeight(height);
            player.setWeight(weight);
            player.setBirth("1992/1/1");
            player.setAvatar(R.drawable.a);
            player.save();
            player.setTeam_id(mTeamId);
            //Team.updateTeam(team);
            Toast.makeText(getApplication(), R.string.text_notice_add_new_success, Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else {
            mEdtNamePlayer.setError(getResources().getString(R.string.text_notice_exist_name));
        }
    }
    private boolean checkPlayerName(String name, int number){
        List<Player> players = Player.getPlayerByNameAndNumber(name, number);
        return (players != null && players.size() > 0);
    }
    private void setValuePlayer(){
        int age = Player.getAge(mPlayerId);
        Team team;
        team = Team.findById(Team.class, mPlayer.getTeam_id());
        mEdtTeamPlayer.setText(team.getName());
        mEdtNamePlayer.setText(mPlayer.getName());
        mEdtCountry.setText(mPlayer.getCountry());
        mEdtNamePlayer.setText(mPlayer.getName());
        mEdtPosition.setText(mPlayer.getPosition());
        mEdtNumber.setText(mPlayer.getNumber() + "");
        mEdtHeight.setText(mPlayer.getHeight() + "");
        mEdtWeight.setText(mPlayer.getWeight() + "");
        mEdtAge.setText(age + "");
        mImgAvatarPlayer.setImageResource(mPlayer.getAvatar());
    }
    private void showAddingPlayerView(){
        mEdtNamePlayer.setEnabled(true);
        mEdtAge.setEnabled(true);
        mEdtHeight.setEnabled(true);
        mEdtWeight.setEnabled(true);
        mEdtNumber.setEnabled(true);
        mEdtCountry.setEnabled(true);
        mEdtPosition.setEnabled(true);
        mTvTeamPlayer.setVisibility(View.GONE);
        mEdtTeamPlayer.setVisibility(View.GONE);
        mImgBtnEditPlayer.setImageResource(R.drawable.custom_button_upload_photo);
        mImgBtnSavePlayer.setVisibility(View.VISIBLE);
        mImgAvatarPlayer.setImageResource(R.drawable.ic_no_avatar);
    }
    private void editPlayer() {
        String name = mEdtNamePlayer.getText().toString();
        String position = mEdtPosition.getText().toString();
        String country = mEdtCountry.getText().toString();
        String sttNumber = mEdtNumber.getText() + "";
        String sttHeight = mEdtHeight.getText() + "";
        String sttWeight = mEdtWeight.getText() + "";
        int number;
        int height;
        int weight;
        try {
            number = Integer.parseInt(sttNumber);
            height = Integer.parseInt(sttHeight);
            weight = Integer.parseInt(sttWeight);
        }catch (NumberFormatException e) {
            return;
            }
        if (name.equals("") || country.equals("") || sttHeight.equals("") || position.equals("") ||
                sttNumber.equals("") || sttWeight.equals("")) {
            Toast.makeText(this, R.string.text_notice_field_add_new, Toast.LENGTH_SHORT).show();
        }else if(number <= 0 || height < 155 || weight < 50){
            Toast.makeText(this, "Please check your form again! ", Toast.LENGTH_SHORT).show();
        }else if(!checkPlayerName(name, number)){
            mPlayer.setName(name);
            mPlayer.setCountry(country);
            mPlayer.setPosition(position);
            mPlayer.setNumber(number);
            mPlayer.setHeight(height);
            mPlayer.setWeight(weight);
            mPlayer.setBirth("1992/1/1");
            mPlayer.setAvatar(R.drawable.a);
            Player.updatePlayer(mPlayer);
            Toast.makeText(getApplication(), R.string.text_notice_add_new_success, Toast.LENGTH_SHORT).show();
            mEdtNamePlayer.setEnabled(false);
            mEdtAge.setEnabled(false);
            mEdtHeight.setEnabled(false);
            mEdtWeight.setEnabled(false);
            mEdtNumber.setEnabled(false);
            mEdtCountry.setEnabled(false);
            mEdtTeamPlayer.setEnabled(false);
            mEdtPosition.setEnabled(false);
            onBackPressed();
        }else {
            mEdtNamePlayer.setError(getResources().getString(R.string.text_notice_exist_name));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
