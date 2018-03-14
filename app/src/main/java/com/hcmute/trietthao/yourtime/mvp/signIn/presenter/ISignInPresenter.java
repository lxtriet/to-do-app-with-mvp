package com.hcmute.trietthao.yourtime.mvp.signIn.presenter;

public interface ISignInPresenter {
    void checkLogin(String email, String passw);
    void createNotification(int idUser);
}
