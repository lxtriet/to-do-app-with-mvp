package com.hcmute.trietthao.yourtime.mvp.login.presenter;

import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.login.view.ILoginView;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.util.ArrayList;


public class LoginPresenter {

//    private ILoginView iLoginView;
//    private String email="";
//    PreferManager mPreferManager;
//
//
//    public LoginPresenter(ILoginView iLoginView) {
//        this.iLoginView=iLoginView;
//    }
//    @Override
//    public void getListUser(ArrayList<NguoiDungModel> listUser) {
//        boolean isSame = false;
//        // Kiểm tra email có tồn tại hay chưa
//        if(listUser!=null)
//        {
//            for(int i=0;i<listUser.size();i++)
//            {
//                if(listUser.get(i).getUserName().equals(email))
//                {
//                    isSame=true;  // Khi email tồn tại thì xuất thông báo
//                    break;
//                }
//            }
//        }
//        if(isSame)
//            iLoginView.accountAlreadyExists();
//        else
//        {
//            iLoginView.accountDoesNotExists();
//        }
//
//
//    }
//
//    @Override
//    public void getResultInsert(Boolean isSuccess) {
//
//    }
//
//    @Override
//    public void getUser(NguoiDungModel user) {
//
//
//    }
//
//    @Override
//    public void getUserFB(String email) {
//        this.email=email;
//    }
}
