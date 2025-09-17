package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import utils.Conexion;
import model.HistorialEventoServicio;

public class HistorialEventoServicioServices {
	Conexion cx;

	public HistorialEventoServicioServices() {
		cx = new Conexion();
	}

	public boolean insertarHistorialEventoServicio(HistorialEventoServicio hes) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"INSERT INTO public.historialeventoservicio(cantidad, costo, descripcionmodificacion, idcontrato, idservicio)VALUES ( ?, ?, ?, ?, ?);");
			ps.setInt(1, hes.getCantidad());
			ps.setDouble(2, hes.getCosto());
			ps.setString(3, hes.getDescripcionModificacion());
			ps.setInt(4, hes.getIdContrato());
			ps.setInt(5, hes.getIdServicio());
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

	public ArrayList<HistorialEventoServicio> consultarHistorialEventoServicios() {
		ArrayList<HistorialEventoServicio> lista = new ArrayList<HistorialEventoServicio>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar().prepareStatement(
					"SELECT * FROM historialeventoservicio");
			rs = ps.executeQuery();
			while (rs.next()) {
				HistorialEventoServicio hes = new HistorialEventoServicio();
				hes.setIdHistorial(rs.getInt("idhistorial"));
				hes.setCantidad(rs.getInt("cantidad"));
				hes.setCosto(rs.getDouble("costo"));
				hes.setFechaModificacion(rs.getTimestamp("fechamodificacion"));
				hes.setDescripcionModificacion(rs
						.getString("descripcionmodificacion"));
				hes.setIdContrato(rs.getInt("idcontrato"));
				hes.setIdServicio(rs.getInt("idservicio"));
				lista.add(hes);
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

	public boolean eliminarHistorialEventoServicio(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"DELETE FROM historialeventoservicio WHERE idhistorial = ?");
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

	public boolean modificarHistorialEventoServicio(HistorialEventoServicio hes) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"UPDATE historialeventoservicio SET cantidad = ?, costo = ?, descripcionmodificacion = ?, idcontrato = ?, idservicio = ? WHERE idhistorial = ?");
			ps.setInt(1, hes.getCantidad());
			ps.setDouble(2, hes.getCosto());
			ps.setString(3, hes.getDescripcionModificacion());
			ps.setInt(4, hes.getIdContrato());
			ps.setInt(5, hes.getIdServicio());
			ps.setInt(6, hes.getIdHistorial());
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
}