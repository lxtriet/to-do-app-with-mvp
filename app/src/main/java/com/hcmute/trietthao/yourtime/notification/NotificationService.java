package com.hcmute.trietthao.yourtime.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hcmute.trietthao.yourtime.MainActivity;
import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.broadcast.NotificationPublisher;
import com.hcmute.trietthao.yourtime.model.CVThongBaoModel;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.service.utils.DateUtils;

import java.text.ParseException;
import java.util.Calendar;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.converStringToCalendar;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDateTimeToInsertUpdate;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDisplayDate;

public class NotificationService {

    public static void createNotificationStart(CongViecModel congViecModel,
                                            CVThongBaoModel cvThongBaoModel ,Context context) {
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Integer idNotification = 0;
        try {
            idNotification = DateUtils.getIdNotification(cvThongBaoModel.getThoiGianBatDau(),cvThongBaoModel.getIdCongViec());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intentDone = new Intent();
        intentDone.setAction("done_work");
        try {
            intentDone.putExtra("DATA", congViecModel.getIdCongViec()+"@"+
                    getDateTimeToInsertUpdate(cvThongBaoModel.getThoiGianBatDau())+"@"+idNotification);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PendingIntent pendingIntentDone = PendingIntent
                .getBroadcast(context, idNotification, intentDone, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentDismiss = new Intent();
        intentDismiss.setAction("dismiss_work");
        try {
            intentDismiss.putExtra("DATA", congViecModel.getIdCongViec()+"@"+
                    getDateTimeToInsertUpdate(cvThongBaoModel.getThoiGianBatDau())+"@"+idNotification);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PendingIntent pendingIntentDismiss = PendingIntent
                .getBroadcast(context, idNotification, intentDismiss, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra("REQUEST_SCREEN", "NOTIFICATION-"+congViecModel.getIdNhom()+"-"+congViecModel.getIdCongViec());

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, idNotification, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(congViecModel.getTenCongViec())
                .setContentText("End at: "+getDisplayDate(cvThongBaoModel.getThoiGianKetThuc()))
                .setSmallIcon(R.mipmap.ic_yourtime)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .addAction(R.mipmap.ic_launcher, "Done", pendingIntentDone)
                .addAction(R.mipmap.ic_launcher, "Dismiss", pendingIntentDismiss)
                .build();

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID,
                idNotification );

        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, (Parcelable) notification);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, idNotification,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,5);
        try {
            calendar = converStringToCalendar(cvThongBaoModel.getThoiGianBatDau());
            calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)-1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);
        Log.e("CREATED","---NOTIFICATION HAVE BEEN CREATED AT--"+
                getDisplayDate(cvThongBaoModel.getThoiGianBatDau()));
    }

    public static void createNotificationEnd(CongViecModel congViecModel,
                                               CVThongBaoModel cvThongBaoModel ,Context context) {
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Integer idNotification = 0;
        try {
            idNotification = DateUtils.getIdNotification(cvThongBaoModel.getThoiGianKetThuc(),cvThongBaoModel.getIdCongViec());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra("REQUEST_SCREEN", "NOTIFICATION-"+congViecModel.getIdNhom()+"-"+congViecModel.getIdCongViec());

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, idNotification, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(congViecModel.getTenCongViec())
                .setContentText("This work has expired")
                .setSmallIcon(R.mipmap.ic_yourtime)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .build();

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID,
                idNotification );

        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, (Parcelable) notification);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, idNotification,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,5);
        try {
            calendar = converStringToCalendar(cvThongBaoModel.getThoiGianKetThuc());
            calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)-1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);
        Log.e("CREATED","---NOTIFICATION END HAVE BEEN CREATED AT--"+
                getDisplayDate(cvThongBaoModel.getThoiGianKetThuc()));
    }

    public static void createNotificationStart(CongViecModel congViecModel,
                                               Calendar timeStart, Calendar timeEnd,Context context) {
        Log.e("CREATED","---NOTIFICATION HAVE BEEN CREATED AT--"
                +timeStart.get(Calendar.HOUR)+":"+timeStart.get(Calendar.MINUTE)+", "+
                (timeStart.get(Calendar.DATE))+"-"+timeStart.get(Calendar.MONTH));
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Integer idNotification = 0;
        try {
            idNotification = DateUtils.getIdNotification(timeStart,congViecModel.getIdCongViec());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intentDone = new Intent();
        intentDone.setAction("done_work");
        try {
            intentDone.putExtra("DATA", congViecModel.getIdCongViec()+"@"+
                    getDateTimeToInsertUpdate(timeStart)+"@"+idNotification);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PendingIntent pendingIntentDone = PendingIntent
                .getBroadcast(context, idNotification, intentDone, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentDismiss = new Intent();
        intentDismiss.setAction("dismiss_work");
        try {
            intentDismiss.putExtra("DATA", congViecModel.getIdCongViec()+"@"+
                    getDateTimeToInsertUpdate(timeStart)+"@"+idNotification);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PendingIntent pendingIntentDismiss = PendingIntent
                .getBroadcast(context, idNotification, intentDismiss, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra("REQUEST_SCREEN", "NOTIFICATION-"+congViecModel.getIdNhom()+"-"+congViecModel.getIdCongViec());

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, idNotification, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(congViecModel.getTenCongViec())
                .setContentText("End at: "+timeEnd.get(Calendar.HOUR)+":"+timeEnd.get(Calendar.MINUTE)+", "+
                        timeEnd.get(Calendar.DATE)+"/"+(timeEnd.get(Calendar.MONTH)+1)+"/2018")
                .setSmallIcon(R.mipmap.ic_yourtime)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .addAction(R.mipmap.ic_launcher, "Done", pendingIntentDone)
                .addAction(R.mipmap.ic_launcher, "Dismiss", pendingIntentDismiss)
                .build();

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID,
                idNotification );

        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, (Parcelable) notification);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, idNotification,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,5);
        calendar = timeStart;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);
    }

    public static void createNotificationEnd(CongViecModel congViecModel,
                                               Calendar timeStart, Calendar timeEnd,Context context) {
        Log.e("CREATED","---NOTIFICATION END HAVE BEEN CREATED AT--"
                +timeEnd.get(Calendar.HOUR)+":"+timeEnd.get(Calendar.MINUTE)+", "+
                (timeEnd.get(Calendar.DATE))+"-"+timeEnd.get(Calendar.MONTH));
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Integer idNotification = 0;
        try {
            idNotification = DateUtils.getIdNotification(timeEnd,congViecModel.getIdCongViec());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra("REQUEST_SCREEN", "NOTIFICATION-"+congViecModel.getIdNhom()+"-"+congViecModel.getIdCongViec());

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, idNotification, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(congViecModel.getTenCongViec())
                .setContentText("This work has expired")
                .setSmallIcon(R.mipmap.ic_yourtime)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .build();

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID,
                idNotification );

        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, (Parcelable) notification);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, idNotification,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,5);
        calendar = timeEnd;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);
    }



    public static void cancelNotification(int id ,Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(context,
                NotificationPublisher.class);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(
                context, id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent3);
    }
}
