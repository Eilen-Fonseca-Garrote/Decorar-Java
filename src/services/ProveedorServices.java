package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Proveedor;

import org.postgresql.util.PSQLException;

import utils.Conexion;

public class ProveedorServices {
	Conexion cx;

	public ProveedorServices() {
		cx = new Conexion();
	}

	public boolean insertarProveedor(Proveedor p) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"INSERT INTO public.proveedor(nombreproveedor, tiposervicio, direccion, telefono, email, responsable, idservicio)VALUES (?, ?, ?, ?, ?, ?, ?);");
			ps.setString(1, p.getNombreProveedor());
			ps.setString(2, p.getTipoServicio());
			ps.setString(3, p.getDireccion());
			ps.setString(4, p.getTelefono());
			ps.setString(5, p.getEmail());
			ps.setString(6, p.getResponsable());
			ps.setInt(7, p.getIdServicio());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
	        if (e instanceof PSQLException) {
	            // Maneja la excepción PSQLException
	            JOptionPane.showMessageDialog(null, 
	                    e.getMessage(),
	                    "Error SQL",
	                    JOptionPane.ERROR_MESSAGE);
	            
	        } else {
	            // Maneja otras excepciones SQL
	            
	        }
	        return false;
	    }
	}

	public ArrayList<Proveedor> consultarProveedores() {
		ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar().prepareStatement("SELECT * FROM proveedor");
			rs = ps.executeQuery();
			while (rs.next()) {
				Proveedor p = new Proveedor();
				p.setIdProveedor(rs.getInt("idproveedor"));
				p.setNombreProveedor(rs.getString("nombreproveedor"));
				p.setTipoServicio(rs.getString("tiposervicio"));
				p.setDireccion(rs.getString("direccion"));
				p.setTelefono(rs.getString("telefono"));
				p.setEmail(rs.getString("email"));
				p.setResponsable(rs.getString("responsable"));
				p.setEstadoProveedor(rs.getBoolean("estadoproveedor"));
				p.setIdServicio(rs.getInt("idservicio"));
				lista.add(p);
			}
		} catch (SQLException e) {
	        if (e instanceof PSQLException) {
	            // Maneja la excepción PSQLException
	            JOptionPane.showMessageDialog(null, 
	                    e.getMessage(),
	                    "Error SQL",
	                    JOptionPane.ERROR_MESSAGE);
	            
	        } else {
	            // Maneja otras excepciones SQL
	            
	        }
	    }
		return lista;
	}

	public boolean eliminarProveedor(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement(
					"DELETE FROM proveedor WHERE idproveedor = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
	        if (e instanceof PSQLException) {
	            // Maneja la excepción PSQLException
	            JOptionPane.showMessageDialog(null, 
	                    e.getMessage(),
	                    "Error SQL",
	                    JOptionPane.ERROR_MESSAGE);
	            
	        } else {
	            // Maneja otras excepciones SQL
	            
	        }
	        return false;
	    }
	}

	public boolean modificarProveedor(Proveedor p) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"UPDATE proveedor SET nombreproveedor = ?, tiposervicio = ?, direccion = ?, telefono = ?, email = ?, responsable = ?, idservicio = ? WHERE idproveedor = ?");
			ps.setString(1, p.getNombreProveedor());
			ps.setString(2, p.getTipoServicio());
			ps.setString(3, p.getDireccion());
			ps.setString(4, p.getTelefono());
			ps.setString(5, p.getEmail());
			ps.setString(6, p.getResponsable());
			ps.setInt(7, p.getIdServicio());
			ps.setInt(8, p.getIdProveedor());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
	        if (e instanceof PSQLException) {
	            // Maneja la excepción PSQLException
	            JOptionPane.showMessageDialog(null, 
	                    e.getMessage(),
	                    "Error SQL",
	                    JOptionPane.ERROR_MESSAGE);
	            
	        } else {
	            // Maneja otras excepciones SQL
	            
	        }
	        return false;
	    }
	}
	
		public ArrayList<String> obtenerNombresProveedors() throws SQLException {
			ArrayList<String> nombres = new ArrayList<>();
			String query = "SELECT nombreProveedor FROM Proveedor";
			PreparedStatement stmt = cx.conectar().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				nombres.add(rs.getString("nombreProveedor"));
			}
			rs.close();
			stmt.close();
			return nombres;
		}

		public boolean eliminarProveedor(String nombre) throws SQLException {
			String query = "DELETE FROM Proveedor WHERE nombreProveedor = ?";
			PreparedStatement stmt = cx.conectar().prepareStatement(query);
			stmt.setString(1, nombre);
			int filasAfectadas = stmt.executeUpdate();
			stmt.close();
			return filasAfectadas > 0; // Devuelve true si se eliminó al menos una
										// fila
		}
		
		public int getidProveedor(String nombreProveedor) throws SQLException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = cx.conectar().prepareStatement(
						"SELECT idProveedor FROM Proveedor WHERE nombreProveedor = ?");
				ps.setString(1, nombreProveedor);
				rs = ps.executeQuery();

				if (rs.next()) {
					return rs.getInt("idProveedor");
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