package com.hcmute.trietthao.yourtime.mvp.detailWork.view;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.imageProcessing.ConvertBitmap;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.LoaiNhacNhoModel;
import com.hcmute.trietthao.yourtime.mvp.addANote.view.AddANoteActivity;
import com.hcmute.trietthao.yourtime.mvp.detailWork.presenter.DetailWorkPresenter;
import com.hcmute.trietthao.yourtime.mvp.settingFragment.view.Utility;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;
import com.hcmute.trietthao.yourtime.service.utils.Base64Utils;
import com.hcmute.trietthao.yourtime.service.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.notification.NotificationService.createNotificationEnd;
import static com.hcmute.trietthao.yourtime.notification.NotificationService.createNotificationStart;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.converStringToDateTime;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDateTimeToInsertUpdate;

/**
 * Created by lxtri on 24/10/2017.
 */

public class DetailWorkActivity extends AppCompatActivity implements View.OnClickListener,IDetailWorkView,
        PostWorkListener{

    @Bind(R.id.tv_name_work)
    EditText tvNameWork;

    @Bind(R.id.tv_name_assignto)
    TextView tvNameAssignTo;

    @Bind(R.id.tv_time_reminder_start)
    TextView tvTimeReminderStart;

    @Bind(R.id.tv_time_reminder_end)
    TextView tvTimeReminderEnd;

    @Bind(R.id.spinner_repeat)
    Spinner spinnerRepeat;

    @Bind(R.id.tv_note)
    TextView tvNote;

    @Bind(R.id.tv_name_img)
    TextView tvNameFile;

    @Bind(R.id.iv_img_assignto)
    ImageView ivImgAssignTo;

    @Bind(R.id.iv_img_time_reminder_start)
    ImageView ivImgTimeReminderStart;

    @Bind(R.id.iv_img_time_reminder_end)
    ImageView ivImgTimeReminderEnd;

    @Bind(R.id.iv_img_repeat)
    ImageView ivImgRepeat;

    @Bind(R.id.iv_img_add_note)
    ImageView ivImgAddNote;

    @Bind(R.id.iv_img_add_picture)
    ImageView ivImgAddFile;

    @Bind(R.id.iv_img_priority)
    ImageView ivImgPriority;

    @Bind(R.id.iv_delete_assignto)
    ImageView ivDeleteAssignTo;

    @Bind(R.id.iv_delete_time_reminder_start)
    ImageView ivDeleteTimeReminderStart;

    @Bind(R.id.iv_delete_time_reminder_end)
    ImageView ivDeleteTimeReminderEnd;

    @Bind(R.id.iv_img_picture)
    ImageView ivImgPicute;

    @Bind(R.id.iv_delete_repeat)
    ImageView ivDeleteRepeat;

    @Bind(R.id.iv_delete_picture)
    ImageView ivDeleteFile;

    @Bind(R.id.iv_img_back)
    ImageView ivBack;

    @Bind(R.id.lnl_add_a_comment)
    LinearLayout lnlAddComment;

    @Bind(R.id.lnl_assignto)
    LinearLayout lnlAssignTo;

    @Bind(R.id.lnl_time_reminder_start)
    LinearLayout lnlTimeReminderStart;

    @Bind(R.id.lnl_time_reminder_end)
    LinearLayout lnlTimeReminderEnd;

    @Bind(R.id.lnl_repeat)
    LinearLayout lnlRepeat;

    @Bind(R.id.lnl_add_picture)
    LinearLayout lnlAddFile;

    @Bind(R.id.lnl_add_note)
    LinearLayout lnlAddNote;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    String EXTRA_WORK_ID = "";
    String EXTRA_GROUPWORK_ID = "";
    String EXTRA_GROUPWORK_NAME = "";

    String encodedString="null";
    private int REQUEST_IMAGE_GALLERY = 8888;
    private int REQUEST_IMAGE_CAPTURE = 9999;
    private int REQUEST_ADD_NOTE = 7777;
    private String userChoosenTask;

    PreferManager mPreferManager;
    CongViecModel currentWork;
    ArrayList<LoaiNhacNhoModel> loaiNhacNhoModelArrayList;

    AlertDialog.Builder dialogReminder;
    LayoutInflater layoutInflaterRemider;
    View viewRemider;
    AlertDialog alertDialogRemider;
    TextView tvSaveRemider, tvRemoveRemider, tvReminder, tvTitleRemider;
    LinearLayout lnlTimeReminder;
    CalendarView calendarViewReminder;

    Calendar timeReminderStart;
    Calendar timeReminderEnd;
    boolean isTimeStartChange = false,isTimeEndChange = false,isChangeName = false;

    DetailWorkPresenter mDetailWorkPresenter;

    DBWorkServer dbWorkServer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);
        ButterKnife.bind(this);
        currentWork = new CongViecModel();

        mPreferManager = new PreferManager(getApplicationContext());


        EXTRA_WORK_ID = getIntent().getStringExtra("EXTRA_WORK_ID");
        EXTRA_GROUPWORK_ID = getIntent().getStringExtra("EXTRA_GROUPWORK_ID");

        // Get detail work
        mDetailWorkPresenter = new DetailWorkPresenter(this,getApplicationContext());
        dbWorkServer = new DBWorkServer(this);
        mDetailWorkPresenter.getDetailWork(Integer.parseInt(EXTRA_WORK_ID));

        timeReminderStart = Calendar.getInstance();
        timeReminderEnd = Calendar.getInstance();

        tvNameWork.setFocusable(false);

        ivDeleteAssignTo.setVisibility(View.INVISIBLE);
        ivDeleteTimeReminderStart.setVisibility(View.INVISIBLE);
        ivDeleteTimeReminderEnd.setVisibility(View.INVISIBLE);
        ivDeleteRepeat.setVisibility(View.INVISIBLE);
        ivDeleteFile.setVisibility(View.INVISIBLE);
        ivImgPicute.setVisibility(View.GONE);

        lnlAssignTo.setOnClickListener(this);
        lnlAddComment.setOnClickListener(this);
        lnlTimeReminderStart.setOnClickListener(this);
        lnlTimeReminderEnd.setOnClickListener(this);
        lnlRepeat.setOnClickListener(this);
        lnlAddFile.setOnClickListener(this);
        lnlAddNote.setOnClickListener(this);
        ivImgPriority.setOnClickListener(this);
        ivDeleteAssignTo.setOnClickListener(this);
        ivDeleteTimeReminderStart.setOnClickListener(this);
        ivDeleteTimeReminderEnd.setOnClickListener(this);
        ivDeleteRepeat.setOnClickListener(this);
        ivDeleteFile.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvNameWork.setOnClickListener(this);

        spinnerRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    ivImgRepeat.setImageResource(R.drawable.ic_repeat_off);
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorGray500));
                }else{
                    ivImgRepeat.setImageResource(R.drawable.ic_repeat_on);
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlue));
                }
                currentWork.setIdNhacNho(i);
                try {
                    currentWork.setThoiGianKetThuc(getDateTimeToInsertUpdate(timeReminderEnd));
                    currentWork.setThoiGianBatDau(getDateTimeToInsertUpdate(timeReminderStart));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dbWorkServer.updateWork(currentWork);
                try {
                    mDetailWorkPresenter.deleteAllNotification(currentWork.getIdCongViec());
                    createWorkNotification(i);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void createWorkNotification(int idrepeat) throws ParseException {
        dbWorkServer.deleteWorkNotification(currentWork.getIdCongViec());
        if(idrepeat==0){
            dbWorkServer.insertWorkNotification(currentWork.getIdCongViec(),
                    getDateTimeToInsertUpdate(timeReminderStart),
                    getDateTimeToInsertUpdate(timeReminderEnd),
                    mPreferManager.getID(),"waiting");
            createNotificationStart(currentWork,timeReminderStart,timeReminderEnd,getApplicationContext());
            createNotificationEnd(currentWork,timeReminderStart,timeReminderEnd,getApplicationContext());
        }else{
            Calendar tempStart,tempEnd;
            tempStart = timeReminderStart; tempEnd = timeReminderEnd;
            for(int i=0;i<5;i++){
                Log.e("TimeStart"+i+":"," "+getDateTimeToInsertUpdate(tempStart));
                Log.e("TimeStart"+i+":"," "+getDateTimeToInsertUpdate(tempEnd));
                Log.e("---------","-------------------------------------------");
                dbWorkServer.insertWorkNotification(currentWork.getIdCongViec(),
                        getDateTimeToInsertUpdate(tempStart),
                        getDateTimeToInsertUpdate(tempEnd),
                        mPreferManager.getID(),"waiting");
                createNotificationStart(currentWork,timeReminderStart,timeReminderEnd,getApplicationContext());
                createNotificationEnd(currentWork,timeReminderStart,timeReminderEnd,getApplicationContext());
                switch (idrepeat){
                    case 1:
                        tempStart.set(Calendar.DATE,tempStart.get(Calendar.DATE)+1);
                        tempEnd.set(Calendar.DATE,tempStart.get(Calendar.DATE)+1);
                        break;
                    case 2:
                        tempStart.set(Calendar.WEEK_OF_YEAR,tempStart.get(Calendar.WEEK_OF_YEAR)+1);
                        tempEnd.set(Calendar.WEEK_OF_YEAR,tempStart.get(Calendar.WEEK_OF_YEAR)+1);
                        break;
                    case 3:
                        tempStart.set(Calendar.MONTH,tempStart.get(Calendar.MONTH)+1);
                        tempEnd.set(Calendar.MONTH,tempStart.get(Calendar.MONTH)+1);
                        break;
                    case 4:
                        tempStart.set(Calendar.YEAR,tempStart.get(Calendar.MONTH)+1);
                        tempEnd.set(Calendar.YEAR,tempStart.get(Calendar.MONTH)+1);
                        break;
                }
            }
            tempStart=null; tempEnd=null;
        }

    }

    public void setupDetailWork() throws ParseException {
        tvNameWork.setText(currentWork.getTenCongViec());
        if(currentWork.getThoiGianBatDau()!=null){
            tvTimeReminderStart.setText(getDateTimeToInsertUpdate(currentWork.getThoiGianBatDau()));
            tvTimeReminderEnd.setText(getDateTimeToInsertUpdate(currentWork.getThoiGianKetThuc()));
            timeReminderStart.setTime(converStringToDateTime(currentWork.getThoiGianBatDau()));
            timeReminderEnd.setTime(converStringToDateTime(currentWork.getThoiGianKetThuc()));
            setupTimeReminderOn();
        }else
            spinnerRepeat.setEnabled(false);
        if(currentWork.getCoUuTien()==1){
            ivImgPriority.setImageResource(R.drawable.ic_priority_selected);
        }else{
            ivImgPriority.setImageResource(R.drawable.ic_priority_unselected);
        }
        if(currentWork.getIdNhacNho()!=0){
            spinnerRepeat.setSelection(currentWork.getIdNhacNho());
        }
        if(currentWork.getGhiChu()!=null){
            tvNote.setText(currentWork.getGhiChu());
            tvNote.setTextColor(getResources().getColor(R.color.colorBlue));
            ivImgAddNote.setImageResource(R.drawable.ic_note_on);
        }
        if(currentWork.getFileDinhKem()!=null){
            ivImgPicute.setVisibility(View.VISIBLE);
            ivDeleteFile.setVisibility(View.VISIBLE);
            ivImgAddFile.setImageResource(R.drawable.ic_camera_on);
            String linkImg = "https://tlcn-yourtime.herokuapp.com/getimg?nameimg="+currentWork.getFileDinhKem()+".png";
            Log.e("Link:",""+linkImg);
            Picasso.with(getApplicationContext())
                    .load(linkImg)
                    .error(R.drawable.ava2)
                    .into(ivImgPicute);
        }
    }

    public void setupTimeReminderOn(){
        tvTimeReminderStart.setTextColor(getResources().getColor(R.color.colorBlue));
        tvTimeReminderEnd.setTextColor(getResources().getColor(R.color.colorBlue));

        ivDeleteTimeReminderStart.setVisibility(View.VISIBLE);
        ivDeleteTimeReminderEnd.setVisibility(View.VISIBLE);

        ivImgTimeReminderStart.setImageResource(R.drawable.ic_reminder_on);
        ivImgTimeReminderEnd.setImageResource(R.drawable.ic_reminder_end_on);
    }

    public void onClickDeleteTimeReminder(){
        tvTimeReminderStart.setText(getResources().getString(R.string.set_date_reminder_start));
        tvTimeReminderEnd.setText(getResources().getString(R.string.set_date_reminder_end));

        tvTimeReminderStart.setTextColor(getResources().getColor(R.color.colorGray500));
        tvTimeReminderEnd.setTextColor(getResources().getColor(R.color.colorGray500));


        ivDeleteTimeReminderStart.setVisibility(View.INVISIBLE);
        ivDeleteTimeReminderEnd.setVisibility(View.INVISIBLE);
        ivDeleteRepeat.setVisibility(View.INVISIBLE);

        ivImgTimeReminderStart.setImageResource(R.drawable.ic_reminder_off);
        ivImgTimeReminderEnd.setImageResource(R.drawable.ic_reminder_end_off);
        ivImgRepeat.setImageResource(R.drawable.ic_repeat_off);

        timeReminderStart = Calendar.getInstance();
        timeReminderEnd = Calendar.getInstance();

        currentWork.setThoiGianKetThuc(null);
        currentWork.setThoiGianBatDau(null);
        currentWork.setIdNhacNho(0);
        mDetailWorkPresenter.deleteAllNotification(currentWork.getIdCongViec());
        // delete time cong viec. delete cvthongbao
        dbWorkServer.deleteWorkNotification(currentWork.getIdCongViec());
        dbWorkServer.updateWork(currentWork);
        spinnerRepeat.setSelection(0);
        spinnerRepeat.setEnabled(false);
        ((TextView) spinnerRepeat.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorGray500));
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.lnl_assignto: break;
            case R.id.lnl_time_reminder_start:
                setupDialog();
                if(currentWork.getThoiGianBatDau()!=null){
                    try {
                        if(!isTimeStartChange){
                            calendarViewReminder.setDate(DateUtils.converStringToCalendar(currentWork.getThoiGianBatDau()).getTimeInMillis());
                            timeReminderStart.setTime(converStringToDateTime(currentWork.getThoiGianBatDau()));
                        }else{
                            calendarViewReminder.setDate(timeReminderStart.getTimeInMillis());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


                calendarViewReminder.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                        timeReminderStart.set(year,month,dayOfMonth);
                    }
                });

                tvTitleRemider.setText("Set Date & Time Start");
                tvReminder.setText("Reminder at "+timeReminderStart.getTime().getHours()+":"
                        +timeReminderStart.getTime().getMinutes());

                tvSaveRemider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            spinnerRepeat.setEnabled(true);
                            isTimeStartChange = true;
                            tvTimeReminderStart.setText(getDateTimeToInsertUpdate(timeReminderStart));
                            if(currentWork.getThoiGianKetThuc()!=null) {
                            }else{
                                timeReminderEnd.set(Calendar.MINUTE, timeReminderStart.get(Calendar.MINUTE) + 10);
                                tvTimeReminderEnd.setText(getDateTimeToInsertUpdate(timeReminderEnd));
                            }
                            setupTimeReminderOn();

                            currentWork.setThoiGianKetThuc(getDateTimeToInsertUpdate(timeReminderEnd));
                            currentWork.setThoiGianBatDau(getDateTimeToInsertUpdate(timeReminderStart));

                            dbWorkServer.updateWork(currentWork);
                            mDetailWorkPresenter.deleteAllNotification(currentWork.getIdCongViec());
                            createWorkNotification(currentWork.getIdNhacNho());
                            createNotificationStart(currentWork,timeReminderStart,timeReminderEnd,getApplicationContext());
                            createNotificationEnd(currentWork,timeReminderStart,timeReminderEnd,getApplicationContext());
                            // update time work. delete all cvthongbao and insert again
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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
                        TimePickerDialog mTimePicker = new TimePickerDialog(DetailWorkActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
            case R.id.lnl_time_reminder_end:
                setupDialog();

                if(currentWork.getThoiGianBatDau()!=null){
                    try {
                        if(!isTimeEndChange){
                            calendarViewReminder.setDate(DateUtils.converStringToCalendar(currentWork.getThoiGianBatDau()).getTimeInMillis());
                            timeReminderEnd.setTime(converStringToDateTime(currentWork.getThoiGianKetThuc()));
                        }else{
                            calendarViewReminder.setDate(timeReminderEnd.getTimeInMillis());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                tvTitleRemider.setText("Set Date & Time End");
                tvReminder.setText("Reminder at "+timeReminderEnd.getTime().getHours()+":"
                        +timeReminderEnd.getTime().getMinutes());

                calendarViewReminder.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                        timeReminderEnd.set(year,month,dayOfMonth);
                    }
                });

                tvSaveRemider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            spinnerRepeat.setEnabled(true);
                            isTimeEndChange = true;
                            tvTimeReminderEnd.setText(getDateTimeToInsertUpdate(timeReminderEnd));
                            if(currentWork.getThoiGianBatDau()!=null) {
                            }else{
                                timeReminderStart.set(Calendar.MINUTE,timeReminderEnd.get(Calendar.MINUTE)-10);
                                tvTimeReminderStart.setText(getDateTimeToInsertUpdate(timeReminderEnd));
                            }
                            setupTimeReminderOn();

                            currentWork.setThoiGianKetThuc(getDateTimeToInsertUpdate(timeReminderEnd));
                            currentWork.setThoiGianBatDau(getDateTimeToInsertUpdate(timeReminderStart));

                            dbWorkServer.updateWork(currentWork);
                            mDetailWorkPresenter.deleteAllNotification(currentWork.getIdCongViec());
                            createWorkNotification(currentWork.getIdNhacNho());
                            createNotificationStart(currentWork,timeReminderStart,timeReminderEnd,getApplicationContext());
                            createNotificationEnd(currentWork,timeReminderStart,timeReminderEnd,getApplicationContext());
                            // update time work. delete all cvthongbao and insert again
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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
                        TimePickerDialog mTimePicker = new TimePickerDialog(DetailWorkActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
            case R.id.lnl_repeat:

                break;
            case R.id.lnl_add_note:
                Intent intent = new Intent(getApplicationContext(), AddANoteActivity.class);
                intent.putExtra("EXTRA_WORK_ID", currentWork.getIdCongViec().toString());
                intent.putExtra("EXTRA_GROUPWORK_ID", EXTRA_GROUPWORK_ID);
                intent.putExtra("EXTRA_WORK_NAME", currentWork.getTenCongViec());
                if(currentWork.getGhiChu()!=null)
                    intent.putExtra("EXTRA_NOTE", currentWork.getGhiChu());
                startActivityForResult(intent,REQUEST_ADD_NOTE);
                break;
            case R.id.lnl_add_picture:
                ivImgPicute.setVisibility(View.VISIBLE);
                ivDeleteFile.setVisibility(View.VISIBLE);
                ivImgAddFile.setImageResource(R.drawable.ic_camera_on);
                selectImage();

                break;
            case R.id.lnl_add_a_comment: break;

            case R.id.iv_delete_assignto: break;
            case R.id.iv_delete_time_reminder_start:
                onClickDeleteTimeReminder();
                currentWork.setIdNhacNho(0);
                ivDeleteRepeat.setVisibility(View.INVISIBLE);
                ivImgRepeat.setImageResource(R.drawable.ic_repeat_off);
                break;
            case R.id.iv_delete_time_reminder_end:
                onClickDeleteTimeReminder();
                currentWork.setIdNhacNho(0);
                ivDeleteRepeat.setVisibility(View.INVISIBLE);
                ivImgRepeat.setImageResource(R.drawable.ic_repeat_off);
                break;
            case R.id.iv_delete_repeat:
                currentWork.setIdNhacNho(0);
                ivDeleteRepeat.setVisibility(View.INVISIBLE);
                ivImgRepeat.setImageResource(R.drawable.ic_repeat_off);
                // update work
                break;
            case R.id.iv_delete_picture:
                encodedString = "null";
                ivImgAddFile.setImageResource(R.drawable.ic_camera_off);
                ivImgPicute.setImageDrawable(null);
                ivImgPicute.setVisibility(View.GONE);
                lnlAddFile.setVisibility(View.VISIBLE);
                ivDeleteFile.setVisibility(View.INVISIBLE);
                break;

            case R.id.iv_img_priority:
                if(currentWork.getCoUuTien()==1){
                    currentWork.setCoUuTien(0);
                    ivImgPriority.setImageResource(R.drawable.ic_priority_unselected);
                    dbWorkServer.updatePriorityWork(0,currentWork.getIdCongViec());
                }else{
                    currentWork.setCoUuTien(1);
                    ivImgPriority.setImageResource(R.drawable.ic_priority_selected);
                    dbWorkServer.updatePriorityWork(1,currentWork.getIdCongViec());
                }
                break;
            case R.id.iv_img_back:
                if(isChangeName){
                    tvNameWork.setFocusable(false);
                    // update work
                    currentWork.setTenCongViec(tvNameWork.getText().toString());
                    if(currentWork.getThoiGianBatDau()!=null){
                        try {
                            currentWork.setThoiGianKetThuc(getDateTimeToInsertUpdate(timeReminderEnd));
                            currentWork.setThoiGianBatDau(getDateTimeToInsertUpdate(timeReminderStart));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    dbWorkServer.updateWork(currentWork);
                    isChangeName = false;
                }else{
                    Intent data = new Intent();
                    data.putExtra("EXTRA_GROUPWORK_ID",EXTRA_GROUPWORK_ID);
                    setResult(RESULT_OK,data);
                    finish();
                }
                break;

            case R.id.tv_name_work:
                isChangeName = true;
                tvNameWork.setFocusableInTouchMode(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(isChangeName){
            tvNameWork.setFocusable(false);
            // update work
            currentWork.setTenCongViec(tvNameWork.getText().toString());
            if(currentWork.getThoiGianBatDau()!=null){
                try {
                    currentWork.setThoiGianKetThuc(getDateTimeToInsertUpdate(timeReminderEnd));
                    currentWork.setThoiGianBatDau(getDateTimeToInsertUpdate(timeReminderStart));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dbWorkServer.updateWork(currentWork);
            isChangeName = false;
        }else{
            Intent data = new Intent();
            data.putExtra("EXTRA_GROUPWORK_ID",EXTRA_GROUPWORK_ID);
            setResult(RESULT_OK,data);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GALLERY)
            onSelectFromGalleryResult(data);
        else if (requestCode == REQUEST_IMAGE_CAPTURE)
            onCaptureImageResult(data);
        else if( requestCode == REQUEST_ADD_NOTE){
            String text = data.getStringExtra("EXTRA_NOTE");
            if(text.equals("")){
                tvNote.setText(getResources().getString(R.string.add_a_note));
                ivImgAddNote.setImageResource(R.drawable.ic_note_off);
                tvNote.setTextColor(getResources().getColor(R.color.colorGray500));
                currentWork.setGhiChu(null);
            }
            else{
                tvNote.setText(text);
                tvNote.setTextColor(getResources().getColor(R.color.black));
                ivImgAddNote.setImageResource(R.drawable.ic_note_on);
                currentWork.setGhiChu(text);
            }
            dbWorkServer.updateWork(currentWork);
            // update work
        }
        //Từ gg vào
    }

    @Override
    public void getDetailWorkSuccess() {
        currentWork = mDetailWorkPresenter.getDetailWork();
        loaiNhacNhoModelArrayList = mDetailWorkPresenter.getListRepeat();
        try {
            setupDetailWork();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDetailWorkFail() {

    }

    @Override
    public void updateDetailWorkSuccess() {

    }

    @Override
    public void updateDetailWorkFail() {

    }

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailWorkActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(DetailWorkActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),REQUEST_IMAGE_GALLERY);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        ConvertBitmap myBitMap = new ConvertBitmap(this);
        Bitmap bitmap = null;
        try {
            bitmap = myBitMap.decodeUri(data.getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ivImgPicute.setImageBitmap(bitmap);
        encodedString = myBitMap.getStringFromBitmap(bitmap);
        dbWorkServer.updateFileWork(encodedString,currentWork.getIdCongViec());

    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Base64Utils myBitMap = new Base64Utils(this);
        ivImgPicute.setImageBitmap(thumbnail);
        encodedString = myBitMap.getStringFromBitmap(thumbnail);
        dbWorkServer.updateFileWork(encodedString,currentWork.getIdCongViec());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {

                }
                break;
        }
    }

}
