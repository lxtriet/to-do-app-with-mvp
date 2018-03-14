package com.hcmute.trietthao.yourtime.mvp.tasksFragment.presenter;

/**
 * Created by lxtri on 11/11/2017.
 */

public interface ITasksPresenter {
    void getAllGroupWorkOnline(int idUser);

    void getAllWorkOnline(int idUser);
    void deleteGroupWork(int idGroup, int idUser);
    void deleteWork(int idWork);

}
