package application;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalHomeController {

    @FXML
    private ListView<String> listaRecomendaciones;
    
    private ApiService apiService = RetrofitClient.getClient().create(ApiService.class);


    @FXML
    public void initialize() {
        // Cargar libros recomendados
        cargarLibros();
    }
    
    private void cargarLibros() {
        Call<List<Libro>> call = apiService.getTodosLibros();

        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Libro> libros = response.body();
                    for (Libro libro : libros) {
                    	listaRecomendaciones.getItems().add(libro.getTitulo() + " - " + libro.getAutor());
                    }
                } else {
                    System.out.println("❌ Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
                System.out.println("❌ Fallo en la petición: " + t.getMessage());
            }
        });
    }
}


