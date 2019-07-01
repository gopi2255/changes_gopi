package com.umpee.app.model;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.umpee.app.AppConfig;
import com.umpee.app.cache.Cache;
import com.umpee.app.utils.FileUtils;
import com.yanzhenjie.nohttp.tools.NetUtil;

import java.io.File;

public class ModelVideo extends ModelBase {
    public String url;
    public String startTime;
    public String endTime;
    public int duration;

    private static String KEY = AppConfig.VER + "AppVideo";

    private ModelVideo() {
        url = getVideoFile();
    }

    public void save() {
        Cache.getInstance().putObject(KEY, this);
    }

    public static ModelVideo build() {
        try {
            ModelVideo data = (ModelVideo) Cache.getInstance().getObject(KEY, ModelVideo.class);
            if (data == null) data = new ModelVideo();
            return data;
        } catch (Exception e) {
            return new ModelVideo();
        }
    }

    public boolean checkVideo() {
        return !TextUtils.isEmpty(url) && FileUtils.isFileExist(url);
    }

    private static String getVideoFile() {
        return FileUtils.getAppDir() + File.separator + AppConfig.VIDEO_NAME;
    }

    public Bitmap getThumb() {
        if (checkVideo())
            return ThumbnailUtils.createVideoThumbnail(url, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
        else return null;
    }

    public String getPublicUrl() {
        return "http://" +  NetUtil.getLocalIPAddress() + ":8081/" + AppConfig.VIDEO_NAME;
    }
}
