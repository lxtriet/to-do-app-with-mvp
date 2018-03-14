package com.hcmute.trietthao.yourtime.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class BinhLuanModel implements Serializable {

    @SerializedName("idBinhLuan")
    @Expose
    private Integer idBinhLuan;

    @SerializedName("idCongViec")
    @Expose
    private Integer idCongViec;

    @SerializedName("idNguoiTao")
    @Expose
    private Integer idNguoiTao;

    @SerializedName("idNguoiNhan")
    @Expose
    private Integer idNguoiNhan;

    @SerializedName("ngayTao")
    @Expose
    private Date ngayTao;

    @SerializedName("noiDung")
    @Expose
    private String noiDung;

    @SerializedName("trangThai")
    @Expose
    private String trangThai;


    public BinhLuanModel() {

    }

    public BinhLuanModel(Integer idBinhLuan, Integer idCongViec, Integer idNguoiTao,
                         Integer idNguoiNhan, Date ngayTao, String noiDung, String trangThai) {
        this.idBinhLuan = idBinhLuan;
        this.idCongViec = idCongViec;
        this.idNguoiTao = idNguoiTao;
        this.idNguoiNhan = idNguoiNhan;
        this.ngayTao = ngayTao;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
    }

    public Integer getIdBinhLuan() {
        return idBinhLuan;
    }

    public void setIdBinhLuan(Integer idBinhLuan) {
        this.idBinhLuan = idBinhLuan;
    }

    public Integer getIdCongViec() {
        return idCongViec;
    }

    public void setIdCongViec(Integer idCongViec) {
        this.idCongViec = idCongViec;
    }

    public Integer getIdNguoiTao() {
        return idNguoiTao;
    }

    public void setIdNguoiTao(Integer idNguoiTao) {
        this.idNguoiTao = idNguoiTao;
    }

    public Integer getIdNguoiNhan() {
        return idNguoiNhan;
    }

    public void setIdNguoiNhan(Integer idNguoiNhan) {
        this.idNguoiNhan = idNguoiNhan;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
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
