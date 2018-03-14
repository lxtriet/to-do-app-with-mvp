package com.hcmute.trietthao.yourtime.mvp.searchWork.presenter;

import com.hcmute.trietthao.yourtime.database.DBGroupWorkServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GetGroupWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.searchWork.view.ISearchWorkView;

import java.util.ArrayList;

/**
 * Created by lxtri on 11/14/2017.
 */

public class SearchGetWorkPresenter implements ISearchWorkPresenter,GetWorkListener,GetGroupWorkListener {

    ISearchWorkView iSearchWorkView;
    ArrayList<NhomCVModel> mListNhomCV;
    ArrayList<CongViecModel> mListCV;
    DBWorkServer dbWorkServer;
    DBGroupWorkServer dbGroupWorkServer;
    int idnguoidung; String keysearch;
    boolean flag= true;

    public SearchGetWorkPresenter(ISearchWorkView iSearchWorkView){ this.iSearchWorkView = iSearchWorkView;}

    public ArrayList<NhomCVModel> getListSearchOnline(){ return mListNhomCV;}

    @Override
    public void getAllWorkSearchOnline(int idnguoidung, String keysearch) {

        mListNhomCV = new ArrayList<NhomCVModel>();
        mListCV = new ArrayList<CongViecModel>();

        iSearchWorkView.showLoading();

        dbGroupWorkServer = new DBGroupWorkServer(this);
        dbGroupWorkServer.getListGroupWork(idnguoidung);

        this.idnguoidung = idnguoidung;
        this.keysearch = keysearch;
    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        mListCV = congViecModelArrayList;
        setupListNhomCV();
    }

    @Override
    public void getListGroupWork(ArrayList<NhomCVModel> listGroupWork) {
        if(listGroupWork==null){
            iSearchWorkView.hideLoading();
            iSearchWorkView.getListGroupWorkFail();
        }else{
            NhomCVModel inbox = new NhomCVModel();
            inbox.setTenNhom("Inbox");
            inbox.setIdNhom(0);
            mListNhomCV.add(inbox);
            mListNhomCV.addAll(listGroupWork);

            dbWorkServer = new DBWorkServer(this);
            dbWorkServer.getListAllWorkSearch(idnguoidung,keysearch);
        }
    }

    public void setupListNhomCV(){
        for(int i=0;i<mListNhomCV.size();i++){
            ArrayList<CongViecModel> listCVTemp = new ArrayList<>();
            for(int j=0;j<mListCV.size();j++){
                if(mListNhomCV.get(i).getIdNhom().equals(mListCV.get(j).getIdNhom())){
                    listCVTemp.add(mListCV.get(j));
                    flag = false;
                }
            }
            mListNhomCV.get(i).setCongViecModels(listCVTemp);
        }
        iSearchWorkView.hideLoading();
        if(flag && !keysearch.equals(""))
            iSearchWorkView.getListGroupWorkEmpty();
        else
        {
            flag = true;
            iSearchWorkView.getListGroupWorkSucess();
        }

    }

}
