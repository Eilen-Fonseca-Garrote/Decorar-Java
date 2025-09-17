package model;

import java.sql.Timestamp;

public class Contrato {
	private int idContrato;
	private Timestamp fechaContrato;
	private String terminosCondiciones;
	private double preciosNegociados;
	private int idEmpresa;
	private int idEvento;
	private String id;
	private double tasaDescuento;

	public Contrato(int idContrato, Timestamp fechaContrato,
			String terminosCondiciones, double preciosNegociados,
			int idEmpresa, int idEvento, String id, double tasaDescuento) {
		super();
		this.idContrato = idContrato;
		this.fechaContrato = fechaContrato;
		this.terminosCondiciones = terminosCondiciones;
		this.preciosNegociados = preciosNegociados;
		this.idEmpresa = idEmpresa;
		this.idEvento = idEvento;
		this.id = id;
		this.tasaDescuento = tasaDescuento;
	}

	public Contrato() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public int getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(int idContrato) {
		this.idContrato = idContrato;
	}

	public Timestamp getFechaContrato() {
		return fechaContrato;
	}

	public void setFechaContrato(Timestamp fechaContrato) {
		this.fechaContrato = fechaContrato;
	}

	public String getTerminosCondiciones() {
		return terminosCondiciones;
	}

	public void setTerminosCondiciones(String terminosCondiciones) {
		this.terminosCondiciones = terminosCondiciones;
	}

	public double getPreciosNegociados() {
		return preciosNegociados;
	}

	public void setPreciosNegociados(double preciosNegociados) {
		this.preciosNegociados = preciosNegociados;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getTasaDescuento() {
		return tasaDescuento;
	}

	public void setTasaDescuento(double tasaDescuento) {
		this.tasaDescuento = tasaDescuento;
	}

}