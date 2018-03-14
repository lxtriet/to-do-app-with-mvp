package com.hcmute.trietthao.yourtime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NguoiDungModel implements Serializable {

    @SerializedName("idNguoiDung")
    @Expose
    private Integer idNguoiDung;

    @SerializedName("tenNguoiDung")
    @Expose
    private String tenNguoiDung;

    @SerializedName("anhDaiDien")
    @Expose
    private String anhDaiDien;

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("passW")
    @Expose
    private String passW;

    public NguoiDungModel() {

    }

    public NguoiDungModel(Integer idNguoiDung, String tenNguoiDung, String anhDaiDien, String userName, String passW) {
        this.idNguoiDung = idNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.anhDaiDien = anhDaiDien;
        this.userName = userName;
        this.passW = passW;
    }
    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public Integer getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(Integer idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassW() {
        return passW;
    }

    public void setPassW(String passW) {
        this.passW = passW;
    }
}
