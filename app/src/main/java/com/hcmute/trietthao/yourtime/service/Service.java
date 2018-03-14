package com.hcmute.trietthao.yourtime.service;

import com.hcmute.trietthao.yourtime.model.CVThongBaoModel;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.LoaiNhacNhoModel;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.model.NhomCVNguoiDungModel;
import com.hcmute.trietthao.yourtime.response.InsertGroupWorkResponse;
import com.hcmute.trietthao.yourtime.response.InsertGroupWorkUserReponse;
import com.hcmute.trietthao.yourtime.response.InsertUpdateWorkNotificationResponse;
import com.hcmute.trietthao.yourtime.response.InsertUpdateWorkResponse;
import com.hcmute.trietthao.yourtime.response.InsertUserResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xuant on 13/05/2017.
 */

public interface Service { //Định nghĩa các REST API (Api Services) cho Retrofit
   // Hàm  get user by email and passw
    @GET("/getuser")
    Call<ArrayList<NguoiDungModel>> getUserByEmail(@Query("username") String username);
    @GET("/getalluser")
    Call<ArrayList<NguoiDungModel>> getListUser();

    @FormUrlEncoded
    @POST("/insertuser")
    Call<InsertUserResponse> insertUser(@Field("idnguoidung") int idnguoidung, @Field("tennguoidung") String tennguoidung, @Field("anhdaidien") String anhdaidien,
                                        @Field("username") String username, @Field("passw") String passw);

    @POST("/insertuser")
    Call<ArrayList<NhomCVModel>> insertWorkGroup(@Field("tennhom") String tennhom, @Field("lanhomcanhan") boolean lanhomcanhan);

    @FormUrlEncoded
    @POST("/insertgroupwork")
    Call<InsertGroupWorkResponse> insertGroupWork(@Field("idnhom") Integer idnhom, @Field("tennhom") String tennhom,
                                                  @Field("lanhomcanhan") Integer lanhomcanhan);

    @GET("/getlistgroupwork")
    Call<ArrayList<NhomCVModel>> getListGroupWork(@Query("idnguoidung") Integer idnguoidung);

    @GET("/getworkbyid")
    Call<ArrayList<CongViecModel>> getWorkById(@Query("idcongviec") Integer idcongviec);

    @GET("/gettyperepeat")
    Call<ArrayList<LoaiNhacNhoModel>> getTypeRepeat();

    @GET("/getlistallwork")
    Call<ArrayList<CongViecModel>> getListAllWork(@Query("idnguoidung") Integer idnguoidung);

    @GET("/getlistallworknotification")
    Call<ArrayList<CVThongBaoModel>> getListAllWorkNotification(@Query("idnguoidung") Integer idnguoidung);

    @GET("/getlistallworknotificationbyidwork")
    Call<ArrayList<CVThongBaoModel>> getListNotificationOfWork(@Query("idcongviec") Integer idcongviec);

    @GET("/getlistworkbyidgroup")
    Call<ArrayList<CongViecModel>> getListWorkByIdGroup(@Query("idnguoidung") Integer idnguoidung,@Query("idnhom") Integer idnhom);

    @GET("/getlistallworksearch")
    Call<ArrayList<CongViecModel>> getListAllWorkSearch(@Query("idnguoidung") Integer idnguoidung,@Query("keysearch") String keysearch);


    @FormUrlEncoded
    @POST("/updatestatuswork")
    Call<InsertUpdateWorkResponse> updateStatusWork(@Field("trangthai") String trangthai,
                                                    @Field("idcongviec") Integer idcongviec);

    @FormUrlEncoded
    @POST("/updatestatusworktimenotnull")
    Call<InsertUpdateWorkResponse> updateStatusWorkTimeNotNull(@Field("trangthai") String trangthai,
                                                                @Field("idcongviec") Integer idcongviec, @Field("thoigianbatdau") String thoigianbatdau);

