package com.example.asterik.moviecatalog.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asterik.moviecatalog.MovieFragmentFav;
import com.example.asterik.moviecatalog.TVShowFragmentFav;

public class FavTabAdapter extends FragmentPagerAdapter {

    public FavTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new MovieFragmentFav();
        }
        return new TVShowFragmentFav();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "MOVIE";
        }
        return "TV SHOW";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
