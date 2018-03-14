package com.hcmute.trietthao.yourtime.mvp.accountDetails.view;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.imageProcessing.ConvertBitmap;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.accountDetails.presenter.AccounDetailsPresenter;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;
import com.hcmute.trietthao.yourtime.mvp.settingFragment.view.Utility;
import com.hcmute.trietthao.yourtime.service.utils.Base64Utils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountDetailsActivity extends AppCompatActivity implements  IAccountDetailsView,  View.OnClickListener, DBNguoiDungServer.userListener {


    @Bind(R.id.ln_take_photo)
    LinearLayout mLnTakePhoto;
    @Bind(R.id.imgv_ava_details)
    CircleImageView mImgPhoto;
    @Bind(R.id.ln_change_name)
    LinearLayout mLnChangeName;
    @Bind(R.id.ln_change_pass)
    LinearLayout mLnChangePass;
    @Bind(R.id.ln_change_email)
    LinearLayout mLnChangeEmail;
    @Bind(R.id.imgv_back)
    ImageView mImgvBack;
    @Bind(R.id.txt_details_name)
    TextView mTxtDetailsName;
    @Bind(R.id.txt_details_email)
    TextView mTxtDetailsEmail;
    @Bind(R.id.txt_details_save)
    TextView mTxtDetailsSave;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    final Context c = this;
    DBNguoiDungServer dbNguoiDungServer;
    PreferManager preferManager;
    NguoiDungModel currentUser=null;
    String passwChange;
    String tenNguoiDung="",userName="";
    AccounDetailsPresenter mAccounDetailsPresenter;
    String encodedString="";
    int idUser;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        ButterKnife.bind(this);
        preferManager= new PreferManager(getApplicationContext());
        dbNguoiDungServer=new DBNguoiDungServer(this);
        mAccounDetailsPresenter= new AccounDetailsPresenter(this);

        HashMap<String, String> user = preferManager.getUserDetails();
        dbNguoiDungServer.getUser(user.get(PreferManager.KEY_EMAIL));
        dbNguoiDungServer.getListUser();
//        mTxtDetailsSave.setVisibility(View.GONE);

        mImgvBack.setOnClickListener(this);
        mLnTakePhoto.setOnClickListener(this);
        mLnChangeName.setOnClickListener(this);
        mLnChangeEmail.setOnClickListener(this);
        mLnChangePass.setOnClickListener(this);
        mTxtDetailsSave.setOnClickListener(this);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {

                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AccountDetailsActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(AccountDetailsActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE)
            onSelectFromGalleryResult(data);
        else if (requestCode == REQUEST_CAMERA)
            onCaptureImageResult(data);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Base64Utils myBitMap = new Base64Utils(this);
        mImgPhoto.setImageBitmap(thumbnail);
        encodedString = myBitMap.getStringFromBitmap(thumbnail);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        ConvertBitmap myBitMap = new ConvertBitmap(this);
        Bitmap bitmap = null;
        try {
            bitmap = myBitMap.decodeUri(data.getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mImgPhoto.setImageBitmap(bitmap);
        encodedString = myBitMap.getStringFromBitmap(bitmap);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_back:
                finish();
                break;
            case R.id.ln_take_photo:
                selectImage();
                break;
            case R.id.ln_change_name:
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_change_name, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText valueView = (EditText) mView.findViewById(R.id.userInputDialog);

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                tenNguoiDung = valueView.getText().toString();
                                mTxtDetailsName.setText(tenNguoiDung);
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
                break;
            case R.id.ln_change_email:
                LayoutInflater layoutInflaterEmail = LayoutInflater.from(c);
                View  mViewEmail = layoutInflaterEmail.inflate(R.layout.dialog_change_email, null);
                AlertDialog.Builder alertDialogBuilderEmail = new AlertDialog.Builder(c);
                alertDialogBuilderEmail.setView(mViewEmail);
                final EditText editEmailDialog = (EditText) mViewEmail.findViewById(R.id.input_dialog_email);
                final EditText editPassDialog = (EditText) mViewEmail.findViewById(R.id.input_dialog_curentpass);
                Log.e("mật khẩu hiện tại::::",currentUser.getPassW());
                alertDialogBuilderEmail
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                if(currentUser.getPassW().equals(editPassDialog.getText().toString())){
                                    userName=editEmailDialog.getText().toString();
                                    mTxtDetailsEmail.setText(userName);
                                }else {
                                    Toast.makeText(AccountDetailsActivity.this, "Wrong password. Please enter!",Toast.LENGTH_LONG).show();
                                }

                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });
                AlertDialog alertDialogEmail = alertDialogBuilderEmail.create();
                alertDialogEmail.show();
                break;
            case R.id.ln_change_pass:
                LayoutInflater layoutInflaterPass = LayoutInflater.from(c);
                View mViewPass = layoutInflaterPass.inflate(R.layout.dialog_password, null);
                AlertDialog.Builder alertDialogBuilderPass = new AlertDialog.Builder(c);
                alertDialogBuilderPass.setView(mViewPass);
                final EditText editCurrentpass = (EditText) mViewPass.findViewById(R.id.input_curent_pass);
                final EditText editNewPass = (EditText) mViewPass.findViewById(R.id.input_new_pass);
                final EditText editConfirmPass = (EditText) mViewPass.findViewById(R.id.input_confirm_pass);
                alertDialogBuilderPass
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                if(currentUser.getPassW().equals(editCurrentpass.getText().toString())){
                                    if(editNewPass.getText().toString().equals(editConfirmPass.getText().toString())){
                                        passwChange=editNewPass.getText().toString();
                                    }else
                                        Toast.makeText(AccountDetailsActivity.this, "Confirm password do not match!",Toast.LENGTH_LONG).show();
                                }else
                                    Toast.makeText(AccountDetailsActivity.this, "Your current password incorrect!",Toast.LENGTH_LONG).show();
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });
                AlertDialog alertDialogPass = alertDialogBuilderPass.create();
                alertDialogPass.show();
                break;
            case R.id.txt_details_save:
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccountDetailsActivity.this);
                alertDialogBuilder.setMessage("Save the changes!");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                Log.e("acountdetails:","idng::"+preferManager.getID());
                                Log.e("Code:",""+encodedString);
                                mAccounDetailsPresenter.updateProfileUser(tenNguoiDung,encodedString, userName,passwChange,preferManager.getID());

                            }
                        })

                        .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();}
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;



        }

    }

    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {
        currentUser=listUser.get(0);

    }

    @Override
    public void getResultInsert(Boolean isSuccess) {

    }

    @Override
    public void getUser(NguoiDungModel user) {
        currentUser=user;
        String url_imgitem="https://tlcn-yourtime.herokuapp.com/getimg?nameimg=";
        if(currentUser.getAnhDaiDien()!=null)
        {
            Picasso.with(this).load(url_imgitem+currentUser.getAnhDaiDien()+".png")
                    .error(R.drawable.null_avatar)
                    .into(mImgPhoto);
        }
        mTxtDetailsName.setText(currentUser.getTenNguoiDung());
        mTxtDetailsEmail.setText(currentUser.getUserName());

        tenNguoiDung=currentUser.getTenNguoiDung();
        userName=currentUser.getUserName();
        passwChange=currentUser.getPassW();


    }
}
