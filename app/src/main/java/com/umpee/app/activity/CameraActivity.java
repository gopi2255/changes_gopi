package com.umpee.app.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umpee.app.AppConfig;
import com.umpee.app.R;
import com.umpee.app.cache.Cache;
import com.umpee.app.custom.CustomActivity;
import com.umpee.app.custom.MyToast;
import com.umpee.app.utils.DateTimeUtils;
import com.umpee.app.utils.FileUtils;
import com.umpee.app.utils.SoundPooler;
import com.umpee.camerafragment.CameraFragment;
import com.umpee.camerafragment.CameraFragmentApi;
import com.umpee.camerafragment.configuration.Configuration;
import com.umpee.camerafragment.listeners.CameraFragmentControlsAdapter;
import com.umpee.camerafragment.listeners.CameraFragmentResultAdapter;
import com.umpee.camerafragment.listeners.CameraFragmentStateAdapter;
import com.umpee.camerafragment.listeners.CameraFragmentVideoRecordTextAdapter;
import com.umpee.camerafragment.widgets.CameraSettingsView;
import com.umpee.camerafragment.widgets.CameraSwitchView;
import com.umpee.camerafragment.widgets.FlashSwitchView;
import com.umpee.camerafragment.widgets.RecordButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends CustomActivity implements View.OnClickListener {
    private static final String TAG = "CameraActivity";
    public static final String FRAGMENT_TAG = "camera";
    private static final int REQUEST_CAMERA_PERMISSIONS = 931;

    protected FrameLayout content;
    protected CameraSettingsView settingsView;
    protected FlashSwitchView flashSwitchView;
    protected CameraSwitchView frontBackCameraSwitcher;
    protected RecordButton recordButton;
    protected TextView recordDurationText;
    protected TextView recordSizeMbText;
    protected RelativeLayout recordPanel;
    protected RelativeLayout cameraLayout;

    long start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_camera);
        initView();
        onLoad();
    }

    private void onLoad() {
        if (Build.VERSION.SDK_INT > 15) {
            final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

            final List<String> permissionsToRequest = new ArrayList<>();
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission);
                }
            }
            if (!permissionsToRequest.isEmpty()) {
                ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]), REQUEST_CAMERA_PERMISSIONS);
            } else addCamera();
        } else {
            addCamera();
        }
    }

    private void initView() {
        content = (FrameLayout) findViewById(R.id.content);
        settingsView = (CameraSettingsView) findViewById(R.id.settings_view);
        settingsView.setOnClickListener(CameraActivity.this);
        flashSwitchView = (FlashSwitchView) findViewById(R.id.flash_switch_view);
        frontBackCameraSwitcher = (CameraSwitchView) findViewById(R.id.front_back_camera_switcher);
        frontBackCameraSwitcher.setOnClickListener(CameraActivity.this);
        recordButton = (RecordButton) findViewById(R.id.record_button);
        recordButton.setOnClickListener(CameraActivity.this);
        recordDurationText = (TextView) findViewById(R.id.record_duration_text);
        recordSizeMbText = (TextView) findViewById(R.id.record_size_mb_text);
        recordPanel = (RelativeLayout) findViewById(R.id.record_panel);
        cameraLayout = (RelativeLayout) findViewById(R.id.cameraLayout);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.settings_view) {
            onSettingsClicked();
        } else if (view.getId() == R.id.record_button) {
            onRecordButtonClicked();
        } else if (view.getId() == R.id.front_back_camera_switcher) {
            onSwitchCameraClicked();
        }
    }

    public void onSwitchCameraClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.switchCameraTypeFrontBack();
        }
    }

    public void onRecordButtonClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.takePhotoOrCaptureVideo(new CameraFragmentResultAdapter() {
                @Override
                public void onVideoRecorded(String filePath) {

                    Toast.makeText(getBaseContext(), "Finished recording", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPhotoTaken(byte[] bytes, String filePath) {
                    Toast.makeText(getBaseContext(), "onPhotoTaken " + filePath, Toast.LENGTH_SHORT).show();
                }
            }, FileUtils.getAppDir(), "umpire");
        }
    }

    public void onSettingsClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.openSettingDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0) {
            addCamera();
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void addCamera() {
        cameraLayout.setVisibility(View.VISIBLE);

        final CameraFragment cameraFragment = CameraFragment.newInstance(new Configuration.Builder().setCamera(Configuration.CAMERA_FACE_REAR).setMediaAction(Configuration.MEDIA_ACTION_VIDEO).setMediaQuality(Configuration.MEDIA_QUALITY_HIGH).setFlashMode(Configuration.FLASH_MODE_OFF).setVideoFileSize(FileUtils.getAvailableStorage())
                .build());
        getSupportFragmentManager().beginTransaction().replace(R.id.content, cameraFragment, FRAGMENT_TAG).commitAllowingStateLoss();

        if (cameraFragment != null) {

            cameraFragment.setStateListener(new CameraFragmentStateAdapter() {

                @Override
                public void onCurrentCameraBack() {
                    frontBackCameraSwitcher.displayBackCamera();
                }

                @Override
                public void onCurrentCameraFront() {
                    frontBackCameraSwitcher.displayFrontCamera();
                }

                @Override
                public void onFlashAuto() {
                    flashSwitchView.displayFlashAuto();
                }

                @Override
                public void onFlashOn() {
                    flashSwitchView.displayFlashOn();
                }

                @Override
                public void onFlashOff() {
                    flashSwitchView.displayFlashOff();
                }

                @Override
                public void onCameraSetupForPhoto() {
                }

                @Override
                public void onCameraSetupForVideo() {
                    recordButton.displayVideoRecordStateReady();
                }

                @Override
                public void shouldRotateControls(int degrees) {
                    ViewCompat.setRotation(frontBackCameraSwitcher, degrees);
                    ViewCompat.setRotation(flashSwitchView, degrees);
                    ViewCompat.setRotation(recordDurationText, degrees);
                    ViewCompat.setRotation(recordSizeMbText, degrees);
                }

                @Override
                public void onRecordStateVideoReadyForRecord() {
                    recordButton.displayVideoRecordStateReady();
                }

                @Override
                public void onRecordStateVideoInProgress() {
                    recordButton.displayVideoRecordStateInProgress();
                }

                @Override
                public void onRecordStatePhoto() {
                    recordButton.displayPhotoState();
                }

                @Override
                public void onStopVideoRecord() {
                    recordSizeMbText.setVisibility(View.GONE);
                    //cameraSwitchView.setVisibility(View.VISIBLE);
                    settingsView.setVisibility(View.VISIBLE);
                    long end = System.currentTimeMillis();
                    Cache.getVideo().endTime = DateTimeUtils.getDateTime();
                    Cache.getVideo().duration = (int) ((end - start) / 1000);
                    Cache.getVideo().save();
                    if (isStartedNotification) {
                        isStartedNotification = false;
                    }
                    Log.i(TAG, "onStopVideoRecord: " + Cache.getVideo().endTime + "+++" + Cache.getVideo().duration);
                }

                @Override
                public void onStartVideoRecord(File outputFile) {
                    Cache.getVideo().startTime = DateTimeUtils.getDateTime();
                    start = System.currentTimeMillis();
                }
            });

            cameraFragment.setControlsListener(new CameraFragmentControlsAdapter() {
                @Override
                public void lockControls() {
                    frontBackCameraSwitcher.setEnabled(false);
                    recordButton.setEnabled(false);
                    settingsView.setEnabled(false);
                    flashSwitchView.setEnabled(false);
                }

                @Override
                public void unLockControls() {
                    frontBackCameraSwitcher.setEnabled(true);
                    recordButton.setEnabled(true);
                    settingsView.setEnabled(true);
                    flashSwitchView.setEnabled(true);
                }

                @Override
                public void allowCameraSwitching(boolean allow) {
                    frontBackCameraSwitcher.setVisibility(allow ? View.VISIBLE : View.GONE);
                }

                @Override
                public void allowRecord(boolean allow) {
                    recordButton.setEnabled(allow);
                }

                @Override
                public void setMediaActionSwitchVisible(boolean visible) {
                }
            });

            cameraFragment.setTextListener(new CameraFragmentVideoRecordTextAdapter() {
                @Override
                public void setRecordSizeText(long size, long fileSize, String text) {
                    recordSizeMbText.setText(text);
                    if (size < fileSize + AppConfig.NOTIFY_SIZE) {
                        remindSize = (size - fileSize) / (1024 * 1024);
                        startNotification();
                    }
                    if (size < fileSize + AppConfig.STOP_SIZE) {
                        onRecordButtonClicked();
                    }
                }

                @Override
                public void setRecordSizeTextVisible(boolean visible) {
                    recordSizeMbText.setVisibility(visible ? View.VISIBLE : View.GONE);
                }

                @Override
                public void setRecordDurationText(String text) {
                    recordDurationText.setText(text);
                }

                @Override
                public void setRecordDurationTextVisible(boolean visible) {
                    recordDurationText.setVisibility(visible ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    long remindSize = -1;
    boolean isStartedNotification = false;
    Handler handler = new Handler();
    SoundPooler soundPooler;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isStartedNotification) {
                MyToast.show(CameraActivity.this, "Storage size is less than " + remindSize + "MB", true);
                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                soundPooler = new SoundPooler(CameraActivity.this, R.raw.bell);
//                soundPooler.execute();
                handler.postDelayed(runnable, 5000);
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isStartedNotification) isStartedNotification = true;
    }

    private void startNotification() {
        if (isStartedNotification) return;
        isStartedNotification = true;
        handler.postDelayed(runnable, 0);
    }


    private CameraFragmentApi getCameraFragment() {
        return (CameraFragmentApi) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }
}
