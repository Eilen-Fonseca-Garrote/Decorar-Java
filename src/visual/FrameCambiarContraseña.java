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

public class FrameCambiarContrase�a extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPasswordField campoContrase�aAnterior;
	private JPasswordField campoContrase�aNueva;
	private JPasswordField campoConfirmarContrase�aNueva;
	private JCheckBox mostrarContrase�aCambiar;
	private String usuarioLogueado;
	private JLabel lblCambiarContrasea;

	public FrameCambiarContrase�a(String usuarioLogueado) {

		setSize(400, 332);
		setLocationRelativeTo(null);
		setResizable(false);
		this.usuarioLogueado = usuarioLogueado;
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JPanel panelContenidoCambiarContrase�a = new JPanel();
		panelContenidoCambiarContrase�a.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelContenidoCambiarContrase�a.setLayout(null);
		setContentPane(panelContenidoCambiarContrase�a);

		// Dise�o llamativo (usa colores, fuentes, im�genes, etc.)
		panelContenidoCambiarContrase�a.setBackground(new Color(240, 240, 240));

		JLabel etiquetaContrase�aAnterior = new JLabel("Contrase�a actual:");
		etiquetaContrase�aAnterior.setBounds(50, 50, 120, 20);
		panelContenidoCambiarContrase�a.add(etiquetaContrase�aAnterior);

		campoContrase�aAnterior = new JPasswordField();
		campoContrase�aAnterior.setBounds(50, 70, 250, 25);
		panelContenidoCambiarContrase�a.add(campoContrase�aAnterior);

		JLabel etiquetaContrase�aNueva = new JLabel("Nueva contrase�a:");
		etiquetaContrase�aNueva.setBounds(50, 100, 120, 20);
		panelContenidoCambiarContrase�a.add(etiquetaContrase�aNueva);

		campoContrase�aNueva = new JPasswordField();
		campoContrase�aNueva.setBounds(50, 120, 250, 25);
		panelContenidoCambiarContrase�a.add(campoContrase�aNueva);

		JLabel etiquetaConfirmarContrase�aNueva = new JLabel(
				"Confirmar nueva contrase�a:");
		etiquetaConfirmarContrase�aNueva.setBounds(50, 150, 160, 20);
		panelContenidoCambiarContrase�a.add(etiquetaConfirmarContrase�aNueva);

		campoConfirmarContrase�aNueva = new JPasswordField();
		campoConfirmarContrase�aNueva.setBounds(50, 170, 250, 25);
		panelContenidoCambiarContrase�a.add(campoConfirmarContrase�aNueva);

		mostrarContrase�aCambiar = new JCheckBox("Mostrar Contrase�a");
		mostrarContrase�aCambiar.setBounds(50, 200, 150, 20);
		panelContenidoCambiarContrase�a.add(mostrarContrase�aCambiar);
		mostrarContrase�aCambiar.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					campoContrase�aAnterior.setEchoChar((char) 0);
					campoContrase�aNueva.setEchoChar((char) 0);
					campoConfirmarContrase�aNueva.setEchoChar((char) 0);
				} else {
					campoContrase�aAnterior.setEchoChar('*');
					campoContrase�aNueva.setEchoChar('*');
					campoConfirmarContrase�aNueva.setEchoChar('*');
				}
			}
		});

		JButton botonCambiar = new JButton("Cambiar");
		botonCambiar.setBounds(50, 230, 150, 30);
		panelContenidoCambiarContrase�a.add(botonCambiar);
		botonCambiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarContrase�a();
			}
		});

		JButton botonCancelarCambiar = new JButton("Cancelar");
		botonCancelarCambiar.setBounds(200, 230, 150, 30);
		panelContenidoCambiarContrase�a.add(botonCancelarCambiar);
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
		panelContenidoCambiarContrase�a.add(getLblCambiarContrasea());

		setVisible(true);
	}

	private void cambiarContrase�a() {
		String contrase�aAnterior = new String(
				campoContrase�aAnterior.getPassword());
		String contrase�aNueva = new String(campoContrase�aNueva.getPassword());
		String confirmarContrase�aNueva = new String(
				campoConfirmarContrase�aNueva.getPassword());

		if (validar()) {
			try {
				// Verificar si las contrase�as coinciden
				if (!contrase�aNueva.equals(confirmarContrase�aNueva)) {
					JOptionPane.showMessageDialog(null,
							"Las contrase�as no coinciden.", "Error",
							JOptionPane.ERROR_MESSAGE);
					campoConfirmarContrase�aNueva.setText("");
					campoContrase�aNueva.setText("");
					return;
				}

				// Verificar si la contrase�a actual es correcta
				if (ServicesLocator
						.getInstance()
						.getUsuarioService()
						.verificarContrase�a(usuarioLogueado,
								contrase�aAnterior)) {
					// Llamar al m�todo de la clase UsuarioServices para cambiar
					// la contrase�a
					boolean cambioExitoso = ServicesLocator
							.getInstance()
							.getUsuarioService()
							.cambiarContrase�a(usuarioLogueado,
									contrase�aAnterior, contrase�aNueva);

					if (cambioExitoso) {
						JOptionPane.showMessageDialog(null,
								"Contrase�a cambiada exitosamente.", "�xito",
								JOptionPane.INFORMATION_MESSAGE);
						campoContrase�aAnterior.setText("");
						campoContrase�aNueva.setText("");
						campoConfirmarContrase�aNueva.setText("");
						dispose(); // Cerrar la ventana
					}
				} else {
					// La contrase�a actual es incorrecta, mostrar mensaje de
					// error
					JOptionPane.showMessageDialog(null,
							"Contrase�a actual incorrecta.", "Error",
							JOptionPane.ERROR_MESSAGE);
					// Limpia el campo de contrase�a anterior
					campoContrase�aAnterior.setText("");
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Error al cambiar la contrase�a: " + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean validar() {
		String contrase�aAnterior = new String(
				campoContrase�aAnterior.getPassword());
		String contrase�aNueva = new String(campoContrase�aNueva.getPassword());
		String confirmarContrase�aNueva = new String(
				campoConfirmarContrase�aNueva.getPassword());

		if (contrase�aAnterior.trim().isEmpty()
				|| contrase�aNueva.trim().isEmpty()
				|| confirmarContrase�aNueva.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"No se pueden dejar campos vac�os.", "Error",
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