package com.beratdamla.klu_mobil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beratdamla.others.Haber;
import com.beratdamla.others.KluWS;
import com.beratdamla.others.OnTaskCompleted;
import com.beratdamla.others.SorguHazirlama;
import com.beratdamla.others.User;
import com.beratdamla.others.WebServisKLU;
import com.beratdamla.others.WebServisTamamlanma;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    TextInputLayout email;
    TextInputLayout password;
    Button login;
    User user;

    String email_login;
    String pass_login;
    private static final String TAG = "LoginActivity";
    private TextView mStatusTextView;
    JSONObject sonuc;
    String durum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.btnsignin_login);
        mStatusTextView = findViewById(R.id.copyright_login);

        login.setOnClickListener(v -> loginClick());
    }

    private void loginClick() {
        email_login = email.getEditText().getText().toString();
        pass_login = password.getEditText().getText().toString();
        HashMap<String,String> sorgu = SorguHazirlama.POSTA_Giris(email_login,pass_login);
        if(email_login.contains("@admin")){
            sorgu = SorguHazirlama.POSTA_Giris("1160505048",pass_login);
        }
        new WebServisKLU(new WebServisTamamlanma() {
            @Override
            public void Tamamlandi(JSONObject result) throws JSONException {
                if(result.getString("durum").equals("ok")) onLoggedIn();
                else{onLoginError();}
            }
        }).execute(sorgu);
    }

    private void onLoginError() {
        Toast.makeText(this,"Eposta veya Sifre Yanlis",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart(){
        super.onStart();
        sharedPref = getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE);
        if(sharedPref.getString(getResources().getString(R.string.static_tip),"") != ""){
            Intent intent = new Intent(getApplicationContext(), AnasayfaActivity.class);
            startActivity(intent);
        }
    }

    private void onLoggedIn() throws JSONException {
        HashMap<String,String> sorgu;
        sonuc = new JSONObject();
        durum = "";
        if(email_login.contains("@ogrenci")){
            sorgu = SorguHazirlama.OBS_Ogrenci_Numara(email_login.substring(0,email_login.lastIndexOf("@")));
            new WebServisKLU(result -> {
                sonuc = result;
                durum = result.getString("durum");
                if(durum.equals("ok")){
                    sharedPref = getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getResources().getString(R.string.static_tip),"OGRENCI");
                    editor.putString(getResources().getString(R.string.static_unvan),sonuc.getString("OGR_NO"));
                    editor.putString(getResources().getString(R.string.static_ad),sonuc.getString("KIMLIK_AD"));
                    editor.putString(getResources().getString(R.string.static_soyad),sonuc.getString("KIMLIK_SOYAD"));
                    editor.putString(getResources().getString(R.string.static_fakulte),sonuc.getString("FAKULTE"));
                    editor.putString(getResources().getString(R.string.static_program),sonuc.getString("PROGRAM"));
                    editor.putString(getResources().getString(R.string.static_kimlik),sonuc.getString("TCKIMLIKNO"));
                    editor.putString(getResources().getString(R.string.static_json),sonuc.toString());
                    editor.commit();
                }
                else{
                    onLoginError();
                }
            }).execute(sorgu);
        }
        else{
            sorgu = SorguHazirlama.LDAP_Personel_Mail(email_login.substring(0,email_login.lastIndexOf("@")));
            new WebServisKLU(result -> {
                sonuc = result;
                durum = result.getString("durum");
                if(durum.equals("ok")){
                    HashMap<String,String> sor = SorguHazirlama.PO_Personel_TC(sonuc.getJSONObject("0").getString("tc"));
                    new WebServisKLU(son -> {
                        sonuc = son;
                        durum = son.getString("durum");

                        if(durum.equals("ok")){
                            sharedPref = getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(getResources().getString(R.string.static_tip),"PERSONEL");
                            editor.putString(getResources().getString(R.string.static_unvan),sonuc.getString("personelUnvanKisaltma"));
                            editor.putString(getResources().getString(R.string.static_ad),sonuc.getString("personelAd"));
                            editor.putString(getResources().getString(R.string.static_soyad),sonuc.getString("personelSoyad"));
                            editor.putString(getResources().getString(R.string.static_fakulte),sonuc.getString("personelBirim"));
                            editor.putString(getResources().getString(R.string.static_program),sonuc.getString("personelAbd"));
                            editor.putString(getResources().getString(R.string.static_kimlik),sonuc.getString("personelTckimlikno"));
                            editor.putString(getResources().getString(R.string.static_sicil),sonuc.getString("personelKurumsicilno"));
                            editor.putString(getResources().getString(R.string.static_json),sonuc.toString());
                            editor.commit();
                        }
                        else{
                            onLoginError();
                        }
                    }).execute(sor);
                }
            }).execute(sorgu);
        }
        Intent intent = new Intent(getApplicationContext(), AnasayfaActivity.class);
        startActivity(intent);
    }
}
