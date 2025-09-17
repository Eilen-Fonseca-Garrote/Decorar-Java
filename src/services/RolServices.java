package services;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import utils.Conexion;
import model.Rol;

public class RolServices {
    Conexion cx;

    public RolServices() {
        cx = new Conexion();
    }

    public boolean insertarRol(Rol rol) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("INSERT INTO public.rol( nombrerol)VALUES ( ?);");
            ps.setString(1, rol.getNombreRol());
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

    public ArrayList<Rol> consultarRoles() {
        ArrayList<Rol> lista = new ArrayList<Rol>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cx.conectar().prepareStatement("SELECT * FROM rol");
            rs = ps.executeQuery();
            while (rs.next()) {
                Rol r = new Rol(rs.getInt("idrol"), rs.getString("nombrerol"));
                lista.add(r);
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

    public boolean eliminarRol(int id) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("DELETE FROM rol WHERE idrol = ?");
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

    public boolean modificarRol(Rol rol) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("UPDATE rol SET nombrerol = ? WHERE idrol = ?");
            ps.setString(1, rol.getNombreRol());
            ps.setInt(2, rol.getIdRol());
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
    
    public Rol obtenerRolPorId(int idRol) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cx.conectar().prepareStatement("SELECT * FROM rol WHERE idrol = ?");
            ps.setInt(1, idRol);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Rol(rs.getInt("idrol"), rs.getString("nombrerol"));
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
        return null; 
    }
}
