package application;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class PrincipalBibliotecaController {

    @FXML
    private ListView<String> listaBiblioteca;
    
    private ApiService apiService = RetrofitClient.getClient().create(ApiService.class);


    @FXML
    public void initialize() {
    	cargarBiblioteca();
    }
    
    private void cargarBiblioteca() {
    	Long usuarioId = LoginController.getIdusuario();
    	
    	if (usuarioId == null) {
            System.out.println("❌ No hay usuario autenticado.");
            return;
        }
    	
        Call<List<Libro>> call = apiService.getObtenerLecturas(usuarioId);

        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Libro> libros = response.body();
                    for (Libro libro : libros) {
                    	listaBiblioteca.getItems().add(libro.getTitulo() + " - " + libro.getAutor());
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
