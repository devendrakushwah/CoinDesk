package com.readybrains.coindesk.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.readybrains.coindesk.fragments.CoinsFragment;
import com.readybrains.coindesk.fragments.FavouritesFragment;
import com.readybrains.coindesk.fragments.NewsFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CoinsFragment();
            case 1:
                return new FavouritesFragment();
            case 2:
                return new NewsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            //
            //Your tab titles
            //
            case 0:return "Coins";
            case 1:return "Favourites";
            case 2: return "News";
            default:return null;
        }
    }
}