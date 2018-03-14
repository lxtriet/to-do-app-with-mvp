package com.hcmute.trietthao.yourtime.database;

import com.hcmute.trietthao.yourtime.model.CongViecModel;

import java.util.ArrayList;

/**
 * Created by lxtri on 11/13/2017.
 */

public interface GetWorkListener {
    void  getListAllWork(final ArrayList<CongViecModel> congViecModelArrayList);

}
