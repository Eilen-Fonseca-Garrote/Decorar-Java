package visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import services.ServicesLocator;

public class FrameRegistro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField, confirmPasswordField;
	private JCheckBox showPasswordCheckbox;
	private JButton registerButton, cancelButton;

	public FrameRegistro() {
		super("Registro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);

		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		setContentPane(contentPanel);

		// Design (colors, fonts, images)
		contentPanel.setBackground(new Color(240, 240, 240));

		JLabel usernameLabel = new JLabel("Nombre de usuario:");
		usernameLabel.setBounds(50, 50, 120, 20);
		contentPanel.add(usernameLabel);

		usernameField = new JTextField();
		usernameField.setBounds(50, 70, 250, 25);
		contentPanel.add(usernameField);

		JLabel passwordLabel = new JLabel("Contraseña:");
		passwordLabel.setBounds(50, 100, 120, 20);
		contentPanel.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(50, 120, 250, 25);
		contentPanel.add(passwordField);

		JLabel confirmPasswordLabel = new JLabel("Confirmar contraseña:");
		confirmPasswordLabel.setBounds(50, 150, 140, 20);
		contentPanel.add(confirmPasswordLabel);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(50, 170, 250, 25);
		contentPanel.add(confirmPasswordField);

		showPasswordCheckbox = new JCheckBox("Mostrar Contraseña");
		showPasswordCheckbox.setBounds(50, 200, 150, 20);
		contentPanel.add(showPasswordCheckbox);
		showPasswordCheckbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					passwordField.setEchoChar((char) 0);
					confirmPasswordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('*');
					confirmPasswordField.setEchoChar('*');
				}
			}
		});

		registerButton = new JButton("Registrar");
		registerButton.setBounds(50, 230, 150, 30);
		contentPanel.add(registerButton);
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registrarUsuario();
			}
		});

		cancelButton = new JButton("Cancelar");
		cancelButton.setBounds(200, 230, 150, 30);
		contentPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				FrameLogin f = new FrameLogin();
				f.setVisible(true);
			}
		});

		// Additional styles
		registerButton.setBackground(new Color(0, 153, 0));
		registerButton.setForeground(Color.WHITE);
		cancelButton.setBackground(new Color(255, 0, 0));
		cancelButton.setForeground(Color.WHITE);
	}

	private void registrarUsuario() {
		String nombreUsuario = usernameField.getText().trim();
		String contraseña = new String(passwordField.getPassword());
		String confirmarContraseña = new String(
				confirmPasswordField.getPassword());

		if (validar()) { 
			try {
				// Verificar si las contraseñas coinciden
				if (!contraseña.equals(confirmarContraseña)) {
					JOptionPane.showMessageDialog(null,
							"Las contraseñas no coinciden.", "Error",
							JOptionPane.ERROR_MESSAGE);
					// Limpia los campos de contraseña
					passwordField.setText("");
					confirmPasswordField.setText("");
					return;
				}

				// Verificar si el usuario ya existe
				if (ServicesLocator.getInstance().getUsuarioService().usuarioExiste(nombreUsuario)) {
					JOptionPane.showMessageDialog(null,
							"El nombre de usuario ya existe.", "Error",
							JOptionPane.ERROR_MESSAGE);
					// Limpia todos los campos
					usernameField.setText("");
					passwordField.setText("");
					confirmPasswordField.setText("");
					return; // Sale de la función
				}

				// Llamar al método de la clase UsuarioServices para registrar
				// el usuario
				boolean registroExitoso = ServicesLocator.getInstance().getUsuarioService().registrarUsuario(
						nombreUsuario, contraseña);

				if (registroExitoso) {
					JOptionPane.showMessageDialog(null,
							"Usuario registrado correctamente.", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					dispose(); // Cerrar la ventana de registro
					FrameLogin f = new FrameLogin();
					f.setVisible(true);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Error registrando usuario: " + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
				// Limpia todos los campos
				usernameField.setText("");
				passwordField.setText("");
				confirmPasswordField.setText("");
			}
		}
	}

	private boolean validar() {
		String nombreUsuario = usernameField.getText();
		String contraseña = new String(passwordField.getPassword());
		String confirmarContraseña = new String(
				confirmPasswordField.getPassword());

		if (nombreUsuario.trim().isEmpty() || contraseña.trim().isEmpty()
				|| confirmarContraseña.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"No se pueden dejar campos vacíos.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}