package com.example.myservices;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import android.util.AndroidException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MusicPlayerService extends Service {

    public static final int REQUEST_CODE = 100;
    private static final String TAG = "MyTag";
    public static final String MUSIC_COMPLETE = "MusicComplete";
    public static final String DONE = "done";
    public static final String PLAY = "Play";
    public static final String PAUSE = "Pause";
    public static final String STOP = "Stop";
    private final Binder mBinder = new MyServiceBinder();
    private MediaPlayer mPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
//        mPlayer=MediaPlayer.create(this, );

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(MUSIC_COMPLETE);
                intent.putExtra(MainActivity.MESSAGE_KEY, DONE);
                LocalBroadcastManager.getInstance(getApplicationContext())
                        .sendBroadcast(intent);
                stopForeground(true);
                stopSelf();

            }
        });
    }


    public class MyServiceBinder extends Binder {
        public MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {

                case Constants.MUSIC_SERVICE_ACTION_PLAY: {
                    play();
                    break;
                }
                case Constants.MUSIC_SERVICE_ACTION_PAUSE: {
                    pause();
                    break;
                }
                case Constants.MUSIC_SERVICE_ACTION_STOP: {
                    stopForeground(true);
                    stopSelf();
                }
                case Constants.MUSIC_SERVICE_ACTION_START: {
                    showNotification();
                    break;
                }
                default: {

                }
            }
        }
        return START_NOT_STICKY;

    }

    private void showNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "FileDownload");

        //Intent for play button
        Intent pIntent = new Intent(this, MusicPlayerService.class);
        pIntent.setAction(Constants.MUSIC_SERVICE_ACTION_PLAY);

        PendingIntent playIntent = PendingIntent.getService(this, REQUEST_CODE, pIntent, 0);

        //Intent for pause button
        Intent psIntent = new Intent(this, MusicPlayerService.class);
        psIntent.setAction(Constants.MUSIC_SERVICE_ACTION_PAUSE);

        PendingIntent pauseIntent = PendingIntent.getService(this, REQUEST_CODE, psIntent, 0);

        //Intent for stop button
        Intent sIntent = new Intent(this, MusicPlayerService.class);
        sIntent.setAction(Constants.MUSIC_SERVICE_ACTION_STOP);

        PendingIntent stopIntent = PendingIntent.getService(this, REQUEST_CODE, sIntent, 0);

        builder.setContentTitle("U4Universe Music Player")
                .setContentText("This is demo music player")
                .setSmallIcon(R.mipmap.ic_launcher)
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play, PLAY, playIntent))
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play, PAUSE, pauseIntent))
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play, STOP, stopIntent));


        startForeground(123, builder.build());

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }


    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public void play() {
        mPlayer.start();
    }

    public void pause() {
        mPlayer.pause();
    }


}