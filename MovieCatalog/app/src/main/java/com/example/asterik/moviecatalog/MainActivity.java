package com.example.asterik.moviecatalog;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.asterik.moviecatalog.Detail.Movie;
import com.example.asterik.moviecatalog.ViewModel.MovieViewModel;
import com.example.asterik.moviecatalog.ViewModel.SearchTvShowViewModel;
import com.example.asterik.moviecatalog.ViewModel.SearchViewModel;
import com.example.asterik.moviecatalog.adapter.ListViewAdapter;
import com.example.asterik.moviecatalog.adapter.TvShowAdapter;


public class MainActivity extends AppCompatActivity{
    private Fragment fragment;
    private ListViewAdapter adapter;
    private TvShowAdapter adapter2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_tvshow:
                    fragment = new TVShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_favourite:
                    fragment = new FavFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_movie);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(fragment instanceof MovieFragment) {
                    MovieFragment.list.clear();
                        SearchViewModel.getQuery(query);
                        fragment = new SearcingFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                                .commit();
                }else if(fragment instanceof TVShowFragment){
                    TVShowFragment.list.clear();
                    SearchTvShowViewModel.getQuery(query);
                    fragment = new SearcingTvShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                }else if(fragment instanceof SearcingFragment) {
                    MovieFragment.list.clear();
                    SearchViewModel.getQuery(query);
                    fragment = new SearcingFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                }else if(fragment instanceof SearcingTvShowFragment) {
                    TVShowFragment.list.clear();
                    SearchTvShowViewModel.getQuery(query);
                    fragment = new SearcingTvShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                    return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_settings:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                return true;
            case R.id.action_remainder:
                fragment = new RemainderFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
