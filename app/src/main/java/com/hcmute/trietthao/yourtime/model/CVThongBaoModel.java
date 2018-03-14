package com.hcmute.trietthao.yourtime.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CVThongBaoModel implements Serializable {

    @SerializedName("idCongViec")
    @Expose
    private Integer idCongViec;

    @SerializedName("thoiGianBatDau")
    @Expose
    private String thoiGianBatDau;

    @SerializedName("thoiGianKetThuc")
    @Expose
    private String thoiGianKetThuc;

    @SerializedName("idNguoiThucHien")
    @Expose
    private int idNguoiThucHien;

    @SerializedName("trangThai")
    @Expose
    private String trangThai;



    public CVThongBaoModel() {

    }

    public Integer getIdCongViec() {
        return idCongViec;
    }

    public void setIdCongViec(Integer idCongViec) {
        this.idCongViec = idCongViec;
    }

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public int getIdNguoiThucHien() {
        return idNguoiThucHien;
    }

    public void setIdNguoiThucHien(int idNguoiThucHien) {
        this.idNguoiThucHien = idNguoiThucHien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public CVThongBaoModel(Integer idCongViec, String thoiGianBatDau, String thoiGianKetThuc, int idNguoiThucHien, String trangThai) {

        this.idCongViec = idCongViec;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.idNguoiThucHien = idNguoiThucHien;
        this.trangThai = trangThai;
    }
}
