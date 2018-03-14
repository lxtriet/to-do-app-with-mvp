package com.hcmute.trietthao.yourtime.model.modelOffline;


import java.util.Date;

public class CVThongBaoMO {
    private Integer idCongViec;
    private Date thoiGianBatDau;
    private Date thoiGianKetThuc;
    private Date idNguoiThucHien;
    private String trangThai;
    private boolean isSync;

    public CVThongBaoMO(Integer idCongViec, Date thoiGianBatDau, Date thoiGianKetThuc, Date idNguoiThucHien, String trangThai, boolean isSync) {
        this.idCongViec = idCongViec;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.idNguoiThucHien = idNguoiThucHien;
        this.trangThai = trangThai;
        this.isSync = isSync;
    }

    public Integer getIdCongViec() {
        return idCongViec;
    }

    public void setIdCongViec(Integer idCongViec) {
        this.idCongViec = idCongViec;
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

    public Date getIdNguoiThucHien() {
        return idNguoiThucHien;
    }

    public void setIdNguoiThucHien(Date idNguoiThucHien) {
        this.idNguoiThucHien = idNguoiThucHien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }
}
