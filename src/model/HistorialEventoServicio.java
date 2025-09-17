package model;

import java.sql.Timestamp;

public class HistorialEventoServicio {
	private int idHistorial;
	private int cantidad;
	private double costo;
	private Timestamp fechaModificacion;
	private String descripcionModificacion;
	private int idContrato;
	private int idServicio;

	public HistorialEventoServicio(int idHistorial, int cantidad, double costo,
			Timestamp fechaModificacion, String descripcionModificacion,
			int idContrato, int idServicio) {
		super();
		this.idHistorial = idHistorial;
		this.cantidad = cantidad;
		this.costo = costo;
		this.fechaModificacion = fechaModificacion;
		this.descripcionModificacion = descripcionModificacion;
		this.idContrato = idContrato;
		this.idServicio = idServicio;
	}

	public HistorialEventoServicio() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public int getIdHistorial() {
		return idHistorial;
	}

	public void setIdHistorial(int idHistorial) {
		this.idHistorial = idHistorial;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getDescripcionModificacion() {
		return descripcionModificacion;
	}

	public void setDescripcionModificacion(String descripcionModificacion) {
		this.descripcionModificacion = descripcionModificacion;
	}

	public int getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(int idContrato) {
		this.idContrato = idContrato;
	}

	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}
}