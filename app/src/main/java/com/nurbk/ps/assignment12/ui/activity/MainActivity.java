package com.nurbk.ps.assignment12.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.viewpager2.widget.ViewPager2;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nurbk.ps.assignment12.ui.fragment.PopularFragment;
import com.nurbk.ps.assignment12.R;
import com.nurbk.ps.assignment12.ui.fragment.RecentFragment;
import com.nurbk.ps.assignment12.adapter.ViewPagerAdapter;
import com.nurbk.ps.assignment12.ui.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {


    private ViewPagerAdapter pagerAdapter;
    private ViewPager2 pagerHome;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerHome = findViewById(R.id.pagerHome);
        tabLayout = findViewById(R.id.tabLayout);
        frameLayout = findViewById(R.id.ContainerSearchFragment);


        setSupportActionBar(findViewById(R.id.toolbar));

        pagerAdapter = new ViewPagerAdapter(this);

        pagerAdapter.addFragment(new RecentFragment(() -> {
            frameLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.groupViewPager).setVisibility(View.GONE);
        }));

        pagerAdapter.addFragment(new PopularFragment(() -> {
            frameLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.groupViewPager).setVisibility(View.GONE);
        }));

        pagerHome.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, pagerHome, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Recent");
                    break;
                case 1:
                    tab.setText("Popular");
                    break;
            }
        }).attach();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search) {
            getSupportFragmentManager().beginTransaction().replace(R.id.ContainerSearchFragment,
                    new SearchFragment(), "").addToBackStack("").commit();
            frameLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.groupViewPager).setVisibility(View.GONE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        frameLayout.setVisibility(View.GONE);
        findViewById(R.id.groupViewPager).setVisibility(View.VISIBLE);
        super.onBackPressed();
    }

    interface OnClick {
    }
}