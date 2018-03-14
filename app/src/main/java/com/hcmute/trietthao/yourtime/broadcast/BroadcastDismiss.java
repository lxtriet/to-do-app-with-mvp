package com.hcmute.trietthao.yourtime.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

public class BroadcastDismiss extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("dismiss message","--------DISMISS---"+intent.getStringExtra("DATA"));
        String []parts = intent.getStringExtra("DATA").split("@");
        int ID_NOTIFICATION = Integer.parseInt(parts[2]);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(ID_NOTIFICATION);
    }
}
