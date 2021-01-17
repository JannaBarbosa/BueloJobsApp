package com.buelojobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import login.Util;

public class ContatoBueloJobs extends AppCompatActivity implements View.OnClickListener {
    private GoogleSignInClient googleSignInClient;
    EditText editText_mensagem;
    CardView cardView_ContatoWhatsapp;
    String mobileNumber = "84988881402";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato_buelo_jobs);

        editText_mensagem = (EditText) findViewById(R.id.editText_mensagem);
        cardView_ContatoWhatsapp = (CardView) findViewById(R.id.cardView_ContatoWhatsapp);

        cardView_ContatoWhatsapp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cardView_ContatoWhatsapp:


                String message = editText_mensagem.getText().toString();


                boolean installed = appInstalledOrNot("com.whatsapp");

               // String mobileNumber = "84988881402".toString();

                if (installed) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + "+55" + mobileNumber + "&text=" + message));
                    startActivity(intent);


                } else {
                    Toast.makeText(this, "O aplicativo WhatsApp não está instalado no seu dispositivo", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }






    private boolean appInstalledOrNot(String url) {

        PackageManager packageManager = getPackageManager();
        boolean app_installed;

        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;


        } catch (PackageManager.NameNotFoundException e) {

            app_installed = false;
        }

        return app_installed;

    }



    //--------------------------------------------- menu ----------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.sobrebuelojobs:

                startActivity(new Intent(getBaseContext(),SobreBuelojobs.class));
                break;

            case R.id.contato:
                startActivity(new Intent(getBaseContext(),ContatoBueloJobs.class));

                break;

            case R.id.action_Search:
                Toast.makeText(this,"Pesquisa",Toast.LENGTH_LONG).show();
                break;

            case R.id.sair:

                FirebaseAuth.getInstance().signOut();

                LoginManager.getInstance().logOut();

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                googleSignInClient = GoogleSignIn.getClient(this, gso);
                googleSignInClient.signOut();
                finish();
                startActivity(new Intent(getBaseContext(),MainActivity.class));


                break;

        }

        return super.onOptionsItemSelected(item);
    }





}//Fim da classe ContatoBueloJobs