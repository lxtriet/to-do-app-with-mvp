package com.hcmute.trietthao.yourtime.mvp.settingFragment.presenter;

import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkNotificationListener;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CVThongBaoModel;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;

import java.util.ArrayList;

/**
 * Created by admin on 1/9/18.
 */

public class SettingPresenter implements ISettingPresenter, DBNguoiDungServer.userListener,
        GetWorkListener,
        PostWorkListener,GetWorkNotificationListener {
    @Override
    public void getResultPostWork(Boolean isSucess) {

    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {

    }

    @Override
    public void getAllWorkNotification(ArrayList<CVThongBaoModel> congViecModelArrayList) {

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
}
