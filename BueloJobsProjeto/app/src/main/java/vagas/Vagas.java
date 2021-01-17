package vagas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
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


public class Vagas extends Fragment {


    private RecyclerView recyclerViewVagas;
    private AdapterVagas adapterVagas;

    private FirebaseDatabase database;
    private ChildEventListener childEventListener;
    private DatabaseReference reference_database;
    private List<String> keys = new ArrayList<String>();


    private List<Vaga> vagas = new ArrayList<Vaga>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_vagas, container, false);
        recyclerViewVagas = (RecyclerView) v.findViewById(R.id.recyclerView_Lista_Vagas);


        database = FirebaseDatabase.getInstance();


        //carregarLista();

        //mostrarDados();

        iniciarRecyclerView();


        return v;


    }


// Inserir manualmente os dados
    /*public void carregarLista(){
        Vaga vaga1 = new Vaga("1","professor","escola elisa","22/12/2021","Gabu");
        Vaga vaga2 = new Vaga("2","medico","escola aldeia SOS","25/1/2021","Bafata");
        Vaga vaga3 = new Vaga("3","porteiro","escola Sol Mansi","30/7/2021","Bissau");
        Vaga vaga4 = new Vaga("4","cientista","escola Padre","22/12/2021","Mansoa");
        Vaga vaga5 = new Vaga("5","enfermeira","escola Daniel M","2/12/2021","Gabu");
        Vaga vaga6 = new Vaga("6","enfermeira","escola Daniel M","2/12/2021","Gabu");
        Vaga vaga7 = new Vaga("7","enfermeira","escola Daniel M","2/12/2021","Gabu");
        Vaga vaga8 = new Vaga("8","enfermeira","escola Daniel M","2/12/2021","Gabu");
        Vaga vaga9 = new Vaga("9","enfermeira","escola Daniel M","2/12/2021","Gabu");
        Vaga vaga10 = new Vaga("10","enfermeira","escola Daniel M","2/12/2021","Gabu");
        Vaga vaga11 = new Vaga("11","enfermeira","escola Daniel M","2/12/2021","Gabu");
        Vaga vaga12 = new Vaga("12","enfermeira","escola Daniel M","2/12/2021","Gabu");
        Vaga vaga13 = new Vaga("13","enfermeira","escola Daniel M","2/12/2021","Gabu");

        vagas.add(vaga1);
        vagas.add(vaga2);
        vagas.add(vaga3);
        vagas.add(vaga4);
        vagas.add(vaga5);
        vagas.add(vaga6);
        vagas.add(vaga7);
        vagas.add(vaga8);
        vagas.add(vaga9);
        vagas.add(vaga10);
        vagas.add(vaga11);
        vagas.add(vaga12);


    }

    public  void mostrarDados(){
        recyclerViewVagas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterVagas = new AdapterVagas(getContext(), vagas);
        recyclerViewVagas.setAdapter(adapterVagas);
        adapterVagas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String profissoes = vagas.get(recyclerViewVagas.getChildAdapterPosition(v)).getNome();
                //Toast.makeText(getContext(),"Posto: "+profissoes,Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), DetalhesdaVagaActivity.class));
            }
        });
    }
*/


    private void iniciarRecyclerView() {


        recyclerViewVagas.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterVagas = new AdapterVagas(getContext(), vagas);

        recyclerViewVagas.setAdapter(adapterVagas);
        adapterVagas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String profissoes = vagas.get(recyclerViewVagas.getChildAdapterPosition(v)).getId();
                // Toast.makeText(getContext(),"Posto: "+profissoes,Toast.LENGTH_LONG).show();
                // startActivity(new Intent(getContext(), PdfViewer.class));
                List<Vaga> listaVagasAtualizada = adapterVagas.getVagas();
                Intent intent = new Intent(getActivity(), PdfViewer.class);
                intent.putExtra("id", listaVagasAtualizada.get(recyclerViewVagas.getChildAdapterPosition(v)).getId());
                startActivity(intent);
            }
        });

    }

    //-------------------------------------------Ouvinte ----------------------------------------------------------


    private void ouvinte() {
        reference_database = database.getReference().child("BD").child("Vagas");


        if (childEventListener == null) {


            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    String key = dataSnapshot.getKey();

                    keys.add(key);

                    Vaga vaga = dataSnapshot.getValue(Vaga.class);

                    vaga.setId(key);

                    // Log.d("testeJone",vaga.getNome());

                    vagas.add(vaga);

                    adapterVagas.notifyDataSetChanged();


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                    String key = dataSnapshot.getKey();


                    int index = keys.indexOf(key);

                    Vaga vaga = dataSnapshot.getValue(Vaga.class);

                    vaga.setId(key);


                    vagas.set(index, vaga);


                    adapterVagas.notifyDataSetChanged();


                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                    String key = dataSnapshot.getKey();

                    int index = keys.indexOf(key);

                    vagas.remove(index);

                    keys.remove(index);


                    adapterVagas.notifyItemRemoved(index);
                    adapterVagas.notifyItemChanged(index, vagas.size());


                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };


            reference_database.addChildEventListener(childEventListener);
        }


    }


    public void pesquisarVagas(String texto) {
        List<Vaga> vagasPesquisa = new ArrayList<>();
        for (Vaga vaga : vagas) {
            String nome = vaga.getNome().toLowerCase();
            if (nome.contains(texto)) {
                vagasPesquisa.add(vaga);

            }
        }
        adapterVagas = new AdapterVagas(getContext(), vagasPesquisa); //se não der certo , coloco get activity
        recyclerViewVagas.setAdapter(adapterVagas);
        adapterVagas.notifyDataSetChanged();

        adapterVagas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), PdfViewer.class);
                intent.putExtra("id", vagasPesquisa.get(recyclerViewVagas.getChildAdapterPosition(v)).getId());
                startActivity(intent);

            }
        });



    }

    public void recarregarVagas(){
        adapterVagas = new AdapterVagas(getContext(),vagas); //se não der certo , coloco get activity
        recyclerViewVagas.setAdapter(adapterVagas);
        adapterVagas.notifyDataSetChanged();

        adapterVagas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PdfViewer.class);
                intent.putExtra("id", vagas.get(recyclerViewVagas.getChildAdapterPosition(v)).getId());
                startActivity(intent);
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();


        ouvinte();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();


        if (childEventListener != null) {

            reference_database.removeEventListener(childEventListener);
        }
    }

}