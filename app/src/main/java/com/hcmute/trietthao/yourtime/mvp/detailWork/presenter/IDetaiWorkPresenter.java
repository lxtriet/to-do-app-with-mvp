package com.hcmute.trietthao.yourtime.mvp.detailWork.presenter;

import com.hcmute.trietthao.yourtime.model.CongViecModel;

/**
 * Created by lxtri on 12/4/2017.
 */

public interface IDetaiWorkPresenter {
    void getDetailWork(int idWork);

    void updateDetailWork(CongViecModel congViecModel);

    void createNotification(int idWork);

    void deleteAllNotification(int idWork);
}
