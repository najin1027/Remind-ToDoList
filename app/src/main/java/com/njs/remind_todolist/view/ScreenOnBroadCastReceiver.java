package com.njs.remind_todolist.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.gun0912.tedpermission.TedPermission;

public class ScreenOnBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_OFF)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (TedPermission.isGranted(context, Manifest.permission.SYSTEM_ALERT_WINDOW)) {
                    Intent mIntent = new Intent(context, MainActivity.class);

                    mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mIntent);
                }
            } else {
                Intent mIntent = new Intent(context, MainActivity.class);

                mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mIntent);
            }
        }

        if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent mIntent = new Intent(context, TodoLisViewService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                context.startForegroundService(mIntent);
            else
                context.startService(mIntent);
        }

    }
}
