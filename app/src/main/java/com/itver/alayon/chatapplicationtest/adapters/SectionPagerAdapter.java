package com.itver.alayon.chatapplicationtest.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itver.alayon.chatapplicationtest.fragments.CameraFragment;
import com.itver.alayon.chatapplicationtest.fragments.ChatsFragment;
import com.itver.alayon.chatapplicationtest.fragments.FriendsFragment;
import com.itver.alayon.chatapplicationtest.fragments.RequestFragment;

/**
 * Created by Alayon on 17/11/2017.
 */

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public SectionPagerAdapter(FragmentManager manager, int numberOfTabs){
        super(manager);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new CameraFragment();
                break;
            case 1:
                fragment = new RequestFragment();
                break;
            case 2:
                fragment = new ChatsFragment();
                break;
            case 3:
                fragment = new FriendsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
