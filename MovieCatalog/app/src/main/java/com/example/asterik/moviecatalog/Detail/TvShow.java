package com.example.asterik.moviecatalog.Detail;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShow implements Parcelable  {
    private String nama, tahun, deskripsi, score, crew,posterPath;
    private int id;
    public TvShow() {

    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getPhoto() {
        return posterPath;
    }

    public void setPhoto(String photo) {
        this.posterPath = photo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int describeContents() {

        return 0;
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.tahun);
        dest.writeString(this.posterPath);
        dest.writeString(this.score);
        dest.writeString(this.deskripsi);
        dest.writeString(this.crew);
    }

    public TvShow(JSONObject object) {
        try {
            setId(object.getInt("id"));
            setNama(object.getString("original_name"));
            setTahun(object.getString("first_air_date"));
            setPhoto("https://image.tmdb.org/t/p/w185" + object.getString("poster_path"));
            setScore(object.getString("vote_average"));
            setDeskripsi(object.getString("overview"));
            setCrew(object.getString("popularity"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected TvShow(Parcel in) {
        this.id= in.readInt();
        this.nama = in.readString();
        this.tahun = in.readString();
        this.posterPath = in.readString();
        this.score = in.readString();
        this.deskripsi = in.readString();
        this.crew = in.readString();
    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

}