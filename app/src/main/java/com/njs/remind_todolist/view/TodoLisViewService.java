package com.njs.remind_todolist.view;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.gun0912.tedpermission.TedPermission;
import com.njs.remind_todolist.R;

public class TodoLisViewService extends Service {
    private ScreenOnBroadCastReceiver screenOnBroadCastReceiver;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        setScreenOnBroadCastReceiver();
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("ch", "todo", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "ch")
                    .setSmallIcon(R.drawable.ic_list_24)
                    .setContentTitle("ReMind-TodoList / 할일 목록")
                    .setContentIntent(pendingIntent)
                    .setContentText("다른앱 위에 그리기 권한 실행 중");
            notificationManager.notify(1, builder.build());
            startForeground(1, builder.build());
        }
        return START_STICKY;
    }

    private void setScreenOnBroadCastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);

        this.screenOnBroadCastReceiver = new ScreenOnBroadCastReceiver();
        registerReceiver(screenOnBroadCastReceiver , intentFilter);
    }

    @Override
    public void onDestroy() {
        if(screenOnBroadCastReceiver != null)
            unregisterReceiver(this.screenOnBroadCastReceiver);
        super.onDestroy();
    }
}
