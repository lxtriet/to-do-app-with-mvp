package com.hcmute.trietthao.yourtime.mvp.createGroupWork.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.createGroupWork.presenter.CreateGroupWorkPresenter;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getIntCurrentDateTime;


public class CreateGroupWorkActivity extends AppCompatActivity implements View.OnClickListener,
        ICreateGroupWorkView,DBNguoiDungServer.userListener{


    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.edit_group_name)
    EditText mEditGroupName;

    @Bind(R.id.txt_name_user)
    TextView txtNameUser;

    @Bind(R.id.txt_mail_user)
    TextView txtMailUser;

    @Bind(R.id.imgv_save)
    ImageView mImgvSave;

    @Bind(R.id.civ_image_user)
    CircleImageView civImageUser;

    int idNhom;
    String tenNhom;
    PreferManager mPreferManager;
    DBNguoiDungServer dbNguoiDungServer;

    CreateGroupWorkPresenter mCreateGroupWorkPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_groupwork);

        ButterKnife.bind(this);
        mImgvSave.setOnClickListener(this);
        mCreateGroupWorkPresenter=new CreateGroupWorkPresenter(this);
        mPreferManager=new PreferManager(getBaseContext());
        dbNguoiDungServer = new DBNguoiDungServer(this);
        HashMap<String, String> user = mPreferManager.getUserDetails();
        dbNguoiDungServer.getUser(user.get(PreferManager.KEY_EMAIL));



        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
        {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_save:
                tenNhom=mEditGroupName.getText().toString();
                idNhom=getIntCurrentDateTime();
                mCreateGroupWorkPresenter.insertGroupWork(idNhom,tenNhom,1);
                mCreateGroupWorkPresenter.insertGroupWorkUser(idNhom,mPreferManager.getID(),"owner");
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                break;
        }
    }

    @Override
    public void createGroupWorkSuccess() {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void createGroupWorkFail() {
        Toast.makeText(this,"Tạo nhóm công việc thất bại!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {

    }

    @Override
    public void getResultInsert(Boolean isSuccess) {

    }

    @Override
    public void getUser(NguoiDungModel user) {
        String url_imgitem="https://tlcn-yourtime.herokuapp.com/getimg?nameimg=";
        if(user.getAnhDaiDien()!=null)
        {
            Picasso.with(getApplication()).load(url_imgitem+user.getAnhDaiDien()+".png")
                    .error(R.drawable.null_avatar)
                    .into(civImageUser);
        }
        txtNameUser.setText(user.getTenNguoiDung());
        txtMailUser.setText(user.getUserName());
    }
}
