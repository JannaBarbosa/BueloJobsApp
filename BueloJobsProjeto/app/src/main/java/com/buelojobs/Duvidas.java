package com.buelojobs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Duvidas extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Duvida> duvidalista = new ArrayList<Duvida>();

    private AdapterDuvidas adapterDuvidas; // adapter


    private FirebaseDatabase database;
    private ChildEventListener childEventListener;
    private DatabaseReference reference_database;
    private List<String> keys = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvidas);

        Toolbar toolbar = findViewById(R.id.toolbarPersonalizado);
        toolbar.setTitle("Dúvidas Frequentes");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerViewP);

        database = FirebaseDatabase.getInstance();

        iniciarRecyclerView();

        //initData();
        //setRecyclerView();
    }

    private void iniciarRecyclerView() {

        adapterDuvidas = new AdapterDuvidas(duvidalista);
        recyclerView.setAdapter(adapterDuvidas);

    }

    //---------------------------------------Ouvinte ---------------------------------------------------

    private void ouvinte(){

        reference_database = database.getReference().child("BD").child("Duvidas");

        if(childEventListener == null){

            childEventListener = new ChildEventListener(){

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                    String key = snapshot.getKey();

                    keys.add(key);

                    Duvida duvida =  snapshot.getValue(Duvida.class);
                    duvida.setId(key);
                    duvidalista.add(duvida);
                    adapterDuvidas.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    String key = snapshot.getKey();
                     int index = keys.indexOf(key);
                    Duvida duvida =  snapshot.getValue(Duvida.class);
                    duvida.setId(key);
                    duvidalista.set(index,duvida);
                    adapterDuvidas.notifyDataSetChanged();

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    String key = snapshot.getKey();
                    int index = keys.indexOf(key);
                    duvidalista.remove(index);
                    keys.remove(index);

                    adapterDuvidas.notifyItemChanged(index);
                    adapterDuvidas.notifyItemChanged(index,duvidalista.size());


                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            reference_database.addChildEventListener(childEventListener);
        }



    }




    //--------------------------------------------Ciclos de Vida------------------------------------

    @Override
    protected void onStart() {
        super.onStart();


        ouvinte();
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();


        if(childEventListener != null){

            reference_database.removeEventListener(childEventListener);
        }
    }



    /*
    private void setRecyclerView() {

        AdapterDuvidas adapterDuvidas = new AdapterDuvidas(duvidalista);
        recyclerView.setAdapter(adapterDuvidas);
        recyclerView.setHasFixedSize(true);


    }

    private void initData() {

        duvidalista = new ArrayList<>();
        duvidalista.add(new Duvida("O que é TDR?","Termos de referência","É um documento contendo a descriçao do cargo"));
        duvidalista.add(new Duvida("O que é CV?","Curriculo vitae","É um documento contendo as competências do candidato"));
        duvidalista.add(new Duvida("O que é Netequita?","Termos de referência","É um documento contendo a descriçao do cargo"));
        duvidalista.add(new Duvida("O que é Contrato de trabalho?","Contrato de trabalho","É um documento contendo a descriçao do cargo"));
        duvidalista.add(new Duvida("Imposto profissional?","Termos de referência","É um documento contendo a descriçao do cargo"));
    }

     */
}