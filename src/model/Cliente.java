package model;

public class Cliente {
	private String id;
	private String nombreCliente;
	private String apellidosCliente;
	private String direccionCliente;
	private String telefonoCliente;
	private String emailCliente;
	private boolean tratoPreferencial;

	public Cliente(String id, String nombreCliente, String apellidosCliente,
			String direccionCliente, String telefonoCliente,
			String emailCliente, boolean tratoPreferencial) {
		super();
		this.id = id;
		this.nombreCliente = nombreCliente;
		this.apellidosCliente = apellidosCliente;
		this.direccionCliente = direccionCliente;
		this.telefonoCliente = telefonoCliente;
		this.emailCliente = emailCliente;
		this.tratoPreferencial = tratoPreferencial;
	}

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getApellidosCliente() {
		return apellidosCliente;
	}

	public void setApellidosCliente(String apellidosCliente) {
		this.apellidosCliente = apellidosCliente;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public String getTelefonoCliente() {
		return telefonoCliente;
	}

	public void setTelefonoCliente(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public boolean isTratoPreferencial() {
		return tratoPreferencial;
	}

	public void setTratoPreferencial(boolean tratoPreferencial) {
		this.tratoPreferencial = tratoPreferencial;
	}
}