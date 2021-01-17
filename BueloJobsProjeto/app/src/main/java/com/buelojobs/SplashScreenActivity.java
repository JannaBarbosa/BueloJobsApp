package com.buelojobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity implements Runnable {

    private ProgressBar progressBar;
    private Thread thread;
    private Handler handler;
    private int i;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar_Abertura);

        handler = new Handler();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        i = 1;

        try {
            while (i <= 100) {
                Thread.sleep(10);
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        i++;
                        progressBar.setProgress(i);

                    }
                });
            }
            FirebaseUser user = auth.getCurrentUser();

            if (user != null && user.isEmailVerified()) {

                finish();
                startActivity(new Intent(getBaseContext(), HomeActivity.class));
            } else {
                finish();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }


        } catch (InterruptedException e) {

        }
    }
}