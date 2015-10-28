package vn.asiantech.internship.footballmanager.ui.league;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
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
import org.androidannotations.annotations.ViewById;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.model.League;
import vn.asiantech.internship.footballmanager.ui.core.BaseAppCompatActivity;
import vn.asiantech.internship.footballmanager.ui.team.TeamActivity_;
import vn.asiantech.internship.footballmanager.util.Common;
import vn.asiantech.internship.footballmanager.widget.HeaderBar;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseAppCompatActivity implements LeagueAdapter.OnItemViewListener {
    private List<League> mLeagues;
    private LeagueAdapter mAdapter;
    private int mPositionSelect = -1;
    private boolean mIsPressDoubleBack;
    private String mPath;
    private boolean mIsUpload;

    @ViewById
    HeaderBar mHeaderBarLeague;
    @ViewById
    RecyclerView mRecyclerViewLeague;
    @ViewById
    FloatingActionButton mFabRemoveLeague;
    @ViewById
    ImageButton mImgBtnDeleteLeague;
    @ViewById
    FloatingActionButton mFabAddLeague;

    @Override
    public void afterView() {
        mHeaderBarLeague.setTitle(getResources().getString(R.string.header_bar_title_league));
        mLeagues = League.listAll(League.class);
        mAdapter = new LeagueAdapter(mLeagues, true);
        mAdapter.setmOnItemViewListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        //mRecyclerViewLeague.setHasFixedSize(true);
        mRecyclerViewLeague.setLayoutManager(linearLayoutManager);
        mRecyclerViewLeague.setAdapter(mAdapter);
        mFabAddLeague.attachToRecyclerView(mRecyclerViewLeague);
        //mFabRemoveLeague.attachToRecyclerView(mRecyclerViewLeague);
    }

    public void updateListLeague() {
        if (mPositionSelect > 0) {
            League league = League.findById(League.class, mLeagues.get(mPositionSelect).getId());
            mLeagues.set(mPositionSelect, league);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListLeague();
    }

    @Override
    public void onItemLeagueClick(int position) {
        long id = mLeagues.get(position).getId();
        mPositionSelect = position;
        TeamActivity_.intent(this).mLeagueId(id).start();
    }

    @Override
    public void onItemCheckboxChecked(int position) {
       mLeagues.get(position).setChecked(true);
        Log.e("Checked", position + "");
    }

    @Override
    public void onItemCheckboxUnchecked(int position) {
        Toast.makeText(this, "UnChecked" + position, Toast.LENGTH_SHORT).show();
        mLeagues.get(position).setChecked(false);
    }

    @Override
    public void onBackPressed() {
       onBackSystemPress();
    }

    @Click(R.id.mImgBtnDeleteLeague)
    void onImageButtonDeleteLeagueClick() {
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        mImgBtnDeleteLeague.setVisibility(View.INVISIBLE);
        mFabRemoveLeague.setVisibility(View.VISIBLE);
        for(int i = mLeagues.size()-1; i >=0; i--){
            Log.e("Position ", + i +" " +  mLeagues.get(i).isChecked()+ "");
            if(mLeagues.get(i).isChecked()){
                long id = mLeagues.get(i).getId();
                mLeagues.remove(i);
                //League.deleteLeague(i);
                League.deleteLeague(id);
            }
        }
            mAdapter.setIsRemoved(true);
            mRecyclerViewLeague.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
    }

    @Click(R.id.mFabRemoveLeague)
    void onButtonRemoveLeagueClick() {
        mFabRemoveLeague.setVisibility(View.GONE);
        mImgBtnDeleteLeague.setVisibility(View.VISIBLE);
        if (mAdapter.getIsRemoved()) {
            mAdapter.setIsRemoved(false);
        }
        mRecyclerViewLeague.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Click(R.id.mFabAddLeague)
    void onButtonAddLeagueClick() {
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
                if(!mIsUpload){
                    Toast.makeText(getBaseContext(), "Upload Image", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, Common.REQUEST_CODE_LOAD_IMAGE);
                }
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
                } else if (!checkLeagueName(name)) {
                    League league = new League();
                    league.setName(name);
                    league.setLogo(mPath);
                    league.setInformation(infor);
                    league.save();
                    mLeagues.add(league);
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
    private boolean checkLeagueName(String name){
        List<League> leagues = League.getLeagueByName(name);
        return (leagues != null && leagues.size() > 0);
    }
    private void onBackSystemPress(){
        if(!mAdapter.getIsRemoved()){
            mAdapter.setIsRemoved(true);
            mImgBtnDeleteLeague.setVisibility(View.INVISIBLE);
            mFabRemoveLeague.setVisibility(View.VISIBLE);
        }
        mRecyclerViewLeague.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        if(mIsPressDoubleBack){
            super.onBackPressed();
            return;
        }
        mIsPressDoubleBack = true;
        Toast.makeText(this, getResources().getString(R.string.text_back_to_exit), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIsPressDoubleBack = false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Common.REQUEST_CODE_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //String picturePath = cursor.getString(columnIndex);
            mPath = cursor.getString(columnIndex);
            Log.e("Path", mPath + "");
            cursor.close();
            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
