package com.example.mysql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static String SQLITE_OR_ROOM = "SQLITE";
    public static String SQLITE = "SQLITE";
    public static String ROOM = "ROOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startFragment(createFirstFragment());
    }

    protected Fragment createFirstFragment() {
        return FirstFragment.newInstance();
    }

    private void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.my_placeHolder, fragment)
                .commit();
    }
}