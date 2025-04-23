package application;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.List;

public class PrincipalCompatidasController {

    @FXML
    private ListView<String> listaRecomendaciones;

    private final List<String> librosCompartidos = new ArrayList<>();

    @FXML
    public void initialize() {
        // 📌 Cargar lista de libros compartidos
        librosCompartidos.addAll(List.of(
            "📖 El club de la pelea - Chuck Palahniuk",
            "📚 La sombra del viento - Carlos Ruiz Zafón",
            "📕 Los juegos del hambre - Suzanne Collins",
            "📙 El nombre del viento - Patrick Rothfuss",
            "📗 1984 - George Orwell",
            "📘 Fundación - Isaac Asimov",
            "📒 Crónica de una muerte anunciada - Gabriel García Márquez",
            "📙 Rayuela - Julio Cortázar"
        ));

        // 📌 Mostrar la lista en la interfaz
        listaRecomendaciones.getItems().setAll(librosCompartidos);
    }
}
