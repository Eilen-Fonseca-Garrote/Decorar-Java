package services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import utils.Conexion;
import model.Usuario;

public class UsuarioServices {
	Conexion cx;

	public UsuarioServices() {
		cx = new Conexion();
	}

	public boolean insertarUsuario(Usuario usuario) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"INSERT INTO public.usuario(nombreusuario, password, idrol)VALUES ( ?, ?, ?);");
			ps.setString(1, usuario.getNombreUsuario());
			ps.setString(2, usuario.getPassword());
			ps.setInt(3, usuario.getIdRol());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			if (e instanceof PSQLException) {
				// Maneja la excepci�n PSQLException
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);

			} else {
				// Maneja otras excepciones SQL

			}
			return false;
		}
	}

	public ArrayList<Usuario> consultarUsuarios() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar().prepareStatement("SELECT * FROM usuario");
			rs = ps.executeQuery();
			while (rs.next()) {
				Usuario u = new Usuario(rs.getInt("idusuario"),
						rs.getString("nombreusuario"),
						rs.getString("password"), rs.getInt("idrol"));
				lista.add(u);
			}
		} catch (SQLException e) {
			if (e instanceof PSQLException) {
				// Maneja la excepci�n PSQLException
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);

			} else {
				// Maneja otras excepciones SQL

			}
		}
		return lista;
	}

	public boolean eliminarUsuario(int id) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement(
					"DELETE FROM usuario WHERE idusuario = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			if (e instanceof PSQLException) {
				// Maneja la excepci�n PSQLException
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);

			} else {
				// Maneja otras excepciones SQL

			}
			return false;
		}
	}

	public boolean modificarUsuario(Usuario usuario) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"UPDATE usuario SET nombreusuario = ?, password = ?, idrol = ? WHERE idusuario = ?");
			ps.setString(1, usuario.getNombreUsuario());
			ps.setString(2, usuario.getPassword());
			ps.setInt(3, usuario.getIdRol());
			ps.setInt(4, usuario.getIdUsuario());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			if (e instanceof PSQLException) {
				// Maneja la excepci�n PSQLException
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);

			} else {
				// Maneja otras excepciones SQL

			}
			return false;
		}
	}

	// M�todo para validar el usuario
	public boolean iniciarSesion(String nombreUsuario, String contrase�a)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar()
					.prepareStatement(
							"SELECT password, idRol FROM usuario WHERE nombreUsuario = ?");
			ps.setString(1, nombreUsuario);
			rs = ps.executeQuery();

			if (rs.next()) {
				String contrase�aHasheadaBaseDeDatos = rs.getString("password");
				String contrase�aHasheadaIngresada = hashearContrase�a(contrase�a);

				if (contrase�aHasheadaBaseDeDatos
						.equals(contrase�aHasheadaIngresada)) {
					return true; // Contrase�a correcta
				}
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
		return false; // Usuario no encontrado o contrase�a incorrecta
	}

	// M�todo para obtener el nivel de acceso del usuario
	public int getNivelAcceso(String nombreUsuario) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar().prepareStatement(
					"SELECT idRol FROM usuario WHERE nombreUsuario = ?");
			ps.setString(1, nombreUsuario);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("idRol");
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
		return -1; // Usuario no encontrado
	}
	
	// M�todo para cambiar la contrase�a
    @SuppressWarnings("resource")
	public boolean cambiarContrase�a(String nombreUsuario, String contrase�aAnterior, String contrase�aNueva) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Verificar si la contrase�a actual es correcta
            ps = cx.conectar()
                .prepareStatement("SELECT password FROM usuario WHERE nombreUsuario = ?");
            ps.setString(1, nombreUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                String contrase�aHasheadaBaseDeDatos = rs.getString("password");
                String contrase�aHasheadaIngresada = hashearContrase�a(contrase�aAnterior);

                if (contrase�aHasheadaBaseDeDatos.equals(contrase�aHasheadaIngresada)) {
                    // Actualizar la contrase�a
                    ps = cx.conectar()
                        .prepareStatement("UPDATE usuario SET password = ? WHERE nombreUsuario = ?");
                    ps.setString(1, hashearContrase�a(contrase�aNueva));
                    ps.setString(2, nombreUsuario);
                    ps.executeUpdate();
                    return true; // Contrase�a cambiada exitosamente
                } 
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
        return false; // Contrase�a actual incorrecta o error al actualizar
    }
    
 // M�todo para registrar un nuevo usuario
    @SuppressWarnings("resource")
	public boolean registrarUsuario(String nombreUsuario, String contrase�a) throws SQLException {
        PreparedStatement ps = null;
        try {
            // Verificar si el nombre de usuario ya existe
            ps = cx.conectar().prepareStatement("SELECT * FROM usuario WHERE nombreUsuario = ?");
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false; // Nombre de usuario ya existe
            }
            rs.close(); 

            // Hash de la contrase�a antes de almacenarla
            String contrase�aHasheada = hashearContrase�a(contrase�a);

            // Insertar nuevo usuario (con idRol por defecto 1)
            ps = cx.conectar()
                .prepareStatement("INSERT INTO usuario (nombreUsuario, password, idRol) VALUES (?, ?, 1)");
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrase�aHasheada);
            ps.executeUpdate();
            return true; // Usuario registrado correctamente
        } finally {
            if (ps != null) {
                ps.close();
            }
            cx.desconectar();
        }
    }
    
 // M�todo para verificar si un usuario existe
    public boolean usuarioExiste(String nombreUsuario) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cx.conectar().prepareStatement("SELECT * FROM usuario WHERE nombreUsuario = ?");
            ps.setString(1, nombreUsuario);
            rs = ps.executeQuery();
            return rs.next(); // Devuelve true si existe, false si no
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            cx.desconectar();
        }
    }

    // M�todo para verificar si la contrase�a es correcta para un usuario dado
    public boolean verificarContrase�a(String nombreUsuario, String contrase�a) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cx.conectar().prepareStatement("SELECT password FROM usuario WHERE nombreUsuario = ?");
            ps.setString(1, nombreUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                String contrase�aHasheadaBaseDeDatos = rs.getString("password");
                String contrase�aHasheadaIngresada = hashearContrase�a(contrase�a);
                return contrase�aHasheadaBaseDeDatos.equals(contrase�aHasheadaIngresada);
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
        return false; // Usuario no encontrado o contrase�a incorrecta
    }

	private String hashearContrase�a(String contrase�a) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = null;
			try {
				hash = digest.digest(contrase�a.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuilder cadenaHexadecimal = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					cadenaHexadecimal.append('0');
				cadenaHexadecimal.append(hex);
			}
			return cadenaHexadecimal.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}