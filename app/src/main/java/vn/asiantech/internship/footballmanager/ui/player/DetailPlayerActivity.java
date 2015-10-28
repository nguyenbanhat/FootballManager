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
    private List<Player> mPlayers;
    private Player mPlayer;
    private Team mTeam;

    @Extra(Common.EXTRA_PLAYER_ID)
    long mPlayerId;
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
        mEdtTeamPlayer.setEnabled(true);
    }
    @Click(R.id.mImgBtnSavePlayer)
    void onButtonSavePlayerClick(){
    if(mPlayer != null){
        Toast.makeText(getApplication(), "OK", Toast.LENGTH_SHORT).show();
        }else {
        addNewPlayer();
    }
    }

    private void addNewPlayer(){
        String name = mEdtNamePlayer.getText().toString().trim();
        String country = mEdtCountry.getText().toString().trim();
        int number = Integer.parseInt(mEdtNumber.getText().toString());
        int height = Integer.parseInt(mEdtHeight.getText().toString());
        int weight = Integer.parseInt(mEdtWeight.getText().toString());
        if(name.equals("") || country.equals("") ||
                mEdtNumber.getText().toString().equals("") || mEdtHeight.getText().toString().equals("") ||
                mEdtWeight.getText().toString().equals("")){
            Toast.makeText(this, R.string.text_notice_field_add_new, Toast.LENGTH_SHORT).show();
        }else if(number <= 0 || height < 155 || weight < 50){
            Toast.makeText(this, "Please check your form again! ", Toast.LENGTH_SHORT).show();
        }
        else if(!checkPlayerName(name, number)){
            Player player = new Player();
            player.setName(name);
            player.setCountry(country);
            player.setPosition("CF");
            player.setNumber(number);
            player.setHeight(height);
            player.setWeight(weight);
            player.setBirth("1992/1/1");
            player.setAvatar(R.drawable.a);
            //player.setTeam_id(mPlayerId);
            player.save();
            Toast.makeText(getApplication(), R.string.text_notice_add_new_success, Toast.LENGTH_SHORT).show();
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
        Log.e("Age", age + "");
        mTeam = Team.findById(Team.class, mPlayer.getTeam_id());
        mEdtTeamPlayer.setText(mTeam.getName());
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
}
