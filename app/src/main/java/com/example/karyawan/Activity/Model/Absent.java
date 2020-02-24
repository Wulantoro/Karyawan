package com.example.karyawan.Activity.Model;

public class Absent {

    private String nama;
    private String jamMasuk;
    private String jamKeluar;
    private String tanggal;

    public Absent(String nama, String jamMasuk, String jamKeluar, String tanggal) {
        this.nama = nama;
        this.jamMasuk = jamMasuk;
        this.jamKeluar = jamKeluar;
        this.tanggal = tanggal;

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJamMasuk() {
        return jamMasuk;
    }

    public void setJamMasuk(String jamMasuk) {
        this.jamMasuk = jamMasuk;
    }

    public String getJamKeluar() {
        return jamKeluar;
    }

    public void setJamKeluar(String jamKeluar) {
        this.jamKeluar = jamKeluar;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}
