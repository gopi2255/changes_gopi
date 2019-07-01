package com.umpee.camerafragment.internal.manager.listener;

import java.io.File;

import com.umpee.camerafragment.internal.utils.Size;
import com.umpee.camerafragment.listeners.CameraFragmentResultListener;

/*
 * Created by memfis on 8/14/16.
 */
public interface CameraVideoListener {
    void onVideoRecordStarted(Size videoSize);

    void onVideoRecordStopped(File videoFile, CameraFragmentResultListener callback);

    void onVideoRecordError();
}
