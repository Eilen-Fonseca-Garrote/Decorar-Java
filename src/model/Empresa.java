package model;

import java.io.InputStream;

public class Empresa {
	private int idEmpresa;
	private String nombreEmpresa;
	private String direccionPostal;
	private String emailEmpresa;
	private String telefono;
	private String directorGeneral;
	private String jefeRecursosHumanos;
	private String jefeContabilidad;
	private String secretarioSindicato;
	private InputStream logo;

	public Empresa(int idEmpresa, String nombreEmpresa, String direccionPostal,
			String emailEmpresa, String telefono, String directorGeneral,
			String jefeRecursosHumanos, String jefeContabilidad,
			String secretarioSindicato, InputStream logo) {
		super();
		this.idEmpresa = idEmpresa;
		this.nombreEmpresa = nombreEmpresa;
		this.direccionPostal = direccionPostal;
		this.emailEmpresa = emailEmpresa;
		this.telefono = telefono;
		this.directorGeneral = directorGeneral;
		this.jefeRecursosHumanos = jefeRecursosHumanos;
		this.jefeContabilidad = jefeContabilidad;
		this.secretarioSindicato = secretarioSindicato;
		this.logo = logo;
	}

	public Empresa() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getDireccionPostal() {
		return direccionPostal;
	}

	public void setDireccionPostal(String direccionPostal) {
		this.direccionPostal = direccionPostal;
	}

	public String getEmailEmpresa() {
		return emailEmpresa;
	}

	public void setEmailEmpresa(String emailEmpresa) {
		this.emailEmpresa = emailEmpresa;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDirectorGeneral() {
		return directorGeneral;
	}

	public void setDirectorGeneral(String directorGeneral) {
		this.directorGeneral = directorGeneral;
	}

	public String getJefeRecursosHumanos() {
		return jefeRecursosHumanos;
	}

	public void setJefeRecursosHumanos(String jefeRecursosHumanos) {
		this.jefeRecursosHumanos = jefeRecursosHumanos;
	}

	public String getJefeContabilidad() {
		return jefeContabilidad;
	}

	public void setJefeContabilidad(String jefeContabilidad) {
		this.jefeContabilidad = jefeContabilidad;
	}

	public String getSecretarioSindicato() {
		return secretarioSindicato;
	}

	public void setSecretarioSindicato(String secretarioSindicato) {
		this.secretarioSindicato = secretarioSindicato;
	}

	public InputStream getLogo() {
		return logo;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}
}