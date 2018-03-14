package com.hcmute.trietthao.yourtime.mvp.signIn.presenter;


import android.content.Context;
import android.util.Log;

import com.hcmute.trietthao.yourtime.base.BasePresenter;
import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkNotificationListener;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CVThongBaoModel;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.signIn.view.ISignInView;

import java.text.ParseException;
import java.util.ArrayList;

import static com.hcmute.trietthao.yourtime.notification.NotificationService.createNotificationEnd;
import static com.hcmute.trietthao.yourtime.notification.NotificationService.createNotificationStart;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.isOverDueDate;


public class SignInPresenter extends BasePresenter implements ISignInPresenter, DBNguoiDungServer.userListener,
        GetWorkListener,
        PostWorkListener,GetWorkNotificationListener {

    DBNguoiDungServer dbNguoiDungServer;
    NguoiDungModel nguoiDungModel=null;
    ISignInView isignInView;
    String email="", passw="";
    DBWorkServer dbWorkServer;
    ArrayList<CongViecModel> mListWork;
    ArrayList<CVThongBaoModel> mListWorkNotification;
    Context context;
    int idUser;

    public SignInPresenter(ISignInView iSignInView, Context context) {
        this.isignInView=iSignInView;
        this.context = context;
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
    public void checkLogin(String email, String passw) {
        dbNguoiDungServer=new DBNguoiDungServer(this);
        dbWorkServer = new DBWorkServer(this,this,this);
        mListWork = new ArrayList<>();
        mListWorkNotification = new ArrayList<>();
        this.email=email;
        this.passw=passw;
        dbNguoiDungServer.getListUser(); // ok.override của retrofit mún chạy đx phải   có hàm sử dụng nó ra chứ. chạy lại đi

    }

    @Override
    public void createNotification(int idUser) {
        this.idUser = idUser;
        dbWorkServer.getListAllWork(idUser);
    }


    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {
        nguoiDungModel=listUser.get(0);
        if(nguoiDungModel.getUserName().equals(email) || nguoiDungModel.getPassW().equals(passw))
        {
            isignInView.loginSuccess();
        }else {
            isignInView.loginFail();
        }

    }

    @Override
    public void getResultInsert(Boolean isSuccess) {

    }

    @Override
    public void getUser(NguoiDungModel user) {

    }

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        mListWork = congViecModelArrayList;
        dbWorkServer.getListAllWorkNotification(idUser);

    }

    @Override
    public void getAllWorkNotification(ArrayList<CVThongBaoModel> congViecModelArrayList) {
        mListWorkNotification = congViecModelArrayList;
        CongViecModel cv;
        CVThongBaoModel cvtb;
        for(int i=0; i <mListWork.size(); i++){
            cv = mListWork.get(i);
            if(cv.getThoiGianBatDau()!=null){
                for(int j=0; j<mListWorkNotification.size(); j++){
                    cvtb = mListWorkNotification.get(j);
                    if(cv.getIdCongViec().equals(cvtb.getIdCongViec()) && cvtb.getTrangThai().equals("waiting")){
                        try {
                            if(!isOverDueDate(cvtb.getThoiGianBatDau())){
                                Log.e("SIGNIN"," create notification "+cv.getIdCongViec());
                                createNotificationStart(cv,cvtb,context);
                                createNotificationEnd(cv,cvtb,context);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
