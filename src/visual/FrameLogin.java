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

public class FrameLogin extends JFrame {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JCheckBox showPasswordCheckbox;
	private JButton loginButton, registerButton, cancelButton; // Agrega el
																// bot�n
				

	public FrameLogin() {
		super("Inicio de Sesi�n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		JLabel passwordLabel = new JLabel("Contrase�a:");
		passwordLabel.setBounds(50, 100, 120, 20);
		contentPanel.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(50, 120, 250, 25);
		contentPanel.add(passwordField);

		showPasswordCheckbox = new JCheckBox("Mostrar Contrase�a");
		showPasswordCheckbox.setBounds(50, 150, 150, 20);
		contentPanel.add(showPasswordCheckbox);
		showPasswordCheckbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('*');
				}
			}
		});

		loginButton = new JButton("Iniciar Sesi�n");
		loginButton.setBounds(50, 180, 150, 30);
		contentPanel.add(loginButton);
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarSesion();
			}
		});

		registerButton = new JButton("Registrarse");
		registerButton.setBounds(200, 180, 150, 30);
		contentPanel.add(registerButton);
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameRegistro f = new FrameRegistro();
				f.setVisible(true);
				dispose();
			}
		});

		// Bot�n Cancelar
		cancelButton = new JButton("Cerrar"); // Crea el bot�n Cancelar
		cancelButton.setBounds(150, 220, 100, 30); // Define su posici�n
		contentPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() { // Listener para
																// el bot�n
																// Cancelar
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose(); // Cierra la ventana
					}
				});
		cancelButton.setBackground(Color.RED); // Define el color de fondo del
												// bot�n Cancelar
		cancelButton.setForeground(Color.WHITE); // Define el color de la fuente
													// del bot�n Cancelar

		// Additional styles
		loginButton.setBackground(new Color(51, 153, 255));
		loginButton.setForeground(Color.WHITE);
		registerButton.setBackground(new Color(0, 153, 0));
		registerButton.setForeground(Color.WHITE);

		setVisible(true);
	}

	private void iniciarSesion() {
		String nombreUsuario = usernameField.getText().trim();
		String contrase�a = new String(passwordField.getPassword());

		if (validar()) {
			try {
				// Verificar si el usuario existe
				if (ServicesLocator.getInstance().getUsuarioService()
						.usuarioExiste(nombreUsuario)) {
					// Verificar si la contrase�a es correcta
					if (ServicesLocator.getInstance().getUsuarioService()
							.verificarContrase�a(nombreUsuario, contrase�a)) {
						// La contrase�a es correcta, mostrar mensaje de �xito
						JOptionPane.showMessageDialog(null,
								"Inicio de sesi�n exitoso.", "�xito",
								JOptionPane.INFORMATION_MESSAGE);

						// Obtiene el nivel de acceso del usuario logueado
						int nivelAccesoLogueado = ServicesLocator.getInstance()
								.getUsuarioService()
								.getNivelAcceso(nombreUsuario);

						dispose(); // Cerrar la ventana de login
						FrameMenuPrincipal f = new FrameMenuPrincipal(
								nombreUsuario, nivelAccesoLogueado);
						f.setVisible(true);
					} else {
						// La contrase�a es incorrecta, mostrar mensaje de error
						JOptionPane.showMessageDialog(null,
								"Contrase�a incorrecta.", "Error",
								JOptionPane.ERROR_MESSAGE);
						passwordField.setText("");
					}
				} else {
					// El usuario no existe, mostrar mensaje y opci�n para
					// registrarse
					int opcion = JOptionPane.showConfirmDialog(null,
							"El usuario no existe. �Deseas registrarte?",
							"Usuario no encontrado", JOptionPane.YES_NO_OPTION);
					if (opcion == JOptionPane.YES_OPTION) {
						FrameRegistro f = new FrameRegistro();
						f.setVisible(true);
						dispose(); // Cierra la ventana de login
					} else {
						// No se registra, limpia el campo de usuario
						usernameField.setText("");
						passwordField.setText("");
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error iniciando sesi�n: "
						+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean validar() {
		String nombreUsuario = usernameField.getText();
		String contrase�a = new String(passwordField.getPassword());

		if (nombreUsuario.trim().isEmpty() || contrase�a.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"No se pueden dejar campos vac�os.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}