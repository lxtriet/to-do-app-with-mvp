package com.hcmute.trietthao.yourtime.mvp.detailGroupWorkMain.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.IOnItemGroupWorkListener;
import com.hcmute.trietthao.yourtime.mvp.IOnItemWorkListener;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view.DetailGroupWorkActivity;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWorkMain.adapter.ItemDetailGroupWorkAdapter;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWorkMain.presenter.DetailGroupWorkMainPresenter;
import com.hcmute.trietthao.yourtime.mvp.detailWork.view.DetailWorkActivity;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.service.utils.NetworkUtils.isNetWorkConnected;


public class DetailGroupWorkMainActivity extends AppCompatActivity implements View.OnClickListener,IDetailGroupWorkMainView,
        IOnItemWorkListener,IOnItemGroupWorkListener{

    Toolbar mToolbar;

    @Bind(R.id.lnl_groupempty)
    LinearLayout mLnlGroupEmpty;

    @Bind(R.id.img_groupempty)
    ImageView mImgGroupEmpty;

    @Bind(R.id.iv_back_longclick_itemwork)
    ImageView ivBackFromLongClick;

    @Bind(R.id.iv_delete_item_work)
    ImageView ivDeleteItemWork;

    @Bind(R.id.txt_groupempty)
    TextView mTxtGroupEmpty;

    @Bind(R.id.tv_name_groupwork)
    TextView tvNameGroupWork;

    @Bind(R.id.tv_name_item_work_selected)
    TextView tvNameItemWork;

    @Bind(R.id.lnlayout_longclick_work)
    LinearLayout lnlLongClickMenu;

    @Bind(R.id.list_groupworks_works)
    ExpandableListView elListGroupWorkMain;

    @Bind(R.id.progressBar_Loadmore)
    ProgressBar pbLoading;

    ArrayList<NhomCVModel> mListNhomCV;
    String EXTRA_GROUPMAIN_ID = "";

    DetailGroupWorkMainPresenter mdetailGroupWorkMainPresenter;
    ItemDetailGroupWorkAdapter itemDetailGroupWorkAdapter;
    CongViecModel currentItemWork;
    LinearLayout lnlCurrentItemLongClick;
    boolean isLongClicking = false;

    PreferManager mPreferManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupworkmain_detail);
        ButterKnife.bind(this);

        mPreferManager = new PreferManager(getApplicationContext());

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
        {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        EXTRA_GROUPMAIN_ID = getIntent().getStringExtra("EXTRA_GROUPMAIN_ID");
        mdetailGroupWorkMainPresenter = new DetailGroupWorkMainPresenter(this);
        initData();

        ivDeleteItemWork.setOnClickListener(this);
        ivBackFromLongClick.setOnClickListener(this);
        lnlLongClickMenu.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.iv_back_longclick_itemwork:
                lnlCurrentItemLongClick.setBackground(getResources().getDrawable(R.drawable.borderwork_white));
                lnlCurrentItemLongClick = null;
                currentItemWork = null;
                isLongClicking = false;
                lnlLongClickMenu.setVisibility(View.GONE);
                mToolbar.setVisibility(View.VISIBLE);

                break;
            case R.id.iv_delete_item_work:
                Toast.makeText(getApplication(), "Delete-----"+currentItemWork.getTenCongViec(),
                        Toast.LENGTH_LONG).show();
                break;
        }

    }

    public void initData(){
        if(isNetWorkConnected(getApplication())){
            Log.e("INIT"," ------------GROUP ID: "+EXTRA_GROUPMAIN_ID);
            mdetailGroupWorkMainPresenter.getAllWorkOnline(mPreferManager.getID(),EXTRA_GROUPMAIN_ID);
            switch (EXTRA_GROUPMAIN_ID){
                case "1": tvNameGroupWork.setText(getResources().getString(R.string.assigned_to_me)); break;
                case "2": tvNameGroupWork.setText(getResources().getString(R.string.starred)); break;
                case "3": tvNameGroupWork.setText(getResources().getString(R.string.today)); break;
                case "4": tvNameGroupWork.setText(getResources().getString(R.string.week)); break;
                case "5": tvNameGroupWork.setText(getResources().getString(R.string.all)); break;
                case "6": tvNameGroupWork.setText(getResources().getString(R.string.completed)); break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
//        elListGroupWorkMain.setAdapter((BaseExpandableListAdapter)null);
//        initData();
        super.onResume();
    }

    @Override
    public void getDetailGroupWorkSuccess() {
        mListNhomCV = formatList(mdetailGroupWorkMainPresenter.getListNhomCV());
        if(mListNhomCV.size()!=0){
            mLnlGroupEmpty.setVisibility(View.GONE);
            itemDetailGroupWorkAdapter = new ItemDetailGroupWorkAdapter(getApplication(),mListNhomCV,this,
                    this);
            elListGroupWorkMain.setAdapter(itemDetailGroupWorkAdapter);

            for(int i=0;i<mListNhomCV.size();i++)
                elListGroupWorkMain.expandGroup(i);

            elListGroupWorkMain.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,int  groupPosition, long id) {
                    return true;
                }
            });
        }else{
            switch (EXTRA_GROUPMAIN_ID) {
                case "1":
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_assigned_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_assigned_empty));
                    break;
                case "2":
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_starred_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_starred_empty));
                    break;
                case "3":
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_today_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_today_empty));
                    break;
                case "4":
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_week_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_week_empty));
                    break;
                case "5":
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_all_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_all_empty));
                    break;
                case "6":
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_completed_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_completed_empty));
                    break;
        }

        }
    }

    public ArrayList<NhomCVModel> formatList(ArrayList<NhomCVModel> nhomCVModelArrayList){
        ArrayList<NhomCVModel> temp = new ArrayList<NhomCVModel>();
        for(int i=0;i<nhomCVModelArrayList.size();i++){
            if(nhomCVModelArrayList.get(i).getCongViecModels().size()!=0)
                temp.add(nhomCVModelArrayList.get(i));
        }
        Log.e("DetailGroupWorkMain:", "size "+temp.size());
        return temp;
    }

    @Override
    public void getDetailGroupWorkFail() {
        Toast.makeText(getApplication(), "Check your connection!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        mLnlGroupEmpty.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLnlGroupEmpty.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(NhomCVModel nhomCVModel) {
        Intent intent = new Intent(getApplicationContext(), DetailGroupWorkActivity.class);
        intent.putExtra("EXTRA_GROUPWORK_ID", nhomCVModel.getIdNhom().toString());
        intent.putExtra("EXTRA_GROUPWORK_NAME", nhomCVModel.getTenNhom());
        startActivity(intent);
    }

    @Override
    public void onItemClick(CongViecModel congViecModel, LinearLayout view) {
        if(!isLongClicking){
            Intent intent = new Intent(getApplicationContext(), DetailWorkActivity.class);
            intent.putExtra("EXTRA_WORK_ID", congViecModel.getIdCongViec().toString());
            intent.putExtra("EXTRA_WORK_NAME", congViecModel.getTenCongViec());
            startActivity(intent);
        }
        else{
            setupLongClick(congViecModel,view);
        }
    }

    @Override
    public void onItemLongClick(CongViecModel congViecModel, LinearLayout view) {
        isLongClicking = true;
        setupLongClick(congViecModel,view);
    }

    public void setupLongClick(CongViecModel congViecModel,LinearLayout view){
        if(currentItemWork!=null && currentItemWork.getIdCongViec()==congViecModel.getIdCongViec()){
            ivBackFromLongClick.performClick();
        }else{
            if(lnlCurrentItemLongClick!=null){
                lnlCurrentItemLongClick.setBackground(getResources().getDrawable(R.drawable.borderwork_white));
            }
            else{
                mToolbar.setVisibility(View.GONE);
                lnlLongClickMenu.setVisibility(View.VISIBLE);
            }
            currentItemWork = new CongViecModel();
            lnlCurrentItemLongClick = view;
            currentItemWork = congViecModel;
            tvNameItemWork.setText(currentItemWork.getTenCongViec()+" seleted");
            lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#0099CC"));
        }
    }
}
