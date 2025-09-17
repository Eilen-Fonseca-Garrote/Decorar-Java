package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import utils.Conexion;
import model.Servicio;

public class ServicioServices {
	Conexion cx;

	public ServicioServices() {
		cx = new Conexion();
	}

	public boolean insertarServicio(Servicio s) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"INSERT INTO public.servicio(nombreservicio, descripcionservicio, categoriaservicio, preciounitario, servicioterceros)VALUES (?, ?, ?, ?, ?);");
			ps.setString(1, s.getNombreServicio());
			ps.setString(2, s.getDescripcionServicio());
			ps.setString(3, s.getCategoriaServicio());
			ps.setDouble(4, s.getPrecioUnitario());
			ps.setBoolean(5, s.isServicioTerceros());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			if (e instanceof PSQLException) {
				// Maneja la excepción PSQLException
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);

			} else {
				// Maneja otras excepciones SQL

			}
			return false;
		}
	}

	public ArrayList<Servicio> consultarServicios() {
		ArrayList<Servicio> lista = new ArrayList<Servicio>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar().prepareStatement("SELECT * FROM servicio");
			rs = ps.executeQuery();
			while (rs.next()) {
				Servicio s = new Servicio();
				s.setIdServicio(rs.getInt("idservicio"));
				s.setNombreServicio(rs.getString("nombreservicio"));
				s.setDescripcionServicio(rs.getString("descripcionservicio"));
				s.setCategoriaServicio(rs.getString("categoriaservicio"));
				s.setPrecioUnitario(rs.getDouble("preciounitario"));
				s.setServicioTerceros(rs.getBoolean("servicioterceros"));
				lista.add(s);
			}
		} catch (SQLException e) {
			if (e instanceof PSQLException) {
				// Maneja la excepción PSQLException
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);

			} else {
				// Maneja otras excepciones SQL

			}
		}
		return lista;
	}

	public boolean eliminarServicio(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement(
					"DELETE FROM servicio WHERE idservicio = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			if (e instanceof PSQLException) {
				// Maneja la excepción PSQLException
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);

			} else {
				// Maneja otras excepciones SQL

			}
			return false;
		}
	}

	public boolean modificarServicio(Servicio s) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"UPDATE servicio SET nombreservicio = ?, descripcionservicio = ?, categoriaservicio = ?, preciounitario = ?, servicioterceros = ? WHERE idservicio = ?");
			ps.setString(1, s.getNombreServicio());
			ps.setString(2, s.getDescripcionServicio());
			ps.setString(3, s.getCategoriaServicio());
			ps.setDouble(4, s.getPrecioUnitario());
			ps.setBoolean(5, s.isServicioTerceros());
			ps.setInt(6, s.getIdServicio());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			if (e instanceof PSQLException) {
				// Maneja la excepción PSQLException
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);

			} else {
				// Maneja otras excepciones SQL

			}
			return false;
		}
	}
	
		public ArrayList<String> obtenerNombresServicios() throws SQLException {
			ArrayList<String> nombres = new ArrayList<>();
			String query = "SELECT nombreServicio FROM Servicio";
			PreparedStatement stmt = cx.conectar().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				nombres.add(rs.getString("nombreServicio"));
			}
			rs.close();
			stmt.close();
			return nombres;
		}

		public boolean eliminarServicio(String nombre) throws SQLException {
			String query = "DELETE FROM Servicio WHERE nombreServicio = ?";
			PreparedStatement stmt = cx.conectar().prepareStatement(query);
			stmt.setString(1, nombre);
			int filasAfectadas = stmt.executeUpdate();
			stmt.close();
			return filasAfectadas > 0; // Devuelve true si se eliminó al menos una
										// fila
		}
		
		public int getIdServicio(String nombreServicio) throws SQLException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = cx.conectar().prepareStatement(
						"SELECT idServicio FROM Servicio WHERE nombreServicio = ?");
				ps.setString(1, nombreServicio);
				rs = ps.executeQuery();

				if (rs.next()) {
					return rs.getInt("idServicio");
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				cx.desconectar();
			}
			return -1; 
		}
}