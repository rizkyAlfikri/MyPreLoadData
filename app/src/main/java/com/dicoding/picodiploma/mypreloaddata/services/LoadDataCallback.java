package com.dicoding.picodiploma.mypreloaddata.services;

public interface LoadDataCallback {
    void onPreLoad();

    void onProgresUpdate(long progress);

    void onLoadSuccess();

    void onLoadFailed();

    void onLoadCancel();
}
