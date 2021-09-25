package com.example.myservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    public static NotificationReceiver.NotificationReceiverListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(MainActivity.MESSAGE);
        if (MainActivity.STOP.equals(message)) {
            listener.onStopClicked();
        }
    }

    public void setListener(NotificationReceiver.NotificationReceiverListener listener){
        NotificationReceiver.listener = listener;
    }

    public interface NotificationReceiverListener{
        void onStopClicked();
    }

}
