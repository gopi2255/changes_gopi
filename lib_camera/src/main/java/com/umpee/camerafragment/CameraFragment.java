package com.umpee.camerafragment;

import android.Manifest;
import android.support.annotation.RequiresPermission;

import com.umpee.camerafragment.configuration.Configuration;
import com.umpee.camerafragment.internal.ui.BaseAnncaFragment;

public class CameraFragment extends BaseAnncaFragment {

    @RequiresPermission(Manifest.permission.CAMERA)
    public static CameraFragment newInstance(Configuration configuration) {
        return (CameraFragment) BaseAnncaFragment.newInstance(new CameraFragment(), configuration);
    }
}
