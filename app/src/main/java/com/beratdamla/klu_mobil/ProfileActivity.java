package com.beratdamla.klu_mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.beratdamla.others.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    FloatingActionButton save;
    TextInputLayout tamAd;
    TextInputLayout tamIletisim;
    TextInputLayout tamKimlik;
    TextInputLayout tamDogum;
    TextInputLayout tamProgram;
    TextInputLayout cinsiyet;
    User user;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initializeView();
    }

    private void initializeView(){
        intent = getIntent();
        String tip = getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE).getString(getResources().getString(R.string.static_tip), "");
        String json = getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE).getString(getResources().getString(R.string.static_json), "");
        tamAd = findViewById(R.id.tamAd_info);
        tamIletisim = findViewById(R.id.tamIletisim_info);
        tamKimlik = findViewById(R.id.tamKimlik_info);
        tamDogum = findViewById(R.id.tamDogum_info);
        tamProgram = findViewById(R.id.tamProgram_info);
        cinsiyet = findViewById(R.id.cinsiyet_info);
        try{
            JSONObject jsonArray = new JSONObject(json);
            user = new User(jsonArray);
            tamAd.getEditText().setText(user.getTamAd());
            tamIletisim.getEditText().setText(user.getIletisim());
            tamKimlik.getEditText().setText(user.getKimlik());
            tamDogum.getEditText().setText(user.getDogum());
            tamProgram.getEditText().setText(user.getTamProgram());
            cinsiyet.getEditText().setText(user.getCinsiyet());
        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(),"Bilgiler Alınamadı",Toast.LENGTH_SHORT).show();
        }

    }
}
