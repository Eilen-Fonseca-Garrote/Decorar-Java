package model;

public class EventoServicio {
	private int idServicioEvento;
	private int idServicio;
	private int idEvento;
	private int cantidad;
	private int idProveedor;

	public EventoServicio(int idServicioEvento, int idServicio, int idEvento,
			int cantidad, int idProveedor) {
		super();
		this.idServicioEvento = idServicioEvento;
		this.idServicio = idServicio;
		this.idEvento = idEvento;
		this.cantidad = cantidad;
		this.idProveedor = idProveedor;
	}

	public EventoServicio() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public int getIdServicioEvento() {
		return idServicioEvento;
	}

	public void setIdServicioEvento(int idServicioEvento) {
		this.idServicioEvento = idServicioEvento;
	}

	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
}