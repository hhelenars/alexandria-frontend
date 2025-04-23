package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class PrincipalBuscarController {

    // Asegúrate de que en el FXML el TextField tenga fx:id="txtBuscar"
    @FXML
    private TextField txtBuscar;

    @FXML
    private ListView<String> listalibros;
    
    private ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

    @FXML
    public void initialize() {
    	 cargarLibros();
        // Configurar el evento para filtrar la lista mientras se escribe
    	 txtBuscar.setOnKeyReleased(this::filtrarLibros);
    }
    
    private void cargarLibros() {
        Call<List<Libro>> call = apiService.getTodosLibros();

        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Libro> libros = response.body();
                    for (Libro libro : libros) {
                    	listalibros.getItems().add(libro.getTitulo() + " - " + libro.getAutor());
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

    private void filtrarLibros(KeyEvent event) {
        String filtro = txtBuscar.getText().trim();
        listalibros.getItems().clear();

        // Mandamos el mismo texto como título y autor (para que busque por ambos)
        Call<List<Libro>> call = apiService.getBuscarLibros(filtro);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Platform.runLater(() -> {
                        listalibros.getItems().setAll(
                            response.body().stream()
                                .map(libro -> libro.getTitulo() + " - " + libro.getAutor())
                                .toList()
                        );
                    });
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

