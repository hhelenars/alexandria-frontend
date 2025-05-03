package application;

public class Subrayado {
    private String textoSubrayado;
    private String comentario;

    public Subrayado() {}

    public Subrayado(String textoSubrayado, String comentario) {
        this.textoSubrayado = textoSubrayado;
        this.comentario = comentario;
    }

    public String getTextoSubrayado() {
        return textoSubrayado;
    }

    public void setTextoSubrayado(String textoSubrayado) {
        this.textoSubrayado = textoSubrayado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}

