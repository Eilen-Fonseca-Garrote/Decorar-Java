package visual;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import model.*;
import services.ServicesLocator;

public class Visual extends JFrame implements ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private String[] nombresColumnas;
	private ArrayList<?> datos;
	private String tablaBD; // Nombre de la tabla en la base de datos
	private String[] nombresCampos; // Nombres de los campos para la interfaz
	private JTextField[] campos; // Campos de texto para la interfaz
	private JButton btnInsertar, btnModificar, btnEliminar, btnLimpiar;

	// ComboBox para seleccionar la tabla
	private JComboBox<String> comboBoxTablas;
	private String[] tablasDisponibles = { "Cliente", "Contrato", "Empleado",
			"Empresa", "Evento", "EventoServicio", "HistorialEventoServicio",
			"Proveedor", "Servicio" };

	private ServicesLocator servicesLocator = ServicesLocator.getInstance();
	private JPanel panelFormulario;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visual frame = new Visual("Empresa");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Visual(String tabla) {
		super("Gestión de Tablas");
		setDatos(tabla);
		crearComboBoxTablas();
		crearPanelFormulario(); // Inicializa el panel de formulario con Cliente
		crearPanelBotones();
		crearTabla();
		configurarJFrame();
	}

	private void setDatos(String tabla) {
		tablaBD = tabla;
		switch (tabla) {
		case "Cliente":
			nombresCampos = new String[] { "  ID", "  Nombre", "  Apellidos",
					"  Dirección", "  Teléfono", "  Email",
					"  Trato Preferencial" };
			nombresColumnas = new String[] { "ID", "Nombre", "Apellidos",
					"Dirección", "Teléfono", "Email", "Trato Preferencial" };
			break;
		case "Contrato":
			nombresCampos = new String[] { "  ID Contrato",
					"  Términos y Condiciones", "  ID Empresa", "  ID Evento",
					"  ID" };
			nombresColumnas = new String[] { "ID Contrato", "Fecha Contrato",
					"Términos y Condiciones", "Precios Negociados",
					"ID Empresa", "ID Evento", "ID", "Tasa de Descuento" };
			break;
		case "Empleado":
			nombresCampos = new String[] { "  DNI Empleado", "  Nombre",
					"  Apellidos", "  Dirección", "  Teléfono", "  Email",
					"  Cargo", "  Departamento", "  ID Empresa" };
			nombresColumnas = new String[] { "DNI Empleado", "Nombre",
					"Apellidos", "Dirección", "Teléfono", "Email", "Cargo",
					"Departamento", "Responsabilidad Eventos", "ID Empresa" };
			break;
		case "Empresa":
			nombresCampos = new String[] { "  ID Empresa", "  Nombre",
					"  Dirección Postal", "  Email", "  Teléfono",
					"  Director General", "  Jefe de RRHH",
					"  Jefe de Contabilidad", "  Secretario Sindicato",
					"  Logo" };
			nombresColumnas = new String[] { "ID Empresa", "Nombre",
					"Dirección Postal", "Email", "Teléfono",
					"Director General", "Jefe de RRHH", "Jefe de Contabilidad",
					"Secretario Sindicato", "Logo" };
			break;
		case "Evento":
			nombresCampos = new String[] { "  ID Evento", "  Nombre", "  Tipo",
					"  Fecha Inicio", "  Fecha Fin", "  Ubicación",
					"  Número Invitados", "  Temas Decoración", "  ID",
					"  DNI Empleado" };
			nombresColumnas = new String[] { "ID Evento", "Nombre", "Tipo",
					"Fecha Inicio", "Fecha Fin", "Fecha Modificación",
					"Ubicación", "Número Invitados", "Temas Decoración", "ID",
					"DNI Empleado" };
			break;
		case "EventoServicio":
			nombresCampos = new String[] { "  ID Servicio Evento",
					"  ID Servicio", "  ID Evento", "  Cantidad",
					"  ID Proveedor" };
			nombresColumnas = new String[] { "ID Servicio Evento",
					"ID Servicio", "ID Evento", "Cantidad", "ID Proveedor" };
			break;
		case "HistorialEventoServicio":
			nombresCampos = new String[] { "  ID Historial", "  Cantidad",
					"  Costo", "  Descripción Modificación", "  ID Contrato",
					"  ID Servicio" };
			nombresColumnas = new String[] { "ID Historial", "Cantidad",
					"Costo", "Fecha Modificación", "Descripción Modificación",
					"ID Contrato", "ID Servicio" };
			btnEliminar.setEnabled(false);
			btnInsertar.setEnabled(false);
			btnModificar.setEnabled(false);
			break;
		case "Proveedor":
			nombresCampos = new String[] { "  ID Proveedor", "  Nombre",
					"  Tipo Servicio", "  Dirección", "  Teléfono", "  Email",
					"  Responsable", "  ID Servicio" };
			nombresColumnas = new String[] { "ID Proveedor", "Nombre",
					"Tipo Servicio", "Dirección", "Teléfono", "Email",
					"Responsable", "Estado", "ID Servicio" };
			break;
		case "Servicio":
			nombresCampos = new String[] { "  ID Servicio",
					"  Nombre Servicio", "  Descripción", "  Categoría",
					"  Precio Unitario", "  Servicio Terceros" };
			nombresColumnas = new String[] { "ID Servicio", "Nombre Servicio",
					"Descripción", "Categoría", "Precio Unitario",
					"Servicio Terceros" };
			break;
		}
	}

	private void crearComboBoxTablas() {
		comboBoxTablas = new JComboBox<String>(tablasDisponibles);
		comboBoxTablas.setSelectedItem(0);
		comboBoxTablas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablaBD = (String) comboBoxTablas.getSelectedItem();
				actualizarFormularioYTabla();
			}
		});
		add(comboBoxTablas, BorderLayout.NORTH);
	}

	private void crearPanelFormulario() {
		panelFormulario = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		campos = new JTextField[nombresCampos.length];

		for (int i = 0; i < nombresCampos.length; i++) {
			JLabel label = new JLabel(nombresCampos[i]);
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.insets = new Insets(5, 5, 5, 5);
			panelFormulario.add(label, gbc);

			if (nombresCampos[i].equalsIgnoreCase("  Trato Preferencial")) {
				gbc.gridx = 1;
				JCheckBox checkbox = new JCheckBox();
				panelFormulario.add(checkbox, gbc);
				checkbox.setVisible(true);
				checkbox.setEnabled(false);
			} else {
				campos[i] = new JTextField(20); // Ancho máximo de 20 caracteres
				gbc.gridx = 1;
				panelFormulario.add(campos[i], gbc);
				campos[i].setEnabled(false);
			}
		}
		add(panelFormulario, BorderLayout.WEST);
	}

	private void crearPanelBotones() {
		JPanel panelBotones = new JPanel();
		btnInsertar = new JButton("Insertar");
		btnModificar = new JButton("Modificar");
		btnEliminar = new JButton("Eliminar");
		btnLimpiar = new JButton("Limpiar Campos");

		JButton botonCancelar = new JButton("Cerrar");
		botonCancelar.setBounds(200, 230, 150, 30);
		botonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		botonCancelar.setBackground(new Color(255, 0, 0));
		botonCancelar.setForeground(Color.WHITE);

		btnInsertar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertarDatos();
			}
		});

		btnModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificarDatos();
			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarDatos();
			}
		});

		btnLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
			}
		});

		botonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		panelBotones.add(btnInsertar);
		panelBotones.add(btnModificar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnLimpiar);
		panelBotones.add(botonCancelar);
		add(panelBotones, BorderLayout.SOUTH);
	}

	private void crearTabla() {
		modeloTabla = new DefaultTableModel(nombresColumnas, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla = new JTable(modeloTabla);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		actualizarTabla();
		add(new JScrollPane(tabla), BorderLayout.CENTER);
		tabla.getSelectionModel().addListSelectionListener(this);
	}

	private void configurarJFrame() {
		setSize(800, 600);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void actualizarFormularioYTabla() {
		tablaBD = (String) comboBoxTablas.getSelectedItem();
		btnEliminar.setEnabled(true);
		btnInsertar.setEnabled(true);
		btnModificar.setEnabled(true);
		setDatos(tablaBD);
		
		actualizarCamposFormulario();

		// Actualiza el modelo de la tabla
		modeloTabla.setColumnIdentifiers(nombresColumnas);
		// Actualiza la tabla
		actualizarTabla();
	}

	// Métodos para insertar, modificar, eliminar y limpiar
	private void insertarDatos() {
		if (!campos[2].isEnabled()) {
			for (int j = 0; j < nombresCampos.length; j++) {
				if (j < nombresCampos.length) {
					String nombreCampo = nombresCampos[j];
					if (nombreCampo.equalsIgnoreCase("  Trato Preferencial")) {
						JCheckBox c = buscarCheckBox();
						c.setEnabled(true);

					} else {
						campos[j].setEnabled(true);
						;
					}
				}
			}
		} else {
			switch (tablaBD) {
			case "Cliente":
				if (verificarCampos())
					insertarCliente();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Contrato":
				if (verificarCampos())
					insertarContrato();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Empleado":
				if (verificarCampos())
					insertarEmpleado();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Empresa":
				if (verificarCampos())
					insertarEmpresa();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Evento":
				if (verificarCampos())
					insertarEvento();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "EventoServicio":
				if (verificarCampos())
					insertarEventoServicio();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "HistorialEventoServicio":
				if (verificarCampos())
					insertarHistorialEventoServicio();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Proveedor":
				if (verificarCampos())
					insertarProveedor();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Servicio":
				if (verificarCampos())
					insertarServicio();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			}
		}
	}

	private void modificarDatos() {
		if (!campos[2].isEnabled()) {
			for (int j = 0; j < nombresCampos.length; j++) {
				if (j < nombresCampos.length) {
					String nombreCampo = nombresCampos[j];
					if (nombreCampo.equalsIgnoreCase("  Trato Preferencial")) {
						JCheckBox c = buscarCheckBox();
						c.setEnabled(true);

					} else {
						campos[j].setEnabled(true);
						;
					}
				}
			}
		} else {
			switch (tablaBD) {
			case "Cliente":
				if (verificarCampos())
					modificarCliente();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Contrato":
				if (verificarCampos())
					modificarContrato();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Empleado":
				if (verificarCampos())
					modificarEmpleado();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Empresa":
				if (verificarCampos())
					modificarEmpresa();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Evento":
				if (verificarCampos())
					modificarEvento();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "EventoServicio":
				if (verificarCampos())
					modificarEventoServicio();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "HistorialEventoServicio":
				if (verificarCampos())
					modificarHistorialEventoServicio();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Proveedor":
				if (verificarCampos())
					modificarProveedor();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				break;
			case "Servicio":
				if (verificarCampos())
					modificarServicio();
				else
					JOptionPane.showMessageDialog(this,
							"Por favor, complete todos los campos.");
				modificarServicio();
				break;
			}
		}
	}

	private void eliminarDatos() {
		switch (tablaBD) {
		case "Cliente":
			int opcion = JOptionPane.showConfirmDialog(this,
					"¿Está seguro de que desea eliminar este cliente?",
					"Confirmar Eliminación", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {
				int opcion2 = JOptionPane
						.showConfirmDialog(
								this,
								"Si borra este cliente, toda la información relacionada con él en la base de datos desaparecerá.\n¿Está seguro de continuar?",
								"Confirmar Eliminación",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);

				if (opcion2 == JOptionPane.YES_OPTION) {
					eliminarCliente();
				}
			}
			break;
		case "Contrato":
			eliminarContrato();
			break;
		case "Empleado":
			int opcion1 = JOptionPane.showConfirmDialog(this,
					"¿Está seguro de que desea eliminar este empleado?",
					"Confirmar Eliminación", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (opcion1 == JOptionPane.YES_OPTION) {
				int opcion22 = JOptionPane
						.showConfirmDialog(
								this,
								"Si borra este empleado, toda la información relacionada con él en la base de datos desaparecerá.\n¿Está seguro de continuar?",
								"Confirmar Eliminación",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);

				if (opcion22 == JOptionPane.YES_OPTION) {
					eliminarEmpleado();
				}
			}

			break;
		case "Empresa":
			int opcion11 = JOptionPane.showConfirmDialog(this,
					"¿Está seguro de que desea eliminar esta empresa?",
					"Confirmar Eliminación", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (opcion11 == JOptionPane.YES_OPTION) {
				int opcion222 = JOptionPane
						.showConfirmDialog(
								this,
								"Si borra esta empresa, toda la información relacionada con ella en la base de datos desaparecerá.\n¿Está seguro de continuar?",
								"Confirmar Eliminación",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);

				if (opcion222 == JOptionPane.YES_OPTION) {
					eliminarEmpresa();
				}
			}
			break;
		case "Evento":
			eliminarEvento();
			break;
		case "EventoServicio":
			eliminarEventoServicio();
			break;
		case "HistorialEventoServicio":
			eliminarHistorialEventoServicio();
			break;
		case "Proveedor":
			int opcion111 = JOptionPane.showConfirmDialog(this,
					"¿Está seguro de que desea eliminar este proveedor?",
					"Confirmar Eliminación", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (opcion111 == JOptionPane.YES_OPTION) {
				int opcion2222 = JOptionPane
						.showConfirmDialog(
								this,
								"Si borra este proveedor, toda la información relacionada con él en la base de datos desaparecerá.\n¿Está seguro de continuar?",
								"Confirmar Eliminación",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);

				if (opcion2222 == JOptionPane.YES_OPTION) {
					eliminarProveedor();
				}
			}

			break;
		case "Servicio":
			int opcion1111 = JOptionPane.showConfirmDialog(this,
					"¿Está seguro de que desea eliminar este servicio?",
					"Confirmar Eliminación", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (opcion1111 == JOptionPane.YES_OPTION) {
				int opcion22222 = JOptionPane
						.showConfirmDialog(
								this,
								"Si borra este servicio, toda la información relacionada con él en la base de datos desaparecerá.\n¿Está seguro de continuar?",
								"Confirmar Eliminación",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);

				if (opcion22222 == JOptionPane.YES_OPTION) {
					eliminarServicio();
				}
			}

			break;
		}
	}

	private void limpiarCampos() {
		if (tablaBD.equalsIgnoreCase("Cliente")) {
			int cantCampos = campos.length - 1;
			int i = 0;
			for (JTextField campo : campos) {
				if (i++ < cantCampos) {
					campo.setText("");
					campo.setEnabled(false);
				} else {
					buscarCheckBox().setSelected(false);
					buscarCheckBox().setEnabled(false);
				}
			}
		} else {
			for (JTextField campo : campos) {
				campo.setText("");
			}
		}
	}

	// Métodos específicos para cada tabla
	private void insertarCliente() {
		String id = campos[0].getText();
		String nombre = campos[1].getText();
		String apellidos = campos[2].getText();
		String direccion = campos[3].getText();
		String telefono = campos[4].getText();
		String email = campos[5].getText();
		boolean tratoPreferencial = buscarCheckBox().isSelected();

		Cliente c = new Cliente(id, nombre, apellidos, direccion, telefono,
				email, tratoPreferencial);
		if (servicesLocator.getClienteService().insertarCliente(c)) {
			JOptionPane.showMessageDialog(this,
					"Cliente insertado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void modificarCliente() {
		String id = campos[0].getText();
		String nombre = campos[1].getText();
		String apellidos = campos[2].getText();
		String direccion = campos[3].getText();
		String telefono = campos[4].getText();
		String email = campos[5].getText();
		boolean tratoPreferencial = buscarCheckBox().isSelected();

		Cliente c = new Cliente(id, nombre, apellidos, direccion, telefono,
				email, tratoPreferencial);
		if (servicesLocator.getClienteService().modificarCliente(c)) {
			JOptionPane.showMessageDialog(this,
					"Cliente modificado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void eliminarCliente() {
		if (tabla.getSelectedRow() >= 0) {
			String idCliente = (modeloTabla.getValueAt(tabla.getSelectedRow(),
					0).toString());
			if (ServicesLocator.getInstance().getClienteService()
					.eliminarCliente(idCliente)) {
				JOptionPane.showMessageDialog(this,
						"Cliente eliminado correctamente.");
				actualizarTabla();
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Seleccione un cliente de la tabla para eliminar.");
		}
	}

	private void insertarContrato() {
		String terminosCondiciones = campos[1].getText();
		int idEmpresa = Integer.parseInt(campos[2].getText());
		int idEvento = Integer.parseInt(campos[3].getText());
		String id = campos[4].getText();

		Contrato c = new Contrato(0, null, terminosCondiciones, 0, idEmpresa,
				idEvento, id, 0);
		if (servicesLocator.getContratoService().insertarContrato(c)) {
			JOptionPane.showMessageDialog(this,
					"Contrato insertado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void modificarContrato() {
		int idContrato = Integer.parseInt(campos[0].getText());
		String terminosCondiciones = campos[1].getText();
		int idEmpresa = Integer.parseInt(campos[2].getText());
		int idEvento = Integer.parseInt(campos[3].getText());
		String id = campos[4].getText();

		Contrato c = new Contrato(idContrato, null, terminosCondiciones, 0,
				idEmpresa, idEvento, id, 0);
		if (servicesLocator.getContratoService().modificarContrato(c)) {
			JOptionPane.showMessageDialog(this,
					"Contrato modificado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void eliminarContrato() {
		if (tabla.getSelectedRow() >= 0) {
			int idContrato = Integer.parseInt(modeloTabla.getValueAt(
					tabla.getSelectedRow(), 0).toString());
			if (ServicesLocator.getInstance().getContratoService()
					.eliminarContrato(idContrato)) {
				JOptionPane.showMessageDialog(this,
						"Contrato eliminado correctamente.");
				actualizarTabla();
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Seleccione un contrato de la tabla para eliminar.");
		}
	}

	private void insertarEmpleado() {
		String nombreEmpleado = campos[1].getText();
		String apellidosEmpleado = campos[2].getText();
		String direccionEmpleado = campos[3].getText();
		String telefonoEmpleado = campos[4].getText();
		String emailEmpleado = campos[5].getText();
		String cargo = campos[6].getText();
		String departamento = campos[7].getText();
		int idEmpresa = Integer.parseInt(campos[8].getText());

		Empleado e = new Empleado(0, nombreEmpleado, apellidosEmpleado,
				direccionEmpleado, telefonoEmpleado, emailEmpleado, cargo,
				departamento, false, idEmpresa);
		if (servicesLocator.getEmpleadoService().insertarEmpleado(e)) {
			JOptionPane.showMessageDialog(this,
					"Empleado insertado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void modificarEmpleado() {
		int dniEmpleado = Integer.parseInt(campos[0].getText());
		String nombreEmpleado = campos[1].getText();
		String apellidosEmpleado = campos[2].getText();
		String direccionEmpleado = campos[3].getText();
		String telefonoEmpleado = campos[4].getText();
		String emailEmpleado = campos[5].getText();
		String cargo = campos[6].getText();
		String departamento = campos[7].getText();
		int idEmpresa = Integer.parseInt(campos[8].getText());

		Empleado e = new Empleado(dniEmpleado, nombreEmpleado,
				apellidosEmpleado, direccionEmpleado, telefonoEmpleado,
				emailEmpleado, cargo, departamento, false, idEmpresa);
		if (servicesLocator.getEmpleadoService().modificarEmpleado(e)) {
			JOptionPane.showMessageDialog(this,
					"Empleado modificado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void eliminarEmpleado() {
		if (tabla.getSelectedRow() >= 0) {
			int idEmpleado = Integer.parseInt(modeloTabla.getValueAt(
					tabla.getSelectedRow(), 0).toString());
			if (ServicesLocator.getInstance().getEmpleadoService()
					.eliminarEmpleado(idEmpleado)) {
				JOptionPane.showMessageDialog(this,
						"Empleado eliminado correctamente.");
				actualizarTabla();
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Seleccione un empleado de la tabla para eliminar.");
		}
	}

	private void insertarEmpresa() {
		String nombreEmpresa = campos[1].getText();
		String direccionPostal = campos[2].getText();
		String emailEmpresa = campos[3].getText();
		String telefono = campos[4].getText();
		String directorGeneral = campos[5].getText();
		String jefeRecursosHumanos = campos[6].getText();
		String jefeContabilidad = campos[7].getText();
		String secretarioSindicato = campos[8].getText();
		// Ruta de la imagen
		String rutaImagen = campos[9].getText();
		// Leer la imagen como un InputStream
		InputStream logo = null;
		if (!rutaImagen.trim().isEmpty()) {
			try {
				logo = new FileInputStream(new File(rutaImagen));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}

		Empresa e = new Empresa(0, nombreEmpresa, direccionPostal,
				emailEmpresa, telefono, directorGeneral, jefeRecursosHumanos,
				jefeContabilidad, secretarioSindicato, logo);
		if (servicesLocator.getEmpresaService().insertarEmpresa(e)) {
			JOptionPane.showMessageDialog(this,
					"Empresa insertada correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void modificarEmpresa() {
		int idEmpresa = Integer.parseInt(campos[0].getText());
		String nombreEmpresa = campos[1].getText();
		String direccionPostal = campos[2].getText();
		String emailEmpresa = campos[3].getText();
		String telefono = campos[4].getText();
		String directorGeneral = campos[5].getText();
		String jefeRecursosHumanos = campos[6].getText();
		String jefeContabilidad = campos[7].getText();
		String secretarioSindicato = campos[8].getText();
		String rutaImagen = campos[9].getText();
		InputStream logo = null;
		try {
			logo = new FileInputStream(new File(rutaImagen));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		Empresa e = new Empresa(idEmpresa, nombreEmpresa, direccionPostal,
				emailEmpresa, telefono, directorGeneral, jefeRecursosHumanos,
				jefeContabilidad, secretarioSindicato, logo);
		if (servicesLocator.getEmpresaService().modificarEmpresa(e)) {
			JOptionPane.showMessageDialog(this,
					"Empresa modificada correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void eliminarEmpresa() {
		if (tabla.getSelectedRow() >= 0) {
			int idEmpresa = Integer.parseInt(modeloTabla.getValueAt(
					tabla.getSelectedRow(), 0).toString());
			if (ServicesLocator.getInstance().getEmpresaService()
					.eliminarEmpresa(idEmpresa)) {
				JOptionPane.showMessageDialog(this,
						"Empresa eliminado correctamente.");
				actualizarTabla();
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Seleccione un empresa de la tabla para eliminar.");
		}
	}

	private void insertarEvento() {
		String nombreEvento = campos[1].getText();
		String tipoEvento = campos[2].getText();
		Timestamp fechaInicio = Timestamp.valueOf(campos[3].getText());
		Timestamp fechaFin = Timestamp.valueOf(campos[4].getText());
		String ubicacion = campos[5].getText();
		int numeroInvitados = Integer.parseInt(campos[6].getText());
		String temasDecoracion = campos[7].getText();
		String id = campos[8].getText();
		int dniEmpleado = Integer.parseInt(campos[9].getText());

		Evento e = new Evento(0, nombreEvento, tipoEvento, fechaInicio,
				fechaFin, null, ubicacion, numeroInvitados, temasDecoracion,
				id, dniEmpleado);
		if (servicesLocator.getEventoService().insertarEvento(e)) {
			JOptionPane.showMessageDialog(this,
					"Evento insertado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void modificarEvento() {
		int idEvento = Integer.parseInt(campos[0].getText());
		String nombreEvento = campos[1].getText();
		String tipoEvento = campos[2].getText();
		Timestamp fechaInicio = Timestamp.valueOf(campos[3].getText());
		Timestamp fechaFin = Timestamp.valueOf(campos[4].getText());
		String ubicacion = campos[5].getText();
		int numeroInvitados = Integer.parseInt(campos[6].getText());
		String temasDecoracion = campos[7].getText();
		String id = campos[8].getText();
		int dniEmpleado = Integer.parseInt(campos[9].getText());

		Evento e = new Evento(idEvento, nombreEvento, tipoEvento, fechaInicio,
				fechaFin, null, ubicacion, numeroInvitados, temasDecoracion,
				id, dniEmpleado);
		if (servicesLocator.getEventoService().modificarEvento(e)) {
			JOptionPane.showMessageDialog(this,
					"Evento modificado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void eliminarEvento() {
		if (tabla.getSelectedRow() >= 0) {
			int idEvento = Integer.parseInt(modeloTabla.getValueAt(
					tabla.getSelectedRow(), 0).toString());
			if (ServicesLocator.getInstance().getEventoService()
					.eliminarEvento(idEvento)) {
				JOptionPane.showMessageDialog(this,
						"Evento eliminado correctamente.");
				actualizarTabla();
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Seleccione un evento de la tabla para eliminar.");
		}
	}

	private void insertarEventoServicio() {
		int idServicio = Integer.parseInt(campos[1].getText());
		int idEvento = Integer.parseInt(campos[2].getText());
		int cantidad = Integer.parseInt(campos[3].getText());
		int idProveedor = Integer.parseInt(campos[4].getText());

		EventoServicio es = new EventoServicio(0, idServicio, idEvento,
				cantidad, idProveedor);
		if (servicesLocator.getEventoServicioService().insertarEventoServicio(
				es)) {
			JOptionPane.showMessageDialog(this,
					"EventoServicio insertado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void modificarEventoServicio() {
		int idServicioEvento = Integer.parseInt(campos[0].getText());
		int idServicio = Integer.parseInt(campos[1].getText());
		int idEvento = Integer.parseInt(campos[2].getText());
		int cantidad = Integer.parseInt(campos[3].getText());
		int idProveedor = Integer.parseInt(campos[4].getText());

		EventoServicio es = new EventoServicio(idServicioEvento, idServicio,
				idEvento, cantidad, idProveedor);
		if (servicesLocator.getEventoServicioService().modificarEventoServicio(
				es)) {
			JOptionPane.showMessageDialog(this,
					"EventoServicio modificado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void eliminarEventoServicio() {
		if (tabla.getSelectedRow() >= 0) {
			int idEventoServicio = Integer.parseInt(modeloTabla.getValueAt(
					tabla.getSelectedRow(), 0).toString());
			if (ServicesLocator.getInstance().getEventoServicioService()
					.eliminarEventoServicio(idEventoServicio)) {
				JOptionPane.showMessageDialog(this,
						"Servicio ligado a evento eliminado correctamente.");
				actualizarTabla();
			}
		} else {
			JOptionPane
					.showMessageDialog(this,
							"Seleccione un servicio ligado a evento de la tabla para eliminar.");
		}
	}

	private void insertarHistorialEventoServicio() {
		int cantidad = Integer.parseInt(campos[1].getText());
		double costo = Double.parseDouble(campos[2].getText());
		String descripcionModificacion = campos[3].getText();
		int idContrato = Integer.parseInt(campos[4].getText());
		int idServicio = Integer.parseInt(campos[5].getText());

		HistorialEventoServicio hes = new HistorialEventoServicio(0, cantidad,
				costo, null, descripcionModificacion, idContrato, idServicio);
		if (servicesLocator.getHistorialEventoServicioService()
				.insertarHistorialEventoServicio(hes)) {
			JOptionPane.showMessageDialog(this,
					"HistorialEventoServicio insertado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void modificarHistorialEventoServicio() {
		int idHistorial = Integer.parseInt(campos[0].getText());
		int cantidad = Integer.parseInt(campos[1].getText());
		double costo = Double.parseDouble(campos[2].getText());
		String descripcionModificacion = campos[3].getText();
		int idContrato = Integer.parseInt(campos[4].getText());
		int idServicio = Integer.parseInt(campos[5].getText());

		HistorialEventoServicio hes = new HistorialEventoServicio(idHistorial,
				cantidad, costo, null, descripcionModificacion, idContrato,
				idServicio);
		if (servicesLocator.getHistorialEventoServicioService()
				.modificarHistorialEventoServicio(hes)) {
			JOptionPane.showMessageDialog(this,
					"HistorialEventoServicio modificado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void eliminarHistorialEventoServicio() {
		if (tabla.getSelectedRow() >= 0) {
			int idHistorialEventoServicio = Integer.parseInt(modeloTabla
					.getValueAt(tabla.getSelectedRow(), 0).toString());
			if (ServicesLocator.getInstance()
					.getHistorialEventoServicioService()
					.eliminarHistorialEventoServicio(idHistorialEventoServicio)) {
				JOptionPane
						.showMessageDialog(this,
								"Historial de servicio ligado a evento eliminado correctamente.");
				actualizarTabla();
			}
		} else {
			JOptionPane
					.showMessageDialog(
							this,
							"Seleccione un historial de servicio ligado a evento de la tabla para eliminar.");
		}
	}

	private void insertarProveedor() {
		String nombreProveedor = campos[1].getText();
		String tipoServicio = campos[2].getText();
		String direccion = campos[3].getText();
		String telefono = campos[4].getText();
		String email = campos[5].getText();
		String responsable = campos[6].getText();
		int idServicio = Integer.parseInt(campos[7].getText());

		Proveedor p = new Proveedor(0, nombreProveedor, tipoServicio,
				direccion, telefono, email, responsable, false, idServicio);
		if (servicesLocator.getProveedorService().insertarProveedor(p)) {
			JOptionPane.showMessageDialog(this,
					"Proveedor insertado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void modificarProveedor() {
		int idProveedor = Integer.parseInt(campos[0].getText());
		String nombreProveedor = campos[1].getText();
		String tipoServicio = campos[2].getText();
		String direccion = campos[3].getText();
		String telefono = campos[4].getText();
		String email = campos[5].getText();
		String responsable = campos[6].getText();
		int idServicio = Integer.parseInt(campos[7].getText());

		Proveedor p = new Proveedor(idProveedor, nombreProveedor, tipoServicio,
				direccion, telefono, email, responsable, false, idServicio);
		if (servicesLocator.getProveedorService().modificarProveedor(p)) {
			JOptionPane.showMessageDialog(this,
					"Proveedor modificado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void eliminarProveedor() {
		if (tabla.getSelectedRow() >= 0) {
			int idProveedor = Integer.parseInt(modeloTabla.getValueAt(
					tabla.getSelectedRow(), 0).toString());
			if (ServicesLocator.getInstance().getProveedorService()
					.eliminarProveedor(idProveedor)) {
				JOptionPane.showMessageDialog(this,
						"Proveedor eliminado correctamente.");
				actualizarTabla();
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Seleccione un proveedor de la tabla para eliminar.");
		}
	}

	private void insertarServicio() {
		String nombreServicio = campos[1].getText();
		String descripcionServicio = campos[2].getText();
		String categoriaServicio = campos[3].getText();
		double precioUnitario = Double.parseDouble(campos[4].getText());
		boolean servicioTerceros = Boolean.parseBoolean(campos[5].getText());

		Servicio s = new Servicio(0, nombreServicio, descripcionServicio,
				categoriaServicio, precioUnitario, servicioTerceros);
		if (servicesLocator.getServicioService().insertarServicio(s)) {
			JOptionPane.showMessageDialog(this,
					"Servicio insertado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void modificarServicio() {
		int idServicio = Integer.parseInt(campos[0].getText());
		String nombreServicio = campos[1].getText();
		String descripcionServicio = campos[2].getText();
		String categoriaServicio = campos[3].getText();
		double precioUnitario = Double.parseDouble(campos[4].getText());
		boolean servicioTerceros = Boolean.parseBoolean(campos[5].getText());

		Servicio s = new Servicio(idServicio, nombreServicio,
				descripcionServicio, categoriaServicio, precioUnitario,
				servicioTerceros);
		if (servicesLocator.getServicioService().modificarServicio(s)) {
			JOptionPane.showMessageDialog(this,
					"Servicio modificado correctamente");
			limpiarCampos();
			actualizarTabla();
		}
	}

	private void eliminarServicio() {
		if (tabla.getSelectedRow() >= 0) {
			int idServicio = Integer.parseInt(modeloTabla.getValueAt(
					tabla.getSelectedRow(), 0).toString());
			if (ServicesLocator.getInstance().getServicioService()
					.eliminarServicio(idServicio)) {
				JOptionPane.showMessageDialog(this,
						"Servicio eliminado correctamente.");
				actualizarTabla();
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Seleccione un servicio de la tabla para eliminar.");
		}
	}

	private void actualizarTabla() {
		datos = new ArrayList<>();
		switch (tablaBD) {
		case "Cliente":
			datos = servicesLocator.getClienteService().consultarClientes();
			break;
		case "Contrato":
			datos = servicesLocator.getContratoService().consultarContratos();
			break;
		case "Empleado":
			datos = servicesLocator.getEmpleadoService().consultarEmpleados();
			break;
		case "Empresa":
			datos = servicesLocator.getEmpresaService().consultarEmpresas();
			break;
		case "Evento":
			datos = servicesLocator.getEventoService().consultarEventos();
			break;
		case "EventoServicio":
			datos = servicesLocator.getEventoServicioService()
					.consultarEventoServicios();
			break;
		case "HistorialEventoServicio":
			datos = servicesLocator.getHistorialEventoServicioService()
					.consultarHistorialEventoServicios();
			break;
		case "Proveedor":
			datos = servicesLocator.getProveedorService()
					.consultarProveedores();
			break;
		case "Servicio":
			datos = servicesLocator.getServicioService().consultarServicios();
			break;
		}
		modeloTabla.setRowCount(0);
		for (Object objeto : datos) {
			Vector<Object> fila = new Vector<>();
			if (objeto instanceof Cliente) {
				Cliente c = (Cliente) objeto;
				fila.add(c.getId());
				fila.add(c.getNombreCliente());
				fila.add(c.getApellidosCliente());
				fila.add(c.getDireccionCliente());
				fila.add(c.getTelefonoCliente());
				fila.add(c.getEmailCliente());
				fila.add(c.isTratoPreferencial());
			} else if (objeto instanceof Contrato) {
				Contrato c = (Contrato) objeto;
				fila.add(c.getIdContrato());
				fila.add(c.getFechaContrato());
				fila.add(c.getTerminosCondiciones());
				fila.add(c.getPreciosNegociados());
				fila.add(c.getIdEmpresa());
				fila.add(c.getIdEvento());
				fila.add(c.getId());
				fila.add(c.getTasaDescuento());
			} else if (objeto instanceof Empleado) {
				Empleado e = (Empleado) objeto;
				fila.add(e.getDniEmpleado());
				fila.add(e.getNombreEmpleado());
				fila.add(e.getApellidosEmpleado());
				fila.add(e.getDireccionEmpleado());
				fila.add(e.getTelefonoEmpleado());
				fila.add(e.getEmailEmpleado());
				fila.add(e.getCargo());
				fila.add(e.getDepartamento());
				fila.add(e.isResponsabilidadEventos());
				fila.add(e.getIdEmpresa());
			} else if (objeto instanceof Empresa) {
				Empresa e = (Empresa) objeto;
				fila.add(e.getIdEmpresa());
				fila.add(e.getNombreEmpresa());
				fila.add(e.getDireccionPostal());
				fila.add(e.getEmailEmpresa());
				fila.add(e.getTelefono());
				fila.add(e.getDirectorGeneral());
				fila.add(e.getJefeRecursosHumanos());
				fila.add(e.getJefeContabilidad());
				fila.add(e.getSecretarioSindicato());
				fila.add(e.getLogo()); // Aquí puedes mostrar una representación
										// del logo (por ejemplo, un icono)
			} else if (objeto instanceof Evento) {
				Evento e = (Evento) objeto;
				fila.add(e.getIdEvento());
				fila.add(e.getNombreEvento());
				fila.add(e.getTipoEvento());
				fila.add(e.getFechaInicio());
				fila.add(e.getFechaFin());
				fila.add(e.getFechaModificacion());
				fila.add(e.getUbicacion());
				fila.add(e.getNumeroInvitados());
				fila.add(e.getTemasDecoracion());
				fila.add(e.getId());
				fila.add(e.getDniEmpleado());
			} else if (objeto instanceof EventoServicio) {
				EventoServicio es = (EventoServicio) objeto;
				fila.add(es.getIdServicioEvento());
				fila.add(es.getIdServicio());
				fila.add(es.getIdEvento());
				fila.add(es.getCantidad());
				fila.add(es.getIdProveedor());
			} else if (objeto instanceof HistorialEventoServicio) {
				HistorialEventoServicio hes = (HistorialEventoServicio) objeto;
				fila.add(hes.getIdHistorial());
				fila.add(hes.getCantidad());
				fila.add(hes.getCosto());
				fila.add(hes.getFechaModificacion());
				fila.add(hes.getDescripcionModificacion());
				fila.add(hes.getIdContrato());
				fila.add(hes.getIdServicio());
			} else if (objeto instanceof Proveedor) {
				Proveedor p = (Proveedor) objeto;
				fila.add(p.getIdProveedor());
				fila.add(p.getNombreProveedor());
				fila.add(p.getTipoServicio());
				fila.add(p.getDireccion());
				fila.add(p.getTelefono());
				fila.add(p.getEmail());
				fila.add(p.getResponsable());
				fila.add(p.isEstadoProveedor());
				fila.add(p.getIdServicio());
			} else if (objeto instanceof Servicio) {
				Servicio s = (Servicio) objeto;
				fila.add(s.getIdServicio());
				fila.add(s.getNombreServicio());
				fila.add(s.getDescripcionServicio());
				fila.add(s.getCategoriaServicio());
				fila.add(s.getPrecioUnitario());
				fila.add(s.isServicioTerceros());
			}
			modeloTabla.addRow(fila);
		}
	}

	private void actualizarCamposFormulario() {
		panelFormulario.removeAll(); // Limpiar el panel antes de agregar los
										// nuevos campos

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		campos = new JTextField[nombresCampos.length];

		for (int i = 0; i < nombresCampos.length; i++) {
			JLabel label = new JLabel(nombresCampos[i]);
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.insets = new Insets(5, 5, 5, 5);
			panelFormulario.add(label, gbc);

			if (nombresCampos[i].equalsIgnoreCase("  Trato Preferencial")) {
				gbc.gridx = 1;
				JCheckBox checkbox = new JCheckBox();
				panelFormulario.add(checkbox, gbc);
				checkbox.setVisible(true);
				checkbox.setEnabled(false);
			} else {
				campos[i] = new JTextField(20); // Ancho máximo de 20 caracteres
				gbc.gridx = 1;
				panelFormulario.add(campos[i], gbc);
				campos[i].setEnabled(false);
			}
		}

		panelFormulario.revalidate(); // Actualizar el diseño del panel
		panelFormulario.repaint(); // Volver a pintar el panel
	}

	private boolean verificarCampos() {
		for (JTextField n : campos) {
			if (!tablaBD.equalsIgnoreCase("Cliente")
					&& buscarCheckBox() != null) {
				String text = n.getText();
				if (text.trim().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		int selectedRow = tabla.getSelectedRow();
		rellenarCampos(selectedRow);
	}

	private void rellenarCampos(int selectedRow) {
		if (selectedRow != -1) {
			for (int i = 0, j = 0; i < nombresColumnas.length; i++) {
				if (j < nombresCampos.length) {
					String nombreCampo = nombresCampos[j];
					if (nombreCampo.trim().equalsIgnoreCase(nombresColumnas[i])) {
						String valor = String.valueOf(modeloTabla.getValueAt(
								selectedRow, i));

						if (!valor.trim().equalsIgnoreCase("null")) {
							if (nombreCampo
									.equalsIgnoreCase("  Trato Preferencial")) {
								JCheckBox checkbox = buscarCheckBox();
								checkbox.setSelected(valor.trim()
										.equalsIgnoreCase("true"));
							} else {
								campos[j].setText(valor);
							}

						} else {
							campos[j].setText("___");
						}
						j++;
					}

				}
			}

		} else {
			limpiarCampos();
		}
	}

	private JCheckBox buscarCheckBox() {
		Component[] components = panelFormulario.getComponents();
		for (Component component : components) {
			if (component instanceof JCheckBox) {
				JCheckBox checkbox = (JCheckBox) component;
				return checkbox;
			}
		}
		return null;
	}

}
