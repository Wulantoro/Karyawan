package com.example.karyawan.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Cuti implements Parcelable {

    private String image_file;
    private String tgl_mulai;
    private String tgl_selesai;
    private String status;
    private String id_cuti;
    private String id_kar;
    private String nama_krw;
    private String lama_cuti;
    private String jns_cuti;
    private String divisi;
    private String alasan;
    private String acc;
    private String created_date;
    public final static Parcelable.Creator<Cuti> CREATOR = new Creator<Cuti>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Cuti createFromParcel(Parcel in) {
            return new Cuti(in);
        }

        public Cuti[] newArray(int size) {
            return (new Cuti[size]);
        }

    };

    protected Cuti(Parcel in) {
        this.image_file = ((String) in.readValue((String.class.getClassLoader())));
        this.tgl_mulai = ((String) in.readValue((String.class.getClassLoader())));
        this.tgl_selesai = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.id_cuti = ((String) in.readValue((String.class.getClassLoader())));
        this.id_kar = ((String) in.readValue((String.class.getClassLoader())));
        this.nama_krw = ((String) in.readValue((String.class.getClassLoader())));
        this.lama_cuti = ((String) in.readValue((String.class.getClassLoader())));
        this.jns_cuti = ((String) in.readValue((String.class.getClassLoader())));
        this.divisi = ((String) in.readValue((String.class.getClassLoader())));
        this.alasan = ((String) in.readValue((String.class.getClassLoader())));
        this.acc = ((String) in.readValue((Object.class.getClassLoader())));
        this.created_date = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Cuti() {
    }

    public String getImage_file() {
        return image_file;
    }

    public void setImage_file(String image_file) {
        this.image_file = image_file;
    }

    public String getTgl_mulai() {
        return tgl_mulai;
    }

    public void setTgl_mulai(String tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }

    public String getTgl_selesai() {
        return tgl_selesai;
    }

    public void setTgl_selesai(String tgl_selesai) {
        this.tgl_selesai = tgl_selesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_cuti() {
        return id_cuti;
    }

    public void setId_cuti(String id_cuti) {
        this.id_cuti = id_cuti;
    }

    public String getId_kar() {
        return id_kar;
    }

    public void setId_kar(String id_kar) {
        this.id_kar = id_kar;
    }

    public String getNama_krw() {
        return nama_krw;
    }

    public void setNama_krw(String nama_krw) {
        this.nama_krw = nama_krw;
    }

    public String getLama_cuti() {
        return lama_cuti;
    }

    public void setLama_cuti(String lama_cuti) {
        this.lama_cuti = lama_cuti;
    }

    public String getJns_cuti() {
        return jns_cuti;
    }

    public void setJns_cuti(String jns_cuti) {
        this.jns_cuti = jns_cuti;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(image_file);
        dest.writeValue(tgl_mulai);
        dest.writeValue(tgl_selesai);
        dest.writeValue(status);
        dest.writeValue(id_cuti);
        dest.writeValue(id_kar);
        dest.writeValue(nama_krw);
        dest.writeValue(lama_cuti);
        dest.writeValue(jns_cuti);
        dest.writeValue(divisi);
        dest.writeValue(alasan);
        dest.writeValue(acc);
        dest.writeValue(created_date);
    }

    public int describeContents() {
        return 0;
    }

}