package model;

public class Proveedor {
	private int idProveedor;
	private String nombreProveedor;
	private String tipoServicio;
	private String direccion;
	private String telefono;
	private String email;
	private String responsable;
	private boolean estadoProveedor;
	private int idServicio;

	public Proveedor(int idProveedor, String nombreProveedor,
			String tipoServicio, String direccion, String telefono,
			String email, String responsable, boolean estadoProveedor,
			int idServicio) {
		super();
		this.idProveedor = idProveedor;
		this.nombreProveedor = nombreProveedor;
		this.tipoServicio = tipoServicio;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.responsable = responsable;
		this.estadoProveedor = estadoProveedor;
		this.idServicio = idServicio;
	}

	public Proveedor() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public boolean isEstadoProveedor() {
		return estadoProveedor;
	}

	public void setEstadoProveedor(boolean estadoProveedor) {
		this.estadoProveedor = estadoProveedor;
	}

	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}
}
