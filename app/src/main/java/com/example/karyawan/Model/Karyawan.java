package com.example.karyawan.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Karyawan implements Parcelable
{

    private String id_krw;
    private String nama_krw;
    private String username_krw;
    private String divisi;
    private String telp_krw;
    private String alamat_krw;
    private String gender_krw;
    private String tgllahir_krw;
    private String image_file;
    private String image_name;
    private String password;
    private String level;
    public final static Parcelable.Creator<Karyawan> CREATOR = new Parcelable.Creator<Karyawan>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Karyawan createFromParcel(Parcel in) {
            return new Karyawan(in);
        }

        public Karyawan[] newArray(int size) {
            return (new Karyawan[size]);
        }

    }
            ;

    protected Karyawan(Parcel in) {
        this.id_krw = ((String) in.readValue((String.class.getClassLoader())));
        this.nama_krw = ((String) in.readValue((String.class.getClassLoader())));
        this.username_krw = ((String) in.readValue((String.class.getClassLoader())));
        this.divisi = ((String) in.readValue((String.class.getClassLoader())));
        this.telp_krw = ((String) in.readValue((String.class.getClassLoader())));
        this.alamat_krw = ((String) in.readValue((String.class.getClassLoader())));
        this.gender_krw = ((String) in.readValue((String.class.getClassLoader())));
        this.tgllahir_krw = ((String) in.readValue((String.class.getClassLoader())));
        this.image_file = ((String) in.readValue((String.class.getClassLoader())));
        this.image_name = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.level = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Karyawan() {
    }

    public String getIdKrw() {
        return id_krw;
    }

    public void setIdKrw(String id_krw) {
        this.id_krw = id_krw;
    }

    public String getNamaKrw() {
        return nama_krw;
    }

    public void setNamaKrw(String nama_krw) {
        this.nama_krw = nama_krw;
    }

    public String getUsernameKrw() {
        return username_krw;
    }

    public void setUsernameKrw(String username_krw) {
        this.username_krw = username_krw;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getTelpKrw() {
        return telp_krw;
    }

    public void setTelpKrw(String telp_krw) {
        this.telp_krw = telp_krw;
    }

    public String getAlamatKrw() {
        return alamat_krw;
    }

    public void setAlamatKrw(String alamat_krw) {
        this.alamat_krw = alamat_krw;
    }

    public String getGenderKrw() {
        return gender_krw;
    }

    public void setGenderKrw(String gender_krw) {
        this.gender_krw = gender_krw;
    }

    public String getTgllahirKrw() {
        return tgllahir_krw;
    }

    public void setTgllahirKrw(String tgllahir_krw) {
        this.tgllahir_krw = tgllahir_krw;
    }

    public String getImageFile() {
        return image_file;
    }

    public void setImageFile(String image_file) {
        this.image_file = image_file;
    }

    public String getImageName() {
        return image_name;
    }

    public void setImageName(String image_name) {
        this.image_name = image_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id_krw);
        dest.writeValue(nama_krw);
        dest.writeValue(username_krw);
        dest.writeValue(divisi);
        dest.writeValue(telp_krw);
        dest.writeValue(alamat_krw);
        dest.writeValue(gender_krw);
        dest.writeValue(tgllahir_krw);
        dest.writeValue(image_file);
        dest.writeValue(image_name);
        dest.writeValue(password);
        dest.writeValue(level);
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return this.getGenderKrw();
    }

}