    @FormUrlEncoded
    @POST("/updateprioritywork")
    Call<InsertUpdateWorkResponse> updatePriorityWork(@Field("couutien") Integer couutien,
                                                       @Field("idcongviec") Integer idcongviec);

    @FormUrlEncoded
    @POST("/updatework")
    Call<InsertUpdateWorkResponse> updateWork(@Field("tencongviec") String tencongviec,
                                                      @Field("thoigianbatdau") String thoigianbatdau,
                                                      @Field("thoigianketthuc") String thoigianketthuc,
                                                      @Field("ghichu") String ghichu,
                                                      @Field("idnhacnho") Integer idnhacnho,
                                                      @Field("idcongviec") Integer idcongviec);

   @FormUrlEncoded
   @POST("/updatefilework")
   Call<InsertUpdateWorkResponse> updateFileWork(@Field("filedinhkem") String filedinhkem,
                                                   @Field("idcongviec") Integer idcongviec);

    @FormUrlEncoded
    @POST("/insertwork")
    Call<InsertUpdateWorkResponse> insertWork(@Field("idcongviec") Integer idcongviec, @Field("tenCongViec") String tenCongViec,
                                                  @Field("thoiGianBatDau") String thoiGianBatDau, @Field("thoiGianKetThuc") String thoiGianKetThuc,
                                                  @Field("ghiChu") String ghiChu, @Field("fileDinhKem") String fileDinhKem,
                                                  @Field("coUuTien") Integer coUuTien, @Field("idNhom") Integer idNhom,
                                                  @Field("idNhacNho") Integer idNhacNho, @Field("idNguoiTaoCV") int idNguoiTaoCV,
                                                  @Field("idNguoiDuocGiaoCV") Integer idNguoiDuocGiaoCV, @Field("trangThai") String trangThai);
    @FormUrlEncoded
    @POST("/insertworknotifycation")
    Call<InsertUpdateWorkNotificationResponse> insertWorkNotification(@Field("idcongviec") Integer idcongviec,
                                                                      @Field("thoiGianBatDau") String thoiGianBatDau, @Field("thoiGianKetThuc") String thoiGianKetThuc,
                                                                      @Field("idNguoiThucHien") Integer idNguoiThucHien, @Field("trangThai") String trangThai);

    @GET("/getgroupbyid")
    Call<ArrayList<NhomCVModel>> getGroupWorkById(@Query("idnhom") Integer idnhom);

    @FormUrlEncoded
    @POST("/insertgroupworkuser")
    Call<InsertGroupWorkUserReponse> insertWorkGroupUser(@Field("idnhom") Integer idnhom, @Field("idnguoidung") Integer idnguoidung, @Field("vaitro") String vaitro);

    @FormUrlEncoded
    @POST("/deletegroupworkuser")
    Call<NhomCVNguoiDungModel> deleteGroupWorkUser(@Field("idnhom") Integer idnhom);

    @FormUrlEncoded
    @POST("/deletegroupwork")
    Call<NhomCVModel> deleteGroupWork(@Field("idnhom") Integer idnhom);

    @FormUrlEncoded
    @POST("/updateprofile")
    Call<InsertUserResponse> updateProfile(@Field("tennguoidung") String tennguoidung,
                                           @Field("username") String username,@Field("passw") String passw,
                                           @Field("idnguoidung") Integer idnguoidung);

    @FormUrlEncoded
    @POST("/deletework")
    Call<CongViecModel> deleteWork(@Field("idcongviec") Integer idcongviec);

    @FormUrlEncoded
    @POST("/deleteworknotification")
    Call<CongViecModel> deleteWorkNotification(@Field("idcongviec") Integer idcongviec);

    @FormUrlEncoded
    @POST("/updateimguser")
    Call<InsertUserResponse> updateImgUser(@Field("anhdaidien") String anhdaidien, @Field("idnguoidung") Integer idnguoidung);

}
