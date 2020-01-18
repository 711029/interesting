package com.example.coder.xiaohua.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private java.util.List<Fragment> List;
    private String[]title={"最新笑话","最新趣图","新闻头条"};

    public FragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.List = list;
    }

    @Override
    public Fragment getItem(int i) {
        return List.get(i);
    }

    @Override
    public int getCount() {
        return List.size();
    }
//方法重写
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
