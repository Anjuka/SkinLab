package com.fyp.vishmi.skinlab.helper;

public interface ImageUploadCallback {
    void onProgressUpdate(int percentage);
    void onError(String message);
    void onSuccess(String message);
}
