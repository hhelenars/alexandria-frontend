package application;


import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
	                    System.out.println("✅ Usuario registrado correctamente.");
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
}
