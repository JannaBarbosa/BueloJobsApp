package eventos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buelojobs.R;

import java.util.List;



public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.ViewHolder> implements View.OnClickListener {

    List<Evento> eventos ;
    LayoutInflater inflater;

    //Listener
    private View.OnClickListener listener;

    public AdapterEventos (Context context, List <Evento> eventos){
        this.inflater = LayoutInflater.from(context);
        this.eventos = eventos;

    }

    public List<Evento> getEventos(){
        return this.eventos;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.cardview_conteudo_lista_eventos,parent,false);

        view.setOnClickListener(this);

        return new ViewHolder(view);

    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener= listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String titulo_da_evento = eventos.get(position).getNome();
        String entidade = eventos.get(position).getEmpresa();
        String data_realizacao = eventos.get(position).getData();
        String localidade = eventos.get(position).getLocalidade();

        holder.textView_Nome.setText(titulo_da_evento);
        holder.textView_Entidade.setText(entidade);
        holder.textView_Prazo.setText(data_realizacao);
        holder.textView_Localidade.setText(localidade);

    }

    @Override
    public int getItemCount() {
        return eventos.size();
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
        TextView textView_Entidade;
        TextView textView_Localidade;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            //cardView = (CardView)itemView.findViewById(R.id.cardView_lista_evento);
            textView_Nome = (TextView)itemView.findViewById(R.id.textView_item_evento);
            textView_Prazo = (TextView)itemView.findViewById(R.id.textView_item_prazo);
            textView_Entidade = (TextView)itemView.findViewById(R.id.textView_item_entidade);
            textView_Localidade = (TextView)itemView.findViewById(R.id.textView_item_localidade);
        }

    }


}
