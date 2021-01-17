package eventos;

public class Evento {

    String id;
    String nome;
    String empresa;
    String data;
    String url;
    String localidade;

   public Evento(){

    }

    public Evento(String id, String nome, String empresa, String data, String url, String localidade) {
        this.id = id;
        this.nome = nome;
        this.empresa = empresa;
        this.data = data;
        this.url = url;
        this.localidade = localidade;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
}
