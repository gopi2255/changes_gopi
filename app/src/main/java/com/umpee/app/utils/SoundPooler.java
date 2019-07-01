package com.umpee.app.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Vibrator;

import com.umpee.app.R;

import java.util.HashMap;

public class SoundPooler extends AsyncTask<Void, Void, Void> {
    private final int iSoundId;
    private SoundPool mShortPlayer = null;
    private Context context;

    @SuppressWarnings("deprecation")
    public SoundPooler(Context context, int id) {
        this.context = context;
        this.mShortPlayer = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        iSoundId = mShortPlayer.load(context, id, 1);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        mShortPlayer.play(iSoundId, 0.99f, 0.99f, 0, 0, 1);
        return null;
    }

    @Override
    protected void onPostExecute(final Void success) {
        mShortPlayer.release();
    }

    @Override
    protected void onCancelled() {
    }

}
