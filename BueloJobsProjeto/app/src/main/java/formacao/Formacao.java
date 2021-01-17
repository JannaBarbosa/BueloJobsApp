package formacao;

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

import formacao.AdapterFormacao;
import formacao.FormacaoModel;



public class Formacao extends Fragment {
    private FirebaseDatabase database;
    private RecyclerView recyclerViewFormacao;
    private AdapterFormacao adapterFormacao;
    private ChildEventListener childEventListener;
    private DatabaseReference reference_database;
    private List<FormacaoModel> formacoesLista = new ArrayList<FormacaoModel>();


    private List<String> keys = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_formacao, container, false);
        recyclerViewFormacao= (RecyclerView) v.findViewById(R.id.recyclerView_Lista_Formacao);

        database = FirebaseDatabase.getInstance();

        iniciarRecyclerView();
        return v;
    }

   private void iniciarRecyclerView(){

       recyclerViewFormacao.setLayoutManager(new LinearLayoutManager(getContext()));
       adapterFormacao = new AdapterFormacao(getContext(),formacoesLista);
       recyclerViewFormacao.setAdapter(adapterFormacao);
       adapterFormacao.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Toast.makeText(getContext(),"Funcionando",Toast.LENGTH_LONG).show();
               List<FormacaoModel> listaFormacaoAtualizada = adapterFormacao.getFormacoes();
               Intent intent = new Intent(getActivity(),FormacaoPDFViewer.class);
               intent.putExtra("id",listaFormacaoAtualizada.get(recyclerViewFormacao.getChildAdapterPosition(v)).getId());
               startActivity(intent);
           }
       });


    }
    //-------------------------------------------Ouvinte ---------------------------------------------------------

    private void ouvinte(){

        reference_database = database.getReference().child("BD").child("Formacao");


        if(childEventListener == null){

            childEventListener = new ChildEventListener(){

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    String key = snapshot.getKey();

                    keys.add(key);

                    FormacaoModel formacao = snapshot.getValue(FormacaoModel.class);
                    formacao.setId(key);
                    formacoesLista.add(formacao);
                    adapterFormacao.notifyDataSetChanged();


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    String key = snapshot.getKey();


                    int index = keys.indexOf(key);

                    FormacaoModel formacao = snapshot.getValue(FormacaoModel.class);
                    formacao.setId(key);

                    formacoesLista.set(index,formacao);
                    adapterFormacao.notifyDataSetChanged();



                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    String key = snapshot.getKey();

                    int index = keys.indexOf(key);

                    formacoesLista.remove(index);
                    adapterFormacao.notifyItemRemoved(index);
                    adapterFormacao.notifyItemChanged(index,formacoesLista.size());

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


    public void pesquisarFormacao(String texto){

        List<FormacaoModel> formacoesListaPesquisa = new ArrayList<>();

        for(FormacaoModel formacaoPesquisa: formacoesLista){
            String nome = formacaoPesquisa.getNome().toLowerCase();
            if(nome.contains(texto)){
                formacoesListaPesquisa.add(formacaoPesquisa);

            }
        }

        adapterFormacao = new AdapterFormacao(getContext(),formacoesListaPesquisa);
        recyclerViewFormacao.setAdapter(adapterFormacao);
        adapterFormacao.notifyDataSetChanged();

        adapterFormacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),FormacaoPDFViewer.class);
                intent.putExtra("id",formacoesListaPesquisa.get(recyclerViewFormacao.getChildAdapterPosition(v)).getId());
                startActivity(intent);


            }
        });


    }



    public void recarregarFormacao(){
        adapterFormacao = new AdapterFormacao(getContext(),formacoesLista);
        recyclerViewFormacao.setAdapter(adapterFormacao);
        adapterFormacao.notifyDataSetChanged();

        adapterFormacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),FormacaoPDFViewer.class);
                intent.putExtra("id",formacoesLista.get(recyclerViewFormacao.getChildAdapterPosition(v)).getId());
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


        if(childEventListener != null){

            reference_database.removeEventListener(childEventListener);
        }
    }

}