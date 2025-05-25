package application;


import java.io.IOException;

import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RegistroController {
		
	
		@FXML
	    private ComboBox<String> cbRol;

	    @FXML
	    private TextField tfApellidos;

	    @FXML
	    private TextField tfContraseña;

	    @FXML
	    private TextField tfEmail;

	    @FXML
	    private TextField tfPrimer_Nombre;
	    
	    @FXML
	    private Button bRegistrarse;
	    	    
	    @FXML
	    private AnchorPane rootPane;
	    
	    private static Long idusuario;
	    
	    private ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
	    
	    @FXML
	    public void initialize() {
	    	
	    	cbRol.getItems().addAll("Rol","Profesor", "Alumno", "Otro");
	    	cbRol.setValue("Rol");
	    	
	    	 Platform.runLater(() -> {
	             rootPane.requestFocus(); 
	         });
	    }
	    
	   @FXML
	    private void registrarUsuario() {
	        Usuario nuevoUsuario = new Usuario();
	        nuevoUsuario.setPrimernombre(tfPrimer_Nombre.getText());
	        nuevoUsuario.setSegundonombre(tfApellidos.getText());
	        nuevoUsuario.setEmail(tfEmail.getText());
	        nuevoUsuario.setContrasena(tfContraseña.getText());
	        nuevoUsuario.setRole(cbRol.getSelectionModel().getSelectedItem().toUpperCase());

	        Gson gson = new Gson();
	        String jsonUsuario = gson.toJson(nuevoUsuario);
	        System.out.println("📤 JSON enviado: " + jsonUsuario);

	        Call<Usuario> call = apiService.crearUusario(nuevoUsuario);
	        call.enqueue(new Callback<Usuario>() {
	            @Override
	            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
	                if (response.isSuccessful()) {
	                	Usuario usuarioactual = response.body();
			            System.out.println("✅ Usuario autenticado.");
			            cambiarEscena("Principal.fxml", usuarioactual);
	                } else {
	                    System.out.println("❌ Error al registrar usuario: " + response.code());
	                }
	            }

	            @Override
	            public void onFailure(Call<Usuario> call, Throwable t) {
	                System.out.println("❌ Error en la solicitud: " + t.getMessage());
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
