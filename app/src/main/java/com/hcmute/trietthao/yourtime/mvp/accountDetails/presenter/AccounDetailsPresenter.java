package com.hcmute.trietthao.yourtime.mvp.accountDetails.presenter;

import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.accountDetails.view.IAccountDetailsView;

import java.util.ArrayList;

/**
 * Created by lttha on 12/1/2017.
 */

public class AccounDetailsPresenter implements DBNguoiDungServer.userListener, IAccountDetailsPresenter {
    IAccountDetailsView mIAccountDetailsView;
    DBNguoiDungServer mDBNguoiDungServer;

    public AccounDetailsPresenter(IAccountDetailsView mIAccountDetailsView){
        this.mIAccountDetailsView = mIAccountDetailsView;
    }


    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {

    }

    @Override
    public void getResultInsert(Boolean isSuccess) {

    }

    @Override
    public void getUser(NguoiDungModel user) {

    }

    @Override
    public void updateProfileUser(String tenNguoiDung, String anhDaiDien, String userName, String passW, int idNguoiDung) {
        mDBNguoiDungServer= new DBNguoiDungServer(this);
        mDBNguoiDungServer.updateProfile(tenNguoiDung,userName,passW,idNguoiDung);
        if(!anhDaiDien.equals("")){
            mDBNguoiDungServer.updateImgUser(anhDaiDien,idNguoiDung);
        }
    }
}
