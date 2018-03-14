package com.hcmute.trietthao.yourtime.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;

import static android.content.Context.NOTIFICATION_SERVICE;

public class BroadcastDone extends BroadcastReceiver implements PostWorkListener {

    DBWorkServer dbWorkServer;

    @Override
    public void onReceive(Context context, Intent intent) {
        String []parts = intent.getStringExtra("DATA").split("@");
        int ID = Integer.parseInt(parts[0]);
        int ID_NOTIFICATION = Integer.parseInt(parts[2]);
        String timeStart = parts[1];
        Log.e("DONE","---"+ID+"---");
        Log.e("DONE","---"+timeStart+"---");

        dbWorkServer = new DBWorkServer(this);
        dbWorkServer.updateStatusWork("done",ID);
        dbWorkServer.updateStatusWorkTimeNotNull("done",ID,
                timeStart);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(ID_NOTIFICATION);

    }

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }
}
