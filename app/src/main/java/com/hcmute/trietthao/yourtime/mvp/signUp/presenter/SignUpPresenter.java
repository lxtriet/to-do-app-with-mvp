package com.hcmute.trietthao.yourtime.mvp.signUp.presenter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;

import com.hcmute.trietthao.yourtime.base.BasePresenter;
import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.login.view.LoginActivity;
import com.hcmute.trietthao.yourtime.mvp.signUp.view.ISignUpView;
import com.hcmute.trietthao.yourtime.mvp.signUp.view.SignUpActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SignUpPresenter extends BasePresenter implements ISignUpPresenter, DBNguoiDungServer.userListener {

    private DBNguoiDungServer dbNguoiDungServer;
    private ISignUpView iSignUpView;
    private String email="", pass="", name="", encodedString="";
    private int id;

    public SignUpPresenter(ISignUpView iSignUpView) {
        this.iSignUpView=iSignUpView;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {
        boolean isSame = false;
        // Kiểm tra email có tồn tại hay chưa
        if(listUser!=null)
        {
            for(int i=0;i<listUser.size();i++)
            {
                if(listUser.get(i).getUserName().equals(email))
                {
                    isSame=true;  // Khi email tồn tại thì xuất thông báo
                    break;
                }
            }
        }
        if(isSame)
            iSignUpView.signUpFail("Tài khoản đã tồn tại!!!");
        else
        {
            dbNguoiDungServer.insertUser(id,name, encodedString, email, pass);
            iSignUpView.getIntentData();
        }


    }

    @Override
    public void getResultInsert(Boolean isSuccess) {
        if(isSuccess)
        {
            iSignUpView.signUpSuccess();
        }else {
            iSignUpView.signUpFail("Insert user fail! Check your connection!");
        }

    }

    @Override
    public void getUser(NguoiDungModel user) {

    }

    @Override
    public void insertUser(int id, String name, String avatar, String userName, String passW) {
        dbNguoiDungServer=new DBNguoiDungServer(this);
        this.id=id;
        this.name=name;
        this.encodedString=avatar;
        this.email=userName;
        this.pass=passW;
        dbNguoiDungServer.getListUser();
    }

}
