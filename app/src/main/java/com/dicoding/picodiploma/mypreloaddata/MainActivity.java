package com.dicoding.picodiploma.mypreloaddata;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.picodiploma.mypreloaddata.services.DataManagerService;

import java.lang.ref.WeakReference;

import static com.dicoding.picodiploma.mypreloaddata.services.DataManagerService.CANCEL_MESSAGE;
import static com.dicoding.picodiploma.mypreloaddata.services.DataManagerService.FAILED_MESSAGE;
import static com.dicoding.picodiploma.mypreloaddata.services.DataManagerService.PREPARATION_MESSAGE;
import static com.dicoding.picodiploma.mypreloaddata.services.DataManagerService.SUCCESS_MESSAGE;
import static com.dicoding.picodiploma.mypreloaddata.services.DataManagerService.UPDATE_MESSAGE;

public class MainActivity extends AppCompatActivity implements HandlerCallback {
    ProgressBar progressBar;
    Messenger mActivityMessenger;

    Messenger mBoundService;
    boolean mServiceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);

        Intent mboundServiceIntent = new Intent(MainActivity.this, DataManagerService.class);
        mActivityMessenger = new Messenger(new IncomingHandler(this));
        mboundServiceIntent.putExtra(DataManagerService.ACTIVITY_HANDLER, mActivityMessenger);

        bindService(mboundServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = new Messenger(service);
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };

    // berfungsi untuk menerima data dari service yang nantinya akan diteruskan ke HandlerCallback
    @Override
    public void preparation() {
        Toast.makeText(this, "Mulai memuat data", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateProgress(long progress) {
        Log.e("PROGRESS", "UPDATE PROGRESS " + progress);
        progressBar.setProgress((int) progress);
    }

    @Override
    public void loadSuccess() {
        Toast.makeText(this, "BERHASIL", Toast.LENGTH_LONG).show();
        startActivity(new Intent(MainActivity.this, MahasiswaActivity.class));
        finish();
    }

    @Override
    public void loadFailed() {
        Toast.makeText(this, "GAGAL", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadCancel() {
        finish();
    }


    private static class IncomingHandler extends Handler {

        WeakReference<HandlerCallback> weakCallback;

        IncomingHandler(HandlerCallback callback) {
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PREPARATION_MESSAGE:
                    weakCallback.get().preparation();
                    break;
                case UPDATE_MESSAGE:
                    Bundle bundle = msg.getData();
                    long progress = bundle.getLong("KEY_PROGRESS");
                    weakCallback.get().updateProgress(progress);
                    break;
                case SUCCESS_MESSAGE:
                    weakCallback.get().loadSuccess();
                    break;
                case FAILED_MESSAGE:
                    weakCallback.get().loadFailed();
                    break;
                case CANCEL_MESSAGE:
                    weakCallback.get().loadCancel();
                    break;
            }
        }

    }
}

    // interface HandlerCallback untuk mendapaktan nilai balik
    interface HandlerCallback {
        void preparation();

        void updateProgress(long progress);

        void loadSuccess();

        void loadFailed();

        void loadCancel();
    }

