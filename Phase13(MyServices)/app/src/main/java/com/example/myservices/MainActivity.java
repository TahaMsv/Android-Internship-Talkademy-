package com.example.myservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        ConnectivityReceiver.ConnectivityReceiverListener, LocationListener, NotificationReceiver.NotificationReceiverListener {
    public static final String START = "Start";
    public static final String STOP = "Stop";
    public static final String MESSAGE_KEY = "message_key";
    private static final int PERMS_REQ_CODE = 1234;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final String CONNECTED = "Connected";
    public static final String NO = "No";
    public static final String YES = "Yes";
    public static final String MESSAGE = "message";
    private ConnectivityReceiver connectivityReceiver;
    private NotificationReceiver notificationReceiver;
    private TextView textView;
    protected LocationManager locationManager;
    private Button startBtn;
    private static final String CHANNEL_ID = "2";
    private MusicPlayerService mMusicPlayerService;
    private boolean mBound=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);
        Button welcomeBtn = findViewById(R.id.btn1);
        welcomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWelcomeAlarmMessage();

            }
        });


        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (START.equals(startBtn.getText().toString())) {
                    updateCurrentLocation();
                } else {
                    cancelGetLocation();
                }
            }
        });

        connectivityReceiver = new ConnectivityReceiver();
        notificationReceiver = new NotificationReceiver();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            checkPermissions();
        }
    }

    private void cancelGetLocation() {
        locationManager.removeUpdates(this);
        startBtn.setText(START);
    }

    private void updateCurrentLocation() {
        startBtn.setText(STOP);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 0, this);
    }

    private void showWelcomeAlarmMessage() {
        Intent intent = new Intent(MainActivity.this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int intervalMillis = 60 * 1000;
        long triggerAtMillis = System.currentTimeMillis() + 1000;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        connectivityReceiver.setListener(this);
        notificationReceiver.setListener(this);
        registerReceiver(connectivityReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectivityReceiver.setListener(null);
        notificationReceiver.setListener(null);
        unregisterReceiver(connectivityReceiver);
        unregisterReceiver(notificationReceiver);

    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS)
                        != PackageManager.PERMISSION_GRANTED) {
            String[] perms = new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.PROCESS_OUTGOING_CALLS
            };
            ActivityCompat.requestPermissions(this, perms, PERMS_REQ_CODE);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle(CONNECTED);
            alertDialog.setMessage("Do you want to play all the musics?");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, NO,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, YES,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            playMusics();
                        }
                    });
            alertDialog.show();
        }
    }



//

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                    }

                } else {
                }
                return;
            }

        }
    }

    private void playMusics() {
        Intent intent=new Intent(MainActivity.this,MusicPlayerService.class);
        intent.setAction(Constants.MUSIC_SERVICE_ACTION_START);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }else{
            startService(intent);
        }

        mMusicPlayerService.play();
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        String loc = "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude();
        Toast.makeText(MainActivity.this, loc, Toast.LENGTH_SHORT).show();
        textView.setText(loc);
        createNotificationChannel();
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra(MESSAGE, STOP);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_location_on_24)
                .setContentTitle("Location")
                .setContentText(loc)
                .addAction(R.mipmap.ic_launcher, STOP, actionIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(100, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "locationChannel";
            String description = "Location notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onStopClicked() {
        cancelGetLocation();
    }






    private ServiceConnection mServiceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {

            MusicPlayerService.MyServiceBinder myServiceBinder=
                    (MusicPlayerService.MyServiceBinder) iBinder;
            mMusicPlayerService=myServiceBinder.getService();
            mBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound=false;
        }
    };

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(MainActivity.this,MusicPlayerService.class);
        bindService(intent,mServiceCon,Context.BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mReceiver,new IntentFilter(MusicPlayerService.MUSIC_COMPLETE));

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mServiceCon);
            mBound=false;
        }

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mReceiver);

    }


//    public boolean checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                new AlertDialog.Builder(this)
//                        .setTitle("")
//                        .setMessage("")
//                        .setPositiveButton("", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(MainActivity.this,
//                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                        MY_PERMISSIONS_REQUEST_LOCATION);
//                            }
//                        })
//                        .create()
//                        .show();
//
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//            return false;
//        } else {
//            return true;
//        }
//    }


}