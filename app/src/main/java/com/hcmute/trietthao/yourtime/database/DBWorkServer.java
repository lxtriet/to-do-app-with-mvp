package com.hcmute.trietthao.yourtime.database;

import android.util.Log;

import com.hcmute.trietthao.yourtime.model.CVThongBaoModel;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.response.InsertUpdateWorkNotificationResponse;
import com.hcmute.trietthao.yourtime.response.InsertUpdateWorkResponse;
import com.hcmute.trietthao.yourtime.service.Service;
import com.hcmute.trietthao.yourtime.service.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lxtri on 11/11/2017.
 */

public class DBWorkServer {

    Service mService;
    GetWorkListener getWorkListener;
    PostWorkListener postWorkListener;
    GetWorkNotificationListener getWorkNotificationListener;

    public DBWorkServer(GetWorkListener getWorkListener) {
        this.getWorkListener = getWorkListener;
    }
    public DBWorkServer(GetWorkNotificationListener getWorkNotificationListener) {
        this.getWorkNotificationListener = getWorkNotificationListener;
    }
    public DBWorkServer(PostWorkListener postWorkListener ) {
        this.postWorkListener = postWorkListener;
    }
    public DBWorkServer(PostWorkListener postWorkListener,GetWorkListener getWorkListener ) {
            this.postWorkListener = postWorkListener;
            this.getWorkListener = getWorkListener;
        }

    public DBWorkServer(PostWorkListener postWorkListener,GetWorkListener getWorkListener,
                        GetWorkNotificationListener getWorkNotificationListener ) {
        this.postWorkListener = postWorkListener;
        this.getWorkListener = getWorkListener;
        this.getWorkNotificationListener = getWorkNotificationListener;
    }

