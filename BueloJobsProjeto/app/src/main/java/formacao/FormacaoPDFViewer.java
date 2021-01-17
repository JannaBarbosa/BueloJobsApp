package formacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.buelojobs.ContatoBueloJobs;
import com.buelojobs.MainActivity;
import com.buelojobs.R;
import com.buelojobs.SobreBuelojobs;
import com.facebook.login.LoginManager;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import vagas.PdfViewer;

public class FormacaoPDFViewer extends AppCompatActivity {


    private PDFView pdfView;
    private TextView text1;
    private FirebaseDatabase database;
    private DatabaseReference mref;
    private GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formacao_p_d_f_viewer);

        pdfView = (PDFView) findViewById(R.id.pdfViewer);
        text1 = (TextView) findViewById(R.id.text1);
        Intent intent = getIntent();
        String vagaId = intent.getStringExtra("id");
        database = FirebaseDatabase.getInstance();
        mref = database.getReference().child("BD").child("Formacao").child(vagaId).child("url");

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                text1.setText(value);
                Toast.makeText(FormacaoPDFViewer.this, "Detalhes", Toast.LENGTH_SHORT).show();
                String url = text1.getText().toString();

                new FormacaoPDFViewer.RetrivePdfStream().execute(url);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FormacaoPDFViewer.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });


    }

    class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL uRl = new URL(strings[0]);
                HttpURLConnection urlConnection =( HttpURLConnection) uRl.openConnection();
                if(urlConnection.getResponseCode()==200){

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e){
                return null;
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }




}