package com.uguroztunc.cs310news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ProgressBar progressBar_LoadTabs;
    ViewPager2 viewPager;

    Handler categoryHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            List<String> data = (List<String>) message.obj;

            for (int i = 0; i < data.size(); i++)
            {
                changeTabName(i, data.get(i));
            }

            progressBar_LoadTabs.setVisibility(View.INVISIBLE);

            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        progressBar_LoadTabs = findViewById(R.id.progressBar_LoadTabs);

        progressBar_LoadTabs.setVisibility(View.VISIBLE);

        NewsRepository repo = new NewsRepository();

        repo.getAllCategories(((NewsApplication) getApplication()).srv, categoryHandler);

        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adp = new ViewPagerAdapter(this);
        this.viewPager.setAdapter(adp);


        viewPager.setVisibility(View.VISIBLE);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.news_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.mnAbout)
        {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
        }

        return true;
    }

    public void changeTabName(int tabPosition, String newName)
    {
        tabLayout.getTabAt(tabPosition).setText(newName);
    }

}