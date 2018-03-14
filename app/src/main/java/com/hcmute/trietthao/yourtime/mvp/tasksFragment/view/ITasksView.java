package com.hcmute.trietthao.yourtime.mvp.tasksFragment.view;

/**
 * Created by lxtri on 11/11/2017.
 */

public interface ITasksView {

    void showLoading();

    void hideLoading();

    void getAllGroupWorkSuccess();

    void getListAllWorkSucess();

    void getAllGroupWorkFailure();

}
