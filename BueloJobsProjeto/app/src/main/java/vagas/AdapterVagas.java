package vagas;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.buelojobs.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterVagas extends RecyclerView.Adapter<AdapterVagas.ViewHolder> implements View.OnClickListener {


    private List<Vaga> vagas;
    LayoutInflater inflater;
    //Listener
    private View.OnClickListener listener;


    public AdapterVagas(Context context, List<Vaga> vagas){

        this.inflater = LayoutInflater.from(context);
        this.vagas = vagas;


    }

    public List<Vaga> getVagas(){
         return this.vagas;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_conteudo_lista_vagas,parent,false);

        view.setOnClickListener(this);


        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener= listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String titulo_da_vaga = vagas.get(position).getNome();
        String empresa_contratante = vagas.get(position).getEmpresa();
        String prazo_candidatura = vagas.get(position).getData();
        String localidade = vagas.get(position).getLocalidade();

        holder.textView_Nome.setText(titulo_da_vaga);
        holder.textView_Empresa.setText(empresa_contratante);
        holder.textView_Prazo.setText(prazo_candidatura);
        holder.textView_Localidade.setText(localidade);




    }

    @Override
    public int getItemCount() {
        return vagas.size();
    }



    @Override
    public void onClick(View v) {
        if(listener!=null){

            listener.onClick(v);
        }

    }


    public class ViewHolder extends  RecyclerView.ViewHolder{

        //CardView cardView;
        TextView textView_Nome;
        TextView textView_Prazo;
        TextView textView_Empresa;
        TextView textView_Localidade;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            //cardView = (CardView)itemView.findViewById(R.id.cardView_lista_vagas);
            textView_Nome = (TextView)itemView.findViewById(R.id.textView_item_vaga);
            textView_Prazo = (TextView)itemView.findViewById(R.id.textView_item_prazo);
            textView_Empresa = (TextView)itemView.findViewById(R.id.textView_item_empresa);
            textView_Localidade = (TextView)itemView.findViewById(R.id.textView_item_localidade);
        }

    }
}
