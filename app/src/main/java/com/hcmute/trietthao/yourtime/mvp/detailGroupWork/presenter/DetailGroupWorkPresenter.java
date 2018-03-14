package com.hcmute.trietthao.yourtime.mvp.detailGroupWork.presenter;

import com.hcmute.trietthao.yourtime.database.DBGroupWorkServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GetGroupWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view.IDetailGroupWorkView;

import java.util.ArrayList;

/**
 * Created by lxtri on 11/15/2017.
 */

public class DetailGroupWorkPresenter implements IDetailGroupWorkPresenter,GetWorkListener,PostWorkListener,
        GetGroupWorkListener{

    ArrayList<CongViecModel> mListWorkNormal, mListWorkCompleted;
    IDetailGroupWorkView iDetailGroupWorkView;
    DBWorkServer dbWorkServer;
    DBGroupWorkServer dbGroupWorkServer;
    NhomCVModel nhomCVModel;
    int idgroup;

    public DetailGroupWorkPresenter(IDetailGroupWorkView iDetailGroupWorkView){
        this.iDetailGroupWorkView = iDetailGroupWorkView;
    }

    public ArrayList<CongViecModel> getListWorkNormal(){return  mListWorkNormal;}
    public ArrayList<CongViecModel> getListWorkCompleted(){return  mListWorkCompleted;}
    public NhomCVModel getGroupWorkDetail(){return  nhomCVModel;}


    @Override
    public void getWorkByIdGroup(Integer idnguoidung,Integer idgroup) {

        iDetailGroupWorkView.showLoading();
        dbWorkServer = new DBWorkServer(this,this);
        dbWorkServer.getListWorkByIdGroup(idnguoidung,idgroup);
    }

    @Override
    public void getDetailGroupWork(Integer idnguoidung,int idgroup) {
        dbGroupWorkServer = new DBGroupWorkServer(this);
        if(idgroup==0){
            nhomCVModel = new NhomCVModel();
            nhomCVModel.setTenNhom("Inbox");
            iDetailGroupWorkView.getGroupWorkDetailSucess();
        }else{
            this.idgroup = idgroup;
            dbGroupWorkServer.getListGroupWork(idnguoidung);
        }
    }

    @Override
    public void insertWork(CongViecModel congViecModel) {
        dbWorkServer.insertWork(congViecModel);
    }

    @Override
    public void deleteWork(int idcongviec) {
        dbWorkServer = new DBWorkServer(this,this);
        dbWorkServer.deleteWork(idcongviec);
        dbWorkServer.deleteWorkNotification(idcongviec);
    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        mListWorkNormal = new ArrayList<CongViecModel>();
        mListWorkCompleted = new ArrayList<CongViecModel>();
        if(congViecModelArrayList==null){
            iDetailGroupWorkView.hideLoading();
            iDetailGroupWorkView.getWorkByIDGroupFail();
        }else{
            for(int i=0;i<congViecModelArrayList.size();i++){
                if(congViecModelArrayList.get(i).getTrangThai().equals("done"))
                    mListWorkCompleted.add(congViecModelArrayList.get(i));
                else
                    mListWorkNormal.add(congViecModelArrayList.get(i));
            }
            iDetailGroupWorkView.hideLoading();
            iDetailGroupWorkView.getWorkByIDGroupSuccess();
        }
    }

    @Override
    public void getResultPostWork(Boolean isSucess) {
        if(isSucess)
            iDetailGroupWorkView.insertWorkSuccess();
        else
            iDetailGroupWorkView.insertWorkFail();
    }

    @Override
    public void getListGroupWork(ArrayList<NhomCVModel> listGroupWork) {
        for(int i=0; i<listGroupWork.size(); i++){
            if(listGroupWork.get(i).getIdNhom().equals(idgroup)){
                nhomCVModel = listGroupWork.get(i); break;
            }
        }
        iDetailGroupWorkView.getGroupWorkDetailSucess();
    }
}
