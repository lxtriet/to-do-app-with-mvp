package com.hcmute.trietthao.yourtime.database;

import com.hcmute.trietthao.yourtime.model.CVThongBaoModel;

import java.util.ArrayList;

/**
 * Created by lxtri on 12/1/2017.
 */

public interface GetWorkNotificationListener {
    void  getAllWorkNotification(final ArrayList<CVThongBaoModel> congViecModelArrayList);
}
