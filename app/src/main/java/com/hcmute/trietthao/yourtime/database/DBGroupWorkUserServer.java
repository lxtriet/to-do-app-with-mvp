package com.hcmute.trietthao.yourtime.database;

import android.util.Log;

import com.hcmute.trietthao.yourtime.model.NhomCVNguoiDungModel;
import com.hcmute.trietthao.yourtime.response.InsertGroupWorkUserReponse;
import com.hcmute.trietthao.yourtime.service.Service;
import com.hcmute.trietthao.yourtime.service.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lttha on 11/28/2017.
 */

public class DBGroupWorkUserServer {
    Service mService;
    PostGroupWorkUserListener postGroupWorkUserListener;
    public DBGroupWorkUserServer(PostGroupWorkUserListener postGroupWork_userListener) {
        this.postGroupWorkUserListener = postGroupWork_userListener;
    }

    public void insertGroupWorkUser(Integer idnhom, Integer idnguoidung,String vaitro){
        mService = ApiUtils.getService();
        Call<InsertGroupWorkUserReponse> call = mService.insertWorkGroupUser(idnhom,idnguoidung,vaitro);
        call.enqueue(new Callback<InsertGroupWorkUserReponse>() {
            @Override
            public void onResponse(Call<InsertGroupWorkUserReponse> call, Response<InsertGroupWorkUserReponse> response) {
                if(response.isSuccessful()) {
                    postGroupWorkUserListener.getResultPostGroupWorkUser(true);
                    Log.e("Response",""+response.message());
                }
                else
                {
                    postGroupWorkUserListener.getResultPostGroupWorkUser(false);
                    Log.e("Response. Lỗi: ",response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertGroupWorkUserReponse> call, Throwable t) {
                Log.e("Response",t.getMessage());
            }
        });
    }
    // Hàm lấy list group work
    public void deleteGroupWorkUser(Integer idNhom){
        mService = ApiUtils.getService();
        Call<NhomCVNguoiDungModel> deleteRequest = mService.deleteGroupWorkUser(idNhom);
        deleteRequest.enqueue(new Callback<NhomCVNguoiDungModel>() {
            @Override
            public void onResponse(Call<NhomCVNguoiDungModel> call, Response<NhomCVNguoiDungModel> response) {
                if(response.isSuccessful()) {
                    postGroupWorkUserListener.getResultPostGroupWorkUser(true);
                    Log.e("Response","deleteGroupWorkUser Thanh cong"+response.message());
                }
                else
                {
                    postGroupWorkUserListener.getResultPostGroupWorkUser(false);
                    Log.e("Response. Lỗi: ",response.message());
                }
            }

            @Override
            public void onFailure(Call<NhomCVNguoiDungModel> call, Throwable t) {
                // handle failure
                Log.e("Response",t.getMessage());
            }
        });
    }
}
