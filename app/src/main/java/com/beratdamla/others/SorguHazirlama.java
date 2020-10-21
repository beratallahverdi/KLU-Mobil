package com.beratdamla.others;

import java.util.HashMap;

public class SorguHazirlama {
    private static HashMap<String, String> sorgu;

    public static HashMap<String,String> PO_Personel_TC(String tc){
        sorgu = new HashMap<>();
        sorgu.put("istek","personel_sorgulama");
        sorgu.put("tc",tc);
        return sorgu;
    }

    public static HashMap<String,String> LDAP_Personel_Isim(String veri){
        sorgu = new HashMap<>();
        sorgu.put("istek","personel_ldap_sorgulama");
        sorgu.put("tip","1");
        sorgu.put("veri",veri);
        return sorgu;
    }

    public static HashMap<String,String> LDAP_Personel_Mail(String veri){
        sorgu = new HashMap<>();
        sorgu.put("istek","personel_ldap_sorgulama");
        sorgu.put("tip","2");
        sorgu.put("veri",veri);
        return sorgu;
    }


    public static HashMap<String,String> LDAP_Personel_TC(String tc){
        sorgu = new HashMap<>();
        sorgu.put("istek","personel_ldap_sorgulama");
        sorgu.put("tip","3");
        sorgu.put("veri",tc);
        return sorgu;
    }

    public static HashMap<String,String> OBS_Personel_Sicil(String sicil){
        sorgu = new HashMap<>();
        sorgu.put("istek","obs_personel_sorgulama");
        sorgu.put("tip","1");
        sorgu.put("veri",sicil);
        return sorgu;
    }

    public static HashMap<String,String> OBS_Personel_TC(String tc){
        sorgu = new HashMap<>();
        sorgu.put("istek","obs_personel_sorgulama");
        sorgu.put("tip","2");
        sorgu.put("veri",tc);
        return sorgu;
    }

    public static HashMap<String,String> OBS_Personel_Mail(String veri){
        sorgu = new HashMap<>();
        sorgu.put("istek","obs_personel_sorgulama");
        sorgu.put("tip","3");
        sorgu.put("veri",veri);
        return sorgu;
    }

    public static HashMap<String,String> OBS_Ogrenci_Numara(String veri){
        sorgu = new HashMap<>();
        sorgu.put("istek","ogrenci_sorgulama");
        sorgu.put("tip","1");
        sorgu.put("no",veri);
        return sorgu;
    }

    public static HashMap<String,String> OBS_Ogrenci_TC(String tc){
        sorgu = new HashMap<>();
        sorgu.put("istek","ogrenci_sorgulama");
        sorgu.put("tip","2");
        sorgu.put("veri",tc);
        return sorgu;
    }

    public static HashMap<String,String> OBS_Danismanlik_Sicil(String sicil){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_danismanlik");
        sorgu.put("sicil",sicil);
        return sorgu;
    }

    public static HashMap<String,String> OBS_DersProgrami_Sicil(String sicil){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_ders_programi");
        sorgu.put("sicil",sicil);
        return sorgu;
    }

    public static HashMap<String,String> OBS_VerilenDersler_Sicil(String sicil){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_verilen_dersler");
        sorgu.put("sicil",sicil);
        return sorgu;
    }

    public static HashMap<String,String> OBS_SinavProgrami_Sicil(String sicil){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_sinav_programi");
        sorgu.put("sicil",sicil);
        return sorgu;
    }

    public static HashMap<String,String> OBS_OgrenciAlinanDersler_Numara(String veri){
        sorgu = new HashMap<>();
        sorgu.put("istek","ogrenci_alinan_dersler");
        sorgu.put("no",veri);
        return sorgu;
    }

    public static HashMap<String,String> OBS_OgrenciDanisman_Numara(String veri){
        sorgu = new HashMap<>();
        sorgu.put("istek","ogrenci_danisman");
        sorgu.put("no",veri);
        return sorgu;
    }

    public static HashMap<String,String> OBS_OgrenciDersProgrami_Numara(String veri){
        sorgu = new HashMap<>();
        sorgu.put("istek","ogrenci_ders_programi");
        sorgu.put("no",veri);
        return sorgu;
    }

    public static HashMap<String,String> OBS_OgrenciSinavNotlari_Numara(String veri){
        sorgu = new HashMap<>();
        sorgu.put("istek","ogrenci_sinav_notlari");
        sorgu.put("no",veri);
        return sorgu;
    }

    public static HashMap<String,String> OBS_OgrenciSinavProgrami_Numara(String veri){
        sorgu = new HashMap<>();
        sorgu.put("istek","ogrenci_sinav_programi");
        sorgu.put("no",veri);
        return sorgu;
    }

    public static HashMap<String,String> OBS_DersiAlanOgrenciler_DersHarId(String dersharid){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_dersi_alan_ogrenciler");
        sorgu.put("ders_haraket_id",dersharid);
        return sorgu;
    }

    public static HashMap<String,String> OBS_DersEtkinlikler_DersHarId(String dersharid){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_ders_etkinlik");
        sorgu.put("ders_haraket_id",dersharid);
        return sorgu;
    }

    public static HashMap<String,String> OBS_DersHarfDagilimi_DersHarId(String dersharid){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_ders_etkinlik_hd");
        sorgu.put("ders_haraket_id",dersharid);
        return sorgu;
    }


    public static HashMap<String,String> OBS_DersSinavSonuc_DersHarId(String dersharid,String sinavid){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_ders_etkinlik_puan_sonuc");
        sorgu.put("ders_haraket_id",dersharid);
        sorgu.put("sinav_id",sinavid);
        return sorgu;
    }

    public static HashMap<String,String> PO_Birimler_Akademik(){
        sorgu = new HashMap<>();
        sorgu.put("istek","personel_birimleri");
        sorgu.put("tip","1");
        return sorgu;
    }

    public static HashMap<String,String> PO_Birimler_Idari(){
        sorgu = new HashMap<>();
        sorgu.put("istek","personel_birimleri");
        sorgu.put("tip","2");
        return sorgu;
    }

    public static HashMap<String,String> PO_Birimler_Sozlesmeli(){
        sorgu = new HashMap<>();
        sorgu.put("istek","personel_birimleri");
        sorgu.put("tip","3");
        return sorgu;
    }

    public static HashMap<String,String> OBS_Birimler(){
        sorgu = new HashMap<>();
        sorgu.put("istek","ogrenci_birimleri");
        return sorgu;
    }

    public static HashMap<String,String> OBS_Donemler(){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_donemler");
        return sorgu;
    }

    public static HashMap<String,String> OBS_Programlar(){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_programlar");
        return sorgu;
    }

    public static HashMap<String,String> OBS_ProgramDonemDersler(String donemid,String programid){
        sorgu = new HashMap<>();
        sorgu.put("istek","akademik_programlar");
        sorgu.put("donem_id",donemid);
        sorgu.put("program_id",programid);
        return sorgu;
    }

    public static HashMap<String,String> POSTA_Giris(String mail,String pass){
        sorgu = new HashMap<>();
        sorgu.put("istek","kullanici_girisi");
        sorgu.put("kul_adi",mail);
        sorgu.put("sifre",pass);
        return sorgu;
    }

}
