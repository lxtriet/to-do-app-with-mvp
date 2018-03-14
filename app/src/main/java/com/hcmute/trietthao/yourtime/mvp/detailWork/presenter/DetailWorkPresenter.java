package com.hcmute.trietthao.yourtime.mvp.detailWork.presenter;

import android.content.Context;
import android.util.Log;

import com.hcmute.trietthao.yourtime.database.DBRepeatServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GetRepeatListener;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkNotificationListener;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CVThongBaoModel;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.LoaiNhacNhoModel;
import com.hcmute.trietthao.yourtime.mvp.detailWork.view.IDetailWorkView;
import com.hcmute.trietthao.yourtime.service.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;

import static com.hcmute.trietthao.yourtime.notification.NotificationService.cancelNotification;
import static com.hcmute.trietthao.yourtime.notification.NotificationService.createNotificationEnd;
import static com.hcmute.trietthao.yourtime.notification.NotificationService.createNotificationStart;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.isOverDueDate;

/**
 * Created by lxtri on 12/4/2017.
 */

public class DetailWorkPresenter implements IDetaiWorkPresenter,GetWorkListener,PostWorkListener,
        GetRepeatListener,GetWorkNotificationListener{

    CongViecModel currentWork;
    ArrayList<LoaiNhacNhoModel> loaiNhacNhoModelArrayList;
    IDetailWorkView iDetailWorkView;
    DBWorkServer dbWorkServer;
    DBRepeatServer dbRepeatServer;
    CongViecModel workCreateNotification;
    Boolean isDeleteAllNotification = false;
    Boolean isCreateNotification = false;
    int idWork;
    Context context;

    public DetailWorkPresenter(IDetailWorkView iDetailWorkView,Context context){
        this.iDetailWorkView = iDetailWorkView;
        this.context = context;
    }

    public  CongViecModel getDetailWork(){ return currentWork; }

    public  ArrayList<LoaiNhacNhoModel> getListRepeat(){ return loaiNhacNhoModelArrayList; }

    @Override
    public void getDetailWork(int idWork) {
        dbWorkServer = new DBWorkServer(this,this);
        dbWorkServer.getWorkById(idWork);
        dbRepeatServer = new DBRepeatServer(this);
        dbRepeatServer.getListRepeat();
    }

    @Override
    public void updateDetailWork(CongViecModel congViecModel) {
        dbWorkServer = new DBWorkServer(this,this);

    }

    @Override
    public void createNotification(int idWork) {
        Log.e("DETAILWORK","--createNotification");
        isCreateNotification = true;
        this.idWork = idWork;
        dbWorkServer = new DBWorkServer(this,this,this);
        dbWorkServer.getWorkById(idWork);
    }

    @Override
    public void deleteAllNotification(int idWork) {
        isDeleteAllNotification = true;
        dbWorkServer = new DBWorkServer(this,this,this);
        dbWorkServer.getListNotificationOfWork(idWork);
    }


    @Override
    public void getResultPostWork(Boolean isSucess) {

    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        if(congViecModelArrayList!=null){
            if(isCreateNotification){
                Log.e("DETAILWORK","--getListNotificationOfWork ");
                workCreateNotification = congViecModelArrayList.get(0);
                dbWorkServer.getListNotificationOfWork(idWork);
            }else{
                currentWork = congViecModelArrayList.get(0);
                iDetailWorkView.getDetailWorkSuccess();
            }
        }else
            iDetailWorkView.getDetailWorkFail();

    }

    @Override
    public void getListRepeat(ArrayList<LoaiNhacNhoModel> loaiNhacNhoModelArrayList) {
        loaiNhacNhoModelArrayList = loaiNhacNhoModelArrayList;
    }

    @Override
    public void getAllWorkNotification(ArrayList<CVThongBaoModel> congViecModelArrayList) {
        CVThongBaoModel cvtb = new CVThongBaoModel();
        if(congViecModelArrayList!=null){
            if(isDeleteAllNotification){
                for(int i=0; i <congViecModelArrayList.size(); i++){
                    cvtb = congViecModelArrayList.get(i);
                    if(cvtb.getTrangThai().equals("waiting")){
                        int idNotificationStart = 0;
                        int idNotificationEnd = 0;
                        try {
                            idNotificationStart = DateUtils.getIdNotification(cvtb.getThoiGianBatDau(),cvtb.getIdCongViec());
                            idNotificationEnd = DateUtils.getIdNotification(cvtb.getThoiGianKetThuc(),cvtb.getIdCongViec());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        cancelNotification(idNotificationStart,context);
                        cancelNotification(idNotificationEnd,context);
                        Log.e("DELETED","--deleted notification--"+cvtb.getIdCongViec()+"--time--"+cvtb.getThoiGianBatDau());
                    }
                    isDeleteAllNotification = false;
                }
            }
        }
    }
}
