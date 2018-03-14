package com.hcmute.trietthao.yourtime.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NhomCVModel {
    @SerializedName("idNhom")
    @Expose
    private Integer idNhom;

    @SerializedName("tenNhom")
    @Expose
    private String tenNhom;

    @SerializedName("laNhomCaNhan")
    @Expose
    private Integer laNhomCaNhan;

    @SerializedName("soCV")
    @Expose
    private Integer soCV;

    @SerializedName("soCVQuaHan")
    @Expose
    private Integer soCVQuaHan;

    public Integer getSoCV() {
        return soCV;
    }

    public void setSoCV(Integer soCV) {
        this.soCV = soCV;
    }

    public Integer getSoCVQuaHan() {
        return soCVQuaHan;
    }

    public void setSoCVQuaHan(Integer soCVQuaHan) {
        this.soCVQuaHan = soCVQuaHan;
    }
        ArrayList<CongViecModel> congViecModels;
//
//    ArrayList<NhomCVNguoiDungModel> nhomCVNguoiDungModels;
//
//
    public ArrayList<CongViecModel> getCongViecModels() {
        return congViecModels;
    }

    public void setCongViecModels(ArrayList<CongViecModel> congViecModels) {
        this.congViecModels = congViecModels;
    }
//
//    public ArrayList<NhomCVNguoiDungModel> getNhomCVNguoiDungModels() {
//        return nhomCVNguoiDungModels;
//    }
//
//    public void setNhomCVNguoiDungModels(ArrayList<NhomCVNguoiDungModel> nhomCVNguoiDungModels) {
//        this.nhomCVNguoiDungModels = nhomCVNguoiDungModels;
//    }

    public NhomCVModel(Integer idNhom, String tenNhom, Integer laNhomCaNhan) {
        this.idNhom = idNhom;
        this.tenNhom = tenNhom;
        this.laNhomCaNhan = laNhomCaNhan;

    }

    public NhomCVModel() {

    }

    public Integer getIdNhom() {
        return idNhom;
    }

    public void setIdNhom(Integer idNhom) {
        this.idNhom = idNhom;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public Integer getLaNhomCaNhan() {
        return laNhomCaNhan;
    }

    public void setLaNhomCaNhan(Integer laNhomCaNhan) {
        this.laNhomCaNhan = laNhomCaNhan;
    }
}
