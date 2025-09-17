package visual;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.Rol;
import model.Usuario;
import services.ServicesLocator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class UsuarioJFrame extends JFrame implements ActionListener,
		ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nombreUsuarioTextField;
	private JPasswordField passwordTextField;
	private JComboBox<RolComboBoxItem> rolComboBox;
	private JButton agregarButton;
	private JButton modificarButton;
	private JButton eliminarButton;
	private JButton limpiarButton;
	private JTable usuariosTable;
	private DefaultTableModel model;

	public static void main(String args[]) {
		UsuarioJFrame f = new UsuarioJFrame();
		f.setVisible(true);
	}

	public UsuarioJFrame() {
        // Configurar JFrame
        setTitle("Gestión de Usuarios");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(800, 400); // Tamaño inicial para que la distribución sea correcta
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar la ventana

        // Panel de control
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Labels
        JLabel nombreUsuarioLabel = new JLabel("Nombre de Usuario:");
        JLabel passwordLabel = new JLabel("Contraseña:");
        JLabel rolLabel = new JLabel("Rol:");

        // Campos de texto
        nombreUsuarioTextField = new JTextField(15);
        passwordTextField = new JPasswordField(15);

        // ComboBox para roles
        rolComboBox = new JComboBox<>();

        // Botones
        agregarButton = new JButton("Agregar");
        modificarButton = new JButton("Modificar");
        eliminarButton = new JButton("Eliminar");
        limpiarButton = new JButton("Limpiar Campos");
        JButton closeButton = new JButton("Cerrar");

        // Panel para campos
        JPanel camposPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        camposPanel.add(nombreUsuarioLabel);
        camposPanel.add(nombreUsuarioTextField);
        camposPanel.add(passwordLabel);
        camposPanel.add(passwordTextField);
        camposPanel.add(rolLabel);
        camposPanel.add(rolComboBox);
        controlPanel.add(camposPanel, BorderLayout.NORTH);

        // Panel para botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Usar FlowLayout
                                                                                    // para
                                                                                    // distribución
                                                                                    // flexible
        botonesPanel.add(agregarButton);
        botonesPanel.add(modificarButton);
        botonesPanel.add(eliminarButton);
        botonesPanel.add(limpiarButton);
        botonesPanel.add(closeButton);
        controlPanel.add(botonesPanel, BorderLayout.CENTER);

        // Agregar panel de control al JFrame
        add(controlPanel, BorderLayout.WEST);

        // Tabla
        usuariosTable = new JTable();
        model = new DefaultTableModel();
        usuariosTable.setModel(model);
        JScrollPane scrollPane = new JScrollPane(usuariosTable);
        add(scrollPane, BorderLayout.CENTER); // Tabla al centro

        // Inicializar tabla
        model.addColumn("ID");
        model.addColumn("Nombre Usuario");
        model.addColumn("Contraseña");
        model.addColumn("Rol");
        cargarUsuarios();

        // Cargar roles en el combo box
        cargarRolesEnComboBox();

        // Agregar listeners
        agregarButton.addActionListener(this);
        modificarButton.addActionListener(this);
        eliminarButton.addActionListener(this);
        limpiarButton.addActionListener(this);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana
            }
        });

        // Agregar ListSelectionListener a la tabla
        usuariosTable.getSelectionModel().addListSelectionListener(this);

        // Estilo para el botón "Cerrar"
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);

        // Ajustar tamaño de los botones (opcional)
        agregarButton.setPreferredSize(new Dimension(120, 35)); // Tamaño fijo para los botones
        modificarButton.setPreferredSize(new Dimension(120, 35)); 
        eliminarButton.setPreferredSize(new Dimension(120, 35)); 
        limpiarButton.setPreferredSize(new Dimension(150, 35)); 
        closeButton.setPreferredSize(new Dimension(120, 35)); 

        // Mostrar JFrame
        setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == agregarButton) {
			String nombreUsuario = nombreUsuarioTextField.getText();
			String password = new String(passwordTextField.getPassword());
			int idRol = ((RolComboBoxItem) rolComboBox.getSelectedItem())
					.getIdRol(); 

			if (!nombreUsuario.isEmpty() && !password.isEmpty() && idRol > 0) {
				try {
					String passwordHasheada = hashearContraseña(password);
					Usuario usuario = new Usuario(0, nombreUsuario,
							passwordHasheada, idRol); // Almacena idRol como
														// entero
					if (ServicesLocator.getInstance().getUsuarioService()
							.insertarUsuario(usuario)) {
						JOptionPane.showMessageDialog(this,
								"Usuario agregado correctamente.");
						limpiarCampos();
						cargarUsuarios();
					}
				} catch (NoSuchAlgorithmException ex) {
					JOptionPane.showMessageDialog(this,
							"Error al hashear la contraseña.");
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Por favor, complete todos los campos.");
			}
		} else if (e.getSource() == modificarButton) {
			if (usuariosTable.getSelectedRow() >= 0) {
				int idUsuario = Integer.parseInt(model.getValueAt(
						usuariosTable.getSelectedRow(), 0).toString());
				String nombreUsuario = nombreUsuarioTextField.getText();
				String password = new String(passwordTextField.getPassword());
				int idRol = ((RolComboBoxItem) rolComboBox.getSelectedItem())
						.getIdRol();

				if (!nombreUsuario.isEmpty() && !password.isEmpty()
						&& idRol > 0) {
					try {
						String passwordHasheada = hashearContraseña(password);
						Usuario usuario = new Usuario(idUsuario, nombreUsuario,
								passwordHasheada, idRol);
						if (ServicesLocator.getInstance().getUsuarioService()
								.modificarUsuario(usuario)) {
							JOptionPane.showMessageDialog(this,
									"Usuario modificado correctamente.");
							limpiarCampos();
							cargarUsuarios();
						}
					} catch (NoSuchAlgorithmException ex) {
						JOptionPane.showMessageDialog(this,
								"Error al hashear la contraseña.");
					}
				} else {
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Seleccione un usuario de la tabla para modificar.");
			}
		} else if (e.getSource() == eliminarButton) {
			if (usuariosTable.getSelectedRow() >= 0) {
				int idUsuario = Integer.parseInt(model.getValueAt(
						usuariosTable.getSelectedRow(), 0).toString());
				if (ServicesLocator.getInstance().getUsuarioService()
						.eliminarUsuario(idUsuario)) {
					JOptionPane.showMessageDialog(this,
							"Usuario eliminado correctamente.");
					cargarUsuarios();
					limpiarCampos();
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Seleccione un usuario de la tabla para eliminar.");
			}
		} else if (e.getSource() == limpiarButton) {
			limpiarCampos();
		}
	}

	private void limpiarCampos() {
		nombreUsuarioTextField.setText("");
		passwordTextField.setText(""); // Limpiar el JPasswordField
		rolComboBox.setSelectedIndex(0);
	}

	private void cargarUsuarios() {
		model.setRowCount(0);
		ArrayList<Usuario> usuarios = ServicesLocator.getInstance()
				.getUsuarioService().consultarUsuarios();
		for (Usuario usuario : usuarios) {
			String nombreRol = obtenerNombreRol(usuario.getIdRol());
			model.addRow(new Object[] { usuario.getIdUsuario(),
					usuario.getNombreUsuario(), usuario.getPassword(),
					nombreRol });
		}
	}

	private String obtenerNombreRol(int idRol) {
		Rol rol = ServicesLocator.getInstance().getRolService()
				.obtenerRolPorId(idRol);
		if (rol != null) {
			return rol.getNombreRol();
		} else {
			return "Rol no encontrado";
		}
	}

	private void cargarRolesEnComboBox() {
		ArrayList<Rol> roles = ServicesLocator.getInstance().getRolService()
				.consultarRoles();
		for (Rol rol : roles) {
			rolComboBox.addItem(new RolComboBoxItem(rol.getIdRol(), rol
					.getNombreRol())); // Crear RolComboBoxItem
		}
	}

	// Función para hashear la contraseña con SHA-256
	private String hashearContraseña(String contraseña)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = null;
		try {
			hash = digest.digest(contraseña.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return; // Ignora eventos durante el ajuste
		}
		int selectedRow = usuariosTable.getSelectedRow();
		if (selectedRow != -1) {
			// Obtener los valores de la fila seleccionada
			String nombreUsuario = (String) model.getValueAt(selectedRow, 1);
			String password = (String) model.getValueAt(selectedRow, 2);
			String rol = (String) model.getValueAt(selectedRow, 3);

			// Establecer los valores en los campos de texto
			nombreUsuarioTextField.setText(nombreUsuario);
			passwordTextField.setText(password);
			rolComboBox.setSelectedItem(rol);
		}
	}
}

// Clase auxiliar para el JComboBox
class RolComboBoxItem {
	private int idRol;
	private String nombreRol;

	public RolComboBoxItem(int idRol, String nombreRol) {
		this.idRol = idRol;
		this.nombreRol = nombreRol;
	}

	public int getIdRol() {
		return idRol;
	}

	@Override
	public String toString() {
		return nombreRol; // Mostrar el nombre del rol en el ComboBox
	}
}