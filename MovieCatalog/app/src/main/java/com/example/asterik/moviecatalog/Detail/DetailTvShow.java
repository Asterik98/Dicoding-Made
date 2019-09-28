package com.example.asterik.moviecatalog.Detail;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.asterik.moviecatalog.R;
import com.example.asterik.moviecatalog.db.TvShowHelper;


public class DetailTvShow extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TVSHOW = "extra_tvshow";
    private boolean favorit;
    ImageView gambar;
    TextView nama, tahun, deskripsi, score, crew;
    ToggleButton toggleButton;
    private TvShowHelper tvshowHelper;
    private TvShow tvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvshowHelper = TvShowHelper.getInstance(getApplicationContext());
        tvshowHelper.open();
        setContentView(R.layout.view_tvshow);
        gambar = findViewById(R.id.img_item_photo);
        nama = findViewById(R.id.tv_item_name);
        tahun = findViewById(R.id.tv_item_year);
        deskripsi = findViewById(R.id.tv_item_deskripsi);
        score = findViewById(R.id.tv_item_score);
        crew = findViewById(R.id.tv_item_crew);

        tvshow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        nama.setText(tvshow.getNama());
        tahun.setText(tvshow.getTahun());
        deskripsi.setText(tvshow.getDeskripsi());
        score.setText(tvshow.getScore());
        crew.setText(tvshow.getCrew());
        Glide.with(this).load(tvshow.getPhoto()).into(gambar);
        favorit = tvshowHelper.favorit(tvshow.getId());
        toggleButton = findViewById(R.id.favouritebtn);
        if (favorit) {
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_favorite_black_24dp));
        } else {
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_favorite_border_black_24dp));
        }
        toggleButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.favouritebtn) {
            setFavorite();
        }
    }

    private void saveFavoriteTvShow() {
        tvshowHelper.open();
        long result = tvshowHelper.insertFavoriteTvShow(this.tvshow);
        if (result > 0) {
            Toast.makeText(this, tvshow.getNama() + " dimasukkan ke favorit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
        }
        tvshowHelper.close();
    }

    private void unFavoriteTvShow() {
        tvshowHelper.open();
        tvshowHelper.deleteFavoriteTvShow(tvshow.getId());
        tvshowHelper.close();
    }

    private void setFavorite() {
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveFavoriteTvShow();
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_favorite_black_24dp));
                } else {
                    unFavoriteTvShow();
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_favorite_border_black_24dp));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvshowHelper.close();
    }
}
