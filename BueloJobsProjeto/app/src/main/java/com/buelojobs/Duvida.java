package com.buelojobs;

public class Duvida {


    private String id;
    private String pergunta;
    private String titulo;
    private String descricao;
    private boolean expandable;


    public Duvida() {

    }

    public Duvida(String id, String pergunta, String titulo, String descricao) {
        this.id = id;
        this.pergunta = pergunta;
        this.titulo = titulo;
        this.descricao = descricao;

        this.expandable = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
}
