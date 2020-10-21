package com.beratdamla.others;

import androidx.annotation.Nullable;

public class Haber {
    private String resim_url = "";
    private String haber_url = "";
    private String haber_baslik = "";
    private String haber_info = "";
    private String haber_birim = "";
    private String haber_tarih = "";
    private String haber_okunma = "";
    private String haber_detay = "";
    Haber(@Nullable String resim_url, @Nullable String haber_url, @Nullable String haber_baslik, @Nullable String haber_info, @Nullable String haber_detay){
        this.resim_url=resim_url;
        this.haber_url=haber_url;
        this.haber_baslik=haber_baslik;
        String[] parse = haber_info.split("</span> ");
        this.haber_okunma = parse[parse.length-1].split("&nbsp;")[0];
        this.haber_tarih = parse[parse.length-2].split("&nbsp;")[0];
        if(parse.length==4) this.haber_birim = parse[parse.length-3].split("&nbsp;")[0];
        this.haber_info=haber_info;
        this.haber_detay=haber_detay;
    }

    public String getResim() { return resim_url; }
    public String getLink() {
        return haber_url;
    }
    public String getBaslik() {
        return haber_baslik;
    }
    public String getDetay() {
        return haber_detay;
    }

    public String getBirim() {
        return haber_birim;
    }

    public String getTarih() {
        return haber_tarih;
    }

    public String getOkunma() {
        return haber_okunma;
    }
}
