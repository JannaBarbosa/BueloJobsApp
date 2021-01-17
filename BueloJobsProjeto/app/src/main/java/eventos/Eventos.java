package eventos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.buelojobs.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;





public class Eventos extends Fragment {
    private RecyclerView recyclerViewEventos;
    private AdapterEventos adapterEventos;

    private FirebaseDatabase database;
    private ChildEventListener childEventListener;
    private DatabaseReference reference_database;
    private List<String> keys = new ArrayList<String>();

    private List <Evento> eventos= new ArrayList<Evento>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_eventos, container, false);
        recyclerViewEventos = (RecyclerView) v.findViewById(R.id.recyclerView_Lista_Eventos);
        database = FirebaseDatabase.getInstance();

        iniciarRecyclerView();

        return  v;
    }

    //------------------------ inicilizar o recylcer ---------------------------------------------------

    private void iniciarRecyclerView(){

        recyclerViewEventos.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterEventos = new AdapterEventos(getContext(),eventos);
        recyclerViewEventos.setAdapter(adapterEventos);
        adapterEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Funcionando", Toast.LENGTH_LONG).show();
                List<Evento> listaEventosAtualizada = adapterEventos.getEventos();
                Intent intent = new Intent(getActivity(), VisualizarFlyer.class);
                intent.putExtra("url", listaEventosAtualizada.get(recyclerViewEventos.getChildAdapterPosition(v)).getUrl());
                startActivity(intent);

            }
        });
    }



    //-------------------------------------------Ouvinte ----------------------------------------------------------




    private void ouvinte(){



        reference_database = database.getReference().child("BD").child("Eventos");

        if(childEventListener == null){

            childEventListener = new ChildEventListener(){

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    String key = snapshot.getKey();
                    keys.add(key);
                    Evento evento = snapshot.getValue(Evento.class);
                    evento.setId(key);

                    eventos.add(evento);
                    adapterEventos.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    String key = snapshot.getKey();


                    int index = keys.indexOf(key);

                    Evento evento = snapshot.getValue(Evento.class);

                    evento.setId(key);


                    eventos.set(index,evento);


                    adapterEventos.notifyDataSetChanged();

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {


                    String key = snapshot.getKey();

                    int index = keys.indexOf(key);

                    eventos.remove(index);

                    keys.remove(index);


                    adapterEventos.notifyItemRemoved(index);
                    adapterEventos.notifyItemChanged(index,eventos.size());

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

public void pesquisarEventos(String texto){
        List<Evento> eventosPesquisa = new ArrayList<>();
        for(Evento evento: eventos){
            String nome = evento.getNome().toLowerCase();
            if(nome.contains(texto)){
                eventosPesquisa.add(evento);
            }
        }
        adapterEventos = new AdapterEventos(getContext(),eventosPesquisa);
        recyclerViewEventos.setAdapter(adapterEventos);
        adapterEventos.notifyDataSetChanged();

    adapterEventos.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), VisualizarFlyer.class);
            intent.putExtra("url", eventosPesquisa.get(recyclerViewEventos.getChildAdapterPosition(v)).getUrl());
            startActivity(intent);

        }
    });



}



public void recarregarEventos(){

        adapterEventos= new AdapterEventos(getContext(),eventos);
        recyclerViewEventos.setAdapter(adapterEventos);
        adapterEventos.notifyDataSetChanged();

    adapterEventos.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), VisualizarFlyer.class);
            intent.putExtra("url", eventos.get(recyclerViewEventos.getChildAdapterPosition(v)).getUrl());
            startActivity(intent);

        }
    }) ;


}

    @Override
    public void onStart() {
        super.onStart();


        ouvinte();
    }





    @Override
    public void onDestroy() {
        super.onDestroy();


        if(childEventListener != null){

            reference_database.removeEventListener(childEventListener);
        }
    }





}