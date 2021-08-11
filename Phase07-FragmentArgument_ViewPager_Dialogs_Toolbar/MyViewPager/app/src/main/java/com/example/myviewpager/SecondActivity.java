package com.example.myviewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private List<String> myFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString(MainActivity.COUNTRY_ID);
            System.out.println(id);
        }

        Toolbar toolbar = findViewById(R.id.mySecondToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ViewPager viewPager = findViewById(R.id.view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
//        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
//            @NonNull
//            @Override
//            public Fragment getItem(int position) {
//                return new DetailsFragment(myFragments.get(position));
//            }
//
//            @Override
//            public int getCount() {
//                return myFragments.size();
//            }
//        });

    }
}