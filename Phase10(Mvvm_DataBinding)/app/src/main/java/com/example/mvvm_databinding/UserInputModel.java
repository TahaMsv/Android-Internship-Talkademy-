package com.example.mvvm_databinding;

public class UserInputModel {
    private String text;

    public UserInputModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
