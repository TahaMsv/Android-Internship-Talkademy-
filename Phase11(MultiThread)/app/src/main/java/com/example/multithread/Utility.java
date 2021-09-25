package com.example.multithread;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Utility {
    public static void increasePercentage1(){
        final AppViewModel appViewModel=AppViewModel.getInstance();
        Disposable subscribe = Observable.intervalRange(0L, 100L, 1, 1, TimeUnit.SECONDS,
                AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long v) throws Exception {
                        appViewModel.setProgressPercentage(v.intValue());
                    }
                });
    }
    public static void increasePercentage2(){
        final AppViewModel appViewModel=AppViewModel.getInstance();
                new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int percentage = appViewModel.getProgressPercentage();
                if (percentage < 100) {
                    percentage++;
                    appViewModel.setProgressPercentage(percentage);
                    System.out.println(percentage);
                }

            }
        }, 100, 1000);
    }
}
