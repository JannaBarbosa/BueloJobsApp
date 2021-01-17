package formacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buelojobs.R;

import java.util.List;




public class AdapterFormacao extends RecyclerView.Adapter<AdapterFormacao.ViewHolder> implements View.OnClickListener {

   private List<FormacaoModel> formacoes;
   private LayoutInflater inflater;
    private View.OnClickListener listener;

    public AdapterFormacao(Context context, List<FormacaoModel> formacoes){

        this.inflater = LayoutInflater.from(context);
        this.formacoes = formacoes;

    }

    public List<FormacaoModel> getFormacoes(){
        return this.formacoes;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cadrview_conteudo_lista_formacao,parent,false);

        view.setOnClickListener(this);


        return new ViewHolder(view);
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener= listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String titulo_da_formacao = formacoes.get(position).getNome();
        String instituicao = formacoes.get(position).getInstituicao();
        String prazo_inscricao = formacoes.get(position).getData();
        String localidade = formacoes.get(position).getLocalidade();

        holder.textView_item_formacao.setText(titulo_da_formacao);
        holder.textView_Instituicao.setText(instituicao);
        holder.textView_Prazo.setText(prazo_inscricao);
        holder.textView_Localidade.setText(localidade);

    }

    @Override
    public int getItemCount() {
        return formacoes.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){

            listener.onClick(v);
        }
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        //CardView cardView;
        TextView textView_item_formacao;
        TextView textView_Prazo;
        TextView textView_Instituicao;
        TextView textView_Localidade;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            //cardView = (CardView)itemView.findViewById(R.id.cardView_lista_formacao);
            textView_item_formacao = (TextView)itemView.findViewById(R.id.textView_item_formacao);
            textView_Prazo = (TextView)itemView.findViewById(R.id.textView_item_prazo);
            textView_Instituicao = (TextView)itemView.findViewById(R.id.textView_item_instituicao);
            textView_Localidade = (TextView)itemView.findViewById(R.id.textView_item_localidade);
        }

    }
}
