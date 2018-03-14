package com.hcmute.trietthao.yourtime.mvp.createGroupWork.presenter;

import com.hcmute.trietthao.yourtime.database.DBGroupWorkServer;
import com.hcmute.trietthao.yourtime.database.DBGroupWorkUserServer;
import com.hcmute.trietthao.yourtime.database.GetGroupWorkByIDListener;
import com.hcmute.trietthao.yourtime.database.GetGroupWorkListener;
import com.hcmute.trietthao.yourtime.database.PostGroupWorkListener;
import com.hcmute.trietthao.yourtime.database.PostGroupWorkUserListener;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.createGroupWork.view.ICreateGroupWorkView;

import java.util.ArrayList;


public class CreateGroupWorkPresenter implements ICreateGroupWorkPresenter,
        PostGroupWorkListener, GetGroupWorkListener, PostGroupWorkUserListener {

    ICreateGroupWorkView iCreateGroupWorkView;
    String tenNhom="";
    DBGroupWorkServer mDBGroupWorkServer;
    DBGroupWorkUserServer mDBGroupWorkUserServer;

    public CreateGroupWorkPresenter(ICreateGroupWorkView iCreateGroupWorkView){
        this.iCreateGroupWorkView = iCreateGroupWorkView;
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
        if(isSuccess)
        {
            iCreateGroupWorkView.createGroupWorkSuccess();
        }else {
            iCreateGroupWorkView.createGroupWorkFail();
        }
    }

    @Override
    public void getListGroupWork(ArrayList<NhomCVModel> listGroupWork) {
        boolean isSame = false;
        // Kiểm tra email có tồn tại hay chưa
        if(listGroupWork!=null)
        {
            for(int i=0;i<listGroupWork.size();i++)
            {
                if(listGroupWork.get(i).getTenNhom().equals(tenNhom))
                {
                    isSame=true;  // Khi email tồn tại thì xuất thông báo
                    break;
                }
            }
        }
        if(isSame)
            iCreateGroupWorkView.showToast("Nhóm đã tồn tại!!!");
        else
        {
//            mDBGroupWorkServer.insertGroupWork(idNhom,tenNhom,laNhomCaNhan);
        }
    }

    @Override
    public void getResultPostGroupWorkUser(Boolean isSuccess) {
        if(isSuccess)
        {
            iCreateGroupWorkView.createGroupWorkSuccess();
        }else {
            iCreateGroupWorkView.createGroupWorkFail();
        }

    }
}
