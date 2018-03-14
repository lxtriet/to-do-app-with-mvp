package com.hcmute.trietthao.yourtime.mvp.signIn.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.signIn.presenter.SignInPresenter;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;
import com.hcmute.trietthao.yourtime.service.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SignInActivity extends AppCompatActivity implements ISignInView, View.OnClickListener,DBNguoiDungServer.userListener {


    @Bind(R.id.btn_sign_in_s)
    Button mBtnSignIn;
    @Bind(R.id.edit_sign_in_email)
    EditText mEditEmail;
    @Bind(R.id.edit_sign_in_pass)
    EditText mEditPassw;

    PreferManager preferManager;
    DBNguoiDungServer dbNguoiDungServer;
    SignInPresenter signInPresenter;
    String email="",pass="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        preferManager = new PreferManager(getApplicationContext());
        signInPresenter=new SignInPresenter(this,this);
        dbNguoiDungServer= new DBNguoiDungServer(this);

        mBtnSignIn.setOnClickListener(this);


    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void loginSuccess() {
        dbNguoiDungServer.getUser(email); // tren day goi ham. cai chinh thuc. hien la ben duoi' ham no override

    }

    @Override
    public void loginFail() {
        Toast.makeText(this,"Đăng nhập thất bại!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in_s:
                if(!NetworkUtils.isNetWorkConnected(this))
                {
                    Toast.makeText(this, R.string.fail_connect,Toast.LENGTH_LONG).show();
                }else {
                    email = mEditEmail.getText().toString();
                    pass = mEditPassw.getText().toString();
                    if (email.length() > 0 && pass.length() > 0 && isEmailValid(email)) {
                        signInPresenter.checkLogin(email, pass);

                    } else {
                        Toast.makeText(SignInActivity.this, "Bạn cần nhập đầy đủ email và password!", Toast.LENGTH_LONG).show();

                    }
                }
                break;
        }

    }

    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {

    }

    @Override
    public void getResultInsert(Boolean isSuccess) {

    }

    @Override
    public void getUser(NguoiDungModel user) {
        final NguoiDungModel currentUser= user;
        Toast.makeText(this,"Đăng nhập thành công \n đang chuyển hướng !\n", Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.e("email prefer::::",email);
                preferManager.createUserSignInSession(currentUser.getIdNguoiDung(),currentUser.getTenNguoiDung(),currentUser.getUserName());
                signInPresenter.createNotification(currentUser.getIdNguoiDung());
                finish();

            }
        }, 3000);
    }

}
