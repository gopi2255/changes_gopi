package com.umpee.camerafragment.internal.manager;

import android.content.Context;

import java.io.File;

import com.umpee.camerafragment.configuration.Configuration;
import com.umpee.camerafragment.configuration.ConfigurationProvider;
import com.umpee.camerafragment.internal.manager.listener.CameraCloseListener;
import com.umpee.camerafragment.internal.manager.listener.CameraOpenListener;
import com.umpee.camerafragment.internal.manager.listener.CameraPhotoListener;
import com.umpee.camerafragment.internal.manager.listener.CameraVideoListener;
import com.umpee.camerafragment.internal.utils.Size;
import com.umpee.camerafragment.listeners.CameraFragmentResultListener;

/*
 * Created by memfis on 8/14/16.
 */
public interface CameraManager<CameraId, SurfaceListener> {

    void initializeCameraManager(ConfigurationProvider configurationProvider, Context context);

    void openCamera(CameraId cameraId, CameraOpenListener<CameraId, SurfaceListener> cameraOpenListener);

    void closeCamera(CameraCloseListener<CameraId> cameraCloseListener);

    void setFlashMode(@Configuration.FlashMode int flashMode);

    void takePhoto(File photoFile, CameraPhotoListener cameraPhotoListener, CameraFragmentResultListener callback);

    void startVideoRecord(File videoFile, CameraVideoListener cameraVideoListener);

    Size getPhotoSizeForQuality(@Configuration.MediaQuality int mediaQuality);

    void stopVideoRecord(CameraFragmentResultListener callback);

    void releaseCameraManager();

    CameraId getCurrentCameraId();

    CameraId getFaceFrontCameraId();

    CameraId getFaceBackCameraId();

    int getNumberOfCameras();

    int getFaceFrontCameraOrientation();

    int getFaceBackCameraOrientation();

    boolean isVideoRecording();

    CharSequence[] getVideoQualityOptions();

    CharSequence[] getPhotoQualityOptions();

    void setCameraId(CameraId currentCameraId);
}
