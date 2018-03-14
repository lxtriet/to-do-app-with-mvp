package com.hcmute.trietthao.yourtime.database;

import android.util.Log;

import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.response.InsertGroupWorkResponse;
import com.hcmute.trietthao.yourtime.service.Service;
import com.hcmute.trietthao.yourtime.service.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lxtri on 11/11/2017.
 */

public class DBGroupWorkServer {

    Service mService;
    GetGroupWorkListener getGroupWorkListener;
    PostGroupWorkListener postGroupWorkListener;
//    GetGroupWorkByIDListener getGroupWorkByIDListener;

    public DBGroupWorkServer(GetGroupWorkListener getGroupWorkListener) {
        this.getGroupWorkListener = getGroupWorkListener;
    }
    public DBGroupWorkServer(PostGroupWorkListener postGroupWorkListener ) {
        this.postGroupWorkListener = postGroupWorkListener;
    }
//    public DBGroupWorkServer(GetGroupWorkByIDListener getGroupWorkByIDListener ) {
//        this.getGroupWorkByIDListener = getGroupWorkByIDListener;
//    }
    public DBGroupWorkServer(PostGroupWorkListener postGroupWorkListener, GetGroupWorkListener getGroupWorkListener ) {
        this.postGroupWorkListener = postGroupWorkListener;
        this.getGroupWorkListener = getGroupWorkListener;
    }

    public void insertGroupWork(Integer idnhom,String tennhom, Integer lanhomcanhan){
        mService = ApiUtils.getService();
        Call<InsertGroupWorkResponse> call = mService.insertGroupWork(idnhom,tennhom,lanhomcanhan);
        call.enqueue(new Callback<InsertGroupWorkResponse>() {
            @Override
            public void onResponse(Call<InsertGroupWorkResponse> call, Response<InsertGroupWorkResponse> response) {
                if(response.isSuccessful()) {
                    postGroupWorkListener.getResultPostGroupWork(true);
                    Log.e("Response",""+response.message());
                }
                else
                {
                    postGroupWorkListener.getResultPostGroupWork(false);
                    Log.e("Response. Lỗi: ",response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertGroupWorkResponse> call, Throwable t) {
                Log.e("Response",t.getMessage());
            }
        });
    }
    // Hàm lấy list group work
    public void getListGroupWork(final Integer idnguoidung){
        mService = ApiUtils.getService();
        Call<ArrayList<NhomCVModel>> call = mService.getListGroupWork(idnguoidung);
        Log.e("Response",call.request().url().toString());
        Log.e("idNguoiDung :::::::",idnguoidung.toString());
        call.enqueue(new Callback<ArrayList<NhomCVModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NhomCVModel>> call, Response<ArrayList<NhomCVModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list groupwork thành công"+response.message());
                    getGroupWorkListener.getListGroupWork(response.body());
                }else
                    Log.e("Response","Lấy list groupwork thất bại "+response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<NhomCVModel>> call, Throwable t) {
                Log.e("Response","Lấy list groupwork thất bại "+t.getMessage());
            }
        });
    }
    public void deleteGroupWorkUser(Integer idNhom){
        mService = ApiUtils.getService();
        Call<NhomCVModel> deleteRequest = mService.deleteGroupWork(idNhom);
        deleteRequest.enqueue(new Callback<NhomCVModel>() {
            @Override
            public void onResponse(Call<NhomCVModel> call, Response<NhomCVModel> response) {
                if(response.isSuccessful()) {
                    postGroupWorkListener.getResultPostGroupWork(true);
                    Log.e("Response",""+response.message());
                }
                else
                {
                    postGroupWorkListener.getResultPostGroupWork (false);
                    Log.e("Response. Lỗi: ",response.message());
                }
            }

            @Override
            public void onFailure(Call<NhomCVModel> call, Throwable t) {
                // handle failure
                Log.e("Response",t.getMessage());
            }
        });
    }
    // Hàm lấy list user
//    public void getGroupWorkById(final Integer idnhom){
//        mService = ApiUtils.getService();
//        Call<ArrayList<NhomCVModel>> call = mService.getGroupWorkById(idnhom);
//        Log.e("Response",call.request().url().toString());
//        Log.e("idnhom :::::::",idnhom.toString());
//        call.enqueue(new Callback<ArrayList<NhomCVModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<NhomCVModel>> call, Response<ArrayList<NhomCVModel>> response) {
//                if(response.isSuccessful()){
//                    Log.e("Response","Lấy groupwork thành công"+response.message());
//                    getGroupWorkByIDListener.getGroupWorkById(response.body().get(0));
//                }else
//                    Log.e("Response","Lấy groupwork thất bại ");
//            }
//            @Override
//            public void onFailure(Call<ArrayList<NhomCVModel>> call, Throwable t) {
//                Log.e("Response","Lấy list groupwork thất bại "+t.getMessage());
//            }
//        });
//    }

}
