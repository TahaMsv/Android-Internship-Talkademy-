package com.example.myservices;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener listener;


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = ConnectivityReceiver.isConnected(context);
        if(listener != null){
            listener.onNetworkConnectionChanged(isConnected);
        }
    }

    private static boolean isConnected(Context context){
        ConnectivityManager conMan = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        return (netInfo!= null && netInfo.isConnectedOrConnecting());
    }

    public interface ConnectivityReceiverListener{
        void onNetworkConnectionChanged(boolean isConnected);
    }

    public void setListener(ConnectivityReceiver.ConnectivityReceiverListener listener){
        ConnectivityReceiver.listener = listener;
    }

}
