package application;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;

public class PrincipalController {

    @FXML
    private Button btnHome, btnBuscar, btnFavoritos, btnBiblioteca, btnCompartidas;

    @FXML
    private Label nombreUsuario;

    @FXML
    private AnchorPane contenidoCentral; // Donde cambiaremos el contenido

    @FXML
    private ListView<String> listaRecomendaciones; // Si quieres mostrar recomendaciones

    @FXML
    public void initialize() {
             
        // Configurar imágenes en los botones
        setButtonImage(btnHome, "/multimedia/casa.png", 20);
        setButtonImage(btnBuscar, "/multimedia/buscar.png", 20);
        setButtonImage(btnFavoritos, "/multimedia/favoritos.png", 20);
        setButtonImage(btnBiblioteca, "/multimedia/biblioteca.png", 20);
        setButtonImage(btnCompartidas, "/multimedia/lecturas_compartidas.png", 20);

        btnHome.setOnAction(e -> cargarVista("PrincipalHome.fxml"));
        btnBuscar.setOnAction(e -> cargarVista("PrincipalBuscar.fxml"));
        btnFavoritos.setOnAction(e -> cargarVista("PrincipalFavoritos.fxml"));
        btnBiblioteca.setOnAction(e -> cargarVista("PrincipalBiblioteca.fxml"));
        btnCompartidas.setOnAction(e -> cargarVista("PrincipalCompartidas.fxml"));

        // Cargar Home por defecto al iniciar
        cargarVista("PrincipalHome.fxml");
        
        
    }
    
    
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario.setText(nombreUsuario);;
	}


	private void cargarVista(String fxmlPath) {
        try {
            URL fxmlLocation = getClass().getResource(fxmlPath);
            if (fxmlLocation == null) {
                System.err.println("❌ ERROR: No se encontró el archivo FXML en la ruta: " + fxmlPath);
                System.err.println("📌 Asegúrate de que " + fxmlPath + " está en src/application/");
                throw new IOException("No se encontró el archivo FXML: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Node vista = loader.load();
            contenidoCentral.getChildren().clear();
            contenidoCentral.getChildren().add(vista);
            System.out.println("✅ Vista cargada correctamente: " + fxmlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setButtonImage(Button button, String imagePath, double size) {
        if (button == null) return;

        URL url = getClass().getResource(imagePath);
        if (url == null) {
            System.err.println("⚠ No se encontró la imagen: " + imagePath);
            return;
        }

        Image img = new Image(url.toString());
        ImageView view = new ImageView(img);

        // Ajustar tamaño sin distorsionar
        view.setPreserveRatio(true);
        view.setFitHeight(size);
        view.setFitWidth(size);

        // Asignar imagen al botón
        button.setGraphic(view);
        button.setContentDisplay(javafx.scene.control.ContentDisplay.LEFT);
    }
    
   
}
