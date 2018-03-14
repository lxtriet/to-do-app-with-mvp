package com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.IOnItemWorkListener;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.adapter.ItemWorkServerAdapter;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.presenter.DetailGroupWorkPresenter;
import com.hcmute.trietthao.yourtime.mvp.detailWork.view.DetailWorkActivity;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.service.utils.AnimationUtils.setFadeInTime;
import static com.hcmute.trietthao.yourtime.service.utils.AnimationUtils.setFadeOutTime;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getIntCurrentDateTime;
import static com.hcmute.trietthao.yourtime.service.utils.NetworkUtils.isNetWorkConnected;


public class DetailGroupWorkActivity extends AppCompatActivity implements View.OnClickListener,IDetailGroupWorkView
        ,IOnItemWorkListener,SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.txt_show_hide_completed_works)
    TextView mTxtShowHideCompletedWorks;

    @Bind(R.id.tv_name_item_work_selected)
    TextView tvNameItemWork;

    @Bind(R.id.tv_name_groupwork)
    TextView tvNameGroupWork;

    @Bind(R.id.iv_back_longclick_itemwork)
    ImageView ivBackFromLongClick;

    @Bind(R.id.iv_delete_item_work)
    ImageView ivDeleteItemWork;

    @Bind(R.id.iv_img_add_work)
    ImageView ivAddWork;

    @Bind(R.id.iv_img_sort)
    ImageView ivSort;

    @Bind(R.id.etxt_add_work)
    EditText etAddWork;

    @Bind(R.id.lnlayout_longclick_work)
    LinearLayout lnlLongClickMenu;

    @Bind(R.id.list_works)
    RecyclerView rvListWork;

    @Bind(R.id.list_works_completed)
    RecyclerView rvListWorkCompleted;

    @Bind(R.id.progressBar_Loadmore)
    ProgressBar pbLoading;

    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeLayout;

    String EXTRA_GROUPWORK_ID = "";
    String EXTRA_GROUPWORK_NAME = "";
    String EXTRA_WORK_ID = "";

    DetailGroupWorkPresenter mDetailGroupWorkPresenter;

    CongViecModel currentItemWork;
    LinearLayout lnlCurrentItemLongClick;
    ArrayList<CongViecModel> mListWork, mListWorkCompleted;

    ItemWorkServerAdapter itemWorkServerAdapter, itemWorkServerAdapterCompleted;

    boolean isLongClicking = false;

    boolean isShowCompletedWorks = false;

    PreferManager mPreferManager;
    Integer REQUEST_WORK_DETAIL = 000;
    boolean isAddWorkEnable = false;
    boolean isSortAscName;
    boolean isSortAscPriority;
    boolean isSortAscDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupwork_detail);
        ButterKnife.bind(this);

        mPreferManager = new PreferManager(getApplicationContext());

        rvListWork.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvListWork.setHasFixedSize(true);
        rvListWorkCompleted.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvListWorkCompleted.setHasFixedSize(true);
        mDetailGroupWorkPresenter = new DetailGroupWorkPresenter(this);

        EXTRA_GROUPWORK_ID = getIntent().getStringExtra("EXTRA_GROUPWORK_ID");
        EXTRA_WORK_ID = getIntent().getStringExtra("EXTRA_WORK_ID");
        if(getIntent().getStringExtra("REQUEST_SCREEN")!=null){
            Toast.makeText(getApplication(), EXTRA_GROUPWORK_ID+ "-" +EXTRA_WORK_ID,
                    Toast.LENGTH_LONG).show();
            if(getIntent().getStringExtra("REQUEST_SCREEN").equals("NOTIFICATION")){
                Intent intent = new Intent(getApplicationContext(), DetailWorkActivity.class);
                intent.putExtra("EXTRA_GROUPWORK_ID", EXTRA_GROUPWORK_ID);
                intent.putExtra("EXTRA_WORK_ID", EXTRA_WORK_ID);
                startActivity(intent);
            }
        }
        else
            initData();


        lnlLongClickMenu.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mTxtShowHideCompletedWorks.setOnClickListener(this);
        ivBackFromLongClick.setOnClickListener(this);
        ivDeleteItemWork.setOnClickListener(this);
        ivAddWork.setOnClickListener(this);
        ivSort.setOnClickListener(this);

        etAddWork.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) { //do your work here }
                    ivAddWork.setImageResource(R.drawable.ic_add_active);
                    isAddWorkEnable = true;
                } else {
                    ivAddWork.setImageResource(R.drawable.ic_add_non_active);
                    isAddWorkEnable = false;
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void initData() {
        if (isNetWorkConnected(getApplication())) {
            Log.e("INIT", " ------------GROUP ID: " + EXTRA_GROUPWORK_ID);
            if (EXTRA_GROUPWORK_ID != null)
            {
                mDetailGroupWorkPresenter.getDetailGroupWork(mPreferManager.getID(), Integer.parseInt(EXTRA_GROUPWORK_ID));
                mDetailGroupWorkPresenter.getWorkByIdGroup(mPreferManager.getID(), Integer.parseInt(EXTRA_GROUPWORK_ID));
            }
        }
    }

    @Override
    protected void onResume() {
//        rvListWork.setAdapter((RecyclerView.Adapter)null);
//        rvListWorkCompleted.setAdapter((RecyclerView.Adapter) null);
//        initData();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i) {
            case R.id.txt_show_hide_completed_works:
                if (isShowCompletedWorks) {
                    mTxtShowHideCompletedWorks.setText(R.string.show_completed_works);
                    isShowCompletedWorks = false;
                    setFadeOutTime(rvListWorkCompleted, 500);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rvListWorkCompleted.setVisibility(View.INVISIBLE);
                        }
                    }, 500);
                } else {
                    mTxtShowHideCompletedWorks.setText(R.string.hide_completed_works);
                    isShowCompletedWorks = true;
                    setFadeInTime(rvListWorkCompleted, 500);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rvListWorkCompleted.setVisibility(View.VISIBLE);
                        }
                    }, 500);

                }
                break;
            case R.id.iv_back_longclick_itemwork:
                lnlCurrentItemLongClick.setBackground(getResources().getDrawable(R.drawable.borderwork_white));
                lnlCurrentItemLongClick = null;
                currentItemWork = null;
                isLongClicking = false;

                lnlLongClickMenu.setVisibility(View.GONE);
                mToolbar.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_img_add_work:
                if (!etAddWork.getText().toString().equals("")) {
                    CongViecModel congViecModel = new CongViecModel();
                    congViecModel.setIdCongViec(getIntCurrentDateTime());
                    congViecModel.setCoUuTien(0);
                    congViecModel.setTrangThai("waiting");
                    congViecModel.setIdNguoiTaoCV(mPreferManager.getID());
                    congViecModel.setTenCongViec(etAddWork.getText().toString());
                    congViecModel.setIdNhom(Integer.parseInt(EXTRA_GROUPWORK_ID));
                    congViecModel.setIdNhacNho(0);
                    mDetailGroupWorkPresenter.insertWork(congViecModel);
                    Toast.makeText(getApplication(), "Insert successfull",
                            Toast.LENGTH_LONG).show();
                    mSwipeLayout.setRefreshing(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {
                            initData();
                            mSwipeLayout.setRefreshing(false);
                        }
                    }, 1000);
                }
                break;
            case R.id.iv_delete_item_work:
                mDetailGroupWorkPresenter.deleteWork(currentItemWork.getIdCongViec());
                currentItemWork = null;
                lnlCurrentItemLongClick = null;
                isLongClicking = false;
                lnlLongClickMenu.setVisibility(View.GONE);
                mToolbar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplication(), "Delete successfull",
                        Toast.LENGTH_LONG).show();
                mSwipeLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        initData();
                        mSwipeLayout.setRefreshing(false);
                    }
                }, 1000);
                break;

            case R.id.iv_img_sort:
                final CharSequence[] items = { "Sort by Apha", "Sort by Priority",
                        "Sort by Date" };

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailGroupWorkActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Sort by Apha")) {
                            if (!isSortAscName)
                            {
                                sortAccesdingNameWork();
                            }else
                                sortDescName();


                        } else if (items[item].equals("Sort by Priority")) {
                            if(!isSortAscPriority){
                                sortAscPriority();
                            }else
                                sortDescPriority();

                        } else if (items[item].equals("Sort by Date")) {
                            if(!isSortAscDate){
                                sortAscDate();
                            }else
                                sortDescDate();
                        }
                    }
                });
                builder.show();
                break;
        }
    }

    public void sortAccesdingNameWork(){
        isSortAscName=true;
        Toast.makeText(getApplication(), "Vao sortAsc",
                Toast.LENGTH_LONG).show();
        Collections.sort(mListWork, CongViecModel.SortAscendingNameWork);
        itemWorkServerAdapter.notifyDataSetChanged();
    }
    public void sortDescName(){
        isSortAscName=false;
//        Collections.sort(mListWork, Collections.reverseOrder());
        Collections.sort(mListWork, CongViecModel.SortDescNameWork);
        itemWorkServerAdapter.notifyDataSetChanged();
    }
    public void sortAscDate(){
        isSortAscDate=true;

        ArrayList<CongViecModel> mListWorkNoDate = new ArrayList<>();
        ArrayList<CongViecModel> mListWorkHaveDate = new ArrayList<>();
        for(int i=0;i<mListWork.size();i++)
            if(mListWork.get(i).getThoiGianBatDau()!=null)
                mListWorkHaveDate.add(mListWork.get(i));
            else
                mListWorkNoDate.add(mListWork.get(i));
        mListWork.clear();
        mListWork.addAll(mListWorkNoDate);
        Collections.sort(mListWorkHaveDate,CongViecModel.SortAscDate);
        mListWork.addAll(mListWorkHaveDate);

        itemWorkServerAdapter.notifyDataSetChanged();
    }
    public void sortDescDate(){
        isSortAscDate=false;

        ArrayList<CongViecModel> mListWorkNoDate = new ArrayList<>();
        ArrayList<CongViecModel> mListWorkHaveDate = new ArrayList<>();
        for(int i=0;i<mListWork.size();i++)
            if(mListWork.get(i).getThoiGianBatDau()!=null)
                mListWorkHaveDate.add(mListWork.get(i));
            else
                mListWorkNoDate.add(mListWork.get(i));
        mListWork.clear();
        Collections.sort(mListWorkHaveDate,CongViecModel.SortAscDate);
        mListWork.addAll(mListWorkHaveDate);
        mListWork.addAll(mListWorkNoDate);

        itemWorkServerAdapter.notifyDataSetChanged();
    }
    public void sortAscPriority(){
        isSortAscPriority=true;
        Collections.sort(mListWork,CongViecModel.SortAscPriority);
        itemWorkServerAdapter.notifyDataSetChanged();
    }
    public void sortDescPriority(){
        isSortAscPriority=false;
        Collections.sort(mListWork,CongViecModel.SortByPriority);
        itemWorkServerAdapter.notifyDataSetChanged();
    }

    @Override
    public void getWorkByIDGroupSuccess() {

        mListWork = mDetailGroupWorkPresenter.getListWorkNormal();
        mListWorkCompleted = mDetailGroupWorkPresenter.getListWorkCompleted();

        itemWorkServerAdapter = new ItemWorkServerAdapter(getApplicationContext(),mListWork,
                mListWorkCompleted,1,this,this);

        itemWorkServerAdapter = new ItemWorkServerAdapter(getApplicationContext(), mListWork,
                mListWorkCompleted, 1, this, this);
        itemWorkServerAdapterCompleted = new ItemWorkServerAdapter(getApplicationContext(),
                mListWork, mListWorkCompleted, 2, this, this);
        rvListWork.setAdapter(itemWorkServerAdapter);
        rvListWorkCompleted.setAdapter(itemWorkServerAdapterCompleted);
        rvListWorkCompleted.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getGroupWorkDetailSucess() {
        NhomCVModel nhomCVModel = new NhomCVModel();
        nhomCVModel = mDetailGroupWorkPresenter.getGroupWorkDetail();
        try{
            tvNameGroupWork.setText(nhomCVModel.getTenNhom());

        }catch (NullPointerException e){
            tvNameGroupWork.setText("GroupWork");
        }
    }

    @Override
    public void getWorkByIDGroupFail() {
        Toast.makeText(getApplication(), "getWorkByIDGroupFail! Check your connection!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifyDataSetChanged() {
        itemWorkServerAdapter.notifyDataSetChanged();
        itemWorkServerAdapterCompleted.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        // pbLoading.setVisibility(View.VISIBLE);
        mSwipeLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        // pbLoading.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void insertWorkSuccess() {
        Toast.makeText(getApplication(), "Add work Successful!",
                Toast.LENGTH_LONG).show();
        etAddWork.setText("");
        ivAddWork.setImageResource(R.drawable.ic_add_non_active);
        isAddWorkEnable = false;
    }

    @Override
    public void insertWorkFail() {
        Toast.makeText(getApplication(), "insertWorkFail! Check your connection!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(CongViecModel congViecModel, LinearLayout view) {
        if (!isLongClicking) {
            Intent intent = new Intent(getApplicationContext(), DetailWorkActivity.class);
            intent.putExtra("EXTRA_WORK_ID", congViecModel.getIdCongViec().toString());
            intent.putExtra("EXTRA_GROUPWORK_ID", EXTRA_GROUPWORK_ID);
            intent.putExtra("EXTRA_GROUPWORK_NAME", EXTRA_GROUPWORK_NAME);
            startActivityForResult(intent, REQUEST_WORK_DETAIL);
        } else {
            setupLongClick(congViecModel, view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_WORK_DETAIL) {
            if (resultCode == RESULT_OK) {
                EXTRA_GROUPWORK_ID = data.getStringExtra("EXTRA_GROUPWORK_ID");
                EXTRA_GROUPWORK_NAME = data.getStringExtra("EXTRA_GROUPWORK_NAME");
                Log.e("OnResult", "" + EXTRA_GROUPWORK_NAME);
                initData();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemLongClick(CongViecModel congViecModel, LinearLayout view) {
        isLongClicking = true;
        setupLongClick(congViecModel, view);
    }

    public void setupLongClick(CongViecModel congViecModel, LinearLayout view) {
        if (currentItemWork != null && currentItemWork.getIdCongViec() == congViecModel.getIdCongViec()) {
            ivBackFromLongClick.performClick();
        } else {
            if (lnlCurrentItemLongClick != null) {
                lnlCurrentItemLongClick.setBackground(getResources().getDrawable(R.drawable.borderwork_white));
            } else{
                if (lnlCurrentItemLongClick != null) {
                    lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#FAFAFA"));
                } else {
                    mToolbar.setVisibility(View.GONE);
                    lnlLongClickMenu.setVisibility(View.VISIBLE);
                }
                currentItemWork = new CongViecModel();
                lnlCurrentItemLongClick = view;
                currentItemWork = congViecModel;
                tvNameItemWork.setText(currentItemWork.getTenCongViec() + " seleted");
                lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#0099CC"));
            }
        }

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                Toast.makeText(getApplicationContext(), "on Refresh ",
                        Toast.LENGTH_LONG).show();
                mSwipeLayout.setRefreshing(false);
            }
        }, 500);

    }
}
