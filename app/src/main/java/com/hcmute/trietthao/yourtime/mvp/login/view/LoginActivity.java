package com.hcmute.trietthao.yourtime.mvp.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.mvp.login.adapter.CustomPagerAdapter;
import com.hcmute.trietthao.yourtime.mvp.signIn.view.SignInActivity;
import com.hcmute.trietthao.yourtime.mvp.signUp.view.SignUpActivity;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;
import com.hcmute.trietthao.yourtime.service.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    ViewPager mViewPager;
    CustomPagerAdapter mCustomPagerAdapter;
    Timer timer;
    CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 4;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private int LOGIN_REQ = 1,LOGIN_REQ2 = 2;
    public static boolean FROM_FB=false;
    public static boolean FROM_GG=false;

    @Bind(R.id.btn_sign_up)
    Button mBtnSignUp;

    @Bind(R.id.btn_login_fb)
    LoginButton mBtnLoginFB;
    @Bind(R.id.btn_login)
    Button mBtnSignIn;
    @Bind(R.id.txt_login)
    TextView mTxtLoginFB;

    @Bind(R.id.btn_sign_in_google)
    SignInButton mBtnSignInGoogle;
    @Bind(R.id.txt_login_google)
    TextView mTxtLoginGoogle;

    PreferManager preferManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initListener();
        preferManager= new PreferManager(getApplicationContext());
        FROM_FB=false;


        //Google
        // y/c người dùng cung câp tt cho bạn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Kết nối vs gg API client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mBtnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mBtnSignInGoogle.setScopes(gso.getScopeArray());


        // facebook

        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
//                nextActivity(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        mBtnLoginFB.setReadPermissions(Arrays.asList("email"));

        mBtnLoginFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                FROM_FB=true;
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        nextActivity(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, picture, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });



        //slide login
        final int[] mResources = {
                R.drawable.collaborate_login,
                R.drawable.login2

        };
        mCustomPagerAdapter = new CustomPagerAdapter(this, mResources);
        mViewPager = (ViewPager) this.findViewById(R.id.pagerF);
        mViewPager.setAdapter(mCustomPagerAdapter);
        //Xác định thời gian chạy slide ảnh
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mViewPager.post(new Runnable() {

                    @Override
                    public void run() {
                        mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % mResources.length);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Profile profile = Profile.getCurrentProfile();
//        nextActivity(profile);
        hideProgressDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        if (requestCode == RC_SIGN_IN) {
            if(responseCode==RESULT_OK) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
                handleSignInResult(result);
            }
        }

        if(requestCode==LOGIN_REQ){
            if(preferManager.isLoggedIn()) {
                finish();
            }
        }
        if(requestCode==LOGIN_REQ2){
            if( preferManager.isLoggedIn())
                finish();
        }else {
                callbackManager.onActivityResult(requestCode, responseCode, intent);
                finish();
        }
    }

    private void nextActivity(JSONObject object){
        if (FROM_FB) {
            try {
                String id = object.getString("id");
                Intent main = new Intent(LoginActivity.this, SignUpActivity.class);
                try {
                    URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                    Log.i("profile_pic", profile_pic + "");

                    Log.e("Vao start activity:", " ");
                    main.putExtra("imageUrl", profile_pic.toString());


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                if (object.has("first_name"))
                    main.putExtra("name", object.getString("first_name"));
                if (object.has("last_name"))
                    main.putExtra("surname", object.getString("last_name"));
                if (object.has("email")) {
                    main.putExtra("email", object.getString("email"));
                }
                if (!object.has("email")) {
                    main.putExtra("email", object.getString(""));
                }
                startActivity(main);


            } catch (JSONException e) {
                Log.d(TAG, "Error parsing JSON");
            }
        }

    }
    protected void initListener(){
        mBtnSignUp.setOnClickListener(this);
        mBtnSignIn.setOnClickListener(this);
        mBtnLoginFB.setOnClickListener(this);
        mTxtLoginFB.setOnClickListener(this);
        mBtnSignInGoogle.setOnClickListener(this);
        mTxtLoginGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==mTxtLoginFB){
            mBtnLoginFB.performClick();
        }
        if(view==mTxtLoginGoogle){
            mBtnSignInGoogle.performClick();
        }
        switch (view.getId()){
            case R.id.btn_login:
                Intent signIn= new Intent(this, SignInActivity.class);
                startActivityForResult(signIn, LOGIN_REQ);
                break;
            case R.id.btn_sign_up:
                Intent signUp= new Intent(this, SignUpActivity.class);
                startActivityForResult(signUp, LOGIN_REQ2);
                break;
            case R.id.txt_login_google:
                FROM_GG=true;
                if(NetworkUtils.isNetWorkConnected(this))
                {
                    if(!mGoogleApiClient.isConnected())
                        signIn();
                    else
                        signOut();
                }else {
                    Toast.makeText(this, R.string.fail_connect,Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_login_fb:
                FROM_FB=true;
                if(!NetworkUtils.isNetWorkConnected(this))
                {
                    Toast.makeText(this, R.string.fail_connect,Toast.LENGTH_LONG).show();
                }
        }

    }
    //Login gg

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {

            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {

            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }

    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("Vao from gg intent", "");
            if(FROM_GG) {
                try {
                    Intent main = new Intent(LoginActivity.this, SignUpActivity.class);
                    String name, email, gender, dpUrl = "";
                    name = acct.getDisplayName();
                    email = acct.getEmail();
                    dpUrl = acct.getPhotoUrl().toString();
                    main.putExtra("gg_name", name);
                    main.putExtra("gg_email", email);
                    main.putExtra("gg_url", dpUrl);
                    startActivity(main);

                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    private void signIn() {
        Log.e("Vào sign in"," ");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut() {
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

                if(mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Log.d(TAG, "User Logged out");
                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d(TAG, "Google API Client Connection Suspended");
            }
        });
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}
