package application;

import com.google.gson.annotations.SerializedName;

public class Usuario {
	
	private Long id;

	@SerializedName("primerNombre")
    private String primerNombre;

    @SerializedName("segundoNombre")
    private String segundoNombre;

    @SerializedName("email")
    private String email;

    @SerializedName("contrasena")
    private String contrasena;

    @SerializedName("role")
    private String role;

	public Usuario(String primernombre, String segundonombre, String email, String contrasena, String role) {
		super();
		this.primerNombre = primernombre;
		this.segundoNombre = segundonombre;
		this.email = email;
		this.contrasena = contrasena;
		this.role = role;
	}

	public Usuario() {
		super();
	}

	public Long getId() {
		return id;
	}
	
	public String getPrimernombre() {
		return primerNombre;
	}

	public void setPrimernombre(String primernombre) {
		this.primerNombre = primernombre;
	}

	public String getSegundonombre() {
		return segundoNombre;
	}

	public void setSegundonombre(String segundonombre) {
		this.segundoNombre = segundonombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
