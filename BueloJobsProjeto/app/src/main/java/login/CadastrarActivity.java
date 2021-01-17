package login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buelojobs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import util.DialogAlerta;


public class CadastrarActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_Email, editText_Senha, editText_SenhaRepetida;
    private CardView cardView_Cadastrar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        editText_Email = (EditText) findViewById(R.id.editText_Email);
        editText_Senha = (EditText) findViewById(R.id.editText_Senha);
        editText_SenhaRepetida = (EditText) findViewById(R.id.editText_SenhaRepetida);

        cardView_Cadastrar = (CardView) findViewById(R.id.cardView_Cadastrar);


        cardView_Cadastrar.setOnClickListener(this);


        auth = FirebaseAuth.getInstance();

    }

    // --------------------------- Tratamentos dos clicks ---------------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardView_Cadastrar:
                cadastrar();

                // Toast.makeText(getBaseContext(),"Cadastrar o usuário",Toast.LENGTH_LONG).show();
                break;



        }
    }

//------------------------------------ Criar conta do usuário/Cadastro -------------------------------------
    private void cadastrar() {


        String email = editText_Email.getText().toString().trim();
        String senha = editText_Senha.getText().toString().trim();
        String confirmaSenha = editText_SenhaRepetida.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()) {

            Toast.makeText(getBaseContext(), "Erro - Preencha os Campos", Toast.LENGTH_LONG).show();


        } else {


            if (senha.contentEquals(confirmaSenha)) {

                if (Util.verificarInternet(this)) {

                    criarUsuario(email, senha);

                } else {

                    Toast.makeText(getBaseContext(), "Erro - Verifique se sua Wifi ou 3G está funcionando", Toast.LENGTH_LONG).show();
                }

            } else {

                Toast.makeText(getBaseContext(), "Erro - Senhas Diferentes", Toast.LENGTH_LONG).show();

            }
        }
    }


    private void criarUsuario(String email, String senha) {


        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                //Toast.makeText(getBaseContext(), "Acesse seu e-mail e faça a validação do seu usuário para acessar o aplicativo", Toast.LENGTH_LONG).show();

                                DialogAlerta alerta = new DialogAlerta("Cadastro efetuado com Sucesso", "Acesse seu e-mail e faça a validação do seu usuário para acessar o aplicativo");
                                alerta.show(getSupportFragmentManager(), "1");
                                limparCampos();


                            } else {

                                Toast.makeText(getBaseContext(), "Falha ao enviar e-mail de confirmação, tente novamente mais tarde.", Toast.LENGTH_LONG).show();


                            }


                        }
                    });

                    // startActivity(new Intent(getBaseContext(),MainActivity.class));
                    // Toast.makeText(getBaseContext(),"Cadastro efetuado com Sucesso",Toast.LENGTH_LONG).show();
                    //  finish();

                } else {

                    String resposta = task.getException().toString();
                    Util.opcoesErro(getBaseContext(), resposta);

                }


            }
        });




    }

    private void limparCampos(){


        editText_Email.setText("");
        editText_Senha.setText("");
        editText_SenhaRepetida.setText("");

    }


}
