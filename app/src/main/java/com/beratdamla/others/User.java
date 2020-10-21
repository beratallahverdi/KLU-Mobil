package com.beratdamla.others;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class User implements Parcelable {
    private String OGR_NO = "";
    private String TCKIMLIKNO = "";
    private String KIMLIK_AD = "";
    private String KIMLIK_SOYAD = "";
    private String KIMLIK_DTARIH;
    private String FAKULTE = "";
    private String BOLUM = "";
    private String PROGRAM = "";
    private String KAYIT_TARIH;
    private String AYRILIS_TARIH;
    private int SINIF = 0;
    private int ARSIV = 0;
    private String OGRENIM_DURUM = "";
    private String UYRUK = "";
    private int CINSIYET = 0;
    private String DOGUM_YER = "";
    private String BABA_AD = "";
    private String TELEFON = "";
    private String AGNO = "";
    private String E_POSTA = "";
    private String ADRES = "";
    private Uri OGR_FOTO_URL;
    private int FAK_KOD = 0;
    private int BOL_ID = 0;
    private int PROG_ID = 0;
    private String json = "";

    public User(JSONObject json){
        this.json = json.toString();
        try {
            OGR_NO = json.getString("OGR_NO");
            TCKIMLIKNO = json.getString("TCKIMLIKNO");
            KIMLIK_AD = json.getString("KIMLIK_AD");
            KIMLIK_SOYAD = json.getString("KIMLIK_SOYAD");
            KIMLIK_DTARIH = json.getString("KIMLIK_DTARIH");
            FAKULTE = json.getString("FAKULTE");
            BOLUM = json.getString("BOLUM");
            PROGRAM = json.getString("PROGRAM");
            KAYIT_TARIH = json.getString("KAYIT_TARIH");
            AYRILIS_TARIH = json.getString("AYRILIS_TARIH");
            SINIF = Integer.parseInt(json.getString("SINIF"));
            ARSIV = Integer.parseInt(json.getString("ARSIV"));
            OGRENIM_DURUM = json.getString("OGRENIM_DURUM");
            UYRUK = json.getString("UYRUK");
            CINSIYET = Integer.parseInt(json.getString("CINSIYET"));
            DOGUM_YER = json.getString("DOGUM_YER");
            BABA_AD = json.getString("BABA_AD");
            TELEFON = json.getString("TELEFON");
            AGNO = json.getString("AGNO");
            E_POSTA = json.getString("E_POSTA");
            ADRES = json.getString("ADRES");
            OGR_FOTO_URL = Uri.parse(json.getString("OGR_FOTO_URL"));
            FAK_KOD = Integer.parseInt(json.getString("FAK_KOD"));
            BOL_ID = Integer.parseInt(json.getString("BOL_ID"));
            PROG_ID = Integer.parseInt(json.getString("PROG_ID"));
        } catch (JSONException e) {
            Log.d(TAG, "User: "+e.getMessage());
        }
    }

    protected User(Parcel in) {
        OGR_NO = in.readString();
        TCKIMLIKNO = in.readString();
        KIMLIK_AD = in.readString();
        KIMLIK_SOYAD = in.readString();
        KIMLIK_DTARIH = in.readString();
        FAKULTE = in.readString();
        BOLUM = in.readString();
        PROGRAM = in.readString();
        KAYIT_TARIH = in.readString();
        AYRILIS_TARIH = in.readString();
        SINIF = Integer.parseInt(in.readString());
        ARSIV = Integer.parseInt(in.readString());
        OGRENIM_DURUM = in.readString();
        UYRUK = in.readString();
        CINSIYET = Integer.parseInt(in.readString());
        DOGUM_YER = in.readString();
        BABA_AD = in.readString();
        TELEFON = in.readString();
        AGNO = in.readString();
        E_POSTA = in.readString();
        ADRES = in.readString();
        OGR_FOTO_URL = Uri.parse(in.readString());
        FAK_KOD = Integer.parseInt(in.readString());
        BOL_ID = Integer.parseInt(in.readString());
        PROG_ID = Integer.parseInt(in.readString());
        OGR_NO = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getKimlikAd() {
        return KIMLIK_AD;
    }
    public String getKimlikSoyad() {
        return KIMLIK_SOYAD;
    }
    public String getEposta() {
        return E_POSTA;
    }
    public String getTelefon() { return TELEFON; }
    public String getOgrenciNo() { return OGR_NO; }
    public String getIletisim(){ return E_POSTA+" / "+TELEFON;}
    public String getKimlikNo() {
        return TCKIMLIKNO;
    }
    public String getUyruk() { return UYRUK; }
    public String getKimlik() { return TCKIMLIKNO+" / "+UYRUK; }
    public String getFakulte(){ return FAKULTE; }
    public String getBolum(){ return BOLUM; }
    public String getProgram(){ return PROGRAM; }
    public String getTamProgram(){ return FAKULTE+" / "+BOLUM+" / "+PROGRAM; }
    public String getCinsiyet(){ return (CINSIYET==0)? "KADIN": "ERKEK"; }
    public String getDogumTarihi(){ return KIMLIK_DTARIH; }
    public String getKayitTarihi(){ return KAYIT_TARIH; }
    public String getAyrilisTarihi(){ return AYRILIS_TARIH; }
    public String getDogumYeri(){ return DOGUM_YER; }
    public String getDogum(){ return KIMLIK_DTARIH+" / "+DOGUM_YER; }
    public String getJson() { return json; }
    public Uri getFotoUri() {
        return OGR_FOTO_URL;
    }
    public String getTamAd() { return KIMLIK_AD+" "+KIMLIK_SOYAD; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(OGR_NO);
        dest.writeString(TCKIMLIKNO);
        dest.writeString(KIMLIK_AD);
        dest.writeString(KIMLIK_SOYAD);
        dest.writeString(KIMLIK_DTARIH);
        dest.writeString(FAKULTE);
        dest.writeString(BOLUM);
        dest.writeString(PROGRAM);
        dest.writeString(KAYIT_TARIH);
        dest.writeString(AYRILIS_TARIH);
        dest.writeString(String.valueOf(SINIF));
        dest.writeString(String.valueOf(ARSIV));
        dest.writeString(OGRENIM_DURUM);
        dest.writeString(UYRUK);
        dest.writeString(String.valueOf(CINSIYET));
        dest.writeString(DOGUM_YER);
        dest.writeString(BABA_AD);
        dest.writeString(TELEFON);
        dest.writeString(AGNO);
        dest.writeString(E_POSTA);
        dest.writeString(ADRES);
        dest.writeString(OGR_FOTO_URL.getPath());
        dest.writeString(String.valueOf(FAK_KOD));
        dest.writeString(String.valueOf(BOL_ID));
        dest.writeString(String.valueOf(PROG_ID));
        dest.writeString(OGR_NO);
    }
}
