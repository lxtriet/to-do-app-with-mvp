package com.hcmute.trietthao.yourtime.mvp.chooseList.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.mvp.chooseList.presenter.ChooseListPresenter;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getIntCurrentDateTime;

public class ChooseListActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener, IChooseListView {

    @Bind(R.id.btn_choose_list_continue)
    Button mBtnContinue;
    @Bind(R.id.checkb_travel)
    CheckBox mCheckbTravel;
    @Bind(R.id.checkb_work)
    CheckBox mCheckbWork;
    @Bind(R.id.checkb_family)
    CheckBox mCheckbFamily;
    @Bind(R.id.checkb_private)
    CheckBox mCheckbPrivate;
    int dem=0;

    int idNhom;
    int idNhomCurrent;
    PreferManager mPreferManager;

    ChooseListPresenter mChooseListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);

        mChooseListPresenter= new ChooseListPresenter(this);
        mPreferManager= new PreferManager(getBaseContext());

        ButterKnife.bind(this);
        mCheckbTravel.setOnCheckedChangeListener(this);
        mCheckbWork.setOnCheckedChangeListener(this);
        mCheckbFamily.setOnCheckedChangeListener(this);
        mCheckbPrivate.setOnCheckedChangeListener(this);

        mBtnContinue.setBackground(getResources().getDrawable(R.drawable.button_low_blue_shadown));



        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(mCheckbTravel.isChecked()||mCheckbWork.isChecked()||mCheckbFamily.isChecked()||mCheckbPrivate.isChecked())
//                {
//                    Intent main= new Intent(ChooseListActivity.this, MainActivity.class);
//                    startActivity(main);
//                }
                if(mCheckbTravel.isChecked()){
                    idNhom=getIntCurrentDateTime();
                    mChooseListPresenter.insertGroupWork(idNhom,"Travel",1);
                    mChooseListPresenter.insertGroupWorkUser(idNhom,mPreferManager.getID(),"owner");
                }
                if(mCheckbWork.isChecked()){
                    idNhomCurrent=getIntCurrentDateTime();
                    while (idNhom==idNhomCurrent){
                        idNhomCurrent++;
                    }

                    mChooseListPresenter.insertGroupWork(idNhomCurrent,"Work",1);
                    mChooseListPresenter.insertGroupWorkUser(idNhomCurrent,mPreferManager.getID(),"owner");
                }
                if(mCheckbFamily.isChecked()){
                    idNhomCurrent=getIntCurrentDateTime();
                    while (idNhom==idNhomCurrent){
                        idNhomCurrent++;
                    }
                    mChooseListPresenter.insertGroupWork(idNhomCurrent,"Family",1);
                    mChooseListPresenter.insertGroupWorkUser(idNhomCurrent,mPreferManager.getID(),"owner");
                }
                if(mCheckbPrivate.isChecked()){
                    idNhomCurrent=getIntCurrentDateTime();
                    while (idNhom==idNhomCurrent){
                        idNhomCurrent++;
                    }
                    mChooseListPresenter.insertGroupWork(idNhomCurrent,"Private",1);
                    mChooseListPresenter.insertGroupWorkUser(idNhomCurrent,mPreferManager.getID(),"owner");
                }
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();
        switch (id){
            case R.id.checkb_travel:
                if(b){
                    dem++;
                }
                else
                    dem--;
                break;
            case R.id.checkb_work:
                if(b)
                    dem++;
                else
                    dem--;
                break;
            case R.id.checkb_family:
                if(b)
                    dem++;
                else
                    dem--;
                break;
            case R.id.checkb_private:
                if(b)
                    dem++;
                else
                    dem--;
                break;

        }
        if(dem==0){
            mBtnContinue.setEnabled(false);
            mBtnContinue.setBackground(getResources().getDrawable(R.drawable.button_low_blue_shadown));
        }else {
            mBtnContinue.setEnabled(true);
            mBtnContinue.setBackground(getResources().getDrawable(R.drawable.button_blue_shadow));
        }
    }

    @Override
    public void onClick(View view) {

    }

}
