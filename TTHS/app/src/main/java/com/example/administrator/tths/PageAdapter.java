package com.example.administrator.tths;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Administrator on 14/11/2017.
 */

public class PageAdapter extends FragmentStatePagerAdapter {

    PageAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new TkbFragment();
                break;
            case 1:
                fragment = new DiemDanhFragment();
                break;
            case 2:
                fragment = new ThongBaoFragment();
                break;
            case 3:
                fragment = new TinTucFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        switch(position){
            case 0:
                title = "THỜI KHÓA BIỂU";
                break;
            case 1:
                title = "ĐIỂM DANH";
                break;
            case 2:
                title = "THÔNG BÁO";
                break;
            case 3:
                title = "TIN TỨC";
                break;

        }
        return  title;
    }
}
