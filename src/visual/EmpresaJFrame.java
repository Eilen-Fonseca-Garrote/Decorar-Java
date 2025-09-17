package visual;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Empresa;
import services.ServicesLocator;

public class EmpresaJFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private JComboBox<String> empresasComboBox;
    private JLabel nombreEmpresaLabel, directorLabel, direccionEmpresaLabel, telefonoEmpresaLabel, emailEmpresaLabel;

    public EmpresaJFrame() {
        setTitle("Eliminar Empresa");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(7, 2, 10, 10)); // Distribución en grid

        // ComboBox para seleccionar la empresa
        empresasComboBox = new JComboBox<>();
        llenarEmpresasComboBox(); // Llena el ComboBox al iniciar
        getContentPane().add(new JLabel("Empresa:"));
        getContentPane().add(empresasComboBox);

        // Labels para mostrar los datos de la empresa seleccionada
        nombreEmpresaLabel = new JLabel("");
        directorLabel = new JLabel("");
        direccionEmpresaLabel = new JLabel("");
        telefonoEmpresaLabel = new JLabel("");
        emailEmpresaLabel = new JLabel("");

        getContentPane().add(new JLabel("Nombre:"));
        getContentPane().add(nombreEmpresaLabel);
        getContentPane().add(new JLabel("Director:"));
        getContentPane().add(directorLabel);
        getContentPane().add(new JLabel("Dirección Postal:"));
        getContentPane().add(direccionEmpresaLabel);
        getContentPane().add(new JLabel("Teléfono:"));
        getContentPane().add(telefonoEmpresaLabel);
        getContentPane().add(new JLabel("Email:"));
        getContentPane().add(emailEmpresaLabel);

        // Botones
        JButton cancelarButton = new JButton("Cancelar");
        JButton eliminarButton = new JButton("Eliminar");
        getContentPane().add(cancelarButton);
        getContentPane().add(eliminarButton);

        // ActionListener para el ComboBox
        empresasComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEmpresa = (String) empresasComboBox.getSelectedItem();
                mostrarDatosEmpresa(nombreEmpresa);
            }
        });

        // ActionListener para el botón Eliminar
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEmpresa = (String) empresasComboBox.getSelectedItem();
                eliminarEmpresa(nombreEmpresa);
            }
        });

        // ActionListener para el botón Cancelar
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra el JFrame
            }
        });

        setVisible(true);
    }

 // Llena el ComboBox con los nombres de las empresas
    private void llenarEmpresasComboBox() {
        try {
            empresasComboBox.removeAllItems(); // Limpia el ComboBox
            for (String nombreEmpresa : ServicesLocator.getInstance().getEmpresaService().obtenerNombresEmpresas()) {
                empresasComboBox.addItem(nombreEmpresa);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empresas: " + e.getMessage());
        }
    }

    // Muestra los datos de la empresa seleccionada en los Labels
    private void mostrarDatosEmpresa(String nombreEmpresa) {
        try {
            Empresa empresa = ServicesLocator.getInstance().getEmpresaService().obtenerEmpresa(nombreEmpresa);
            if (empresa != null) {
                nombreEmpresaLabel.setText(empresa.getNombreEmpresa());
                directorLabel.setText((empresa.getDirectorGeneral()));
                direccionEmpresaLabel.setText(empresa.getDireccionPostal());
                telefonoEmpresaLabel.setText(empresa.getTelefono());
                emailEmpresaLabel.setText(empresa.getEmailEmpresa());
            } else {
                nombreEmpresaLabel.setText("");
                directorLabel.setText("");
                direccionEmpresaLabel.setText("");
                telefonoEmpresaLabel.setText("");
                emailEmpresaLabel.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos de la empresa: " + e.getMessage());
        }
    }

    // Elimina la empresa de la base de datos
    private void eliminarEmpresa(String nombreEmpresa) {
        try {
            if (ServicesLocator.getInstance().getEmpresaService().eliminarEmpresa(nombreEmpresa)) {
                JOptionPane.showMessageDialog(this, "Empresa eliminada correctamente.");
                llenarEmpresasComboBox();
                mostrarDatosEmpresa("");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la empresa.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar empresa: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        
        // Crear el JFrame principal
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmpresaJFrame();
            }
        });
    }
}