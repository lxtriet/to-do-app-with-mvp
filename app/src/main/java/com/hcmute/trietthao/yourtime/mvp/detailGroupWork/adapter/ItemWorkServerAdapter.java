package com.hcmute.trietthao.yourtime.mvp.detailGroupWork.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.mvp.IOnItemWorkListener;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view.IDetailGroupWorkView;

import java.text.ParseException;
import java.util.ArrayList;

import static com.hcmute.trietthao.yourtime.service.utils.AnimationUtils.setFadeInTime;
import static com.hcmute.trietthao.yourtime.service.utils.AnimationUtils.setFadeOutAnimation;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDateTimeToInsertUpdate;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDisplayDate;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.isOverDueDate;

/**
 * Created by lxtri on 11/15/2017.
 */

public class ItemWorkServerAdapter extends RecyclerView.Adapter<ItemWorkServerAdapter.ViewHolder>
    implements PostWorkListener{

    Activity activity;
    Context context;
    ArrayList<CongViecModel> mListCV1,mListCV2;
    Integer flag;
    DBWorkServer dbWorkServer;
    IOnItemWorkListener itemWorkListener;
    IDetailGroupWorkView iDetailGroupWorkView;

    public ItemWorkServerAdapter(Context context, ArrayList<CongViecModel> mListCV1,
                                 ArrayList<CongViecModel> mListCV2,Integer flag,
                                 IOnItemWorkListener itemWorkListener, IDetailGroupWorkView iDetailGroupWorkView){
        this.context = context;
        this.mListCV1 = mListCV1;
        this.mListCV2 = mListCV2;
        this.flag = flag;
        this.itemWorkListener = itemWorkListener;
        this.iDetailGroupWorkView = iDetailGroupWorkView;
    }

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lnlWork;
        CheckBox cbTickWork;
        TextView tvNameWork, tvTimeWork;
        ImageView ivPriorityWork, ivConversationWork, ivRepeatWork, ivAssignedWork;
        public ViewHolder(View converView) {
            super(converView);

            lnlWork = (LinearLayout) converView.findViewById(R.id.lnl_item_work);
            cbTickWork = (CheckBox) converView.findViewById(R.id.item_work_checkbox);
            tvNameWork = (TextView) converView.findViewById(R.id.item_work_name);
            tvTimeWork = (TextView) converView.findViewById(R.id.item_work_time);
            ivPriorityWork = (ImageView) converView.findViewById(R.id.item_work_priority);
            ivConversationWork = (ImageView) converView.findViewById(R.id.item_work_conversation);
            ivRepeatWork = (ImageView) converView.findViewById(R.id.item_work_repeat);
            ivAssignedWork = (ImageView) converView.findViewById(R.id.item_work_assigned);
        }
    }

    @Override
    public ItemWorkServerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_work, parent, false);
        return new ItemWorkServerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemWorkServerAdapter.ViewHolder holder, final int position) {
        final CongViecModel congViecModel;
        if(flag==1){
            congViecModel = mListCV1.get(position);
            holder.tvNameWork.setText(congViecModel.getTenCongViec());

            if(congViecModel.getThoiGianBatDau()!=null)
                holder.tvTimeWork.setText(getDisplayDate(congViecModel.getThoiGianBatDau()));
            else
                holder.tvTimeWork.setText("");

            if(congViecModel.getTrangThai().equals("waiting"))
                holder.tvTimeWork.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
            if(congViecModel.getTrangThai().equals("overdue"))
                holder.tvTimeWork.setTextColor(ContextCompat.getColor(context, R.color.colorRed));

            dbWorkServer = new DBWorkServer(this);

            if(congViecModel.getCoUuTien()==1){
                holder.ivPriorityWork.setImageResource(R.drawable.ic_priority_selected);
            }

            else{
                holder.ivPriorityWork.setImageResource(R.drawable.ic_priority_unselected);
            }
            holder.ivPriorityWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(congViecModel.getCoUuTien()==1){
                        dbWorkServer.updatePriorityWork(0,congViecModel.getIdCongViec());
                        congViecModel.setCoUuTien(0);
                        holder.ivPriorityWork.setImageResource(R.drawable.ic_priority_unselected);
                    }
                    else{
                        holder.ivPriorityWork.setImageResource(R.drawable.ic_priority_selected);
                        dbWorkServer.updatePriorityWork(1,congViecModel.getIdCongViec());
                        congViecModel.setCoUuTien(1);
                    }
                }
            });

            holder.cbTickWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if ( isChecked )
                    {
                        setFadeOutAnimation(holder.itemView);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dbWorkServer.updateStatusWork("done",congViecModel.getIdCongViec());
                                congViecModel.setTrangThai("done");
                                if(congViecModel.getThoiGianBatDau()!=null){
                                    try {
                                        dbWorkServer.updateStatusWorkTimeNotNull("done",congViecModel.getIdCongViec(),
                                                getDateTimeToInsertUpdate(congViecModel.getThoiGianBatDau()));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                mListCV2.add(congViecModel);
                                mListCV1.remove(position);
                                Log.e("Size:","--List1: "+mListCV1.size()+"    --List2: "+mListCV2.size());
                                iDetailGroupWorkView.notifyDataSetChanged();
                            }
                        }, 1000);
                    }

                }
            });

            holder.ivAssignedWork.setVisibility(View.INVISIBLE);
            holder.ivConversationWork.setVisibility(View.INVISIBLE);

            if(congViecModel.getIdNhacNho()==0)
                holder.ivRepeatWork.setVisibility(View.INVISIBLE);
            else
                holder.ivRepeatWork.setVisibility(View.VISIBLE);

        }else{
            congViecModel = mListCV2.get(position);
            Log.e("ITEM"," ------ "+congViecModel.getTenCongViec());


            holder.tvNameWork.setText(congViecModel.getTenCongViec());
            holder.tvNameWork.setTextColor(ContextCompat.getColor(context, R.color.colorGray500));
            holder.tvNameWork.setPaintFlags(holder.tvNameWork.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.lnlWork.setBackground(ContextCompat.getDrawable(context,R.drawable.borderwork_completed));

            if(congViecModel.getThoiGianBatDau()!=null)
                holder.tvTimeWork.setText(getDisplayDate(congViecModel.getThoiGianBatDau()));
            else
                holder.tvTimeWork.setText("");

            holder.tvTimeWork.setTextColor(ContextCompat.getColor(context, R.color.colorGray500));

            dbWorkServer = new DBWorkServer(this);

            if(congViecModel.getCoUuTien()==1){
                holder.ivPriorityWork.setImageResource(R.drawable.ic_priority_selected_completed);
            }

            else{
                holder.ivPriorityWork.setImageResource(R.drawable.ic_priority_unselected);
            }
            holder.ivPriorityWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(congViecModel.getCoUuTien()==1){
                        dbWorkServer.updatePriorityWork(0,congViecModel.getIdCongViec());
                        congViecModel.setCoUuTien(0);
                        holder.ivPriorityWork.setImageResource(R.drawable.ic_priority_unselected);
                    }
                    else{
                        holder.ivPriorityWork.setImageResource(R.drawable.ic_priority_selected_completed);
                        dbWorkServer.updatePriorityWork(1,congViecModel.getIdCongViec());
                        congViecModel.setCoUuTien(1);
                    }
                }
            });
            holder.cbTickWork.setChecked(true);

            holder.cbTickWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if ( !isChecked )
                    {
                        setFadeOutAnimation(holder.itemView);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if(congViecModel.getThoiGianBatDau()!=null){
                                        if(isOverDueDate((congViecModel.getThoiGianKetThuc()))){
                                            dbWorkServer.updateStatusWorkTimeNotNull("overdue",congViecModel.getIdCongViec(),
                                                    getDateTimeToInsertUpdate(congViecModel.getThoiGianBatDau()));
                                            dbWorkServer.updateStatusWork("overdue",congViecModel.getIdCongViec());
                                            congViecModel.setTrangThai("overdue");
                                        }
                                        else{
                                            dbWorkServer.updateStatusWork("waiting",congViecModel.getIdCongViec());
                                            congViecModel.setTrangThai("waiting");
                                            dbWorkServer.updateStatusWorkTimeNotNull("waiting",congViecModel.getIdCongViec(),
                                                    getDateTimeToInsertUpdate(congViecModel.getThoiGianBatDau()));
                                        }
                                    }else{
                                        dbWorkServer.updateStatusWork("waiting",congViecModel.getIdCongViec());
                                        congViecModel.setTrangThai("waiting");
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                mListCV1.add(congViecModel);
                                mListCV2.remove(position);
                                Log.e("Size:","--List1: "+mListCV1.size()+"    --List2: "+mListCV2.size());
                                iDetailGroupWorkView.notifyDataSetChanged();
                            }
                        }, 1000);
                    }

                }
            });

            holder.ivAssignedWork.setVisibility(View.INVISIBLE);
            holder.ivConversationWork.setVisibility(View.INVISIBLE);

            if(congViecModel.getIdNhacNho()==0)
                holder.ivRepeatWork.setVisibility(View.INVISIBLE);
            else
            {
                holder.ivRepeatWork.setVisibility(View.VISIBLE);
                holder.ivRepeatWork.setImageResource(R.drawable.ic_repeat_off);
            }

        }

        holder.lnlWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemWorkListener.onItemClick(congViecModel,(LinearLayout) view);
            }
        });

        holder.lnlWork.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemWorkListener.onItemLongClick(congViecModel,(LinearLayout) view);
                return true;
            }
        });

        setFadeInTime(holder.itemView,1000);
    }

    @Override
    public int getItemCount() {
        if(flag==1)
            return mListCV1.size();
        return mListCV2.size();
    }

    public void clearData(){
        if(flag==1)
             mListCV1.clear();
        else mListCV2.clear();
    }

}
