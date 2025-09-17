package services;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import model.*; // Asegúrate de que tus clases de modelo estén en el paquete 'model'
import utils.Conexion;

public class ClienteServices {
    Conexion cx;

    public ClienteServices() {
        cx = new Conexion();
    }

    public boolean insertarCliente(Cliente c) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("INSERT INTO public.cliente(id, nombrecliente, apellidoscliente, direccioncliente, telefonocliente, emailcliente, tratopreferencial)VALUES (?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, c.getId());
            ps.setString(2, c.getNombreCliente());
            ps.setString(3, c.getApellidosCliente());
            ps.setString(4, c.getDireccionCliente());
            ps.setString(5, c.getTelefonoCliente());
            ps.setString(6, c.getEmailCliente());
            ps.setBoolean(7, c.isTratoPreferencial());
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

    public ArrayList<Cliente> consultarClientes() {
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cx.conectar().prepareStatement("SELECT * FROM cliente");
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getString("id"));
                c.setNombreCliente(rs.getString("nombrecliente"));
                c.setApellidosCliente(rs.getString("apellidoscliente"));
                c.setDireccionCliente(rs.getString("direccioncliente"));
                c.setTelefonoCliente(rs.getString("telefonocliente"));
                c.setEmailCliente(rs.getString("emailcliente"));
                c.setTratoPreferencial(rs.getBoolean("tratopreferencial"));
                lista.add(c);
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

    public boolean eliminarCliente(String id) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("DELETE FROM cliente WHERE id = ?");
            ps.setString(1, id);
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

    public boolean modificarCliente(Cliente c) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("UPDATE cliente SET nombrecliente = ?, apellidoscliente = ?, direccioncliente = ?, telefonocliente = ?, emailcliente = ?, tratopreferencial = ? WHERE id = ?");
            ps.setString(1, c.getNombreCliente());
            ps.setString(2, c.getApellidosCliente());
            ps.setString(3, c.getDireccionCliente());
            ps.setString(4, c.getTelefonoCliente());
            ps.setString(5, c.getEmailCliente());
            ps.setBoolean(6, c.isTratoPreferencial());
            ps.setString(7, c.getId());
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
    
 	public ArrayList<String> obtenerNombresClientes() throws SQLException {
 		ArrayList<String> nombres = new ArrayList<>();
 		String query = "SELECT nombreCliente FROM Cliente";
 		PreparedStatement stmt = cx.conectar().prepareStatement(query);
 		ResultSet rs = stmt.executeQuery();
 		while (rs.next()) {
 			nombres.add(rs.getString("nombreCliente"));
 		}
 		rs.close();
 		stmt.close();
 		return nombres;
 	}

 	public Cliente obtenerCliente(String nombre) throws SQLException {
 		String query = "SELECT * FROM Cliente WHERE nombreCliente = ?";
 		PreparedStatement stmt = cx.conectar().prepareStatement(query);
 		stmt.setString(1, nombre);
 		ResultSet rs = stmt.executeQuery();
 		if (rs.next()) {
 			Cliente Cliente = new Cliente();
 			Cliente.setDireccionCliente(rs.getString("direccioncliente"));
 			Cliente.setNombreCliente(rs.getString("nombreCliente"));
 			Cliente.setTelefonoCliente(rs.getString("telefonoCliente"));
 			Cliente.setEmailCliente(rs.getString("emailCliente"));
 			Cliente.setTratoPreferencial(rs.getBoolean("tratoPreferencial"));
 			rs.close();
 			stmt.close();
 			return Cliente;
 		}
 		rs.close();
 		stmt.close();
 		return null;
 	}

 	// Método para eliminar una Cliente por nombre
 	public boolean eliminarClienteNombre(String nombre) throws SQLException {
 		String query = "DELETE FROM Cliente WHERE nombreCliente = ?";
 		PreparedStatement stmt = cx.conectar().prepareStatement(query);
 		stmt.setString(1, nombre);
 		int filasAfectadas = stmt.executeUpdate();
 		stmt.close();
 		return filasAfectadas > 0; // Devuelve true si se eliminó al menos una
 									// fila
 	}
 	
 	public int getid(String nombreCliente) throws SQLException {
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try {
 			ps = cx.conectar().prepareStatement(
 					"SELECT id FROM Cliente WHERE nombreCliente = ?");
 			ps.setString(1, nombreCliente);
 			rs = ps.executeQuery();

 			if (rs.next()) {
 				return rs.getInt("id");
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