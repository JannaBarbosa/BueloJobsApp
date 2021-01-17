package com.buelojobs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDuvidas extends RecyclerView.Adapter<AdapterDuvidas.DuvidaVH> {

    List<Duvida> duvidaLista ;

    public AdapterDuvidas(List<Duvida> duvidaLista) {
        this.duvidaLista = duvidaLista;
    }

    @NonNull
    @Override
    public AdapterDuvidas.DuvidaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conteudo_lista_duvidas,parent,false);
        return new DuvidaVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDuvidas.DuvidaVH holder, int position) {

        Duvida duvida = duvidaLista.get(position);
        holder.pergunta.setText(duvida.getPergunta());
        holder.titulo.setText(duvida.getTitulo());
        holder.descricao.setText(duvida.getDescricao());

        boolean isExpandable = duvidaLista.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE:View.GONE);

    }

    @Override
    public int getItemCount() {
        return duvidaLista.size();
    }

    public class DuvidaVH extends RecyclerView.ViewHolder {

        TextView pergunta , titulo , descricao;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public DuvidaVH(@NonNull View itemView) {
            super(itemView);

        pergunta = (TextView)  itemView.findViewById(R.id.pergunta);
        titulo = (TextView)  itemView.findViewById(R.id.titulo);
        descricao = (TextView)  itemView.findViewById(R.id.descricao);


            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout);
            expandableLayout =(RelativeLayout) itemView.findViewById(R.id.expandable_layout);


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            Duvida duvida = duvidaLista.get(getAdapterPosition());
            duvida.setExpandable(!duvida.isExpandable());
            notifyItemChanged(getAdapterPosition());


                }
            });
        }
    }
}
