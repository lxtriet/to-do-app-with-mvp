package com.hcmute.trietthao.yourtime.mvp.tasksFragment.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.hcmute.trietthao.yourtime.database.DBGroupWorkServer;
import com.hcmute.trietthao.yourtime.database.DBGroupWorkUserServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GetGroupWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkNotificationListener;
import com.hcmute.trietthao.yourtime.database.PostGroupWorkListener;
import com.hcmute.trietthao.yourtime.database.PostGroupWorkUserListener;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CVThongBaoModel;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.view.ITasksView;

import java.text.ParseException;
import java.util.ArrayList;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDateTimeToInsertUpdate;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.isOverDueDate;


public class TasksPresenter  implements ITasksPresenter,GetGroupWorkListener,GetWorkListener,
        PostWorkListener,GetWorkNotificationListener,
        PostGroupWorkListener, PostGroupWorkUserListener {

    ITasksView iTasksView;
    private static ArrayList<NhomCVModel> mList;
    private static ArrayList<CongViecModel> mListWork;
    DBGroupWorkServer dbGroupWorkServer;
    DBWorkServer dbWorkServer;
    DBGroupWorkUserServer dbGroupWorkUserServer;
    boolean isDeletedWork = false;
    int idUser;
    Context context;

    public TasksPresenter(ITasksView iTasksView,Context context){
        this.iTasksView = iTasksView;
        this.context = context;
    }

    public ArrayList<NhomCVModel> getListGroupWorkOnline(){ return mList;}
    public ArrayList<CongViecModel> getListAllWorkOnline(){ return mListWork;}

    @Override
    public void getAllGroupWorkOnline(int idUser) {
        iTasksView.showLoading();
        dbGroupWorkServer = new DBGroupWorkServer(this,this);
        dbGroupWorkServer.getListGroupWork(idUser);
    }

    @Override
    public void getAllWorkOnline(int idUser) {
        this.idUser = idUser;
        Log.e("TaskPresenter","Vaoo get all workonline");
        dbWorkServer = new DBWorkServer(this,this,this);
        dbWorkServer.getListAllWork(idUser);
    }

    @Override
    public void deleteGroupWork(int idGroup, int idUser) {
        isDeletedWork = true;

        dbGroupWorkServer=new DBGroupWorkServer(this,this);
        dbGroupWorkUserServer= new DBGroupWorkUserServer(this);
        dbWorkServer=new DBWorkServer(this,this);
//
        dbWorkServer.getListWorkByIdGroup(idUser,idGroup);

        dbGroupWorkUserServer.deleteGroupWorkUser(idGroup);
        dbGroupWorkServer.deleteGroupWorkUser(idGroup);
    }

    @Override
    public void deleteWork(int idWork) {

    }

    @Override
    public void getListGroupWork(final ArrayList<NhomCVModel> listGroupWork) {
        mList = new ArrayList<>();
        final Handler handler = new Handler();
        if(listGroupWork!=null){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mList = listGroupWork;
                    iTasksView.hideLoading();
                    iTasksView.getAllGroupWorkSuccess();
                }
            }, 1000);
        }
    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        if(isDeletedWork){
            dbWorkServer=new DBWorkServer(this,this);
            for(int i=0;i<congViecModelArrayList.size();i++){
                dbWorkServer.deleteWorkNotification(congViecModelArrayList.get(i).getIdCongViec());
                dbWorkServer.deleteWork(congViecModelArrayList.get(i).getIdCongViec());
            }
            isDeletedWork = false;
        }else{
            for(int i=0;i<congViecModelArrayList.size();i++){
                if(congViecModelArrayList.get(i).getThoiGianKetThuc()!=null){
                    try {
                        if(congViecModelArrayList.get(i).getTrangThai().equals("waiting")){
                            if(isOverDueDate(congViecModelArrayList.get(i).getThoiGianKetThuc())){
                                congViecModelArrayList.get(i).setTrangThai("overdue");
                                dbWorkServer.updateStatusWork("overdue",congViecModelArrayList.get(i).getIdCongViec());
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            mListWork = congViecModelArrayList;
            dbWorkServer.getListAllWorkNotification(idUser);
            iTasksView.getListAllWorkSucess();
        }
    }

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }

    @Override
    public void getAllWorkNotification(ArrayList<CVThongBaoModel> congViecModelArrayList) {
        for (int i = 0; i < congViecModelArrayList.size(); i++) {
            if (congViecModelArrayList.get(i).getThoiGianKetThuc() != null) {
                try {
                    if (congViecModelArrayList.get(i).getTrangThai().equals("waiting")) {
                        if (isOverDueDate(congViecModelArrayList.get(i).getThoiGianKetThuc())) {
                            congViecModelArrayList.get(i).setTrangThai("overdue");
                            dbWorkServer.updateStatusWorkTimeNotNull("overdue",
                                    congViecModelArrayList.get(i).getIdCongViec(),
                                    getDateTimeToInsertUpdate(congViecModelArrayList.get(i).getThoiGianBatDau()));
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void getResultPostGroupWork(Boolean isSuccess) {

    }

    @Override
    public void getResultPostGroupWorkUser(Boolean isSuccess) {

    }
}
