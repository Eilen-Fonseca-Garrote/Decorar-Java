package model;

import java.sql.Timestamp;

public class Evento {
	private int idEvento;
	private String nombreEvento;
	private String tipoEvento;
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private Timestamp fechaModificacion;
	private String ubicacion;
	private int numeroInvitados;
	private String temasDecoracion;
	private String id;
	private int dniEmpleado;

	public Evento(int idEvento, String nombreEvento, String tipoEvento,
			Timestamp fechaInicio, Timestamp fechaFin,
			Timestamp fechaModificacion, String ubicacion, int numeroInvitados,
			String temasDecoracion, String id, int dniEmpleado) {
		super();
		this.idEvento = idEvento;
		this.nombreEvento = nombreEvento;
		this.tipoEvento = tipoEvento;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaModificacion = fechaModificacion;
		this.ubicacion = ubicacion;
		this.numeroInvitados = numeroInvitados;
		this.temasDecoracion = temasDecoracion;
		this.id = id;
		this.dniEmpleado = dniEmpleado;
	}

	public Evento() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public int getNumeroInvitados() {
		return numeroInvitados;
	}

	public void setNumeroInvitados(int numeroInvitados) {
		this.numeroInvitados = numeroInvitados;
	}

	public String getTemasDecoracion() {
		return temasDecoracion;
	}

	public void setTemasDecoracion(String temasDecoracion) {
		this.temasDecoracion = temasDecoracion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDniEmpleado() {
		return dniEmpleado;
	}

	public void setDniEmpleado(int dniEmpleado) {
		this.dniEmpleado = dniEmpleado;
	}
}