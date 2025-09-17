package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexion {
	Connection cx = null;
	String URL = "jdbc:postgresql://localhost:5432/EmpresaDecoracion";
	String USER = "postgres";
	String PASS = "1234";
	
	public Connection conectar(){
		try {
			Class.forName("org.postgresql.Driver");
			cx = DriverManager.getConnection(URL,USER,PASS);
			System.out.println("Conectado...");
		} catch (ClassNotFoundException e) {
			mostrarError(e.getMessage());
		} catch (SQLException e) {
			mostrarError(e.getMessage());
		}
		
		return cx;
		
	}
	
	public void desconectar(){
		try {
			cx.close();
		} catch (SQLException e) {
			mostrarError(e.getMessage());
		}
	}
	
	private void mostrarError(String mensajeError) {
        JOptionPane.showMessageDialog(null, mensajeError, "Error en la base de datos", JOptionPane.ERROR_MESSAGE);
    }
	
	public static void main(String [] args){
		Conexion cx = new Conexion();
		cx.conectar();
	}
}
