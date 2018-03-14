package com.hcmute.trietthao.yourtime.mvp.settingFragment.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkNotificationListener;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CVThongBaoModel;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.accountDetails.view.AccountDetailsActivity;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;
import com.hcmute.trietthao.yourtime.service.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hcmute.trietthao.yourtime.notification.NotificationService.cancelNotification;


public class SettingsFragment extends Fragment implements DBNguoiDungServer.userListener,
        GetWorkListener,
        PostWorkListener,GetWorkNotificationListener {


    CircleImageView mImgVAvatar;
    TextView mTxtUserName;

    TabHost mTabHost;
    TabWidget mTabWidget;
    RelativeLayout mRltProfile;
    LinearLayout mLnSignOut, mLnDetails;
    int CurrentTab = -1;
    public static boolean isListOpen = false;
    PreferManager preferManager;

    DBNguoiDungServer dbNguoiDungServer;
    DBWorkServer dbWorkServer;
    ArrayList<CVThongBaoModel> mListWorkNotification;
    public static NguoiDungModel userCurrent=null;



    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mTabHost = view.findViewById(R.id.tabhost);
        mRltProfile=view.findViewById(R.id.rlt_profile);

        mImgVAvatar=view.findViewById(R.id.img_avatar);
        mTxtUserName=view.findViewById(R.id.txt_user_name);

        mLnDetails=view.findViewById(R.id.ln_account_details);
        mLnSignOut=view.findViewById(R.id.ln_sign_out);

        preferManager=new PreferManager(getActivity().getApplicationContext());
        dbNguoiDungServer=new DBNguoiDungServer(this);
        dbWorkServer = new DBWorkServer(this,this,this);


        // get user data from session
        HashMap<String, String> user = preferManager.getUserDetails();
        Log.e("Settingfragment:",preferManager.getID()+"");
        dbNguoiDungServer.getUser(user.get(PreferManager.KEY_EMAIL));


        tabHostSetup();      // Khởi tạo tabhost chính

        mTabWidget.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(0);
            }
        });
        mTabWidget.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(1);
            }
        });
        mTabWidget.getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(2);
            }
        });
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        mLnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details= new Intent(getActivity(), AccountDetailsActivity.class);
                startActivity(details);

            }
        });
        mLnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Sign Out");
                alertDialogBuilder.setMessage("Are you sure you want to sign out?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                dbWorkServer.getListAllWorkNotification(preferManager.getID());
//                                LoginManager.getInstance().logOut();
                            }
                        });

                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


        return view;
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
        userCurrent = user;
        if(userCurrent.getAnhDaiDien()!=null)
        {
            Picasso.with(getActivity()).load(url_imgitem+userCurrent.getAnhDaiDien()+".png")
                    .error(R.drawable.null_avatar)
                    .into(mImgVAvatar);
        }
        mTxtUserName.setText(userCurrent.getTenNguoiDung());
        Log.e("SettingFragment:","Anh avatar"+url_imgitem+userCurrent.getAnhDaiDien()+".png");

    }



    public void tabHostSetup() {
        mTabHost.setup();
        TabHost.TabSpec tabAccount = mTabHost.newTabSpec("Account");
        tabAccount.setContent(R.id.ln_tab_account);
        tabAccount.setIndicator("Account");
        mTabHost.addTab(tabAccount);

        TabHost.TabSpec tabGeneral = mTabHost.newTabSpec("General");
        tabGeneral.setIndicator("General");
        tabGeneral.setContent(R.id.ln_tab_general);
        mTabHost.addTab(tabGeneral);

        TabHost.TabSpec tabAddress = mTabHost.newTabSpec("Extras");
        tabAddress.setIndicator("Extras");
        tabAddress.setContent(R.id.ln_tab_extras);
        mTabHost.addTab(tabAddress);

        mTabWidget = mTabHost.getTabWidget();

        mTabHost.setCurrentTab(3);
    }

    private void hideList(){
        isListOpen = false;
        CurrentTab = 3;
        mTabHost.setCurrentTab(CurrentTab);
    }
    private void showList(int tab){
        isListOpen = true;
        CurrentTab = tab;
        mTabHost.setCurrentTab(tab);
    }
    private void changeTab(int tab) {
        if (CurrentTab == tab) {
            hideList();
        } else {
            showList(tab);
        }
    }

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {

    }

    @Override
    public void getAllWorkNotification(ArrayList<CVThongBaoModel> congViecModelArrayList) {
        mListWorkNotification = congViecModelArrayList;
        CVThongBaoModel cvtb;
        for(int i=0; i <mListWorkNotification.size(); i++){
            cvtb = mListWorkNotification.get(i);
            if(cvtb.getTrangThai().equals("waiting")){
                int idNotificationStart = 0;
                int idNotificationEnd = 0;
                try {
                    idNotificationStart = DateUtils.getIdNotification(cvtb.getThoiGianBatDau(),cvtb.getIdCongViec());
                    idNotificationEnd = DateUtils.getIdNotification(cvtb.getThoiGianKetThuc(),cvtb.getIdCongViec());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cancelNotification(idNotificationStart,getContext());
                cancelNotification(idNotificationEnd,getContext());
                Log.e("DELETED","--deleted notification--"+cvtb.getIdCongViec()+"--time--"+cvtb.getThoiGianBatDau());
            }
        }
        preferManager.logoutUser();
    }
}