package com.example.myviewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Objects;

public class SecondActivity extends AppCompatActivity {


    private static final int NUM_PAGES = 3;
    private static final String CONTENT = "SecondActivity.Content";
    public static final String OK = "SecondActivity.OK";
    public static final String SAMPLE_MESSAGE = "SecondActivity.Sample message";
    public static final String ALERT = "SecondActivity.Alert";
    private CountryModel countryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString(MainActivity.COUNTRY_ID);
            countryModel = DataModel.getCountry(id);
        }

        Toolbar toolbar = findViewById(R.id.mySecondToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ViewPager mPager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);


    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    bundle.putString(CONTENT, countryModel.getId());
                    break;
                case 1:
                    bundle.putString(CONTENT, countryModel.getName());
                    break;
                case 2:
                    bundle.putString(CONTENT, countryModel.getContinent().name());
                    break;

            }

            ScreenSlidePageFragment sspf = new ScreenSlidePageFragment();
            sspf.setArguments(bundle);
            return sspf;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            Utils.showNEUTRALAlertDialog(SecondActivity.this, ALERT, SAMPLE_MESSAGE, OK);
        }

        return super.onOptionsItemSelected(item);
    }
}