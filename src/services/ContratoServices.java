package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import utils.Conexion;
import model.Contrato;

public class ContratoServices {
    Conexion cx;

    public ContratoServices() {
        cx = new Conexion();
    }

    public boolean insertarContrato(Contrato c) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("INSERT INTO public.contrato(terminoscondiciones, idempresa, idevento, id)VALUES (?, ?, ?, ?);");
            ps.setString(1, c.getTerminosCondiciones());
            ps.setInt(2, c.getIdEmpresa());
            ps.setInt(3, c.getIdEvento());
            ps.setString(4, c.getId());
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

    public ArrayList<Contrato> consultarContratos() {
        ArrayList<Contrato> lista = new ArrayList<Contrato>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cx.conectar().prepareStatement("SELECT * FROM contrato");
            rs = ps.executeQuery();
            while (rs.next()) {
                Contrato c = new Contrato();
                c.setIdContrato(rs.getInt("idcontrato"));
                c.setFechaContrato(rs.getTimestamp("fechacontrato"));
                c.setTerminosCondiciones(rs.getString("terminoscondiciones"));
                c.setPreciosNegociados(rs.getDouble("preciosnegociados"));
                c.setIdEmpresa(rs.getInt("idempresa"));
                c.setIdEvento(rs.getInt("idevento"));
                c.setId(rs.getString("id"));
                c.setTasaDescuento(rs.getDouble("tasaDescuento"));
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

    public boolean eliminarContrato(Integer id) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("DELETE FROM contrato WHERE idcontrato = ?");
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

    public boolean modificarContrato(Contrato c) {
        PreparedStatement ps = null;
        try {
            ps = cx.conectar().prepareStatement("UPDATE contrato SET terminoscondiciones = ?, idempresa = ?, idevento = ?, id = ? WHERE idcontrato = ?");
            ps.setString(1, c.getTerminosCondiciones());
            ps.setInt(2, c.getIdEmpresa());
            ps.setInt(3, c.getIdEvento());
            ps.setString(4, c.getId());
            ps.setInt(5, c.getIdContrato());
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