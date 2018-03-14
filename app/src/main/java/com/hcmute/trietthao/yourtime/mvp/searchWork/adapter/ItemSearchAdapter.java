package com.hcmute.trietthao.yourtime.mvp.searchWork.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.IOnItemGroupWorkListener;
import com.hcmute.trietthao.yourtime.mvp.IOnItemWorkListener;

import java.text.ParseException;
import java.util.ArrayList;

import static com.hcmute.trietthao.yourtime.service.utils.AnimationUtils.setFadeInTime;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDateTimeToInsertUpdate;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDisplayDate;

/**
 * Created by lxtri on 11/11/2017.
 */



public class ItemSearchAdapter extends BaseExpandableListAdapter implements PostWorkListener{

    Context context;
    ArrayList<NhomCVModel> mListNhomCV;
    DBWorkServer dbWorkServer;
    IOnItemWorkListener itemSearchListener;
    IOnItemGroupWorkListener itemGroupWorkListener;

    public ItemSearchAdapter(Context context, ArrayList<NhomCVModel> mListNhomCV,
                             IOnItemWorkListener itemSearchListener,IOnItemGroupWorkListener itemGroupWorkListener
       ){
        this.context = context;
        this.mListNhomCV = mListNhomCV;
        this.itemSearchListener = itemSearchListener;
        this.itemGroupWorkListener = itemGroupWorkListener;
    }

    @Override
    public int getGroupCount() {
        return mListNhomCV.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListNhomCV.get(groupPosition).getCongViecModels().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListNhomCV.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListNhomCV.get(groupPosition).getCongViecModels().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mListNhomCV.get(groupPosition).getIdNhom();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mListNhomCV.get(groupPosition).getCongViecModels().get(childPosition).getIdCongViec();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View converView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        converView = inflater.inflate(R.layout.item_group_header,null);
        final NhomCVModel nhomCVModel= mListNhomCV.get(groupPosition);
        Button btnNameGroup = (Button) converView.findViewById(R.id.item_groupwork_name);
        btnNameGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemGroupWorkListener.onItemClick(nhomCVModel);
            }
        });
        btnNameGroup.setText(nhomCVModel.getTenNhom());

        return converView;
    }

    @Override
    public View getChildView(final int groupPosition,final int childPosition, boolean b, View converView, ViewGroup viewGroup) {

        dbWorkServer = new DBWorkServer(this);

        final CongViecModel congViecModel = mListNhomCV.get(groupPosition).getCongViecModels().get(childPosition);
        Log.e("ItemSearchAdapter","getChildView  "+congViecModel.getTenCongViec());
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        converView = inflater.inflate(R.layout.item_work,null);
        LinearLayout lnlWork = (LinearLayout) converView.findViewById(R.id.lnl_item_work);
        CheckBox cbTickWork = (CheckBox) converView.findViewById(R.id.item_work_checkbox);
        TextView tvNameWork = (TextView) converView.findViewById(R.id.item_work_name);
        TextView tvTimeWork = (TextView) converView.findViewById(R.id.item_work_time);
        ImageView ivConversationWork = (ImageView) converView.findViewById(R.id.item_work_conversation);
        ImageView ivRepeatWork = (ImageView) converView.findViewById(R.id.item_work_repeat);
        ImageView ivAssignedWork = (ImageView) converView.findViewById(R.id.item_work_assigned);
        final ImageView ivPriorityWork = (ImageView) converView.findViewById(R.id.item_work_priority);

        tvNameWork.setText(congViecModel.getTenCongViec());
        if(congViecModel.getThoiGianBatDau()!=null)
            tvTimeWork.setText(getDisplayDate(congViecModel.getThoiGianBatDau()));
        else
            tvTimeWork.setText("");

        if(congViecModel.getTrangThai().equals("waiting"))
                tvTimeWork.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
        if(congViecModel.getTrangThai().equals("done"))
            tvTimeWork.setTextColor(ContextCompat.getColor(context, R.color.colorGray800));
        if(congViecModel.getTrangThai().equals("overdue"))
            tvTimeWork.setTextColor(ContextCompat.getColor(context, R.color.colorRed));

        dbWorkServer = new DBWorkServer(this);

        if(congViecModel.getCoUuTien()==1){
            ivPriorityWork.setImageResource(R.drawable.ic_priority_selected);
        }

        else{
            ivPriorityWork.setImageResource(R.drawable.ic_priority_unselected);
        }
        ivPriorityWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(congViecModel.getCoUuTien()==1){
                    dbWorkServer.updatePriorityWork(0,congViecModel.getIdCongViec());
                    congViecModel.setCoUuTien(0);
                    ivPriorityWork.setImageResource(R.drawable.ic_priority_unselected);
                }
                else{
                    ivPriorityWork.setImageResource(R.drawable.ic_priority_selected);
                    dbWorkServer.updatePriorityWork(1,congViecModel.getIdCongViec());
                    congViecModel.setCoUuTien(1);
                }
            }
        });

        cbTickWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dbWorkServer.updateStatusWork("done",congViecModel.getIdCongViec());
                            if(congViecModel.getThoiGianBatDau()!=null){
                                try {

                                    dbWorkServer.updateStatusWorkTimeNotNull("done",congViecModel.getIdCongViec(),
                                            getDateTimeToInsertUpdate(congViecModel.getThoiGianBatDau()));
                                    Log.e("Thoigianupdate","---------- "+ getDateTimeToInsertUpdate(congViecModel.getThoiGianBatDau()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            removeItem(groupPosition,childPosition);
                            //dbWorkServer.updateStatusWork("done",congViecModel.getIdCongViec(),congViecModel.getThoiGianBatDau());

                        }
                    }, 1000);
                }

            }
        });

        ivAssignedWork.setVisibility(View.INVISIBLE);

        if(congViecModel.getIdNhacNho()==0)
            ivRepeatWork.setVisibility(View.INVISIBLE);
        else
            ivRepeatWork.setVisibility(View.VISIBLE);

        ivConversationWork.setVisibility(View.INVISIBLE);

        lnlWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            itemSearchListener.onItemClick(congViecModel,(LinearLayout) view);
            }
        });

        lnlWork.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemSearchListener.onItemLongClick(congViecModel,(LinearLayout) view);
                return true;
            }
        });

        setFadeInTime(converView,1000);

        return converView;

    }

    public void removeItem(int groupPosition, int childPosition){
        mListNhomCV.get(groupPosition).getCongViecModels().remove(childPosition);
        if(mListNhomCV.get(groupPosition).getCongViecModels().size()==0)
            mListNhomCV.remove(groupPosition);
        notifyDataSetChanged();
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }

}
