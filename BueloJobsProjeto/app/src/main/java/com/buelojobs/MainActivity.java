package com.buelojobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import login.CadastrarActivity;
import login.LoginEmailActivity;
import login.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView cardView_LoginFacebook;
    private CardView  cardView_LoginGoogle, cardView_LoginEmail;
    private CardView cardView_loginCadastrar, cardView_LoginAnonimo;

    private FirebaseUser user;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    private CallbackManager callbackManager;



    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        //getSupportActionBar().hide();

        cardView_LoginFacebook = (CardView)findViewById(R.id.cardView_LoginFacebook);
        cardView_LoginGoogle = (CardView)findViewById(R.id.cardView_LoginGoogle);
        cardView_LoginEmail = (CardView)findViewById(R.id.cardView_LoginEmail);
        cardView_loginCadastrar = (CardView)findViewById(R.id.cardView_LoginCadastrar);
        cardView_LoginAnonimo  = (CardView)findViewById(R.id.cardView_LoginAnonimo);

        cardView_LoginFacebook.setOnClickListener(this);
        cardView_LoginGoogle.setOnClickListener(this);
        cardView_LoginEmail.setOnClickListener(this);
        cardView_loginCadastrar.setOnClickListener(this);
        cardView_LoginAnonimo.setOnClickListener(this);


        servicosAutenticacao();

        servicosGoogle();
        servicosFacebook();

    }

    //-------------------------- Serviço de autenticaçao---------------------------------------------



    private void servicosAutenticacao(){


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user!=null && user.isEmailVerified()){

                    Toast.makeText(getBaseContext(),"Usuario "+ user.getEmail() + " está logado" , Toast.LENGTH_LONG).show();

                }else{


                }


            }
        };



    }

    //---------------------------- Login com o Facebook ---------------------------------------

    private void servicosFacebook(){


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                adicionarContaFacebookaoFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {


                Toast.makeText(getBaseContext(),"Cancelado" , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                String resultado = error.getMessage();//

                Util.opcoesErro(getBaseContext(),resultado);

            }


        });



    }


    private void adicionarContaFacebookaoFirebase(AccessToken token) {


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            finish();
                            startActivity(new Intent(getBaseContext(),HomeActivity.class));


                        } else {

                            String resultado = task.getException().toString();

                            Util.opcoesErro(getBaseContext(),resultado);
                        }

                        // ...
                    }
                });
    }


    private void signInFacebook(){

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));


    }




    //---------------------------- Tratamento dos Clicks ---------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.cardView_LoginFacebook:
               // Toast.makeText(getBaseContext(),"Logar com o facebook",Toast.LENGTH_LONG).show();

                signInFacebook();

                break;
            case R.id.cardView_LoginGoogle:

               // Toast.makeText(getBaseContext(),"Logar com o Gmail",Toast.LENGTH_LONG).show();

                signInGoogle();

                break;

            case R.id.cardView_LoginEmail:

                //Toast.makeText(getBaseContext(),"Logar com o Email e senha",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(this, LoginEmailActivity.class));

                signInEmail();


                break;
            case R.id.cardView_LoginCadastrar:
                //Toast.makeText(getBaseContext(),"Criar uma conta",Toast.LENGTH_LONG).show();
                if(Util.verificarInternet(getBaseContext())){

                startActivity(new Intent(this, CadastrarActivity.class));
                break;} else {
                    Toast.makeText(getBaseContext(),"Sem conexão com a internet- Verifique se sua Wifi ou 3G está a funcionar",Toast.LENGTH_LONG).show();

                    break;
                }

            case R.id.cardView_LoginAnonimo:
                //Toast.makeText(getBaseContext(),"Pular",Toast.LENGTH_LONG).show();

                signInAnonimo();
                break;



        }
    }


    //------------------------------- Entrar com Gmail ------------------------------------------------------------------------------
    private void servicosGoogle(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    private void signInGoogle() {
        if (Util.verificarInternet(getBaseContext())) {

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            if (account == null) {

                Intent intent = googleSignInClient.getSignInIntent();

                startActivityForResult(intent, 555);
            } else {

                //já existe alguem conectado pelo google
                Toast.makeText(getBaseContext(), "Já logado", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(), HomeActivity.class));

                // googleSignInClient.signOut();


            }

        } else{

            Toast.makeText(getBaseContext(), "Sem conexão com a internet - Verifique se sua Wifi ou 3G está a funcionar", Toast.LENGTH_LONG).show();


        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 555){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);


            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);

                adicionarContaGoogleaoFirebase(account);


            }catch (ApiException e){

                //ele deve cair aqui dentro
                String resultado = e.getMessage();

                Util.opcoesErro(getBaseContext(),resultado);
            }



        }

    }



    private void adicionarContaGoogleaoFirebase(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            finish();
                            startActivity(new Intent(getBaseContext(),HomeActivity.class));

                        } else {

                            String resultado = task.getException().toString();

                            Util.opcoesErro(getBaseContext(),resultado);

                        }

                        // ...
                    }
                });
    }







    //------------------------------- Entrar com Email e senha --------------------------------------


    private void signInEmail(){


        user = auth.getCurrentUser();

        if (user!=null && user.isEmailVerified()) {

            finish();
            startActivity(new Intent(this,HomeActivity.class));


        }else{

            startActivity(new Intent(this,LoginEmailActivity.class));


        }

    }

    //--------------------------------- Entrar de forma anonima /pular-------------------------------

    private void signInAnonimo(){

        if(Util.verificarInternet(getBaseContext())){
        acessarContaAnonimaaoFirebase();}
        else{

            Toast.makeText(this,"Sem conexão com a internet, verifique se sua Wifi ou 3G está a funcionar",Toast.LENGTH_LONG).show();
        }

    }


    private void acessarContaAnonimaaoFirebase(){

        auth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            finish();
                            startActivity(new Intent(getBaseContext(),HomeActivity.class));

                        } else {

                            String resultado = task.getException().toString();

                            Util.opcoesErro(getBaseContext(),resultado);


                        }

                        // ...
                    }
                });


    }


//-------------------------------METODOS DA ACTIVITY--------------------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();

        auth.addAuthStateListener(authStateListener);

    }


    @Override
    protected void onStop() {
        super.onStop();

        if (authStateListener != null){

            auth.removeAuthStateListener(authStateListener);
        }

    }

}