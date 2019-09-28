package com.example.asterik.moviecatalog;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.asterik.moviecatalog.adapter.FavTabAdapter;

public class FavFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_fav, container, false);
        TabLayout tabLayout;
        tabLayout=v.findViewById(R.id.tab);
        ViewPager viewPager = v.findViewById(R.id.home_viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new FavTabAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
