package vn.asiantech.internship.footballmanager.ui.player;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.model.Player;
import vn.asiantech.internship.footballmanager.ui.core.BaseAppCompatActivity;
import vn.asiantech.internship.footballmanager.widget.CircleImageView;
import vn.asiantech.internship.footballmanager.widget.HeaderBar;

/**
 *  Created by sunday on 22/10/2015.
 */
@EActivity(R.layout.activity_player)
public class PlayerActivity extends BaseAppCompatActivity implements PlayerAdapter.OnItemViewListener{
    private List<Player> mPlayers;

    @ViewById
    HeaderBar mHeaderBarPlayer;
    @ViewById
    EditText mEdtTeamDetail;
    @ViewById
    CircleImageView mImgLogoTeamDetail;
    @ViewById
    RecyclerView mRecyclerViewTeam;
    @ViewById
    ImageButton mImgBtnEditTeam;
    @ViewById
    ImageButton mImgBtnSaveTeamInfo;
    @ViewById
    ImageButton mImgBtnAddTeam;

    @Override
    public void afterView() {
        loadData();
        setValue();
    }

    private void loadData(){
        mPlayers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Player player = new Player();
            player.setName("Leone Messi");
            player.setAvatarId(R.drawable.ic_avatar);
            mPlayers.add(player);
        }
    }
    private void setValue(){
        mHeaderBarPlayer.setTitle(getResources().getString(R.string.header_bar_title_player));
        PlayerAdapter adapter = new PlayerAdapter(mPlayers);
        adapter.setmOnItemViewListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewTeam.setLayoutManager(linearLayoutManager);
        mRecyclerViewTeam.setAdapter(adapter);
    }

    @Override
    public void onItemPlayerClick(int position) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        DetailPlayerActivity_.intent(this).start();
    }

    @Override
    public void onDeletePlayerClick(int position) {

    }
}
