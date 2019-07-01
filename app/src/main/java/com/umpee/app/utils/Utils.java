package com.umpee.app.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.yanzhenjie.nohttp.tools.NetUtil;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by King Jocoa on 10/11/17.
 */

public class Utils {

    @SuppressWarnings("deprecation")
    public static String getIPAddress(Context context) {
        String ip = null;
        try {
            WifiManager wm = (WifiManager) context. getSystemService(WIFI_SERVICE);
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(ip)){
            return NetUtil.getLocalIPAddress();
        }
        return ip;
    }
}
