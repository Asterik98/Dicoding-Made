package com.example.asterik.favapp;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.asterik.favapp.DatabaseContract.getColumnString;

public class Movie implements Parcelable{
    private String nama, tahun,deskripsi,score, attendance,posterPath;
    private int id;

    public Movie() {

    }


    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
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

    public void setPhoto(String posterPath) {
        this.posterPath = posterPath;
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
        dest.writeString(this.attendance);
    }


    public Movie(String title, String description, String date, String poster, String sco, String att) {
        this.nama = title;
        this.deskripsi = description;
        this.tahun = date;
        this.posterPath = poster;
        this.score = sco;
        this.attendance = att;
    }
    public Movie(Cursor cursor) {
        this.nama = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.deskripsi = getColumnString(cursor, DatabaseContract.MovieColumns.DESKRIPSI);
        this.tahun= getColumnString(cursor, DatabaseContract.MovieColumns.DATE);
        this.posterPath = getColumnString(cursor, DatabaseContract.MovieColumns.IMAGE);
        this.score = getColumnString(cursor, DatabaseContract.MovieColumns.SCORE);
        this.attendance = getColumnString(cursor, DatabaseContract.MovieColumns.ATTENDANCE);
    }
    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.nama = in.readString();
        this.tahun = in.readString();
        this.posterPath = in.readString();
        this.score = in.readString();
        this.deskripsi = in.readString();
        this.attendance = in.readString();
    }
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
