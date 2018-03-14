package com.hcmute.trietthao.yourtime.database;

import com.hcmute.trietthao.yourtime.model.LoaiNhacNhoModel;

import java.util.ArrayList;

/**
 * Created by lxtri on 11/13/2017.
 */

public interface GetRepeatListener {
    void  getListRepeat(final ArrayList<LoaiNhacNhoModel> loaiNhacNhoModelArrayList);

}
