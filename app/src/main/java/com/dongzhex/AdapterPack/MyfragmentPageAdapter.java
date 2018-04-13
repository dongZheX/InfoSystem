package com.dongzhex.AdapterPack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by ASUS on 2018/4/12.
 */

public class MyfragmentPageAdapter extends FragmentStatePagerAdapter{
    private static final String TAG = "MyfragmentPageAdapter";
    private final int PAGER_COUNT = 3;
    //碎片对象生命
    public MyfragmentPageAdapter(FragmentManager fm){
        super(fm);
        //碎片对象实例化
    }
    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

        }
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "position Destroy "+position);
        super.destroyItem(container, position, object);
    }
}
