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
				// Maneja la excepción PSQLException
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
				// Maneja la excepción PSQLException
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
				// Maneja la excepción PSQLException
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
				// Maneja la excepción PSQLException
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);

			} else {
				// Maneja otras excepciones SQL

			}
			return false;
		}
	}

	// Método para validar el usuario
	public boolean iniciarSesion(String nombreUsuario, String contraseña)
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
				String contraseñaHasheadaBaseDeDatos = rs.getString("password");
				String contraseñaHasheadaIngresada = hashearContraseña(contraseña);

				if (contraseñaHasheadaBaseDeDatos
						.equals(contraseñaHasheadaIngresada)) {
					return true; // Contraseña correcta
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
		return false; // Usuario no encontrado o contraseña incorrecta
	}

	// Método para obtener el nivel de acceso del usuario
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
	
	// Método para cambiar la contraseña
    @SuppressWarnings("resource")
	public boolean cambiarContraseña(String nombreUsuario, String contraseñaAnterior, String contraseñaNueva) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Verificar si la contraseña actual es correcta
            ps = cx.conectar()
                .prepareStatement("SELECT password FROM usuario WHERE nombreUsuario = ?");
            ps.setString(1, nombreUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                String contraseñaHasheadaBaseDeDatos = rs.getString("password");
                String contraseñaHasheadaIngresada = hashearContraseña(contraseñaAnterior);

                if (contraseñaHasheadaBaseDeDatos.equals(contraseñaHasheadaIngresada)) {
                    // Actualizar la contraseña
                    ps = cx.conectar()
                        .prepareStatement("UPDATE usuario SET password = ? WHERE nombreUsuario = ?");
                    ps.setString(1, hashearContraseña(contraseñaNueva));
                    ps.setString(2, nombreUsuario);
                    ps.executeUpdate();
                    return true; // Contraseña cambiada exitosamente
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
        return false; // Contraseña actual incorrecta o error al actualizar
    }
    
 // Método para registrar un nuevo usuario
    @SuppressWarnings("resource")
	public boolean registrarUsuario(String nombreUsuario, String contraseña) throws SQLException {
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

            // Hash de la contraseña antes de almacenarla
            String contraseñaHasheada = hashearContraseña(contraseña);

            // Insertar nuevo usuario (con idRol por defecto 1)
            ps = cx.conectar()
                .prepareStatement("INSERT INTO usuario (nombreUsuario, password, idRol) VALUES (?, ?, 1)");
            ps.setString(1, nombreUsuario);
            ps.setString(2, contraseñaHasheada);
            ps.executeUpdate();
            return true; // Usuario registrado correctamente
        } finally {
            if (ps != null) {
                ps.close();
            }
            cx.desconectar();
        }
    }
    
 // Método para verificar si un usuario existe
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

    // Método para verificar si la contraseña es correcta para un usuario dado
    public boolean verificarContraseña(String nombreUsuario, String contraseña) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cx.conectar().prepareStatement("SELECT password FROM usuario WHERE nombreUsuario = ?");
            ps.setString(1, nombreUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                String contraseñaHasheadaBaseDeDatos = rs.getString("password");
                String contraseñaHasheadaIngresada = hashearContraseña(contraseña);
                return contraseñaHasheadaBaseDeDatos.equals(contraseñaHasheadaIngresada);
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
        return false; // Usuario no encontrado o contraseña incorrecta
    }

	private String hashearContraseña(String contraseña) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = null;
			try {
				hash = digest.digest(contraseña.getBytes("UTF-8"));
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