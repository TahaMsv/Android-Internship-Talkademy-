package com.example.fragmentrecyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyFragment myFragment = new MyFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, myFragment)
                .commit();

        MyFragment myFragment3 = new MyFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, myFragment3)
                .commit();

    }
}