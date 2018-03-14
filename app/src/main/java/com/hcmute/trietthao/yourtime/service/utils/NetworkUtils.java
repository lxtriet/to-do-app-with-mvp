package com.hcmute.trietthao.yourtime.service.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public static boolean isNetWorkConnected(Context context) {
        boolean isConnecting = false;
        if (context == null) {
            return false;
        }

        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            final NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null) {
                isConnecting = info.isConnectedOrConnecting();
            }
        }
        return isConnecting;
    }
}
