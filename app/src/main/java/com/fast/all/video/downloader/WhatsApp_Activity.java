package com.fast.all.video.downloader;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.Window;
import android.view.WindowManager;

import com.fast.all.video.downloader.R;

public class WhatsApp_Activity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    view_pager_adapter pager_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.whatsapp_activity);
        //setSupportActionBar(toolbar);


        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.container);
        pager_adapter = new view_pager_adapter(getSupportFragmentManager());
        pager_adapter.add_fragments(new pictures(), "Pictures");
        pager_adapter.add_fragments(new videos(), "Videos");
        viewPager.setAdapter(pager_adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        viewPager.getAdapter().notifyDataSetChanged();
        super.onResume();
    }



}