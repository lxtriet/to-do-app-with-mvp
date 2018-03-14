package com.hcmute.trietthao.yourtime.mvp.tasksFragment.adapter;

import android.widget.LinearLayout;

import com.hcmute.trietthao.yourtime.model.NhomCVModel;

/**
 * Created by lxtri on 11/21/2017.
 */

public interface IOnItemGroupWorkTasksListener {
    void onItemClick(NhomCVModel nhomCVModel, LinearLayout view);

    void onItemLongClick(NhomCVModel nhomCVModel, LinearLayout view);
}
