package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Rol;
import services.ServicesLocator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RolJFrame extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nombreRolTextField;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private JButton visualizarButton;
    private JTable rolesTable;
    private DefaultTableModel model;

    public static void main (String args[]){
    	RolJFrame r = new RolJFrame();
    	r.setVisible(true);
    }
    
    public RolJFrame() {
        // Configurar JFrame
        setTitle("Gestión de Roles");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de control
        JPanel controlPanel = new JPanel(new FlowLayout());
        nombreRolTextField = new JTextField(15);
        agregarButton = new JButton("Agregar");
        modificarButton = new JButton("Modificar");
        eliminarButton = new JButton("Eliminar");
        limpiarButton = new JButton("Limpiar");
        visualizarButton = new JButton("Visualizar");
        controlPanel.add(new JLabel("Nombre del Rol:"));
        controlPanel.add(nombreRolTextField);
        controlPanel.add(agregarButton);
        controlPanel.add(modificarButton);
        controlPanel.add(eliminarButton);
        controlPanel.add(limpiarButton);
        controlPanel.add(visualizarButton);
        add(controlPanel, BorderLayout.NORTH);

        // Tabla
        rolesTable = new JTable();
        model = new DefaultTableModel();
        rolesTable.setModel(model);
        JScrollPane scrollPane = new JScrollPane(rolesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Inicializar tabla
        model.addColumn("ID");
        model.addColumn("Nombre Rol");
        cargarRoles();

        // Agregar listeners
        agregarButton.addActionListener(this);
        modificarButton.addActionListener(this);
        eliminarButton.addActionListener(this);
        limpiarButton.addActionListener(this);
        visualizarButton.addActionListener(this);

        // Mostrar JFrame
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == agregarButton) {
            String nombreRol = nombreRolTextField.getText();
            if (!nombreRol.isEmpty()) {
                Rol rol = new Rol(0, nombreRol); // ID = 0 (autogenerado en la BD)
                if (ServicesLocator.getInstance().getRolService().insertarRol(rol)) {
                    JOptionPane.showMessageDialog(this, "Rol agregado correctamente.");
                    limpiarCampos();
                    cargarRoles(); // Recargar la tabla
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar rol.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del rol.");
            }
        } else if (e.getSource() == modificarButton) {
            if (rolesTable.getSelectedRow() >= 0) {
                int idRol = Integer.parseInt(model.getValueAt(rolesTable.getSelectedRow(), 0).toString());
                String nombreRol = nombreRolTextField.getText();
                if (!nombreRol.isEmpty()) {
                    Rol rol = new Rol(idRol, nombreRol);
                    if (ServicesLocator.getInstance().getRolService().modificarRol(rol)) {
                        JOptionPane.showMessageDialog(this, "Rol modificado correctamente.");
                        limpiarCampos();
                        cargarRoles();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al modificar rol.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del rol.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un rol de la tabla para modificar.");
            }
        } else if (e.getSource() == eliminarButton) {
            if (rolesTable.getSelectedRow() >= 0) {
                int idRol = Integer.parseInt(model.getValueAt(rolesTable.getSelectedRow(), 0).toString());
                if (ServicesLocator.getInstance().getRolService().eliminarRol(idRol)) {
                    JOptionPane.showMessageDialog(this, "Rol eliminado correctamente.");
                    cargarRoles();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar rol.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un rol de la tabla para eliminar.");
            }
        } else if (e.getSource() == limpiarButton) {
            limpiarCampos();
        } else if (e.getSource() == visualizarButton) {
            cargarRoles();
        }
    }

    private void limpiarCampos() {
        nombreRolTextField.setText("");
    }

    private void cargarRoles() {
        model.setRowCount(0); // Limpiar filas existentes
        ArrayList<Rol> roles = ServicesLocator.getInstance().getRolService().consultarRoles();
        for (Rol rol : roles) {
            model.addRow(new Object[]{rol.getIdRol(), rol.getNombreRol()});
        }
    }
}