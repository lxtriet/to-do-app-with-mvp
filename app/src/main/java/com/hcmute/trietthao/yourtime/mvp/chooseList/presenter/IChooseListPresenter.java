package com.hcmute.trietthao.yourtime.mvp.chooseList.presenter;


public interface IChooseListPresenter {
    void insertGroupWork(int idNhom, String tenNhom,int laNhomCaNhan);
    void insertGroupWorkUser(int idNhom,int idNguoiDung, String vaitro);
}
