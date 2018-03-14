package com.hcmute.trietthao.yourtime.mvp.calendarFragment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.base.BaseFragment;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.mvp.calendarFragment.presenter.CalendarPresenter;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.view.TasksFragment;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;
import com.hcmute.trietthao.yourtime.service.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends BaseFragment implements ICalendarView {

    private static ArrayList<CongViecModel> mListWork;
    List<WeekViewEvent> events;
    CalendarPresenter mCalendarPresenter;
    PreferManager mPreferManager;

    private WeekView mWeekView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCalendarPresenter = new CalendarPresenter(this);
        mPreferManager = new PreferManager(getContext());
        mCalendarPresenter.getAllWorkOnline(mPreferManager.getID());
    }

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        mWeekView = view.findViewById(R.id.weekView);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(final int newYear, int newMonth) {
        final Calendar temp = Calendar.getInstance();
        mListWork = TasksFragment.mListCV;

        events = new ArrayList<WeekViewEvent>();
        if(mListWork!=null){
            for (int i=0; i<mListWork.size();i++){
                CongViecModel congViecModel = mListWork.get(i);
                WeekViewEvent event = new WeekViewEvent();
                if(mListWork.get(i).getThoiGianBatDau()!=null) {
                    try {
                        if (newYear==temp.get(Calendar.YEAR)-1) {
                            Log.e("calenderFragment::","Vao create------"+i+"--"+newMonth);
                            event = new WeekViewEvent(mListWork.get(i).getIdCongViec(), congViecModel.getTenCongViec(),
                                    DateUtils.converStringToCalendar(congViecModel.getThoiGianBatDau()),
                                    DateUtils.converStringToCalendar(congViecModel.getThoiGianKetThuc()));
                            if (mListWork.get(i).getCoUuTien() == 1)
                                event.setColor(getResources().getColor(R.color.colorRed));
                            else{
                                if(mListWork.get(i).getIdCongViec()%5 == 0)
                                    event.setColor(getResources().getColor(R.color.event_color_01));
                                if(mListWork.get(i).getIdCongViec()%5 == 1)
                                    event.setColor(getResources().getColor(R.color.colorBlue));
                                if(mListWork.get(i).getIdCongViec()%5 == 2)
                                    event.setColor(getResources().getColor(R.color.event_color_04));
                                if(mListWork.get(i).getIdCongViec()%5 == 3)
                                    event.setColor(getResources().getColor(R.color.colorNearGreen));
                                if(mListWork.get(i).getIdCongViec()%5 == 4)
                                    event.setColor(getResources().getColor(R.color.colorGray600));
                            }
                            events.add(event);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        return events;
    }


    @Override
    public void getAllWorkSucessful() {
        mListWork = mCalendarPresenter.getListAllWorkOnline();
        onMonthChange(2017,5);
        mWeekView.notifyDatasetChanged();
        mWeekView.invalidate();
    }

    @Override
    public void getAllWorkFail() {
        Toast.makeText(getContext(),"Check your connection!!!",Toast.LENGTH_LONG).show();
    }
}