    // Hàm lấy list user
    public void getListAllWork(final Integer idnguoidung){
        mService = ApiUtils.getService();
        Call<ArrayList<CongViecModel>> call = mService.getListAllWork(idnguoidung);
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<CongViecModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CongViecModel>> call, Response<ArrayList<CongViecModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list work thành công"+response.message());
                    getWorkListener.getListAllWork(response.body());
                }else
                    Log.e("Response","Lấy list work thất bại "+response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<CongViecModel>> call, Throwable t) {
                Log.e("Response","Lấy list work thất bại "+t.getMessage());
            }
        });
    }

    public void getListAllWorkNotification(final Integer idnguoidung){
        mService = ApiUtils.getService();
        Call<ArrayList<CVThongBaoModel>> call = mService.getListAllWorkNotification(idnguoidung);
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<CVThongBaoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CVThongBaoModel>> call, Response<ArrayList<CVThongBaoModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list work notification thành công"+response.message());
                    getWorkNotificationListener.getAllWorkNotification(response.body());
                }else
                    Log.e("Response","Lấy list work notification thất bại "+response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<CVThongBaoModel>> call, Throwable t) {
                Log.e("Response","Lấy list work thất bại "+t.getMessage());
            }
        });
    }

    public void getListNotificationOfWork(final Integer idWork){
        mService = ApiUtils.getService();
        Call<ArrayList<CVThongBaoModel>> call = mService.getListNotificationOfWork(idWork);
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<CVThongBaoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CVThongBaoModel>> call, Response<ArrayList<CVThongBaoModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list work notification by id thành công"+response.message());
                    getWorkNotificationListener.getAllWorkNotification(response.body());
                }else
                    Log.e("Response","Lấy list work notification by id thất bại "+response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<CVThongBaoModel>> call, Throwable t) {
                Log.e("Response","Lấy list work by id thất bại "+t.getMessage());
            }
        });
    }

    public void getWorkById(final Integer idcongviec){
        mService = ApiUtils.getService();
        Call<ArrayList<CongViecModel>> call = mService.getWorkById(idcongviec);
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<CongViecModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CongViecModel>> call, Response<ArrayList<CongViecModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy  work by id thành công"+response.message());
                    getWorkListener.getListAllWork(response.body());
                }else
                    Log.e("Response","Lấy  work by id thất bại "+response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<CongViecModel>> call, Throwable t) {
                Log.e("Response","Lấy  work by id thất bại "+t.getMessage());
            }
        });
    }

    public void getListWorkByIdGroup( Integer idnguoidung,final Integer idnhom){
        mService = ApiUtils.getService();
        Call<ArrayList<CongViecModel>> call = mService.getListWorkByIdGroup(idnguoidung,idnhom);
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<CongViecModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CongViecModel>> call, Response<ArrayList<CongViecModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list work thành công"+response.message());
                    getWorkListener.getListAllWork(response.body());
                }else
                    Log.e("Response","Lấy list work thất bại "+response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<CongViecModel>> call, Throwable t) {
                Log.e("Response","Lấy list work thất bại "+t.getMessage());
            }
        });
    }

    public void getListAllWorkSearch(final Integer idnguoidung,String keysearch){
        mService = ApiUtils.getService();
        Call<ArrayList<CongViecModel>> call = mService.getListAllWorkSearch(idnguoidung,keysearch);
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<CongViecModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CongViecModel>> call, Response<ArrayList<CongViecModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list work search thành công"+response.message());
                    getWorkListener.getListAllWork(response.body());
                }else
                    Log.e("Response","Lấy list work search thất bại "+response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<CongViecModel>> call, Throwable t) {
                Log.e("Response","Lấy list work search thất bại "+t.getMessage());
            }
        });
    }

    public void updateStatusWorkTimeNotNull(final String trangthai,Integer idcongviec,String thoigianbatdau){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkResponse> call = mService.updateStatusWorkTimeNotNull(trangthai,idcongviec,thoigianbatdau);
        call.enqueue(new Callback<InsertUpdateWorkResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkResponse> call, Response<InsertUpdateWorkResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Update status work time not null thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Update status work time not null thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkResponse> call, Throwable t) {
                Log.e("Response","Update status work time not null thất bại "+t.getMessage());
            }
        });
    }

    public void updateStatusWork(final String trangthai,Integer idcongviec){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkResponse> call = mService.updateStatusWork(trangthai,idcongviec);
        call.enqueue(new Callback<InsertUpdateWorkResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkResponse> call, Response<InsertUpdateWorkResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Update status work thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Update status work thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkResponse> call, Throwable t) {
                Log.e("Response","Update status work thất bại "+t.getMessage());
            }
        });
    }

    public void updatePriorityWork(final Integer couutien,Integer idcongviec){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkResponse> call = mService.updatePriorityWork(couutien,idcongviec);
        call.enqueue(new Callback<InsertUpdateWorkResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkResponse> call, Response<InsertUpdateWorkResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Update priority work thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Update priority work thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkResponse> call, Throwable t) {
                Log.e("Response","Update priority work thất bại "+t.getMessage());
            }
        });
    }

    public void updateWork(CongViecModel congViecModel){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkResponse> call = mService.updateWork(congViecModel.getTenCongViec(),
                congViecModel.getThoiGianBatDau(),congViecModel.getThoiGianKetThuc(),
                congViecModel.getGhiChu(),congViecModel.getIdNhacNho(),congViecModel.getIdCongViec());
        call.enqueue(new Callback<InsertUpdateWorkResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkResponse> call, Response<InsertUpdateWorkResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Update  work thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Update  work thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkResponse> call, Throwable t) {
                Log.e("Response","Update  work thất bại "+t.getMessage());
            }
        });
    }

    public void updateFileWork(String fileDinhKem, Integer idcongviec){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkResponse> call = mService.updateFileWork(fileDinhKem,idcongviec);
        call.enqueue(new Callback<InsertUpdateWorkResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkResponse> call, Response<InsertUpdateWorkResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Update file work thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Update file work thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkResponse> call, Throwable t) {
                Log.e("Response","Update file work thất bại "+t.getMessage());
            }
        });
    }

    public void insertWork(CongViecModel congViecModel){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkResponse> call = mService.insertWork(congViecModel.getIdCongViec(),congViecModel.getTenCongViec(),
                congViecModel.getThoiGianBatDau(),congViecModel.getThoiGianKetThuc(),congViecModel.getGhiChu(),
                congViecModel.getFileDinhKem(),congViecModel.getCoUuTien(),congViecModel.getIdNhom(),
                congViecModel.getIdNhacNho(),congViecModel.getIdNguoiTaoCV(),
                congViecModel.getIdNguoiDuocGiaoCV(),congViecModel.getTrangThai());
        call.enqueue(new Callback<InsertUpdateWorkResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkResponse> call, Response<InsertUpdateWorkResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Insert work thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Insert  work thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkResponse> call, Throwable t) {
                Log.e("Response","Insert  work thất bại "+t.getMessage());
            }
        });
    }

    public void insertWorkNotification(Integer idcongviec,
                                       String thoiGianBatDau,String thoiGianKetThuc,
                                       Integer idNguoiThucHien,String trangThai){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkNotificationResponse> call = mService.insertWorkNotification(idcongviec,thoiGianBatDau,
                thoiGianKetThuc,idNguoiThucHien,trangThai);
        call.enqueue(new Callback<InsertUpdateWorkNotificationResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkNotificationResponse> call, Response<InsertUpdateWorkNotificationResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Insert work notification thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Insert  work notification thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkNotificationResponse> call, Throwable t) {
                Log.e("Response","Insert work notification thất bại "+t.getMessage());
            }
        });
    }

    public void deleteWork(final Integer idcongviec){
        mService = ApiUtils.getService();
        Call<CongViecModel> call = mService.deleteWork(idcongviec);
        call.enqueue(new Callback<CongViecModel>() {
            @Override
            public void onResponse(Call<CongViecModel> call, Response<CongViecModel> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Update status work thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Update status work thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<CongViecModel> call, Throwable t) {
                Log.e("Response","Update status work thất bại "+t.getMessage());
            }
        });
    }

    public void deleteWorkNotification(final Integer idcongviec){
        mService = ApiUtils.getService();
        Call<CongViecModel> call = mService.deleteWorkNotification(idcongviec);
        call.enqueue(new Callback<CongViecModel>() {
            @Override
            public void onResponse(Call<CongViecModel> call, Response<CongViecModel> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Delete work notification thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Delete work notification thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<CongViecModel> call, Throwable t) {
                Log.e("Response","Update status work thất bại "+t.getMessage());
            }
        });
    }


}
