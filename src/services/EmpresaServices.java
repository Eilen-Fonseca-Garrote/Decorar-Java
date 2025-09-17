package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import utils.Conexion;
import model.Empresa;

public class EmpresaServices {
	Conexion cx;

	public EmpresaServices() {
		cx = new Conexion();
	}

	public boolean insertarEmpresa(Empresa e) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"INSERT INTO public.empresa(nombreempresa, direccionpostal, emailEmpresa, telefono, directorgeneral, jeferecursoshumanos, jefeContabilidad, secretarioSindicato, logo)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
			ps.setString(1, e.getNombreEmpresa());
			ps.setString(2, e.getDireccionPostal());
			ps.setString(3, e.getEmailEmpresa());
			ps.setString(4, e.getTelefono());
			ps.setString(5, e.getDirectorGeneral());
			ps.setString(6, e.getJefeRecursosHumanos());
			ps.setString(7, e.getJefeContabilidad());
			ps.setString(8, e.getSecretarioSindicato());
			ps.setBinaryStream(9, e.getLogo());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e1) {
			if (e1 instanceof PSQLException) {
				// Maneja la excepción PSQLException
				JOptionPane.showMessageDialog(null, e1.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} else {
				// Maneja otras excepciones SQL
				e1.printStackTrace();
			}
			return false;
		}
	}

	public ArrayList<Empresa> consultarEmpresas() {
		ArrayList<Empresa> lista = new ArrayList<Empresa>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"SELECT idempresa,nombreempresa,direccionpostal,emailempresa, telefono,directorgeneral,jeferecursoshumanos,jefecontabilidad,secretariosindicato FROM empresa");
			rs = ps.executeQuery();
			while (rs.next()) {
				Empresa e = new Empresa();
				e.setIdEmpresa(rs.getInt("idempresa"));
				e.setNombreEmpresa(rs.getString("nombreempresa"));
				e.setDireccionPostal(rs.getString("direccionpostal"));
				e.setEmailEmpresa(rs.getString("emailEmpresa"));
				e.setTelefono(rs.getString("telefono"));
				e.setDirectorGeneral(rs.getString("directorgeneral"));
				e.setJefeRecursosHumanos(rs.getString("jeferecursoshumanos"));
				e.setJefeContabilidad(rs.getString("jefeContabilidad"));
				e.setSecretarioSindicato(rs.getString("secretarioSindicato"));
				// e.setLogo(rs.getBytes("logo"));
				lista.add(e);
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

	public boolean eliminarEmpresa(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement(
					"DELETE FROM empresa WHERE idempresa = ?");
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

	public boolean modificarEmpresa(Empresa e) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"UPDATE empresa SET nombreempresa = ?, direccionpostal = ?, emailEmpresa = ?, telefono = ?, directorgeneral = ?, jeferecursoshumanos = ?, jefeContabilidad = ?, secretarioSindicato = ?, logo = ? WHERE idempresa = ?");
			ps.setString(1, e.getNombreEmpresa());
			ps.setString(2, e.getDireccionPostal());
			ps.setString(3, e.getEmailEmpresa());
			ps.setString(4, e.getTelefono());
			ps.setString(5, e.getDirectorGeneral());
			ps.setString(6, e.getJefeRecursosHumanos());
			ps.setString(7, e.getJefeContabilidad());
			ps.setString(8, e.getSecretarioSindicato());
			ps.setBinaryStream(9, e.getLogo());
			ps.setInt(10, e.getIdEmpresa());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e1) {
			if (e1 instanceof PSQLException) {
				// Maneja la excepción PSQLException
				JOptionPane.showMessageDialog(null, e1.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);
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
		String query = "SELECT nombreEmpresa FROM Empresa";
		PreparedStatement stmt = cx.conectar().prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			nombres.add(rs.getString("nombreEmpresa"));
		}
		rs.close();
		stmt.close();
		return nombres;
	}

	// Método para obtener una empresa por nombre
	public Empresa obtenerEmpresa(String nombre) throws SQLException {
		String query = "SELECT * FROM Empresa WHERE nombreEmpresa = ?";
		PreparedStatement stmt = cx.conectar().prepareStatement(query);
		stmt.setString(1, nombre);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			Empresa empresa = new Empresa();
			empresa.setDireccionPostal(rs.getString("direccionpostal"));
			empresa.setNombreEmpresa(rs.getString("nombreEmpresa"));
			empresa.setTelefono(rs.getString("telefono"));
			empresa.setEmailEmpresa(rs.getString("emailEmpresa"));
			empresa.setDirectorGeneral(rs.getString("directorGeneral"));
			rs.close();
			stmt.close();
			return empresa;
		}
		rs.close();
		stmt.close();
		return null;
	}

	// Método para eliminar una empresa por nombre
	public boolean eliminarEmpresa(String nombre) throws SQLException {
		String query = "DELETE FROM Empresa WHERE nombreEmpresa = ?";
		PreparedStatement stmt = cx.conectar().prepareStatement(query);
		stmt.setString(1, nombre);
		int filasAfectadas = stmt.executeUpdate();
		stmt.close();
		return filasAfectadas > 0; // Devuelve true si se eliminó al menos una
									// fila
	}
	
	public int getIdEmpresa(String nombreEmpresa) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar().prepareStatement(
					"SELECT idEmpresa FROM empresa WHERE nombreEmpresa = ?");
			ps.setString(1, nombreEmpresa);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("idEmpresa");
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
