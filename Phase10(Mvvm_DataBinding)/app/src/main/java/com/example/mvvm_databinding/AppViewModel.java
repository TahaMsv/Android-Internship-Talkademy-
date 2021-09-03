package com.example.mvvm_databinding;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class AppViewModel extends BaseObservable {
    private UserInputModel model;
    private static AppViewModel instance;

    public static AppViewModel getInstance() {
        if (instance == null) {
            UserInputModel model = new UserInputModel("");
            instance = new AppViewModel(model);
        }
        return instance;
    }

    private AppViewModel(UserInputModel model) {
        this.model = model;
    }

    @Bindable
    private boolean buttonEnabled = false;

    public boolean getButtonEnabled() {
        return buttonEnabled;
    }

    public void setButtonEnabled(boolean enabled) {
        this.buttonEnabled = enabled;
        notifyPropertyChanged(BR.buttonEnabled);
    }


    @Bindable
    private String textToSend = "";

    public String getTextToSend() {
        return textToSend;
    }

    public void setTextToSEnd(String text) {
        this.textToSend = text;
        notifyPropertyChanged(BR.textToSend);
    }


    @Bindable
    public String getUserText() {
        return model.getText();
    }

    public void setUserText(String text) {
        model.setText(text);
        //null or empty is true
        setButtonEnabled((TextUtils.isEmpty(text)));
        notifyPropertyChanged(BR.userText);
    }

    public void onButtonClicked() {
        setTextToSEnd(getUserText());
    }


}
