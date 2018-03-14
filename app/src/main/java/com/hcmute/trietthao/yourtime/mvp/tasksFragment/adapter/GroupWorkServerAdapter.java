package com.hcmute.trietthao.yourtime.mvp.tasksFragment.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;

import java.util.ArrayList;

import static com.hcmute.trietthao.yourtime.service.utils.AnimationUtils.setFadeInTime;

/**
 * Created by lxtri on 11/11/2017.
 */



public class GroupWorkServerAdapter extends RecyclerView.Adapter<GroupWorkServerAdapter.GroupWorkServerViewHolder> {

    private ArrayList<NhomCVModel> nhomCVModelList;
    private Activity activity;
    private IOnItemGroupWorkTasksListener itemEventListener;
    String SCREEN_REQUEST = "";
    /**Contructor*/
    public GroupWorkServerAdapter(Activity activity,ArrayList<NhomCVModel> nhomCVModelList,
                                  IOnItemGroupWorkTasksListener itemEventListener, String SCREEN_REQUEST) {
        this.activity = activity;
        this.nhomCVModelList = nhomCVModelList;
        this.itemEventListener = itemEventListener;
        this.SCREEN_REQUEST = SCREEN_REQUEST;
    }
    /** Create ViewHolder*/
    public class GroupWorkServerViewHolder extends  RecyclerView.ViewHolder {
        private ImageView ivGroupWork;
        private TextView tvGroupWork;
        private TextView tvGroupWorkAll;
        private TextView tvGroupWorkOverDue;
        private LinearLayout lnlGroup;

        public GroupWorkServerViewHolder(View itemView) {
            super(itemView);

            ivGroupWork = (ImageView) itemView.findViewById(R.id.item_groupwork_image);
            lnlGroup = (LinearLayout) itemView.findViewById(R.id.lnlayout_item_group);
            tvGroupWork = (TextView) itemView.findViewById(R.id.item_groupwork_namegroup);
            tvGroupWorkAll = (TextView) itemView.findViewById(R.id.item_groupwork_all);
            tvGroupWorkOverDue = (TextView) itemView.findViewById(R.id.item_groupwork_overdue);
        }
    }
    @Override
    public GroupWorkServerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /** Get layout */
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_groupwork,parent,false);
        return new GroupWorkServerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupWorkServerViewHolder holder, int position) {
        /** Set Value*/

        final NhomCVModel nhomCVModel= nhomCVModelList.get(position);
        Log.e("MainActivity","Item: "+nhomCVModel.getTenNhom());
        holder.tvGroupWork.setText(nhomCVModel.getTenNhom());
        if(nhomCVModel.getLaNhomCaNhan()==1){
            if(nhomCVModel.getIdNhom()==0){
                holder.ivGroupWork.setImageResource(R.drawable.ic_inbox_blue);
                holder.ivGroupWork.setPadding(2,2,2,2);
            }
            else
                holder.ivGroupWork.setImageResource(R.drawable.ic_groupnormal);
        }
        else
            holder.ivGroupWork.setImageResource(R.drawable.ic_groupassigned);
        if(SCREEN_REQUEST.equals("TasksFragment")){
            if(nhomCVModel.getSoCV()>0)
                holder.tvGroupWorkAll.setText(nhomCVModel.getSoCV().toString());
            else
                holder.tvGroupWorkAll.setVisibility(View.INVISIBLE);
            if(nhomCVModel.getSoCVQuaHan()>0)
                holder.tvGroupWorkOverDue.setText(nhomCVModel.getSoCVQuaHan().toString());
            else
                holder.tvGroupWorkOverDue.setVisibility(View.INVISIBLE);
        }else{
            holder.tvGroupWorkOverDue.setVisibility(View.GONE);
            holder.tvGroupWorkAll.setVisibility(View.GONE);
        }
   /*Sự kiện click vào item*/
        holder.lnlGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemEventListener.onItemClick(nhomCVModel,(LinearLayout)view);
            }
        });
        holder.lnlGroup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemEventListener.onItemLongClick(nhomCVModel,(LinearLayout) view);
                return true;
            }
        });

        setFadeInTime(holder.itemView,300);
    }

    @Override
    public int getItemCount() {
        return nhomCVModelList.size();
    }

}

