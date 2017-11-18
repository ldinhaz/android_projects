package com.example.administrator.tths;


import android.app.Activity;
import android.content.Intent;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.view.Window;
import android.widget.Button;

import database.ConnectDB;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private static ConnectDB con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        init();
        addControl();

    }

    private void init(){
        String a, b, c, d;
        a = getString(R.string.ip);
        b = getString(R.string.db_name);
        c = getString(R.string.sql_user);
        d = getString(R.string.sql_pw);

        con = new ConnectDB(a,b,c,d);
    }

    public static ConnectDB getConnectDB(){
        return con;
    }


    private void addControl(){

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        PageAdapter pageAdapter = new PageAdapter(fragmentManager);
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(pageAdapter);

    }

}
