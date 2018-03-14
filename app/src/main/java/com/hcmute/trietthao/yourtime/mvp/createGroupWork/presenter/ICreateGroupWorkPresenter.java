package com.hcmute.trietthao.yourtime.mvp.createGroupWork.presenter;

/**
 * Created by lxtri on 11/11/2017.
 */

public interface ICreateGroupWorkPresenter {
    void insertGroupWork(int idNhom, String tenNhom,int laNhomCaNhan);
    void insertGroupWorkUser(int idNhom,int idNguoiDung, String vaitro);
}
