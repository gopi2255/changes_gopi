package com.umpee.app.cache;


import com.umpee.app.MyApp;
import com.umpee.app.model.ModelUser;
import com.umpee.app.model.ModelVideo;
import com.umpee.app.utils.TinyDB;

import java.util.ArrayList;

public class Cache {
    private static TinyDB instance = null;

    private static ModelUser mUser;
    private static ModelVideo mVideo;

    public static ArrayList<ModelUser> mUmpireList;

    public static TinyDB getInstance() {
        if (instance == null) {
            instance = new TinyDB(MyApp.getContext());
        }
        return instance;
    }

    public static ModelUser getUser() {
        if (mUser == null) mUser = ModelUser.build();
        return mUser;
    }

    public static ModelVideo getVideo() {
        if (mVideo == null) mVideo = ModelVideo.build();
        return mVideo;
    }

    public static void setUser(ModelUser mUser) {
        Cache.mUser = mUser;
    }
}
