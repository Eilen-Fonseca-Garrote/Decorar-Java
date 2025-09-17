package visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import services.ServicesLocator;

import com.jgoodies.forms.factories.DefaultComponentFactory;

public class FrameCambiarContraseña extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPasswordField campoContraseñaAnterior;
	private JPasswordField campoContraseñaNueva;
	private JPasswordField campoConfirmarContraseñaNueva;
	private JCheckBox mostrarContraseñaCambiar;
	private String usuarioLogueado;
	private JLabel lblCambiarContrasea;

	public FrameCambiarContraseña(String usuarioLogueado) {

		setSize(400, 332);
		setLocationRelativeTo(null);
		setResizable(false);
		this.usuarioLogueado = usuarioLogueado;
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JPanel panelContenidoCambiarContraseña = new JPanel();
		panelContenidoCambiarContraseña.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelContenidoCambiarContraseña.setLayout(null);
		setContentPane(panelContenidoCambiarContraseña);

		// Diseño llamativo (usa colores, fuentes, imágenes, etc.)
		panelContenidoCambiarContraseña.setBackground(new Color(240, 240, 240));

		JLabel etiquetaContraseñaAnterior = new JLabel("Contraseña actual:");
		etiquetaContraseñaAnterior.setBounds(50, 50, 120, 20);
		panelContenidoCambiarContraseña.add(etiquetaContraseñaAnterior);

		campoContraseñaAnterior = new JPasswordField();
		campoContraseñaAnterior.setBounds(50, 70, 250, 25);
		panelContenidoCambiarContraseña.add(campoContraseñaAnterior);

		JLabel etiquetaContraseñaNueva = new JLabel("Nueva contraseña:");
		etiquetaContraseñaNueva.setBounds(50, 100, 120, 20);
		panelContenidoCambiarContraseña.add(etiquetaContraseñaNueva);

		campoContraseñaNueva = new JPasswordField();
		campoContraseñaNueva.setBounds(50, 120, 250, 25);
		panelContenidoCambiarContraseña.add(campoContraseñaNueva);

		JLabel etiquetaConfirmarContraseñaNueva = new JLabel(
				"Confirmar nueva contraseña:");
		etiquetaConfirmarContraseñaNueva.setBounds(50, 150, 160, 20);
		panelContenidoCambiarContraseña.add(etiquetaConfirmarContraseñaNueva);

		campoConfirmarContraseñaNueva = new JPasswordField();
		campoConfirmarContraseñaNueva.setBounds(50, 170, 250, 25);
		panelContenidoCambiarContraseña.add(campoConfirmarContraseñaNueva);

		mostrarContraseñaCambiar = new JCheckBox("Mostrar Contraseña");
		mostrarContraseñaCambiar.setBounds(50, 200, 150, 20);
		panelContenidoCambiarContraseña.add(mostrarContraseñaCambiar);
		mostrarContraseñaCambiar.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					campoContraseñaAnterior.setEchoChar((char) 0);
					campoContraseñaNueva.setEchoChar((char) 0);
					campoConfirmarContraseñaNueva.setEchoChar((char) 0);
				} else {
					campoContraseñaAnterior.setEchoChar('*');
					campoContraseñaNueva.setEchoChar('*');
					campoConfirmarContraseñaNueva.setEchoChar('*');
				}
			}
		});

		JButton botonCambiar = new JButton("Cambiar");
		botonCambiar.setBounds(50, 230, 150, 30);
		panelContenidoCambiarContraseña.add(botonCambiar);
		botonCambiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarContraseña();
			}
		});

		JButton botonCancelarCambiar = new JButton("Cancelar");
		botonCancelarCambiar.setBounds(200, 230, 150, 30);
		panelContenidoCambiarContraseña.add(botonCancelarCambiar);
		botonCancelarCambiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// Estilos adicionales
		botonCambiar.setBackground(new Color(0, 153, 0));
		botonCambiar.setForeground(Color.WHITE);
		botonCancelarCambiar.setBackground(new Color(255, 0, 0));
		botonCancelarCambiar.setForeground(Color.WHITE);
		panelContenidoCambiarContraseña.add(getLblCambiarContrasea());

		setVisible(true);
	}

	private void cambiarContraseña() {
		String contraseñaAnterior = new String(
				campoContraseñaAnterior.getPassword());
		String contraseñaNueva = new String(campoContraseñaNueva.getPassword());
		String confirmarContraseñaNueva = new String(
				campoConfirmarContraseñaNueva.getPassword());

		if (validar()) {
			try {
				// Verificar si las contraseñas coinciden
				if (!contraseñaNueva.equals(confirmarContraseñaNueva)) {
					JOptionPane.showMessageDialog(null,
							"Las contraseñas no coinciden.", "Error",
							JOptionPane.ERROR_MESSAGE);
					campoConfirmarContraseñaNueva.setText("");
					campoContraseñaNueva.setText("");
					return;
				}

				// Verificar si la contraseña actual es correcta
				if (ServicesLocator
						.getInstance()
						.getUsuarioService()
						.verificarContraseña(usuarioLogueado,
								contraseñaAnterior)) {
					// Llamar al método de la clase UsuarioServices para cambiar
					// la contraseña
					boolean cambioExitoso = ServicesLocator
							.getInstance()
							.getUsuarioService()
							.cambiarContraseña(usuarioLogueado,
									contraseñaAnterior, contraseñaNueva);

					if (cambioExitoso) {
						JOptionPane.showMessageDialog(null,
								"Contraseña cambiada exitosamente.", "Éxito",
								JOptionPane.INFORMATION_MESSAGE);
						campoContraseñaAnterior.setText("");
						campoContraseñaNueva.setText("");
						campoConfirmarContraseñaNueva.setText("");
						dispose(); // Cerrar la ventana
					}
				} else {
					// La contraseña actual es incorrecta, mostrar mensaje de
					// error
					JOptionPane.showMessageDialog(null,
							"Contraseña actual incorrecta.", "Error",
							JOptionPane.ERROR_MESSAGE);
					// Limpia el campo de contraseña anterior
					campoContraseñaAnterior.setText("");
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Error al cambiar la contraseña: " + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean validar() {
		String contraseñaAnterior = new String(
				campoContraseñaAnterior.getPassword());
		String contraseñaNueva = new String(campoContraseñaNueva.getPassword());
		String confirmarContraseñaNueva = new String(
				campoConfirmarContraseñaNueva.getPassword());

		if (contraseñaAnterior.trim().isEmpty()
				|| contraseñaNueva.trim().isEmpty()
				|| confirmarContraseñaNueva.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"No se pueden dejar campos vacíos.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private JLabel getLblCambiarContrasea() {
		if (lblCambiarContrasea == null) {
			lblCambiarContrasea = DefaultComponentFactory.getInstance()
					.createTitle("Cambiar Contrase\u00F1a");
			lblCambiarContrasea.setHorizontalAlignment(SwingConstants.CENTER);
			lblCambiarContrasea.setBounds(120, 11, 150, 14);
		}
		return lblCambiarContrasea;
	}
}