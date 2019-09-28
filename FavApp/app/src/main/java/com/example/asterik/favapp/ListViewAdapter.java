package com.example.asterik.favapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

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
