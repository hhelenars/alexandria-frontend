package application;



import java.util.List;

public class Anotacion {
    private int pagina;
    private List<String> subrayado;
    private List<String> comentarios;

    // Constructor vacío (para que JPA/Gson pueda usarlo)
    public Anotacion() {}

    public Anotacion(int pagina, List<String> subrayado, List<String> comentarios) {
        this.pagina = pagina;
        this.subrayado = subrayado;
        this.comentarios = comentarios;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public List<String> getSubrayado() {
        return subrayado;
    }

    public void setSubrayado(List<String> subrayado) {
        this.subrayado = subrayado;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }
}
