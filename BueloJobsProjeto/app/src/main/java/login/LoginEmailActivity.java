package login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.buelojobs.HomeActivity;
import com.buelojobs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView cardView_Entrar;
    private Button button_RecuperarSenha;
    private EditText editText_Email, editText_Senha;
    CheckBox mostrarSenha;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        editText_Email = (EditText) findViewById(R.id.editText_Emaillogin);
        editText_Senha = (EditText) findViewById(R.id.editText_SenhaLogin);

        //getSupportActionBar().hide();
        mostrarSenha = (CheckBox) findViewById(R.id.mostrarSenha);

        cardView_Entrar = (CardView) findViewById(R.id.cardView_Entrar);
        button_RecuperarSenha = (Button) findViewById(R.id.button_RecuperarSenha);

        cardView_Entrar.setOnClickListener(this);
        button_RecuperarSenha.setOnClickListener(this);
        mostrarSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editText_Senha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else{
                    editText_Senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        auth = FirebaseAuth.getInstance();


    }

    //-------------------------- Tratamento dos clicks --------------------------------------------
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cardView_Entrar:

                //Toast.makeText(getBaseContext(),"Entrar no sistema",Toast.LENGTH_LONG).show();
                 loginEmail();

                break;
            case R.id.button_RecuperarSenha:

                recuperarSenha();
                break;
        }

    }


    //------------------------------- Entrar com e-mail e senha -------------------------------


    private void loginEmail() {

        String email = editText_Email.getText().toString().trim();
        String senha = editText_Senha.getText().toString().trim();


        if (email.isEmpty() || senha.isEmpty()) {

            Toast.makeText(getBaseContext(), "Insira os campos obrigatórios", Toast.LENGTH_LONG).show();


        } else {


            if (Util.verificarInternet(this)) {


                ConnectivityManager conexao = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);


                confirmarLoginEmail(email, senha);

            } else {

                Toast.makeText(getBaseContext(), "Erro - Verifique se sua Wifi ou 3G está a funcionar", Toast.LENGTH_LONG).show();

            }


        }

    }

    private void confirmarLoginEmail(String email, String senha) {


        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {
                        startActivity(new Intent(getBaseContext(), HomeActivity.class));
                        Toast.makeText(getBaseContext(), "Usuario Logado com Sucesso", Toast.LENGTH_LONG).show();
                        finish();

                    } else {

                        Toast.makeText(getBaseContext(), "Seu E-mail ainda não foi confirmado", Toast.LENGTH_LONG).show();

                    }

                } else {


                    String resposta = task.getException().toString();
                    Util.opcoesErro(getBaseContext(), resposta);
                    // Toast.makeText(getBaseContext(),"Erro ao Logar usuario", Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    //------------------------ Recuperar Senha-----------------------------------------------------




    private void recuperarSenha(){

        if(Util.verificarInternet(getBaseContext())) {

            String email = editText_Email.getText().toString().trim();

            if (email.isEmpty()) {

                Toast.makeText(getBaseContext(), "Insira pelo menos seu e-mail para poder Redefinir sua senha", Toast.LENGTH_LONG).show();


            } else {


                enviarEmail(email);
            }


        } else{

            Toast.makeText(getBaseContext(), "Erro- Verifique se sua Wifi ou 3G está a funcionar", Toast.LENGTH_LONG).show();


        }


    }





    private void  enviarEmail(String email){



        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getBaseContext(),"Enviamos uma mensagem para o seu email com um link para você redefinir a sua senha",
                        Toast.LENGTH_LONG).show();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                String erro = e.toString();

                Util.opcoesErro(getBaseContext(),erro);


            }
        });


    }





}