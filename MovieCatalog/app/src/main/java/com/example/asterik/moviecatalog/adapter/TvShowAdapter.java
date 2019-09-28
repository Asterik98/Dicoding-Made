package com.example.asterik.moviecatalog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asterik.moviecatalog.Detail.Movie;
import com.example.asterik.moviecatalog.R;
import com.example.asterik.moviecatalog.Detail.TvShow;

import java.util.ArrayList;
import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<TvShow> listTvShow = new ArrayList<>();
    private ArrayList<TvShow> listTvShowTemp =new ArrayList<>();

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie, viewGroup, false);
        return new TvShowAdapter.CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.CategoryViewHolder categoryViewHolder, int position) {
        categoryViewHolder.tvNama.setText(this.listTvShow.get(position).getNama());
        categoryViewHolder.tvTahun.setText(this.listTvShow.get(position).getTahun());

        Glide.with(context)
                .load(this.listTvShow.get(position).getPhoto())
                .apply(new RequestOptions().override(55, 55))
                .into(categoryViewHolder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    public void setListTvShow(ArrayList<TvShow> listTvShow) {
        this.listTvShow.addAll(listTvShow);
        listTvShowTemp=listTvShow;
        notifyDataSetChanged();
    }
    public ArrayList<TvShow> getListTvShow() {
        return listTvShow;
    }
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TvShow> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList=listTvShowTemp;
                filteredList.addAll(listTvShow);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TvShow item : listTvShow) {
                    if (item.getNama().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.values!=null) {
                Log.d("filter kosong","no");
                listTvShow.clear();
                listTvShow.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };

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
