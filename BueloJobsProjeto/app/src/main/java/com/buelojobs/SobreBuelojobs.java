package com.buelojobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SobreBuelojobs extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_buelojobs);

        Toolbar toolbar = findViewById(R.id.toolbarPersonalizado);
        toolbar.setTitle("Sobre o BueloJobs");
        setSupportActionBar(toolbar);
        //getSupportActionBar().hide();
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
    }
}



