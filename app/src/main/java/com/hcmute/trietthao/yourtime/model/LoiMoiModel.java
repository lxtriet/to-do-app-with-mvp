package com.hcmute.trietthao.yourtime.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoiMoiModel implements Serializable {
    @SerializedName("idLoiMoi")
    @Expose
    private Integer idLoiMoi;

    @SerializedName("idNguoiMoi")
    @Expose
    private Integer idNguoiMoi;

    @SerializedName("idNguoiDuocMoi")
    @Expose
    private Integer idNguoiDuocMoi;

    @SerializedName("idNhom")
    @Expose
    private Integer idNhom;

    @SerializedName("trangThai")
    @Expose
    private String trangThai;

    public LoiMoiModel(Integer idLoiMoi, Integer idNguoiMoi, Integer idNguoiDuocMoi, Integer idNhom, String trangThai) {
        this.idLoiMoi = idLoiMoi;
        this.idNguoiMoi = idNguoiMoi;
        this.idNguoiDuocMoi = idNguoiDuocMoi;
        this.idNhom = idNhom;
        this.trangThai = trangThai;
    }

    public LoiMoiModel() {

    }

    public Integer getIdLoiMoi() {
        return idLoiMoi;
    }

    public void setIdLoiMoi(Integer idLoiMoi) {
        this.idLoiMoi = idLoiMoi;
    }

    public Integer getIdNguoiMoi() {
        return idNguoiMoi;
    }

    public void setIdNguoiMoi(Integer idNguoiMoi) {
        this.idNguoiMoi = idNguoiMoi;
    }

    public Integer getIdNguoiDuocMoi() {
        return idNguoiDuocMoi;
    }

    public void setIdNguoiDuocMoi(Integer idNguoiDuocMoi) {
        this.idNguoiDuocMoi = idNguoiDuocMoi;
    }

    public Integer getIdNhom() {
        return idNhom;
    }

    public void setIdNhom(Integer idNhom) {
        this.idNhom = idNhom;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
