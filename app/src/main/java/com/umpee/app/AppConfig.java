package com.umpee.app;


/**
 * Created by King on 2015.12.26..
 */
public class AppConfig {
    private static final boolean DEBUG = true;

    // Versions
    public static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    public static final String VER_STRING = BuildConfig.VERSION_NAME;
    public static final int VER = BuildConfig.VERSION_CODE;

    public static final boolean LOGS = DEBUG;
    public static final boolean NET_LOG = true;

    public static final String APP_FOLDER = "umpee";
    public static final String VIDEO_NAME = "umpire.mp4";
//    public static final String NET_URL = "http://10.1.1.3/umpee/Api/";
    public static final String  NET_URL = "http://okpjoint.com/kely/umpee/Api/";

    public static final int NOTIFY_SIZE = 30 * 1024 * 1024; //30MB
    public static final int STOP_SIZE = 2 * 1024 * 1024; //2MB


}
