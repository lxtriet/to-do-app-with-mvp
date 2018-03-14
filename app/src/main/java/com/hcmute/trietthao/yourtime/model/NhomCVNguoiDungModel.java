package com.hcmute.trietthao.yourtime.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NhomCVNguoiDungModel implements Serializable {
    @SerializedName("idNhom")
    @Expose
    private Integer idNhom;

    @SerializedName("idNguoiDung")
    @Expose
    private Integer idNguoiDung;

    @SerializedName("vaiTro")
    @Expose
    private String vaiTro;

    public NhomCVNguoiDungModel(Integer idNhom, Integer idNguoiDung, String vaiTro) {
        this.idNhom = idNhom;
        this.idNguoiDung = idNguoiDung;
        this.vaiTro = vaiTro;
    }

    public NhomCVNguoiDungModel() {

    }

    public Integer getIdNhom() {
        return idNhom;
    }

    public void setIdNhom(Integer idNhom) {
        this.idNhom = idNhom;
    }

    public Integer getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(Integer idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
}
