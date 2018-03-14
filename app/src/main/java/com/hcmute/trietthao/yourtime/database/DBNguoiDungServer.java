package com.hcmute.trietthao.yourtime.database;

import android.util.Log;

import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.response.InsertUserResponse;
import com.hcmute.trietthao.yourtime.service.Service;
import com.hcmute.trietthao.yourtime.service.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBNguoiDungServer {

    private Service mService;
    public  interface userListener{
        void  getListUser(ArrayList<NguoiDungModel> listUser);// Model j vayaj ItemWhere
        void  getResultInsert(Boolean isSuccess);
        void  getUser(NguoiDungModel user);
    }

    userListener userListener;

    public DBNguoiDungServer(userListener userListener ) {
        this.userListener = userListener;
    }


    // Hàm insert user chỗ đăng ký
    public void insertUser(int id, String name, String avatar, String username, String pass){
        mService = ApiUtils.getService();
        Call<InsertUserResponse> call = mService.insertUser(id,name,avatar,username,pass);
        Log.e("Response",call.request().url().toString());
        Log.e("Response id:::::",String.valueOf(id));
        Log.e("Response name:::::",name);
        Log.e("Response anh:::::",avatar);
        Log.e("Response username:::::",username);
        Log.e("Response pass:::::",pass);
        call.enqueue(new Callback<InsertUserResponse>() {
            @Override
            public void onResponse(Call<InsertUserResponse> call, Response<InsertUserResponse> response) {
                if(response.isSuccessful()) {
                    Log.e("Response",""+response.message());
                    userListener.getResultInsert(true);
                }
                else
                {
                    Log.e("Response. Lỗi: ",response.message());
                    userListener.getResultInsert(false);
                }
            }
            @Override
            public void onFailure(Call<InsertUserResponse> call, Throwable t) {
                Log.e("Response",t.getMessage());
            }
        });
    }
    // Hàm lấy list user
    public void getListUser(){
        mService = ApiUtils.getService();
        Call<ArrayList<NguoiDungModel>> call = mService.getListUser();
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<NguoiDungModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDungModel>> call, Response<ArrayList<NguoiDungModel>> response) {
                userListener.getListUser(response.body());
                Log.e("","Lấy list user thành công");
            }

            @Override
            public void onFailure(Call<ArrayList<NguoiDungModel>> call, Throwable t) {
                Log.e("","Lấy list user thất bại "+t.getMessage());
            }
        });
    }

    // ==== get user ==== //
    public void getUser(String mail){
        mService = ApiUtils.getService();
        Call<ArrayList<NguoiDungModel>> call = mService.getUserByEmail(mail);
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<NguoiDungModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDungModel>> call, Response<ArrayList<NguoiDungModel>> response) {
                if(response.isSuccessful()) {
                    if(response.body()!=null && response.body().size()>0){
                        userListener.getUser(response.body().get(0));
                        Log.e("Response","lay duoc "+response.body().size());
                    }
                }
                else
                    Log.e("Response","khong lay duoc");
            }
            @Override
            public void onFailure(Call<ArrayList<NguoiDungModel>> call, Throwable t) {
                Log.e("Response",t.getMessage());
            }
        });
    }

    public void updateProfile(final String tennguoidung, String username,
                              String passw,Integer idnguoidung){
        mService = ApiUtils.getService();
        Call<InsertUserResponse> call = mService.updateProfile(tennguoidung,username,passw,idnguoidung);
        call.enqueue(new Callback<InsertUserResponse>() {
            @Override
            public void onResponse(Call<InsertUserResponse> call, Response<InsertUserResponse> response) {
                if(response.isSuccessful()){
                    userListener.getResultInsert(true);
                    Log.e("Response","Update profile thành công"+response.message());
                }else
                {
                    userListener.getResultInsert(false);
                    Log.e("Response","Update profile thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUserResponse> call, Throwable t) {
                Log.e("Response","Update status work thất bại "+t.getMessage());
            }
        });
    }

    public void updateImgUser(final String anhdaidien,Integer idnguoidung){
        mService = ApiUtils.getService();
        Call<InsertUserResponse> call = mService.updateImgUser(anhdaidien,idnguoidung);
        Log.e("Response anh:::::",anhdaidien);
        call.enqueue(new Callback<InsertUserResponse>() {
            @Override
            public void onResponse(Call<InsertUserResponse> call, Response<InsertUserResponse> response) {
                if(response.isSuccessful()){
                    userListener.getResultInsert(true);
                    Log.e("Response","Update profile thành công"+response.message());
                }else
                {
                    userListener.getResultInsert(false);
                    Log.e("Response","Update profile thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUserResponse> call, Throwable t) {
                Log.e("Response","Update status work thất bại "+t.getMessage());
            }
        });
    }


}