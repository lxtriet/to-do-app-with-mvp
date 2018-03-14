package com.hcmute.trietthao.yourtime.mvp.detailGroupWork.presenter;

import com.hcmute.trietthao.yourtime.model.CongViecModel;

/**
 * Created by lxtri on 11/15/2017.
 */

public interface IDetailGroupWorkPresenter {
    void getWorkByIdGroup(Integer idnguoidung,Integer idgroup);

    void getDetailGroupWork(Integer idnguoidung,int idgroup);

    void insertWork(CongViecModel congViecModel);

    void deleteWork(int idcongviec);
}
