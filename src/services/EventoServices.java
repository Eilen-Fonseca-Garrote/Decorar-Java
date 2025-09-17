package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import model.Evento;
import utils.Conexion;

public class EventoServices {
	Conexion cx;

	public EventoServices() {
		cx = new Conexion();
	}

	public boolean insertarEvento(Evento e) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"INSERT INTO public.evento(nombreevento, tipoevento, fechainicio, fechafin, ubicacion, numeroinvitados, temasdecoracion, id, dniempleado)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
			ps.setString(1, e.getNombreEvento());
			ps.setString(2, e.getTipoEvento());
			ps.setTimestamp(3, e.getFechaInicio());
			ps.setTimestamp(4, e.getFechaFin());
			ps.setString(5, e.getUbicacion());
			ps.setInt(6, e.getNumeroInvitados());
			ps.setString(7, e.getTemasDecoracion());
			ps.setString(8, e.getId());
			ps.setInt(9, e.getDniEmpleado());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e1) {
	        if (e1 instanceof PSQLException) {
	            // Maneja la excepción PSQLException
	            JOptionPane.showMessageDialog(null, 
	                    e1.getMessage(),
	                    "Error SQL",
	                    JOptionPane.ERROR_MESSAGE);
	            e1.printStackTrace();
	        } else {
	            // Maneja otras excepciones SQL
	            e1.printStackTrace();
	        }
	        return false;
	    }
	}

	public ArrayList<Evento> consultarEventos() {
		ArrayList<Evento> lista = new ArrayList<Evento>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar().prepareStatement("SELECT * FROM evento");
			rs = ps.executeQuery();
			while (rs.next()) {
				Evento e = new Evento();
				e.setIdEvento(rs.getInt("idevento"));
				e.setNombreEvento(rs.getString("nombreevento"));
				e.setTipoEvento(rs.getString("tipoevento"));
				e.setFechaInicio(rs.getTimestamp("fechainicio"));
				e.setFechaFin(rs.getTimestamp("fechafin"));
				e.setFechaModificacion(rs.getTimestamp("fechamodificacion"));
				e.setUbicacion(rs.getString("ubicacion"));
				e.setNumeroInvitados(rs.getInt("numeroinvitados"));
				e.setTemasDecoracion(rs.getString("temasdecoracion"));
				e.setId(rs.getString("id"));
				e.setDniEmpleado(rs.getInt("dniempleado"));
				lista.add(e);
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

	public boolean eliminarEvento(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement(
					"DELETE FROM evento WHERE idevento = ?");
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

	public boolean modificarEvento(Evento e) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"UPDATE evento SET nombreevento = ?, tipoevento = ?, fechainicio = ?, fechafin = ?, ubicacion = ?, numeroinvitados = ?, temasdecoracion = ?, id = ?, dniempleado = ? WHERE idevento = ?");
			ps.setString(1, e.getNombreEvento());
			ps.setString(2, e.getTipoEvento());
			ps.setTimestamp(3, e.getFechaInicio());
			ps.setTimestamp(4, e.getFechaFin());
			ps.setString(5, e.getUbicacion());
			ps.setInt(6, e.getNumeroInvitados());
			ps.setString(7, e.getTemasDecoracion());
			ps.setString(8, e.getId());
			ps.setInt(9, e.getDniEmpleado());
			ps.setInt(10, e.getIdEvento());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e1) {
	        if (e1 instanceof PSQLException) {
	            // Maneja la excepción PSQLException
	            JOptionPane.showMessageDialog(null, 
	                    e1.getMessage(),
	                    "Error SQL",
	                    JOptionPane.ERROR_MESSAGE);
	            e1.printStackTrace();
	        } else {
	            // Maneja otras excepciones SQL
	            e1.printStackTrace();
	        }
	        return false;
	    }
	}
}