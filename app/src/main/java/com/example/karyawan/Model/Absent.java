package com.example.karyawan.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Absent implements Parcelable
{

    private String username_Krw;
    private String image_File;
    private String jam_Masuk;
    private String jam_Keluar;
    private String status_Absn;
    private String tgl_Absen;
    public final static Parcelable.Creator<Absent> CREATOR = new Parcelable.Creator<Absent>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Absent createFromParcel(Parcel in) {
            return new Absent(in);
        }

        public Absent[] newArray(int size) {
            return (new Absent[size]);
        }

    }
            ;

    protected Absent(Parcel in) {
        this.username_Krw = ((String) in.readValue((String.class.getClassLoader())));
        this.image_File = ((String) in.readValue((String.class.getClassLoader())));
        this.jam_Masuk = ((String) in.readValue((String.class.getClassLoader())));
        this.jam_Keluar = ((String) in.readValue((String.class.getClassLoader())));
        this.status_Absn = ((String) in.readValue((String.class.getClassLoader())));
        this.tgl_Absen = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Absent() {
    }

    public String getUsernameKrw() {
        return username_Krw;
    }

    public void setUsernameKrw(String username_Krw) {
        this.username_Krw = username_Krw;
    }

    public String getImageFile() {
        return image_File;
    }

    public void setImageFile(String image_File) {
        this.image_File = image_File;
    }

    public String getJamMasuk() {
        return jam_Masuk;
    }

    public void setJamMasuk(String jam_Masuk) {
        this.jam_Masuk = jam_Masuk;
    }

    public String getJamKeluar() {
        return jam_Keluar;
    }

    public void setJamKeluar(String jam_Keluar) {
        this.jam_Keluar = jam_Keluar;
    }

    public String getStatusAbsn() {
        return status_Absn;
    }

    public void setStatusAbsn(String status_Absn) {
        this.status_Absn = status_Absn;
    }

    public String getTglAbsen() {
        return tgl_Absen;
    }

    public void setTglAbsen(String tgl_Absen) {
        this.tgl_Absen = tgl_Absen;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(username_Krw);
        dest.writeValue(image_File);
        dest.writeValue(jam_Masuk);
        dest.writeValue(jam_Keluar);
        dest.writeValue(status_Absn);
        dest.writeValue(tgl_Absen);
    }

    public int describeContents() {
        return 0;
    }

}

//public class Absent {
//
//    private String nama;
//    private String jamMasuk;
//    private String jamKeluar;
//    private String tanggal;
//
//    public Absent(String nama, String jamMasuk, String jamKeluar, String tanggal) {
//        this.nama = nama;
//        this.jamMasuk = jamMasuk;
//        this.jamKeluar = jamKeluar;
//        this.tanggal = tanggal;
//
//    }
//
//    public String getNama() {
//        return nama;
//    }
//
//    public void setNama(String nama) {
//        this.nama = nama;
//    }
//
//    public String getJamMasuk() {
//        return jamMasuk;
//    }
//
//    public void setJamMasuk(String jamMasuk) {
//        this.jamMasuk = jamMasuk;
//    }
//
//    public String getJamKeluar() {
//        return jamKeluar;
//    }
//
//    public void setJamKeluar(String jamKeluar) {
//        this.jamKeluar = jamKeluar;
//    }
//
//    public String getTanggal() {
//        return tanggal;
//    }
//
//    public void setTanggal(String tanggal) {
//        this.tanggal = tanggal;
//    }
//
//}
