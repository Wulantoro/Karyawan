package com.example.karyawan.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Level implements Parcelable
{

    private String id_level;
    private String kd_level;
    public final static Parcelable.Creator<Level> CREATOR = new Creator<Level>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Level createFromParcel(Parcel in) {
            return new Level(in);
        }

        public Level[] newArray(int size) {
            return (new Level[size]);
        }

    }
            ;

    protected Level(Parcel in) {
        this.id_level = ((String) in.readValue((String.class.getClassLoader())));
        this.kd_level = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Level() {
    }

    public String getId_level() {
        return id_level;
    }

    public void setId_level(String id_level) {
        this.id_level = id_level;
    }

    public String getKd_level() {
        return kd_level;
    }

    public void setKd_level(String kd_level) {
        this.kd_level = kd_level;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id_level);
        dest.writeValue(kd_level);
    }

    public int describeContents() {
        return 0;
    }

}
