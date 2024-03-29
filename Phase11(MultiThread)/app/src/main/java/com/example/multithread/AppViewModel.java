package com.example.multithread;

import android.os.Handler;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AppViewModel extends BaseObservable {
    private Model model;
    private static AppViewModel instance;

    public static AppViewModel getInstance() {
        if (instance == null) {
            Model model = new Model(0);
            instance = new AppViewModel(model);
        }
        return instance;
    }

    private AppViewModel(Model model) {
        this.model = model;
    }


    @Bindable
    private int progressPercentage = 0;

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int percentage) {
        this.progressPercentage = percentage;
        notifyPropertyChanged(BR.progressPercentage);
    }


    public void onButtonClicked() {

//        Utility.increasePercentage1();
        Utility.increasePercentage2();


    }


}
