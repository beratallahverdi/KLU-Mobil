package com.beratdamla.klu_mobil;

import android.os.Bundle;
import android.view.Menu;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.evolve.backdroplibrary.BackdropContainer;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BackdropContainer backdropContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar=(Toolbar)findViewById(R.id.testToolbar);
        backdropContainer =(BackdropContainer)findViewById(R.id.backdropcontainer);

        int height = 400;
        backdropContainer.attachToolbar(toolbar)
                .dropInterpolator(new LinearInterpolator())
                .dropHeight(height)
                .build();
    }
}
