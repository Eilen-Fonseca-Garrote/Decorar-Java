package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import utils.Conexion;
import model.EventoServicio;

public class EventoServicioServices {
	Conexion cx;

	public EventoServicioServices() {
		cx = new Conexion();
	}

	public boolean insertarEventoServicio(EventoServicio es) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"INSERT INTO public.eventoservicio(idservicio, idevento, cantidad, idproveedor)VALUES (?, ?, ?, ?);");
			ps.setInt(1, es.getIdServicio());
			ps.setInt(2, es.getIdEvento());
			ps.setInt(3, es.getCantidad());
			ps.setInt(4, es.getIdProveedor());
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

	public ArrayList<EventoServicio> consultarEventoServicios() {
		ArrayList<EventoServicio> lista = new ArrayList<EventoServicio>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar().prepareStatement("SELECT * FROM eventoservicio");
			rs = ps.executeQuery();
			while (rs.next()) {
				EventoServicio es = new EventoServicio();
				es.setIdServicioEvento(rs.getInt("idservicioevento"));
				es.setIdServicio(rs.getInt("idservicio"));
				es.setIdEvento(rs.getInt("idevento"));
				es.setCantidad(rs.getInt("cantidad"));
				es.setIdProveedor(rs.getInt("idproveedor"));
				lista.add(es);
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

	public boolean eliminarEventoServicio(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement(
					"DELETE FROM eventoservicio WHERE idservicioevento = ?");
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

	public boolean modificarEventoServicio(EventoServicio es) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"UPDATE eventoservicio SET idservicio = ?, idevento = ?, cantidad = ?, idproveedor = ? WHERE idservicioevento = ?");
			ps.setInt(1, es.getIdServicio());
			ps.setInt(2, es.getIdEvento());
			ps.setInt(3, es.getCantidad());
			ps.setInt(4, es.getIdProveedor());
			ps.setInt(5, es.getIdServicioEvento());
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