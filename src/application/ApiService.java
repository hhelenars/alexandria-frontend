package application;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
	
	@GET("/api/libros/{archivo}/url")
	Call<String> getUrlDelLibro(@Path("archivo") String archivo);

	// Endpoint para obtener las anotaciones de un libro en base al archivo
    @GET("/api/libros/{archivo}/anotaciones")
    Call<List<Anotacion>> getAnotacionesDelLibro(@Path("archivo") String archivo);
    
    @GET("/api/libros/todos")
    Call<List<Libro>> getTodosLibros();
    
    @GET("/api/libros/buscar")
    Call<List<Libro>> getBuscarLibros(@Query("texto") String texto);
    
    @GET("/api/usuarios/login")
    Call<Usuario> loginUsuario(@Query("email") String email, @Query("contrasena") String contrasena);

    @POST("/api/usuarios/crear")
    Call<Usuario> crearUusario(@Body Usuario usuario);
    
    @GET("/api/biblioteca/favoritos")
    Call<List<Libro>> getObtenerFavoritos(@Query("usuarioId") Long usuarioid);
    
    @GET("/api/biblioteca/lecturas")
    Call<List<Libro>> getObtenerLecturas(@Query("usuarioId") Long usuarioid);
    
    
}

