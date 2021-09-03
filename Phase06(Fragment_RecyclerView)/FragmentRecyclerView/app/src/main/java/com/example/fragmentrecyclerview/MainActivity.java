package com.example.fragmentrecyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyFragment myFragment = new MyFragment();
        replaceFragment(myFragment, R.id.fragment);


    }

    private void replaceFragment(MyFragment myFragment, int layoutResId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(layoutResId, myFragment)
                .commit();
    }
}