package com.hcmute.trietthao.yourtime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoaiNhacNhoModel implements Serializable {
    @SerializedName("idNhacNho")
    @Expose
    private Integer idNhacNho;

    @SerializedName("noiDung")
    @Expose
    private String noiDung;

    @SerializedName("thoiGianLapLai")
    @Expose
    private Integer thoiGianLapLai;

    @SerializedName("loaiLapLai")
    @Expose
    private String loaiLapLai;



    public LoaiNhacNhoModel() {

    }

    public LoaiNhacNhoModel(Integer idNhacNho, String noiDung, Integer thoiGianLapLai, String loaiLapLai) {
        this.idNhacNho = idNhacNho;
        this.noiDung = noiDung;
        this.thoiGianLapLai = thoiGianLapLai;
        this.loaiLapLai = loaiLapLai;
    }

    public Integer getIdNhacNho() {
        return idNhacNho;
    }

    public void setIdNhacNho(Integer idNhacNho) {
        this.idNhacNho = idNhacNho;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Integer getThoiGianLapLai() {
        return thoiGianLapLai;
    }

    public void setThoiGianLapLai(Integer thoiGianLapLai) {
        this.thoiGianLapLai = thoiGianLapLai;
    }

    public String getLoaiLapLai() {
        return loaiLapLai;
    }

    public void setLoaiLapLai(String loaiLapLai) {
        this.loaiLapLai = loaiLapLai;
    }
}
