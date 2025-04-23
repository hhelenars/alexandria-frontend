package application;


public class Libro {

	private Long id;
	private String titulo;
	private String autor;
	private Integer paginas;
	private String categoria;
	private Integer anioPublicacion; 
	private String descripcion;
	private String archivo_url;

	// Constructor vacío (Obligatorio para JPA)
	public Libro() {
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Integer getPaginas() {
		return paginas;
	}

	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getAnioPublicacion() {
	    return anioPublicacion;
	}

	public void setAnioPublicacion(Integer anioPublicacion) {
	    this.anioPublicacion = anioPublicacion;
	}

	public String getArchivo_url() {
		return archivo_url;
	}

	public void setArchivo_url(String archivo_url) {
		this.archivo_url = archivo_url;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
