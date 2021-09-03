package com.example.myimplicitintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.myimplicitintent.Fragments.DetailsFragment;
import com.example.myimplicitintent.Fragments.ListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Phone is in portrait mode
        if (findViewById(R.id.layout_default) != null) {
            FragmentManager manager = this.getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fragment, ListFragment.newInstance(false))
                    .commit();
        }

        //Phone is in landscape mode
        else if (findViewById(R.id.layout_land) != null) {
            FragmentManager manager = this.getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fragment, ListFragment.newInstance(true))
                    .replace(R.id.frame_layout, DetailsFragment.newInstance())
                    .commit();
        }


    }
}