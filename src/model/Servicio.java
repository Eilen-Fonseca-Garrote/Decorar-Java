package model;

public class Servicio {
	private int idServicio;
	private String nombreServicio;
	private String descripcionServicio;
	private String categoriaServicio;
	private double precioUnitario;
	private boolean servicioTerceros;

	public Servicio(int idServicio, String nombreServicio,
			String descripcionServicio, String categoriaServicio,
			double precioUnitario, boolean servicioTerceros) {
		super();
		this.idServicio = idServicio;
		this.nombreServicio = nombreServicio;
		this.descripcionServicio = descripcionServicio;
		this.categoriaServicio = categoriaServicio;
		this.precioUnitario = precioUnitario;
		this.servicioTerceros = servicioTerceros;
	}

	public Servicio() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public String getDescripcionServicio() {
		return descripcionServicio;
	}

	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}

	public String getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(String categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public boolean isServicioTerceros() {
		return servicioTerceros;
	}

	public void setServicioTerceros(boolean servicioTerceros) {
		this.servicioTerceros = servicioTerceros;
	}
}