package com.hcmute.trietthao.yourtime.model.modelOffline;

import com.hcmute.trietthao.yourtime.model.CongViecModel;

import java.util.ArrayList;

public class NhomCVMO {
    private Integer idNhom;
    private String tenNhom;
    private boolean laNhomCaNhan;
    private boolean isSync;
    ArrayList<CongViecModel> congViecModels;

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

    public boolean isLaNhomCaNhan() {
        return laNhomCaNhan;
    }

    public void setLaNhomCaNhan(boolean laNhomCaNhan) {
        this.laNhomCaNhan = laNhomCaNhan;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public ArrayList<CongViecModel> getCongViecModels() {
        return congViecModels;
    }

    public void setCongViecModels(ArrayList<CongViecModel> congViecModels) {
        this.congViecModels = congViecModels;
    }
}
