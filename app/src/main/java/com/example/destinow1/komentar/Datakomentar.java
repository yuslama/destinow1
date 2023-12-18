package com.example.destinow1.komentar;

public class Datakomentar {


    public String namaKomentar;
    public String komentar;
    public String dataGambarkomentar;
    public String keykomentar;


    public Datakomentar(String namaKomentar, String komentar, String dataGambarkomentar) {
        this.namaKomentar = namaKomentar;
        this.komentar = komentar;
        this.dataGambarkomentar = dataGambarkomentar;
        this.keykomentar = keykomentar;
    }

    public String getNamaKomentar() {
        return namaKomentar;
    }

    public void setNamaKomentar(String namaKomentar) {
        this.namaKomentar = namaKomentar;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getDataGambarkomentar() {
        return dataGambarkomentar;
    }

    public void setDataGambarkomentar(String dataGambarkomentar) {
        this.dataGambarkomentar = dataGambarkomentar;
    }

    public String getKeykomentar() {
        return keykomentar;
    }

    public void setKeykomentar(String keykomentar) {
        this.keykomentar = keykomentar;
    }

    public Datakomentar() {

    }




}
