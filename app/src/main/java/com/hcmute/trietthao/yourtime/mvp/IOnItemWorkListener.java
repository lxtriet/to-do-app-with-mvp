package com.hcmute.trietthao.yourtime.mvp;

import android.widget.LinearLayout;

import com.hcmute.trietthao.yourtime.model.CongViecModel;

/**
 * Created by lxtri on 11/21/2017.
 */

public interface IOnItemWorkListener {

    void onItemClick(CongViecModel congViecModel, LinearLayout view);

    void onItemLongClick(CongViecModel congViecModel, LinearLayout view);

}
