package application;

import java.util.List;
import java.util.Map;

public class Anotacion {
    private Long idBiblioteca;
    private Map<Integer, List<Subrayado>> anotaciones;

    public Anotacion() {}

    public Anotacion(Long idBiblioteca, Map<Integer, List<Subrayado>> anotaciones) {
        this.idBiblioteca = idBiblioteca;
        this.anotaciones = anotaciones;
    }

    public Long getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(Long idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public Map<Integer, List<Subrayado>> getAnotaciones() {
        return anotaciones;
    }

    public void setAnotaciones(Map<Integer, List<Subrayado>> anotaciones) {
        this.anotaciones = anotaciones;
    }
}
