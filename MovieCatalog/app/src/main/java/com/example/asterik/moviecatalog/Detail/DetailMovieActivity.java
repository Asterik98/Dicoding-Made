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
import com.example.asterik.moviecatalog.db.MovieHelper;


public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIE = "extra_movie";
    private Movie movie;
    private MovieHelper movieHelper;
    private ImageView gambar;
    private TextView nama, tahun, deskripsi, score, attendance;
    private ToggleButton toggleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean favorit;
        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();
        setContentView(R.layout.view_movie);
        gambar = findViewById(R.id.img_item_photo);
        nama = findViewById(R.id.tv_item_name);
        tahun = findViewById(R.id.tv_item_year);
        deskripsi = findViewById(R.id.tv_item_deskripsi);
        score = findViewById(R.id.tv_item_score);
        attendance = findViewById(R.id.tv_item_attendance);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        nama.setText(movie.getNama());
        tahun.setText(movie.getTahun());
        deskripsi.setText(movie.getDeskripsi());
        score.setText(movie.getScore());
        attendance.setText(movie.getAttendance());
        Glide.with(this).load(movie.getPhoto()).into(gambar);
        favorit = movieHelper.favorit(movie.getId());
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

    private void saveFavoriteMovie() {
        movieHelper.open();
        long result = movieHelper.insertFavoriteMovie(this.movie);
        if (result > 0) {
            Toast.makeText(this, movie.getNama() + " dimasukkan ke favorit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
        }
        movieHelper.close();
    }

    private void unFavoriteMovie() {
        movieHelper.open();
        movieHelper.deleteFavoriteMovie(movie.getId());
        movieHelper.close();
    }

    private void setFavorite() {
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    saveFavoriteMovie();
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_favorite_black_24dp));
                } else {
                    unFavoriteMovie();
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_favorite_border_black_24dp));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
