package com.hcmute.trietthao.yourtime.mvp.chooseList.presenter;

import com.hcmute.trietthao.yourtime.database.DBGroupWorkServer;
import com.hcmute.trietthao.yourtime.database.DBGroupWorkUserServer;
import com.hcmute.trietthao.yourtime.database.GetGroupWorkListener;
import com.hcmute.trietthao.yourtime.database.PostGroupWorkListener;
import com.hcmute.trietthao.yourtime.database.PostGroupWorkUserListener;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.chooseList.view.IChooseListView;

import java.util.ArrayList;

/**
 * Created by lttha on 11/29/2017.
 */

public class ChooseListPresenter implements IChooseListPresenter,
        PostGroupWorkListener, GetGroupWorkListener, PostGroupWorkUserListener {

    IChooseListView iChooseListView;
    DBGroupWorkServer mDBGroupWorkServer;
    DBGroupWorkUserServer mDBGroupWorkUserServer;

    public ChooseListPresenter(IChooseListView iChooseListView){
        this.iChooseListView = iChooseListView;
    }

    @Override
    public void insertGroupWork(int idNhom, String tenNhom, int laNhomCaNhan) {
        mDBGroupWorkServer= new DBGroupWorkServer(this,this);
        mDBGroupWorkServer.insertGroupWork(idNhom,tenNhom,laNhomCaNhan);

    }

    @Override
    public void insertGroupWorkUser(int idNhom, int idNguoiDung, String vaitro) {
        mDBGroupWorkUserServer= new DBGroupWorkUserServer(this);
        mDBGroupWorkUserServer.insertGroupWorkUser(idNhom,idNguoiDung,vaitro);

    }

    @Override
    public void getResultPostGroupWork(Boolean isSuccess) {

    }

    @Override
    public void getResultPostGroupWorkUser(Boolean isSuccess) {

    }

    @Override
    public void getListGroupWork(ArrayList<NhomCVModel> listGroupWork) {

    }
}
