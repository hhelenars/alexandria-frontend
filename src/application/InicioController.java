package application;

import java.net.URL;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioController {

    @FXML
    private Button btnBuscar, btnHome;

    @FXML
    private Button btnIniciarSesion, btnCrearCuenta;

    @FXML
    private ListView<String> listalibros;
    
    private ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

    @FXML
    public void initialize() {
        // Configurar imágenes para los botones
        setButtonImage(btnHome, "/multimedia/casa.png", 20); // 30 pixeles
        setButtonImage(btnBuscar, "/multimedia/buscar.png", 20);      
        
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

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnIniciarSesion) {
            System.out.println("🔐 Ir a Iniciar Sesión");
            // Aquí puedes cargar la pantalla de Iniciar Sesión
        } else if (event.getSource() == btnCrearCuenta) {
            System.out.println("📝 Ir a Crear Cuenta");
            // Aquí puedes cargar la pantalla de Registro
        }
    }
    private void setButtonImage(Button button, String imagePath, double size) {
        URL url = getClass().getResource(imagePath);
        if (url == null) {
            System.err.println("⚠ No se encontró la imagen: " + imagePath);
            return;
        }

        Image img = new Image(url.toString());
        ImageView view = new ImageView(img);
        
        // Ajusta el tamaño sin distorsionar la imagen
        view.setPreserveRatio(true);
        view.setFitHeight(size);
        view.setFitWidth(size);
        
        button.setGraphic(view);
        
        // Poner la imagen a la izquierda del texto
        button.setGraphic(view);
        button.setContentDisplay(javafx.scene.control.ContentDisplay.LEFT);
    }
}

