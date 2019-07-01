package com.umpee.camerafragment.listeners;

/*
 * Created by florentchampigny on 13/01/2017.
 */

public interface CameraFragmentVideoRecordTextListener {
    void setRecordSizeText(long maxSize, long fileSize, String text);
    void setRecordSizeTextVisible(boolean visible);

    void setRecordDurationText(String text);
    void setRecordDurationTextVisible(boolean visible);
}
