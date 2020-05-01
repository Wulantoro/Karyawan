package com.example.karyawan.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Absent implements Parcelable
{

    private String username_krw;
    private String image_file;
    private String jam_masuk;
    private String jam_keluar;
    private String status_absn;
    private String tgl_absen;
    private String id_absen;
    private String nama_krw;
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
        this.username_krw = ((String) in.readValue((String.class.getClassLoader())));
        this.image_file = ((String) in.readValue((String.class.getClassLoader())));
        this.jam_masuk = ((String) in.readValue((String.class.getClassLoader())));
        this.jam_keluar = ((String) in.readValue((String.class.getClassLoader())));
        this.status_absn = ((String) in.readValue((String.class.getClassLoader())));
        this.tgl_absen = ((String) in.readValue((String.class.getClassLoader())));
        this.id_absen = ((String) in.readValue((String.class.getClassLoader())));
        this.nama_krw = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Absent() {
    }

    public String getUsernameKrw() {
        return username_krw;
    }

    public void setUsernameKrw(String username_Krw) {
        this.username_krw = username_Krw;
    }

    public String getImageFile() {
        return image_file;
    }

    public void setImageFile(String image_File) {
        this.image_file = image_File;
    }

    public String getJamMasuk() {
        return jam_masuk;
    }

    public void setJamMasuk(String jam_Masuk) {
        this.jam_masuk = jam_Masuk;
    }

    public String getJamKeluar() {
        return jam_keluar;
    }

    public void setJamKeluar(String jam_Keluar) {
        this.jam_keluar = jam_Keluar;
    }

    public String getStatusAbsn() {
        return status_absn;
    }

    public void setStatusAbsn(String status_Absn) {
        this.status_absn = status_Absn;
    }

    public String getTglAbsen() {
        return tgl_absen;
    }

    public void setTglAbsen(String tgl_Absen) {
        this.tgl_absen = tgl_Absen;
    }

    public String getId_absen() {
        return id_absen;
    }

    public void setId_absen(String id_absen) {
        this.id_absen = id_absen;
    }

    public String getNamaKrw() {
        return nama_krw;
    }

    public void setNamaKrw(String namaKrw) {
        this.nama_krw = namaKrw;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(username_krw);
        dest.writeValue(image_file);
        dest.writeValue(jam_masuk);
        dest.writeValue(jam_keluar);
        dest.writeValue(status_absn);
        dest.writeValue(tgl_absen);
        dest.writeValue(id_absen);
    }

    public int describeContents() {
        return 0;
    }

}
