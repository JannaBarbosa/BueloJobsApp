package formacao;

public class FormacaoModel {

    String id;
    String nome;
    String instituicao;
    String data;
    String localidade;
    String url;

    public FormacaoModel(){

    }

    public FormacaoModel(String id, String nome, String instituicao, String data, String localidade, String url) {
        this.id = id;
        this.nome = nome;
        this.instituicao = instituicao;
        this.data = data;
        this.localidade = localidade;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
