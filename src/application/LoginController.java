package application;


import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {
	@FXML
	private TextField tfContraseña;

	@FXML
	private TextField tfEmail;

	@FXML
	private AnchorPane rootPane;

	private static Long idusuario;

	private ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

	@FXML
	public void initialize() {

		Platform.runLater(() -> {
			rootPane.requestFocus();
		});
	}

	public static Long getIdusuario() {
		return idusuario;
	}

	@FXML
	private void iniciarSesion() {
		String email = tfEmail.getText().trim();
		String contrasena = tfContraseña.getText();

		Call<Usuario> call = apiService.loginUsuario(email, contrasena);
		call.enqueue(new Callback<Usuario>() {
		    @Override
		    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
		        if (response.isSuccessful() && response.body() != null) {
		        	Usuario usuarioactual = response.body();
		            System.out.println("✅ Usuario autenticado.");
		            cambiarEscena("Principal.fxml", usuarioactual);
		        } else {
		            System.out.println("❌ Usuario o contraseña incorrectos.");
		        }
		    }

		    @Override
		    public void onFailure(Call<Usuario> call, Throwable t) {
		        System.out.println("❌ Error en la autenticación: " + t.getMessage());
		    }
		});

	}

	private void cambiarEscena(String fxml, Usuario usuarioactual) {
	    // Usamos Platform.runLater para asegurar que la acción se ejecute en el hilo de la interfaz de usuario (FX Application Thread)
	    Platform.runLater(() -> {
	        try {
	            // Cargar el archivo FXML
	            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
	            Scene nuevaEscena = new Scene(loader.load());
	            
	            PrincipalController controller = loader.getController();
	            controller.setNombreUsuario(crearNombre(usuarioactual));
	            idusuario = usuarioactual.getId();
	            

	            // Obtener la ventana (Stage) actual desde el rootPane o cualquier otro contenedor
	            Stage stage = (Stage) rootPane.getScene().getWindow();
	            stage.setScene(nuevaEscena);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    });
	}
	
	private String crearNombre ( Usuario usuarioactual) {
		String primernombre = usuarioactual.getPrimernombre().substring(0, 1).toUpperCase() + usuarioactual.getPrimernombre().substring(1).toLowerCase();
		String segundonombre = usuarioactual.getSegundonombre().substring(0, 1).toUpperCase() + usuarioactual.getSegundonombre().substring(1).toLowerCase();
		return primernombre + " " + segundonombre;
	}
	

}