package com.example.asterik.moviecatalog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asterik.moviecatalog.BuildConfig;
import com.example.asterik.moviecatalog.Detail.Movie;
import com.example.asterik.moviecatalog.MovieFragment;
import com.example.asterik.moviecatalog.R;
import com.example.asterik.moviecatalog.ViewModel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie =new ArrayList<>();
    public ListViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {
        categoryViewHolder.tvNama.setText(this.listMovie.get(position).getNama());
        categoryViewHolder.tvTahun.setText(this.listMovie.get(position).getTahun());

        Glide.with(context)
                .load(this.listMovie.get(position).getPhoto())
                .apply(new RequestOptions().override(55, 55))
                .into(categoryViewHolder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }
    public void setListMovies(ArrayList<Movie> listMovie) {
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }
    public ArrayList<Movie> getListMovies() {
        return listMovie;
    }
    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama;
        TextView tvTahun;
        ImageView imgPhoto;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_item_name);
            tvTahun = itemView.findViewById(R.id.tv_item_year);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
