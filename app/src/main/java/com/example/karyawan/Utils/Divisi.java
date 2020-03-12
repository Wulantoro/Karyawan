package com.example.karyawan.Utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Divisi implements Parcelable
{

    private String idDivisi;
    private String nmDivisi;
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
        this.idDivisi = ((String) in.readValue((String.class.getClassLoader())));
        this.nmDivisi = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Divisi() {
    }

    public String getIdDivisi() {
        return idDivisi;
    }

    public void setIdDivisi(String idDivisi) {
        this.idDivisi = idDivisi;
    }

    public String getNmDivisi() {
        return nmDivisi;
    }

    public void setNmDivisi(String nmDivisi) {
        this.nmDivisi = nmDivisi;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idDivisi);
        dest.writeValue(nmDivisi);
    }

    public int describeContents() {
        return 0;
    }

}
