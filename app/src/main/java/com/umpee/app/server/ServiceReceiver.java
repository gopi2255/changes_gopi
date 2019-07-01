package com.umpee.app.server;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.umpee.app.AppConfig;

public class ServiceReceiver extends BroadcastReceiver {
    static Intent pushIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        startService(context);
    }
    public static void startService(Context context) {
        if (!isCoreServiceRunning(context)) {
            pushIntent = new Intent(context, CoreService.class);
            pushIntent.setPackage(AppConfig.PACKAGE_NAME);
            context.startService(pushIntent);
        }
    }

    public static void stopService(Context context) {
        if (isCoreServiceRunning(context)) {
            pushIntent = new Intent(context, CoreService.class);
            pushIntent.setPackage(AppConfig.PACKAGE_NAME);
            context.stopService(pushIntent);
        }
    }

    public static boolean isCoreServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (CoreService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}
