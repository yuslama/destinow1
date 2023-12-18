package com.example.destinow1;

public class DataClass {
    public String getDataJudul() {
        return dataJudul;
    }

    public void setDataJudul(String dataJudul) {
        this.dataJudul = dataJudul;
    }

    public String getDataDeskripsi() {
        return dataDeskripsi;
    }

    public void setDataDeskripsi(String dataDeskripsi) {
        this.dataDeskripsi = dataDeskripsi;
    }

    public String getDataLokasi() {
        return dataLokasi;
    }

    public void setDataLokasi(String dataLokasi) {
        this.dataLokasi = dataLokasi;
    }

    public String getDataGambar() {
        return dataGambar;
    }

    public void setDataGambar(String dataGambar) {
        this.dataGambar = dataGambar;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String dataJudul;
    private String dataDeskripsi;
    private String dataLokasi;
    private String dataGambar;
    private String key;



    public DataClass(String dataJudul, String dataDeskripsi, String dataLokasi, String dataGambar) {
        this.dataJudul = dataJudul;
        this.dataDeskripsi = dataDeskripsi;
        this.dataLokasi = dataLokasi;
        this.dataGambar = dataGambar;
        this.key = key;
    }



    public DataClass(){
    }

//REGISTER

    public String namaUser;
    public String emailUser;
    public String passwordUser;

    public DataClass(String namaUser, String emailUser, String passwordUser) {
        this.namaUser = namaUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
    }



    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }
















}
