package com.xiu.core.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CorePagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    private List<T> data;


    public CorePagerAdapter(FragmentManager fm) {
        super(fm);
        data = new ArrayList<>();
    }

    @Override
    public T getItem(int i) {
        return data.get(i);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void addData(List<T> data){
        this.data = data;
        notifyDataSetChanged();
    }
}
