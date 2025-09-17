package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import utils.Conexion;
import model.Empleado;

public class EmpleadoServices {
    Conexion cx;

    public EmpleadoServices() {
        cx = new Conexion();
    }

    public boolean insertarEmpleado(Empleado e) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("INSERT INTO public.empleado(nombreempleado, apellidosempleado, direccionempleado, telefonoempleado, emailempleado, cargo, departamento,idempresa)VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, e.getNombreEmpleado());
            ps.setString(2, e.getApellidosEmpleado());
            ps.setString(3, e.getDireccionEmpleado());
            ps.setString(4, e.getTelefonoEmpleado());
            ps.setString(5, e.getEmailEmpleado());
            ps.setString(6, e.getCargo());
            ps.setString(7, e.getDepartamento());
            ps.setInt(8, e.getIdEmpresa());
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

    public ArrayList<Empleado> consultarEmpleados() {
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cx.conectar().prepareStatement("SELECT * FROM empleado");
            rs = ps.executeQuery();
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setDniEmpleado(rs.getInt("dniempleado"));
                e.setNombreEmpleado(rs.getString("nombreempleado"));
                e.setApellidosEmpleado(rs.getString("apellidosEmpleado"));
                e.setDireccionEmpleado(rs.getString("direccionempleado"));
                e.setTelefonoEmpleado(rs.getString("telefonoempleado"));
                e.setEmailEmpleado(rs.getString("emailEmpleado"));
                e.setCargo(rs.getString("cargo"));
                e.setDepartamento(rs.getString("departamento"));
                e.setResponsabilidadEventos(rs.getBoolean("responsabilidadEventos"));
                e.setIdEmpresa(rs.getInt("idempresa"));
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

    public boolean eliminarEmpleado(Integer id) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("DELETE FROM empleado WHERE dniempleado = ?");
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

    public boolean modificarEmpleado(Empleado e) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("UPDATE empleado SET nombreempleado = ?, apellidosempleado = ?, direccionempleado = ?, telefonoempleado = ?, emailempleado = ?, cargo = ?, departamento = ?, idempresa = ? WHERE dniempleado = ?");
            ps.setString(1, e.getNombreEmpleado());
            ps.setString(2, e.getApellidosEmpleado());
            ps.setString(3, e.getDireccionEmpleado());
            ps.setString(4, e.getTelefonoEmpleado());
            ps.setString(5, e.getEmailEmpleado());
            ps.setString(6, e.getCargo());
            ps.setString(7, e.getDepartamento());
            ps.setInt(8, e.getIdEmpresa());
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
    
 // Método para obtener todos los nombres de las empresas
 	public ArrayList<String> obtenerNombresEmpresas() throws SQLException {
 		ArrayList<String> nombres = new ArrayList<>();
 		String query = "SELECT nombreEmpleado FROM Empleado";
 		PreparedStatement stmt = cx.conectar().prepareStatement(query);
 		ResultSet rs = stmt.executeQuery();
 		while (rs.next()) {
 			nombres.add(rs.getString("nombreEmpleado"));
 		}
 		rs.close();
 		stmt.close();
 		return nombres;
 	}

 	// Método para obtener una empresa por nombre
 	public Empleado obtenerEmpresa(String nombre) throws SQLException {
 		String query = "SELECT * FROM Empleado WHERE nombreEmpleado = ?";
 		PreparedStatement stmt = cx.conectar().prepareStatement(query);
 		stmt.setString(1, nombre);
 		ResultSet rs = stmt.executeQuery();
 		if (rs.next()) {
 			Empleado empleado = new Empleado();
 			empleado.setDireccionEmpleado(rs.getString("direccionEmpleado"));
 			empleado.setNombreEmpleado(rs.getString("nombreEmpleado"));
 			empleado.setTelefonoEmpleado(rs.getString("telefonoEmpleado"));
 			empleado.setEmailEmpleado(rs.getString("emailEmpleado"));
 			empleado.setResponsabilidadEventos(rs.getBoolean("responsabilidadEventos"));
 			rs.close();
 			stmt.close();
 			return empleado;
 		}
 		rs.close();
 		stmt.close();
 		return null;
 	}

 	// Método para eliminar una empresa por nombre
 	public boolean eliminarEmpresa(String nombre) throws SQLException {
 		String query = "DELETE FROM Empleado WHERE nombreEmpleado = ?";
 		PreparedStatement stmt = cx.conectar().prepareStatement(query);
 		stmt.setString(1, nombre);
 		int filasAfectadas = stmt.executeUpdate();
 		stmt.close();
 		return filasAfectadas > 0; // Devuelve true si se eliminó al menos una
 									// fila
 	}
 	
 	public int getIdEmpresa(String nombreEmpleado) throws SQLException {
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try {
 			ps = cx.conectar().prepareStatement(
 					"SELECT idEmpleado FROM Empleado WHERE nombreEmpleado = ?");
 			ps.setString(1, nombreEmpleado);
 			rs = ps.executeQuery();

 			if (rs.next()) {
 				return rs.getInt("idEmpleado");
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