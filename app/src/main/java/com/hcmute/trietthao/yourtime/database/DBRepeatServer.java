package com.hcmute.trietthao.yourtime.database;

import android.util.Log;

import com.hcmute.trietthao.yourtime.model.LoaiNhacNhoModel;
import com.hcmute.trietthao.yourtime.service.Service;
import com.hcmute.trietthao.yourtime.service.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lxtri on 11/11/2017.
 */

public class DBRepeatServer {

    Service mService;
    GetRepeatListener getRepeatListener;

    public DBRepeatServer(GetRepeatListener getRepeatListener) {
        this.getRepeatListener = getRepeatListener;
    }

    // Hàm lấy list user
    public void getListRepeat(){
        mService = ApiUtils.getService();
        Call<ArrayList<LoaiNhacNhoModel>> call = mService.getTypeRepeat();
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<LoaiNhacNhoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<LoaiNhacNhoModel>> call, Response<ArrayList<LoaiNhacNhoModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list repeat thành công"+response.message());
                    getRepeatListener.getListRepeat(response.body());
                }else
                    Log.e("Response","Lấy list repeat thất bại "+response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<LoaiNhacNhoModel>> call, Throwable t) {
                Log.e("Response","Lấy list repeat thất bại "+t.getMessage());
            }
        });
    }

}
