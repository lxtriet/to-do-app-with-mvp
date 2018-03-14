package com.hcmute.trietthao.yourtime.model.modelOffline;


import java.util.Date;


public class LoaiNhacNhoMO {
    private Integer idNhacNho;
    private String noiDung;
    private Date thoiGianLapLai;
    private boolean isSync;

    public LoaiNhacNhoMO(Integer idNhacNho, String noiDung, Date thoiGianLapLai, boolean isSync) {
        this.idNhacNho = idNhacNho;
        this.noiDung = noiDung;
        this.thoiGianLapLai = thoiGianLapLai;
        this.isSync = isSync;
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

    public Date getThoiGianLapLai() {
        return thoiGianLapLai;
    }

    public void setThoiGianLapLai(Date thoiGianLapLai) {
        this.thoiGianLapLai = thoiGianLapLai;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }
}
