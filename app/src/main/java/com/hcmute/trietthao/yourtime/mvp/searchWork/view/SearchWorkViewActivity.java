package com.hcmute.trietthao.yourtime.mvp.searchWork.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.mvp.detailWork.view.DetailWorkActivity;
import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.IOnItemGroupWorkListener;
import com.hcmute.trietthao.yourtime.mvp.IOnItemWorkListener;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view.DetailGroupWorkActivity;
import com.hcmute.trietthao.yourtime.mvp.searchWork.adapter.ItemSearchAdapter;
import com.hcmute.trietthao.yourtime.mvp.searchWork.presenter.SearchGetWorkPresenter;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.service.utils.NetworkUtils.isNetWorkConnected;


public class SearchWorkViewActivity extends AppCompatActivity implements View.OnClickListener
        ,ISearchWorkView,IOnItemWorkListener,IOnItemGroupWorkListener{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.etxt_search)
    EditText etSearch;

    @Bind(R.id.iv_clear_search)
    ImageView ivClearSearch;

    @Bind(R.id.iv_change_group)
    ImageView ivChangeGroup;

    @Bind(R.id.iv_delete_item_work)
    ImageView ivDeleteItemWork;

    @Bind(R.id.iv_back_longclick_itemwork)
    ImageView ivBackFromLongClick;

    @Bind(R.id.tv_name_item_work_selected)
    TextView tvNameItemWork;

    @Bind(R.id.lnlayout_longclick_work)
    LinearLayout lnlLongClickMenu;

    @Bind(R.id.progressBar_Loadmore)
    ProgressBar pbLoading;

    @Bind(R.id.list_groupworks_works_search)
    ExpandableListView elListSearch;

    SearchGetWorkPresenter mSearchWorkPresenter;
    ArrayList<NhomCVModel> mListNhomCv;

    LinearLayout lnlCurrentItemLongClick;
    CongViecModel currentItemWork;

    ItemSearchAdapter itemSearchAdapter;
    PreferManager mPreferManager;

    boolean isLongClicking = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchwork);
        ButterKnife.bind(this);

        mSearchWorkPresenter = new SearchGetWorkPresenter(this);
        mListNhomCv = new ArrayList<>();

        mPreferManager = new PreferManager(getApplicationContext());

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
        {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        ivClearSearch.setOnClickListener(this);
        ivBackFromLongClick.setOnClickListener(this);
        ivDeleteItemWork.setOnClickListener(this);
        ivChangeGroup.setOnClickListener(this);


        ivClearSearch.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.GONE);
        lnlLongClickMenu.setVisibility(View.GONE);

        etSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                { //do your work here }
                    ivClearSearch.setVisibility(View.VISIBLE);

                    if(isNetWorkConnected(getApplicationContext())){
                        mSearchWorkPresenter.getAllWorkSearchOnline(mPreferManager.getID(),etSearch.getText().toString());  // ID USER IN PREFERENCE
                    }

                }else
                    ivClearSearch.setVisibility(View.INVISIBLE);

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.iv_clear_search:
                ivClearSearch.setVisibility(View.INVISIBLE);
                etSearch.setText("");
                break;
            case R.id.iv_change_group:
                Toast.makeText(getApplication(), "Change group-----"+currentItemWork.getTenCongViec(),
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_delete_item_work:
                Toast.makeText(getApplication(), "Delete-----"+currentItemWork.getTenCongViec(),
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_back_longclick_itemwork:
                lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#FAFAFA"));
                lnlCurrentItemLongClick = null;
                currentItemWork = null;
                isLongClicking = false;
                lnlLongClickMenu.setVisibility(View.GONE);
                mToolbar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void getListGroupWorkSucess() {
        mListNhomCv = formatList(mSearchWorkPresenter.getListSearchOnline());

        itemSearchAdapter = new ItemSearchAdapter(getApplication(),mListNhomCv,this
                ,this);
        elListSearch.setAdapter(itemSearchAdapter);

        for(int i=0;i<mListNhomCv.size();i++)
            elListSearch.expandGroup(i);

        elListSearch.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,int  groupPosition, long id) {
                return true;
            }
        });
    }

    public ArrayList<NhomCVModel> formatList(ArrayList<NhomCVModel> nhomCVModelArrayList){
        ArrayList<NhomCVModel> temp = new ArrayList<NhomCVModel>();
        for(int i=0;i<nhomCVModelArrayList.size();i++){
            if(nhomCVModelArrayList.get(i).getCongViecModels().size()!=0)
                temp.add(nhomCVModelArrayList.get(i));
        }
        return temp;
    }

    @Override
    public void getListGroupWorkEmpty() {
        Toast.makeText(getApplication(), "No result found!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void getListGroupWorkFail() {
        Toast.makeText(getApplication(), "Check your connection!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.GONE);
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
                lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#FAFAFA"));
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

    @Override
    public void onItemClick(NhomCVModel nhomCVModel) {
        Intent intent = new Intent(getApplicationContext(), DetailGroupWorkActivity.class);
        intent.putExtra("EXTRA_GROUPWORK_ID", nhomCVModel.getIdNhom().toString());
        intent.putExtra("EXTRA_GROUPWORK_NAME", nhomCVModel.getTenNhom());
        startActivity(intent);
    }
}
