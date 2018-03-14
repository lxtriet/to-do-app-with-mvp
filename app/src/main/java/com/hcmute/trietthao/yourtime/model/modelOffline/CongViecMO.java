package com.hcmute.trietthao.yourtime.model.modelOffline;


import com.hcmute.trietthao.yourtime.model.BinhLuanModel;

import java.util.ArrayList;
import java.util.Date;

public class CongViecMO {
    private Integer idCongViec;

    private String tenCongViec;
    private Date thoiGianBatDau;
    private Date thoiGianKetThuc;
    private String ghiChu;
    private String fileDinhKem;
    private boolean coUuTien;
    private Integer idNhom;
    private Integer idNhacNho;
    private boolean isSync;

    ArrayList<BinhLuanModel> binhLuanModels;

    public CongViecMO(Integer idCongViec, String tenCongViec, Date thoiGianBatDau, Date thoiGianKetThuc, String ghiChu, String fileDinhKem, boolean coUuTien, Integer idNhom, Integer idNhacNho, boolean isSync, ArrayList<BinhLuanModel> binhLuanModels) {
        this.idCongViec = idCongViec;
        this.tenCongViec = tenCongViec;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.ghiChu = ghiChu;
        this.fileDinhKem = fileDinhKem;
        this.coUuTien = coUuTien;
        this.idNhom = idNhom;
        this.idNhacNho = idNhacNho;
        this.isSync = isSync;
        this.binhLuanModels = binhLuanModels;
    }

    public Integer getIdCongViec() {
        return idCongViec;
    }

    public void setIdCongViec(Integer idCongViec) {
        this.idCongViec = idCongViec;
    }

    public String getTenCongViec() {
        return tenCongViec;
    }

    public void setTenCongViec(String tenCongViec) {
        this.tenCongViec = tenCongViec;
    }

    public Date getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Date thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Date getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Date thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getFileDinhKem() {
        return fileDinhKem;
    }

    public void setFileDinhKem(String fileDinhKem) {
        this.fileDinhKem = fileDinhKem;
    }

    public boolean isCoUuTien() {
        return coUuTien;
    }

    public void setCoUuTien(boolean coUuTien) {
        this.coUuTien = coUuTien;
    }

    public Integer getIdNhom() {
        return idNhom;
    }

    public void setIdNhom(Integer idNhom) {
        this.idNhom = idNhom;
    }

    public Integer getIdNhacNho() {
        return idNhacNho;
    }

    public void setIdNhacNho(Integer idNhacNho) {
        this.idNhacNho = idNhacNho;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public ArrayList<BinhLuanModel> getBinhLuanModels() {
        return binhLuanModels;
    }

    public void setBinhLuanModels(ArrayList<BinhLuanModel> binhLuanModels) {
        this.binhLuanModels = binhLuanModels;
    }
}
