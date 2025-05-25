package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Inicio.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/Principal.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Lector EPUB Alexandria");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
}