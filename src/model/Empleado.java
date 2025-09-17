package model;

public class Empleado {
	private int dniEmpleado;
	private String nombreEmpleado;
	private String apellidosEmpleado;
	private String direccionEmpleado;
	private String telefonoEmpleado;
	private String emailEmpleado;
	private String cargo;
	private String departamento;
	private boolean responsabilidadEventos;
	private int idEmpresa;

	public Empleado(int dniEmpleado, String nombreEmpleado,
			String apellidosEmpleado, String direccionEmpleado,
			String telefonoEmpleado, String emailEmpleado, String cargo,
			String departamento, boolean responsabilidadEventos, int idEmpresa) {
		super();
		this.dniEmpleado = dniEmpleado;
		this.nombreEmpleado = nombreEmpleado;
		this.apellidosEmpleado = apellidosEmpleado;
		this.direccionEmpleado = direccionEmpleado;
		this.telefonoEmpleado = telefonoEmpleado;
		this.emailEmpleado = emailEmpleado;
		this.cargo = cargo;
		this.departamento = departamento;
		this.responsabilidadEventos = responsabilidadEventos;
		this.idEmpresa = idEmpresa;
	}

	public Empleado() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public int getDniEmpleado() {
		return dniEmpleado;
	}

	public void setDniEmpleado(int dniEmpleado) {
		this.dniEmpleado = dniEmpleado;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getApellidosEmpleado() {
		return apellidosEmpleado;
	}

	public void setApellidosEmpleado(String apellidosEmpleado) {
		this.apellidosEmpleado = apellidosEmpleado;
	}

	public String getDireccionEmpleado() {
		return direccionEmpleado;
	}

	public void setDireccionEmpleado(String direccionEmpleado) {
		this.direccionEmpleado = direccionEmpleado;
	}

	public String getTelefonoEmpleado() {
		return telefonoEmpleado;
	}

	public void setTelefonoEmpleado(String telefonoEmpleado) {
		this.telefonoEmpleado = telefonoEmpleado;
	}

	public String getEmailEmpleado() {
		return emailEmpleado;
	}

	public void setEmailEmpleado(String emailEmpleado) {
		this.emailEmpleado = emailEmpleado;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public boolean isResponsabilidadEventos() {
		return responsabilidadEventos;
	}

	public void setResponsabilidadEventos(boolean responsabilidadEventos) {
		this.responsabilidadEventos = responsabilidadEventos;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
}