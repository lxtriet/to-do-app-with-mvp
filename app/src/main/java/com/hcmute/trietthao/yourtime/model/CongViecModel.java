package com.hcmute.trietthao.yourtime.model;


import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class CongViecModel {
    @SerializedName("idCongViec")
    @Expose
    private Integer idCongViec;

    @SerializedName("tenCongViec")
    @Expose
    private String tenCongViec;

    @SerializedName("thoiGianBatDau")
    @Expose
    private String thoiGianBatDau;

    @SerializedName("thoiGianKetThuc")
    @Expose
    private String thoiGianKetThuc;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("fileDinhKem")
    @Expose
    private String fileDinhKem;

    @SerializedName("coUuTien")
    @Expose
    private Integer coUuTien;

    @SerializedName("idNhom")
    @Expose
    private Integer idNhom;

    @SerializedName("idNhacNho")
    @Expose
    private Integer idNhacNho;

    @SerializedName("idNguoiTaoCV")
    @Expose
    private Integer idNguoiTaoCV;

    @SerializedName("idNguoiDuocGiaoCV")
    @Expose
    private Integer idNguoiDuocGiaoCV;

    @SerializedName("trangThai")
    @Expose
    private String trangThai;

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    ArrayList<BinhLuanModel> binhLuanModels;

    public ArrayList<BinhLuanModel> getBinhLuanModels() {
        return binhLuanModels;
    }

    public void setBinhLuanModels(ArrayList<BinhLuanModel> binhLuanModels) {
        this.binhLuanModels = binhLuanModels;
    }

    public CongViecModel() {

    }

    public static Comparator<CongViecModel> SortByPriority = new Comparator<CongViecModel>() {

        public int compare(CongViecModel s1, CongViecModel s2) {
            if (s1.getCoUuTien() > s2.getCoUuTien())
            {
                return -1;
            }
            else if (s1.getCoUuTien() < s2.getCoUuTien())
            {
                return 1;
            }
            return 0;

        }};
    public static Comparator<CongViecModel> SortAscPriority = new Comparator<CongViecModel>() {

        public int compare(CongViecModel s1, CongViecModel s2) {
            if (s1.getCoUuTien() > s2.getCoUuTien())
            {
                return 1;
            }
            else if (s1.getCoUuTien() < s2.getCoUuTien())
            {
                return -1;
            }
            return 0;

        }};
    public static Comparator<CongViecModel> SortAscendingNameWork = new Comparator<CongViecModel>() {

        public int compare(CongViecModel s1, CongViecModel s2) {
            String nameWork1 = s1.getTenCongViec().toUpperCase();
            String nameWork2 = s2.getTenCongViec().toUpperCase();
            //ascending order
            return nameWork1.compareTo(nameWork2);

        }};
    public static Comparator<CongViecModel> SortDescNameWork = new Comparator<CongViecModel>() {

        public int compare(CongViecModel s1, CongViecModel s2) {
            String nameWork1 = s1.getTenCongViec().toUpperCase();
            String nameWork2 = s2.getTenCongViec().toUpperCase();
            //ascending order
            return nameWork2.compareTo(nameWork1);

        }};
    public static Comparator<CongViecModel> SortAscDate = new Comparator<CongViecModel>() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        int compareResult = 0;
        public int compare(CongViecModel s1, CongViecModel s2) {
            try {
                Date arg0Date = dateFormat.parse(s1.getThoiGianBatDau());
                Date arg1Date = dateFormat.parse(s2.getThoiGianBatDau());
                compareResult = arg0Date.compareTo(arg1Date);
            } catch (ParseException e) {
                e.printStackTrace();
                compareResult = s1.getThoiGianBatDau().compareTo(s2.getThoiGianBatDau());
            }
            return compareResult;
        }

    };
    public static Comparator<CongViecModel> SortDescDate = new Comparator<CongViecModel>() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        int compareResult = 0;
        public int compare(CongViecModel s1, CongViecModel s2) {
            try {
                Date arg0Date = dateFormat.parse(s1.getThoiGianBatDau());
                Date arg1Date = dateFormat.parse(s2.getThoiGianBatDau());
                compareResult = arg1Date.compareTo(arg0Date);
            } catch (ParseException e) {
                e.printStackTrace();
                compareResult = s2.getThoiGianBatDau().compareTo(s1.getThoiGianBatDau());
            }
            return compareResult;
        }

    };

    public CongViecModel(Integer idCongViec, String tenCongViec, String thoiGianBatDau,
                         String thoiGianKetThuc, String ghiChu, String fileDinhKem, Integer coUuTien,
                         Integer idNhom, Integer idNhacNho, Integer idNguoiTaoCV, Integer idNguoiDuocGiaoCV) {
        this.idCongViec = idCongViec;
        this.tenCongViec = tenCongViec;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.ghiChu = ghiChu;
        this.fileDinhKem = fileDinhKem;
        this.coUuTien = coUuTien;
        this.idNhom = idNhom;
        this.idNhacNho = idNhacNho;
        this.idNguoiTaoCV = idNguoiTaoCV;
        this.idNguoiDuocGiaoCV = idNguoiDuocGiaoCV;
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

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
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

    public Integer getCoUuTien() {
        return coUuTien;
    }

    public void setCoUuTien(Integer coUuTien) {
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

    public Integer getIdNguoiTaoCV() {
        return idNguoiTaoCV;
    }

    public void setIdNguoiTaoCV(Integer idNguoiTaoCV) {
        this.idNguoiTaoCV = idNguoiTaoCV;
    }

    public Integer getIdNguoiDuocGiaoCV() {
        return idNguoiDuocGiaoCV;
    }

    public void setIdNguoiDuocGiaoCV(Integer idNguoiDuocGiaoCV) {
        this.idNguoiDuocGiaoCV = idNguoiDuocGiaoCV;
    }
}
