package com.hcmute.trietthao.yourtime.mvp.calendarFragment.presenter;

import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.mvp.calendarFragment.view.ICalendarView;

import java.util.ArrayList;

/**
 * Created by lxtri on 1/9/2018.
 */

public class CalendarPresenter implements ICalendarPresenter,GetWorkListener {

    ICalendarView iCalendarView;
    ArrayList<CongViecModel> mListWork;
    DBWorkServer dbWorkServer;

    public CalendarPresenter(ICalendarView iCalendarView){
        this.iCalendarView = iCalendarView;
    }

    public ArrayList<CongViecModel> getListAllWorkOnline(){ return mListWork;}

    @Override
    public void getAllWorkOnline(int idUser) {
        dbWorkServer = new DBWorkServer(this);
        dbWorkServer.getListAllWork(idUser);
    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        mListWork = congViecModelArrayList;
        if(congViecModelArrayList!=null)
            iCalendarView.getAllWorkSucessful();
        else
            iCalendarView.getAllWorkFail();
    }
}
