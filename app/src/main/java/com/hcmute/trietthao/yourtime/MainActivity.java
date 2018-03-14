package com.hcmute.trietthao.yourtime;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.calendarFragment.view.CalendarFragment;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view.DetailGroupWorkActivity;
import com.hcmute.trietthao.yourtime.mvp.login.view.LoginActivity;
import com.hcmute.trietthao.yourtime.mvp.settingFragment.view.SettingsFragment;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.adapter.GroupWorkServerAdapter;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.adapter.IOnItemGroupWorkTasksListener;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.presenter.TasksPresenter;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.view.ITasksView;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.view.TasksFragment;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.notification.NotificationService.createNotificationEnd;
import static com.hcmute.trietthao.yourtime.notification.NotificationService.createNotificationStart;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDateTimeToInsertUpdate;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getIntCurrentDateTime;


public class MainActivity extends AppCompatActivity implements DBNguoiDungServer.userListener,View.OnClickListener, ITasksView,
        IOnItemGroupWorkTasksListener,PostWorkListener{

    @Bind(R.id.fab_create_work)
    FloatingActionButton mFabCreateWork;

    public static BottomNavigationView mBottomNavigationView;

    RelativeLayout rlBottomSheetChooseGroup;
    ImageView ivBottomSheetSetReminderStart, ivBottomSheetSetReminderEnd, ivBottomSheetSetPriority, ivBottomSheetImgGroup;
    TextView tvBottomSheetAddWork,tvBottomSheetCancel, tvBottomSheetNameGroup;
    EditText etBottomSheetNameWork;

    ScreenUtils screenUtils;

    View mBottomSheetCreateWorkView;
    PreferManager preferManager;

    DBNguoiDungServer dbNguoiDungServer;
    HashMap<String, String> user;

    BottomSheetDialog bottomSheetDialog;

    AlertDialog.Builder dialogReminder;
    LayoutInflater layoutInflaterRemider;
    View viewRemider;
    AlertDialog alertDialogRemider;
    TextView tvSaveRemider, tvRemoveRemider, tvReminder, tvTitleRemider;
    LinearLayout lnlTimeReminder;
    CalendarView calendarViewReminder;

    // value insert work
    Calendar timeReminderStart;
    Calendar timeReminderEnd;
    int idGroupWorkCurrent = 0;
    String nameGroupCurrent = "Inbox";
    boolean isTimeEndChoose = false;
    boolean isChooseTime = false;
    int isPriority = 0;
    NhomCVModel currentGroup;

    DBWorkServer dbWorkServer;

    TasksPresenter mTasksPresenter;
    ArrayList<NhomCVModel> mListNhomCV;
    String EXTRA_GROUPWORK_ID ="",EXTRA_WORK_ID="",  REQUEST_SCREEN="";




    public static NguoiDungModel userCurrent=null;
    private int MAIN_REQ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mBottomNavigationView = findViewById(R.id.navigation);

        setupBottomSheetView();
        preferManager = new PreferManager(getApplicationContext());
        dbNguoiDungServer=new DBNguoiDungServer(this);
        dbWorkServer = new DBWorkServer(this);

        mTasksPresenter = new TasksPresenter(this,getApplicationContext());
        mTasksPresenter.getAllGroupWorkOnline(preferManager.getID());
        REQUEST_SCREEN = getIntent().getStringExtra("REQUEST_SCREEN");
        if(getIntent().getStringExtra("REQUEST_SCREEN")!=null){
            String[] parts = REQUEST_SCREEN.split("-");
            Intent intent = new Intent(getApplicationContext(), DetailGroupWorkActivity.class);
            intent.putExtra("EXTRA_GROUPWORK_ID",parts[1] );
            intent.putExtra("REQUEST_SCREEN",parts[0] );
            intent.putExtra("EXTRA_WORK_ID",parts[2]);
            startActivity(intent);
        }

        if(preferManager.isLoggedIn()) {
            HashMap<String, String> user = preferManager.getUserDetails();
            dbNguoiDungServer.getUser(user.get(preferManager.KEY_EMAIL));
        }
        else
        {
            Intent login= new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(login,MAIN_REQ);
        }

        rlBottomSheetChooseGroup.setOnClickListener(this);
        ivBottomSheetSetReminderStart.setOnClickListener(this);
        ivBottomSheetSetReminderEnd.setOnClickListener(this);
        ivBottomSheetSetPriority.setOnClickListener(this);
        tvBottomSheetAddWork.setOnClickListener(this);
        tvBottomSheetCancel.setOnClickListener(this);


        mBottomNavigationView .setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_tasks:
                                selectedFragment = TasksFragment.newInstance();
                                mFabCreateWork.animate().alpha(1.0f).setDuration(200);
                                mFabCreateWork.setVisibility(View.VISIBLE);
                                break;
                            case R.id.navigation_calendar:
                                selectedFragment = CalendarFragment.newInstance();
                                mFabCreateWork.animate().alpha(1.0f).setDuration(200);
                                mFabCreateWork.setVisibility(View.VISIBLE);
                                break;
                            case R.id.navigation_setting:
                                selectedFragment = SettingsFragment.newInstance();
                                mFabCreateWork.animate().alpha(0.0f).setDuration(200);

                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mFabCreateWork.setVisibility(View.INVISIBLE);
                                    }
                                }, 300);

                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, TasksFragment.newInstance());
        transaction.commit();
    }

    public void setupBottomSheetView(){

        mBottomSheetCreateWorkView = getLayoutInflater().inflate(R.layout.bottom_sheet_create_work, null);

        bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog.setContentView(mBottomSheetCreateWorkView);
        bottomSheetDialog.setCancelable (true);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) mBottomSheetCreateWorkView.getParent());
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);

        screenUtils = new ScreenUtils(getApplicationContext());
        bottomSheetBehavior.setPeekHeight(screenUtils.getHeight()*80/100);

        rlBottomSheetChooseGroup = mBottomSheetCreateWorkView.findViewById(R.id.lnl_bottomsheet_choooselist);
        ivBottomSheetSetReminderStart = mBottomSheetCreateWorkView.findViewById(R.id.img_bottomsheet_reminder_start);
        ivBottomSheetSetReminderEnd =  mBottomSheetCreateWorkView.findViewById(R.id.img_bottomsheet_reminder_end);
        ivBottomSheetImgGroup =  mBottomSheetCreateWorkView.findViewById(R.id.img_bottomsheet_imggroup);
        ivBottomSheetSetPriority = mBottomSheetCreateWorkView.findViewById(R.id.img_bottomsheet_setpriority);
        tvBottomSheetAddWork =  mBottomSheetCreateWorkView.findViewById(R.id.txt_bottomsheet_add);
        tvBottomSheetCancel = mBottomSheetCreateWorkView.findViewById(R.id.txt_bottomsheet_cancel);
        tvBottomSheetNameGroup =  mBottomSheetCreateWorkView.findViewById(R.id.txt_bottomsheet_namegroup);
        etBottomSheetNameWork =  mBottomSheetCreateWorkView.findViewById(R.id.etxt_name_work);

        mFabCreateWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTasksPresenter.getAllGroupWorkOnline(preferManager.getID());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetDialog.show();
            }
        });
    }

    BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
        }
        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                // Cập nhập lại user với thông tin mới
                dbNguoiDungServer = new DBNguoiDungServer(this);
                preferManager = new PreferManager(getBaseContext());
                user = preferManager.getUserDetails();
                dbNguoiDungServer.getUser(user.get(PreferManager.KEY_EMAIL));
            }
        }
        if(requestCode==2){
            if(resultCode==RESULT_OK){
                // Cập nhập lại user với thông tin mới
                dbNguoiDungServer = new DBNguoiDungServer(this);
                preferManager = new PreferManager(getBaseContext());
                user = preferManager.getUserDetails();
                dbNguoiDungServer.getUser(user.get(PreferManager.KEY_EMAIL));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {
        String url = "http://192.168.43.219:8000/getimg?nameimg=";
        String url_imgitem="https://foody-trietv2.herokuapp.com/getimg?nameimg=";
        userCurrent = listUser.get(0);

    }

    @Override
    public void getResultInsert(Boolean isSuccess) {

    }

    @Override
    public void getUser(NguoiDungModel user) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lnl_bottomsheet_choooselist:
                dialogReminder = new AlertDialog.Builder(MainActivity.this);
                dialogReminder.setCancelable(true);
                layoutInflaterRemider = LayoutInflater.from((getBaseContext()));
                viewRemider = layoutInflaterRemider.inflate(R.layout.dialog_list_groupwork, null);

                RecyclerView list = viewRemider.findViewById(R.id.rv_list_groupwork);
                list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                list.setHasFixedSize(true);

                GroupWorkServerAdapter groupWorkServerAdapter = new GroupWorkServerAdapter(MainActivity.this,
                        mListNhomCV,this,"MainActivity");

                list.setAdapter(groupWorkServerAdapter);

                dialogReminder.setView(viewRemider);

                alertDialogRemider = dialogReminder.create();

                alertDialogRemider.show();

            break;
            case R.id.img_bottomsheet_reminder_start:
                setupDialog();
                isChooseTime = true;
                ivBottomSheetSetReminderStart.setImageResource(R.drawable.ic_setdate_on);
                timeReminderStart = Calendar.getInstance();
                timeReminderStart.set(Calendar.MINUTE,timeReminderStart.get(Calendar.MINUTE)+5);


                tvTitleRemider.setText("Set Date & Time Start");
                tvReminder.setText("Reminder at "+timeReminderStart.getTime().getHours()+":"
                        +timeReminderStart.getTime().getMinutes());

                calendarViewReminder.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year,int month, int dayOfMonth) {
                        timeReminderStart.set(year,month,dayOfMonth);
                    }
                });

                tvSaveRemider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // save data

                        alertDialogRemider.dismiss();

                    }
                });

                tvRemoveRemider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // remove time
                        alertDialogRemider.dismiss();

                    }
                });

                lnlTimeReminder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                timeReminderStart.set(Calendar.HOUR_OF_DAY, selectedHour);
                                timeReminderStart.set(Calendar.MINUTE, selectedMinute);
                                tvReminder.setText("Reminder at "+selectedHour+":"
                                        +selectedMinute);
                            }
                        }, timeReminderStart.getTime().getHours(), timeReminderStart.getTime().getMinutes(), true);

                        mTimePicker.setTitle("Time reminder");
                        mTimePicker.show();
                    }
                });

                alertDialogRemider.show();

                break;

            case R.id.img_bottomsheet_reminder_end:
                isTimeEndChoose = true;
                setupDialog();
                ivBottomSheetSetReminderEnd.setImageResource(R.drawable.ic_reminder_end_on);
                timeReminderEnd = Calendar.getInstance();
                timeReminderEnd.set(Calendar.MINUTE,timeReminderEnd.get(Calendar.MINUTE)+10);

                calendarViewReminder.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year,int month, int dayOfMonth) {
                        timeReminderEnd.set(year,month,dayOfMonth);
                    }
                });

                tvTitleRemider.setText("Set Date & Time End");
                tvReminder.setText("Reminder at "+timeReminderEnd.getTime().getHours()+":"
                        +timeReminderEnd.getTime().getMinutes());

                tvSaveRemider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // save data
                        alertDialogRemider.dismiss();

                    }
                });

                tvRemoveRemider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // remove time
                        alertDialogRemider.dismiss();

                    }
                });

                lnlTimeReminder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                timeReminderEnd.set(Calendar.HOUR_OF_DAY, selectedHour);
                                timeReminderEnd.set(Calendar.MINUTE, selectedMinute);
                                tvReminder.setText("Reminder at "+selectedHour+":"
                                        +selectedMinute);
                            }
                        }, timeReminderEnd.getTime().getHours(), timeReminderEnd.getTime().getMinutes(), true);

                        mTimePicker.setTitle("Time reminder");
                        mTimePicker.show();
                    }
                });

                alertDialogRemider.show();
                break;
            case R.id.img_bottomsheet_setpriority:
                if(isPriority == 1){
                    isPriority = 0;
                    ivBottomSheetSetPriority.setImageResource(R.drawable.ic_priority_off);
                }else{
                    isPriority = 1;
                    ivBottomSheetSetPriority.setImageResource(R.drawable.ic_priority_on);
                }
            break;
            case R.id.txt_bottomsheet_add:
                boolean canInsert = false;
                if(!etBottomSheetNameWork.getText().toString().equals("")){
                    CongViecModel congViecModel = new CongViecModel();
                    congViecModel.setIdCongViec(getIntCurrentDateTime());
                    congViecModel.setIdNhom(idGroupWorkCurrent);
                    congViecModel.setTenCongViec(etBottomSheetNameWork.getText().toString());
                    congViecModel.setCoUuTien(isPriority);
                    congViecModel.setIdNhacNho(0);
                    congViecModel.setIdNguoiTaoCV(preferManager.getID());


                    if(isChooseTime){
                        if(!isTimeEndChoose)
                            Toast.makeText(getApplicationContext(), "Please choose Reminder finish!",
                                    Toast.LENGTH_LONG).show();
                        else{
                            if(timeReminderStart.getTime().compareTo(timeReminderEnd.getTime())>0
                                    ){
                                Toast.makeText(getApplicationContext(), "Reminder finish must greater Reminder begin!",
                                        Toast.LENGTH_LONG).show();
                            }else{
                                canInsert = true;
                                try {
                                    congViecModel.setThoiGianBatDau(getDateTimeToInsertUpdate(timeReminderStart));
                                    congViecModel.setThoiGianKetThuc(getDateTimeToInsertUpdate(timeReminderEnd));
                                    createNotificationStart(congViecModel,timeReminderStart,timeReminderEnd,getApplicationContext());
                                    createNotificationEnd(congViecModel,timeReminderStart,timeReminderEnd,getApplicationContext());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(timeReminderEnd.getTime().compareTo(Calendar.getInstance().getTime())<0)
                                    congViecModel.setTrangThai("overdue");
                                else
                                    congViecModel.setTrangThai("waiting");
                            }
                        }
                    }else{
                        canInsert = true;
                        congViecModel.setThoiGianBatDau(null);
                        congViecModel.setThoiGianKetThuc(null);
                        congViecModel.setTrangThai("waiting");
                    }

                    if(canInsert){
                        dbWorkServer.insertWork(congViecModel);
                        dbWorkServer.insertWorkNotification(congViecModel.getIdCongViec(),congViecModel.getThoiGianBatDau(),
                                congViecModel.getThoiGianKetThuc(),congViecModel.getIdNguoiTaoCV(),congViecModel.getTrangThai());

                        isChooseTime = false;
                        isTimeEndChoose = false;
                        canInsert = false;
                        etBottomSheetNameWork.setHint("Add a to-do in '"+currentGroup.getTenNhom()+"'...");
                        bottomSheetDialog.cancel();
                        TasksFragment.initData();
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "Enter name work!",
                            Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.txt_bottomsheet_cancel:
                bottomSheetDialog.cancel();
                break;
        }
    }

    public void setupDialog(){
        dialogReminder = new AlertDialog.Builder(this);
        dialogReminder.setCancelable(true);
        layoutInflaterRemider = LayoutInflater.from((getBaseContext()));
        viewRemider = layoutInflaterRemider.inflate(R.layout.dialog_reminder, null);

        dialogReminder.setView(viewRemider);
        alertDialogRemider = dialogReminder.create();

        calendarViewReminder = viewRemider.findViewById(R.id.calendar_reminder);
        calendarViewReminder.setMinDate(System.currentTimeMillis() - 1000);
        tvSaveRemider = viewRemider.findViewById(R.id.tv_save);
        tvRemoveRemider =  viewRemider.findViewById(R.id.tv_remove);
        tvReminder = viewRemider.findViewById(R.id.tv_time_reminder);
        tvTitleRemider = viewRemider.findViewById(R.id.tv_title);
        lnlTimeReminder = viewRemider.findViewById(R.id.lnl_time_reminder);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getAllGroupWorkSuccess() {
        mListNhomCV = new ArrayList<>();
        NhomCVModel inbox = new NhomCVModel();
        inbox.setIdNhom(0);
        inbox.setTenNhom("Inbox");
        inbox.setLaNhomCaNhan(1);
        mListNhomCV.add(inbox);
        mListNhomCV.addAll(mTasksPresenter.getListGroupWorkOnline());
    }

    @Override
    public void getListAllWorkSucess() {

    }

    @Override
    public void getAllGroupWorkFailure() {

    }

    @Override
    public void onItemClick(NhomCVModel nhomCVModel, LinearLayout view) {
        idGroupWorkCurrent = nhomCVModel.getIdNhom();
        currentGroup = nhomCVModel;
        nameGroupCurrent = nhomCVModel.getTenNhom();
        tvBottomSheetNameGroup.setText(nhomCVModel.getTenNhom());
        etBottomSheetNameWork.setHint("Add a to-do in '"+nhomCVModel.getTenNhom()+"'...");
        if(nhomCVModel.getLaNhomCaNhan()==1){
            if(nhomCVModel.getIdNhom()==0)
                ivBottomSheetImgGroup.setImageResource(R.drawable.ic_inbox_blue);
            else
                ivBottomSheetImgGroup.setImageResource(R.drawable.ic_groupnormal);
        }
        else
            ivBottomSheetImgGroup.setImageResource(R.drawable.ic_groupassigned);
        alertDialogRemider.dismiss();
    }

    @Override
    public void onItemLongClick(NhomCVModel nhomCVModel, LinearLayout view) {

    }

    @Override
    public void getResultPostWork(Boolean isSucess) {
        idGroupWorkCurrent = 0;
        isTimeEndChoose = false;
        isPriority = 0;
        ivBottomSheetSetReminderStart.setImageResource(R.drawable.ic_setdate);
        ivBottomSheetSetReminderEnd.setImageResource(R.drawable.ic_reminder_end_off);
        ivBottomSheetSetPriority.setImageResource(R.drawable.ic_priority_off);
        etBottomSheetNameWork.setHint("Add to-do in '"+nameGroupCurrent+"'...");
        if(isSucess){
            Toast.makeText(getApplicationContext(), "Add work success!",
                    Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getApplicationContext(), "Add work fail!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
