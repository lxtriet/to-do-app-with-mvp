package com.hcmute.trietthao.yourtime.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ThongBaoModel implements Serializable {
    @SerializedName("idThongBao")
    @Expose
    private Integer idThongBao;

    @SerializedName("idNguoiGui")
    @Expose
    private Integer idNguoiGui;

    @SerializedName("idNguoiNhan")
    @Expose
    private Integer idNguoiNhan;

    @SerializedName("noiDung")
    @Expose
    private String noiDung;

    @SerializedName("trangThai")
    @Expose
    private String trangThai;

    public ThongBaoModel(Integer idThongBao, Integer idNguoiGui, Integer idNguoiNhan, String noiDung, String trangThai) {
        this.idThongBao = idThongBao;
        this.idNguoiGui = idNguoiGui;
        this.idNguoiNhan = idNguoiNhan;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
    }

    public ThongBaoModel() {

    }

    public Integer getIdThongBao() {
        return idThongBao;
    }

    public void setIdThongBao(Integer idThongBao) {
        this.idThongBao = idThongBao;
    }

    public Integer getIdNguoiGui() {
        return idNguoiGui;
    }

    public void setIdNguoiGui(Integer idNguoiGui) {
        this.idNguoiGui = idNguoiGui;
    }

    public Integer getIdNguoiNhan() {
        return idNguoiNhan;
    }

    public void setIdNguoiNhan(Integer idNguoiNhan) {
        this.idNguoiNhan = idNguoiNhan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
