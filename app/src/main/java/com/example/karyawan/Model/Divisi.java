package com.example.karyawan.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Divisi implements Parcelable
{

    private String id_divisi;
    private String nm_divisi;
    public final static Parcelable.Creator<Divisi> CREATOR = new Creator<Divisi>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Divisi createFromParcel(Parcel in) {
            return new Divisi(in);
        }

        public Divisi[] newArray(int size) {
            return (new Divisi[size]);
        }

    }
            ;

    protected Divisi(Parcel in) {
        this.id_divisi = ((String) in.readValue((String.class.getClassLoader())));
        this.nm_divisi = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Divisi() {
    }

    public String getIdDivisi() {
        return id_divisi;
    }

    public void setIdDivisi(String idDivisi) {
        this.id_divisi = idDivisi;
    }

    public String getNmDivisi() {
        return nm_divisi;
    }

    public void setNmDivisi(String nmDivisi) {
        this.nm_divisi = nmDivisi;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id_divisi);
        dest.writeValue(nm_divisi);
    }

    public int describeContents() {
        return 0;
    }

}
