package visual;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import utils.Conexion;
@SuppressWarnings("unused")
public class InsertarEventoJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Connection conexion; // Conexión a la base de datos

	// JFrames para cada paso
	private JFrame inicioJFrame;
	private JFrame nuevoClienteJFrame;
	private JFrame seleccionarClienteJFrame;
	private JFrame seleccionarEmpresaJFrame;
	private JFrame agregarEventoJFrame;
	private JFrame agregarServicioJFrame;
	private JFrame finalizarContratoJFrame;

	// Componentes de los JFrames
	private JButton nuevoClienteButton, seleccionarClienteButton,
			aceptarButton, cancelarButton, volverButton;
	private JComboBox<String> clientesComboBox, empresasComboBox,
			serviciosComboBox, proveedoresComboBox;
	private JTextField nombreClienteTextField, apellidoClienteTextField,
			direccionClienteTextField, telefonoClienteTextField,
			emailClienteTextField, tratoPreferencialTextField,
			nombreEmpresaTextField, idEmpresaTextField, nombreEventoTextField,
			tipoEventoTextField, fechaInicioTextField, fechaFinTextField,
			ubicacionTextField, temasDecoracionTextField,
			cantidadServicioTextField;
	private JLabel nombreLabel, idLabel, tratoPreferencialLabel,
			nombreEmpresaLabel, idEmpresaLabel, apellidoLabel, direccionLabel,
			nombreEventoLabel, tipoEventoLabel, fechaInicioLabel,
			fechaFinLabel, ubicacionLabel, temasDecoracionLabel, telefonoLabel,
			cantidadServicioLabel, servicioLabel, proveedorLabel, mensajeLabel,
			emailLabel;
	private JTable serviciosTabla;

	public InsertarEventoJFrame(Connection conexion) {
		this.conexion = conexion;
		crearJFrames(); // Crear todos los JFrames necesarios
		inicioJFrame.setVisible(true); // Mostrar el JFrame de inicio
	}

	// Crea todos los JFrames necesarios
	private void crearJFrames() {
		// JFrame de inicio
		inicioJFrame = new JFrame("Insertar Evento");
		inicioJFrame.setSize(400, 200);
		inicioJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inicioJFrame.setLayout(new GridLayout(3, 1, 10, 10)); // Agregar espacio
																// entre filas

		nuevoClienteButton = new JButton("Nuevo Cliente");
		seleccionarClienteButton = new JButton("Seleccionar Cliente");
		aceptarButton = new JButton("Aceptar");

		nuevoClienteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inicioJFrame.setVisible(false);
				nuevoClienteJFrame.setVisible(true);
			}
		});

		seleccionarClienteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inicioJFrame.setVisible(false);
				llenarClientesComboBox();
				seleccionarClienteJFrame.setVisible(true);
			}
		});

		aceptarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtener datos del cliente actual
				String nombreCliente = nombreClienteTextField.getText();
				String apellidoCliente = apellidoClienteTextField.getText();
				// ... (obtener otros datos del cliente)
				// Agregar nuevo cliente a la base de datos
				try {
					// Insertar cliente en la base de datos
					String query = "INSERT INTO Cliente (nombre, apellido, direccion, telefono, email, tratoPreferencial) VALUES (?, ?, ?, ?, ?, ?)";
					PreparedStatement stmt = conexion.prepareStatement(query);
					stmt.setString(1, nombreCliente);
					stmt.setString(2, apellidoCliente);
					// ... (establecer otros parámetros)
					stmt.executeUpdate();
					stmt.close();

					// Mostrar mensaje de éxito
					JOptionPane.showMessageDialog(nuevoClienteJFrame,
							"Cliente agregado correctamente.");
					// Ocultar JFrame y volver al JFrame de inicio
					nuevoClienteJFrame.setVisible(false);
					inicioJFrame.setVisible(true);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(nuevoClienteJFrame,
							"Error al agregar cliente: " + ex.getMessage());
				}
			}
		});

		inicioJFrame.add(nuevoClienteButton);
		inicioJFrame.add(seleccionarClienteButton);
		inicioJFrame.add(aceptarButton);

		// JFrame para agregar un nuevo cliente
		nuevoClienteJFrame = new JFrame("Nuevo Cliente");
		nuevoClienteJFrame.setSize(400, 300);
		nuevoClienteJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nuevoClienteJFrame.setLayout(new GridLayout(8, 2, 10, 10)); 

		nombreClienteTextField = new JTextField();
		apellidoClienteTextField = new JTextField();
		direccionClienteTextField = new JTextField();
		telefonoClienteTextField = new JTextField();
		emailClienteTextField = new JTextField();
		tratoPreferencialTextField = new JTextField();

		nombreLabel = new JLabel("Nombre:");
		apellidoLabel = new JLabel("Apellido:");
		direccionLabel = new JLabel("Dirección:");
		telefonoLabel = new JLabel("Teléfono:");
		emailLabel = new JLabel("Email:");
		tratoPreferencialLabel = new JLabel("Trato Preferencial:");

		cancelarButton = new JButton("Cancelar");
		aceptarButton = new JButton("Aceptar");

		cancelarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nuevoClienteJFrame.setVisible(false);
				inicioJFrame.setVisible(true);
			}
		});

		nuevoClienteJFrame.add(nombreLabel);
		nuevoClienteJFrame.add(nombreClienteTextField);
		nuevoClienteJFrame.add(apellidoLabel);
		nuevoClienteJFrame.add(apellidoClienteTextField);
		nuevoClienteJFrame.add(direccionLabel);
		nuevoClienteJFrame.add(direccionClienteTextField);
		nuevoClienteJFrame.add(telefonoLabel);
		nuevoClienteJFrame.add(telefonoClienteTextField);
		nuevoClienteJFrame.add(emailLabel);
		nuevoClienteJFrame.add(emailClienteTextField);
		nuevoClienteJFrame.add(tratoPreferencialLabel);
		nuevoClienteJFrame.add(tratoPreferencialTextField);
		nuevoClienteJFrame.add(cancelarButton);
		nuevoClienteJFrame.add(aceptarButton);

		// JFrame para seleccionar un cliente existente
		seleccionarClienteJFrame = new JFrame("Seleccionar Cliente");
		seleccionarClienteJFrame.setSize(400, 200);
		seleccionarClienteJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		seleccionarClienteJFrame.setLayout(new GridLayout(5, 2, 10, 10)); 

		clientesComboBox = new JComboBox<>();
		nombreLabel = new JLabel("Nombre:");
		idLabel = new JLabel("ID:");
		tratoPreferencialLabel = new JLabel("Trato Preferencial:");

		cancelarButton = new JButton("Cancelar");
		volverButton = new JButton("Volver");
		aceptarButton = new JButton("Aceptar");

		cancelarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarClienteJFrame.setVisible(false);
				inicioJFrame.setVisible(true);
			}
		});

		volverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarClienteJFrame.setVisible(false);
				inicioJFrame.setVisible(true);
			}
		});

		aceptarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarClienteJFrame.setVisible(false);
				llenarEmpresasComboBox();
				seleccionarEmpresaJFrame.setVisible(true);
			}
		});

		seleccionarClienteJFrame.add(new JLabel("Cliente:"));
		seleccionarClienteJFrame.add(clientesComboBox);
		seleccionarClienteJFrame.add(nombreLabel);
		seleccionarClienteJFrame.add(new JLabel("")); // Espacio en blanco
		seleccionarClienteJFrame.add(idLabel);
		seleccionarClienteJFrame.add(new JLabel("")); // Espacio en blanco
		seleccionarClienteJFrame.add(tratoPreferencialLabel);
		seleccionarClienteJFrame.add(new JLabel("")); // Espacio en blanco
		seleccionarClienteJFrame.add(cancelarButton);
		seleccionarClienteJFrame.add(volverButton);
		seleccionarClienteJFrame.add(aceptarButton);

		// JFrame para seleccionar una empresa
		seleccionarEmpresaJFrame = new JFrame("Seleccionar Empresa");
		seleccionarEmpresaJFrame.setSize(400, 200);
		seleccionarEmpresaJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		seleccionarEmpresaJFrame.setLayout(new GridLayout(4, 2, 10, 10));

		empresasComboBox = new JComboBox<>();
		nombreEmpresaLabel = new JLabel("Nombre:");
		idEmpresaLabel = new JLabel("ID:");

		cancelarButton = new JButton("Cancelar");
		volverButton = new JButton("Volver");
		aceptarButton = new JButton("Aceptar");

		cancelarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarEmpresaJFrame.setVisible(false);
				seleccionarClienteJFrame.setVisible(true);
			}
		});

		volverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarEmpresaJFrame.setVisible(false);
				seleccionarClienteJFrame.setVisible(true);
			}
		});

		aceptarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarEmpresaJFrame.setVisible(false);
				agregarEventoJFrame.setVisible(true);
			}
		});

		seleccionarEmpresaJFrame.add(new JLabel("Empresa:"));
		seleccionarEmpresaJFrame.add(empresasComboBox);
		seleccionarEmpresaJFrame.add(nombreEmpresaLabel);
		seleccionarEmpresaJFrame.add(new JLabel("")); // Espacio en blanco
		seleccionarEmpresaJFrame.add(idEmpresaLabel);
		seleccionarEmpresaJFrame.add(new JLabel("")); // Espacio en blanco
		seleccionarEmpresaJFrame.add(cancelarButton);
		seleccionarEmpresaJFrame.add(volverButton);
		seleccionarEmpresaJFrame.add(aceptarButton);

		// JFrame para agregar un evento
        agregarEventoJFrame = new JFrame("Agregar Evento");
        agregarEventoJFrame.setSize(400, 300);
        agregarEventoJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        agregarEventoJFrame.setLayout(new GridLayout(8, 2, 10, 10)); // Agregar espacio entre filas y columnas

        nombreEventoTextField = new JTextField();
        tipoEventoTextField = new JTextField();
        fechaInicioTextField = new JTextField();
        fechaFinTextField = new JTextField();
        ubicacionTextField = new JTextField();
        temasDecoracionTextField = new JTextField();

        nombreEventoLabel = new JLabel("Nombre:");
        tipoEventoLabel = new JLabel("Tipo:");
        fechaInicioLabel = new JLabel("Fecha Inicio:");
        fechaFinLabel = new JLabel("Fecha Fin:");
        ubicacionLabel = new JLabel("Ubicación:");
        temasDecoracionLabel = new JLabel("Temas Decoración:");

        cancelarButton = new JButton("Cancelar");
        aceptarButton = new JButton("Aceptar");

		cancelarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarEventoJFrame.setVisible(false);
				seleccionarEmpresaJFrame.setVisible(true);
			}
		});

		aceptarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarEventoJFrame.setVisible(false);
				llenarServiciosComboBox();
				llenarProveedoresComboBox();
				agregarServicioJFrame.setVisible(true);
			}
		});

		agregarEventoJFrame.add(nombreEventoLabel);
		agregarEventoJFrame.add(nombreEventoTextField);
		agregarEventoJFrame.add(tipoEventoLabel);
		agregarEventoJFrame.add(tipoEventoTextField);
		agregarEventoJFrame.add(fechaInicioLabel);
		agregarEventoJFrame.add(fechaInicioTextField);
		agregarEventoJFrame.add(fechaFinLabel);
		agregarEventoJFrame.add(fechaFinTextField);
		agregarEventoJFrame.add(ubicacionLabel);
		agregarEventoJFrame.add(ubicacionTextField);
		agregarEventoJFrame.add(temasDecoracionLabel);
		agregarEventoJFrame.add(temasDecoracionTextField);
		agregarEventoJFrame.add(cancelarButton);
		agregarEventoJFrame.add(aceptarButton);

		// JFrame para agregar servicios al evento
        agregarServicioJFrame = new JFrame("Agregar Servicios");
        agregarServicioJFrame.setSize(400, 300);
        agregarServicioJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        agregarServicioJFrame.setLayout(new GridLayout(7, 2, 10, 10)); // Agregar espacio entre filas y columnas

        serviciosComboBox = new JComboBox<>();
        proveedoresComboBox = new JComboBox<>();
        cantidadServicioTextField = new JTextField();
        servicioLabel = new JLabel("Servicio:");
        proveedorLabel = new JLabel("Proveedor:");
        cantidadServicioLabel = new JLabel("Cantidad:");

        cancelarButton = new JButton("Cancelar");
        aceptarButton = new JButton("Aceptar");
        volverButton = new JButton("Volver");

		cancelarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarServicioJFrame.setVisible(false);
				agregarEventoJFrame.setVisible(true);
			}
		});

		volverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarServicioJFrame.setVisible(false);
				agregarEventoJFrame.setVisible(true);
			}
		});

		aceptarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtener datos del servicio seleccionado
				String servicioSeleccionado = (String) serviciosComboBox
						.getSelectedItem();
				String proveedorSeleccionado = (String) proveedoresComboBox
						.getSelectedItem();
				int cantidad = Integer.parseInt(cantidadServicioTextField
						.getText());

				// Agregar servicio al evento
				try {
					// Insertar servicio en la base de datos
					String query = "INSERT INTO EventoServicio (idEvento, idServicio, cantidad) VALUES (?, ?, ?)";
					PreparedStatement stmt = conexion.prepareStatement(query);
					stmt.setInt(1, obtenerIdEvento()); // Obtener ID del evento
														// actual
					stmt.setInt(2, obtenerIdServicio(servicioSeleccionado)); // Obtener
																				// ID
																				// del
																				// servicio
					stmt.setInt(3, cantidad);
					stmt.executeUpdate();
					stmt.close();

					// Actualizar tabla de servicios
					actualizarTablaServicios();

					// Limpiar campos de texto
					cantidadServicioTextField.setText("");

					// Mostrar mensaje de éxito
					JOptionPane.showMessageDialog(agregarServicioJFrame,
							"Servicio agregado correctamente.");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(agregarServicioJFrame,
							"Error al agregar servicio: " + ex.getMessage());
				}
			}
		});

		agregarServicioJFrame.add(servicioLabel);
		agregarServicioJFrame.add(serviciosComboBox);
		agregarServicioJFrame.add(proveedorLabel);
		agregarServicioJFrame.add(proveedoresComboBox);
		agregarServicioJFrame.add(cantidadServicioLabel);
		agregarServicioJFrame.add(cantidadServicioTextField);
		agregarServicioJFrame.add(cancelarButton);
		agregarServicioJFrame.add(volverButton);
		agregarServicioJFrame.add(aceptarButton);

		// Crear tabla para mostrar los servicios añadidos
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Servicio");
        modeloTabla.addColumn("Proveedor");
        modeloTabla.addColumn("Cantidad");
        serviciosTabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(serviciosTabla);
        agregarServicioJFrame.add(scrollPane); // Agrega la tabla al JFrame

        agregarServicioJFrame.add(cancelarButton);
        agregarServicioJFrame.add(volverButton);
        agregarServicioJFrame.add(aceptarButton);

        // JFrame para finalizar el contrato
        finalizarContratoJFrame = new JFrame("Finalizar Contrato");
        finalizarContratoJFrame.setSize(400, 250);
        finalizarContratoJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finalizarContratoJFrame.setLayout(new GridLayout(7, 2, 10, 10)); // Agregar espacio entre filas y columnas

        JLabel empresaLabel = new JLabel("Empresa:");
        JLabel fechaContratoLabel = new JLabel("Fecha Contrato:");
        JLabel preciosNegociadosLabel = new JLabel("Precios Negociados:");
        JLabel nombreEventoLabel = new JLabel("Nombre del Evento:");
        JLabel idClienteLabel = new JLabel("ID Cliente:");
        JLabel tasaDescuentoLabel = new JLabel("Tasa de Descuento:");
        final JLabel mensajeLabel = new JLabel("");

		// Obtener detalles del contrato desde la base de datos
		// ... (código para obtener los detalles del contrato)
		// ...

		cancelarButton = new JButton("Cancelar");
		aceptarButton = new JButton("Aceptar");

		cancelarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finalizarContratoJFrame.setVisible(false);
				agregarServicioJFrame.setVisible(true);
			}
		});

		aceptarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Insertar el contrato en la base de datos
				try {
					// Obtener datos del contrato
					// ... (código para obtener los datos del contrato)
					// ...

					// Insertar contrato en la base de datos
					String query = "INSERT INTO Contrato (idEmpresa, fechaContrato, preciosNegociados, nombreEvento, id, tasaDescuento) VALUES (?, ?, ?, ?, ?, ?)";
					PreparedStatement stmt = conexion.prepareStatement(query);
					stmt.setInt(1, obtenerIdEmpresa());
					stmt.setDate(2, obtenerFechaActual());
					stmt.setDouble(3, obtenerPreciosNegociados());
					stmt.setString(4, obtenerNombreEvento());
					stmt.setInt(5, obtenerIdCliente());
					stmt.setDouble(6, obtenerTasaDescuento());
					stmt.executeUpdate();
					stmt.close();

					// Mostrar mensaje de éxito
					mensajeLabel.setText("Contrato generado correctamente.");
					// Generar informe del contrato
					generarInformeContrato();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(finalizarContratoJFrame,
							"Error al generar el contrato: " + ex.getMessage());
				}
			}
		});

		finalizarContratoJFrame.add(empresaLabel);
		finalizarContratoJFrame.add(new JLabel(obtenerNombreEmpresa()));
		finalizarContratoJFrame.add(fechaContratoLabel);
		finalizarContratoJFrame.add(new JLabel(obtenerFechaActualString()));
		finalizarContratoJFrame.add(preciosNegociadosLabel);
		finalizarContratoJFrame.add(new JLabel(String
				.valueOf(obtenerPreciosNegociados())));
		finalizarContratoJFrame.add(nombreEventoLabel);
		finalizarContratoJFrame.add(new JLabel(obtenerNombreEvento()));
		finalizarContratoJFrame.add(idClienteLabel);
		finalizarContratoJFrame.add(new JLabel(String
				.valueOf(obtenerIdCliente())));
		finalizarContratoJFrame.add(tasaDescuentoLabel);
		finalizarContratoJFrame.add(new JLabel(String
				.valueOf(obtenerTasaDescuento())));
		finalizarContratoJFrame.add(mensajeLabel);
		finalizarContratoJFrame.add(cancelarButton);
		finalizarContratoJFrame.add(aceptarButton);
	}

	// Llena el ComboBox de clientes con los nombres de los clientes de la base
	// de datos
	private void llenarClientesComboBox() {
		try {
			String query = "SELECT nombrecliente FROM Cliente";
			PreparedStatement stmt = conexion.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				clientesComboBox.addItem(rs.getString("nombrecliente"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(seleccionarClienteJFrame,
					"Error al cargar clientes: " + e.getMessage());
		}
	}

	// Llena el ComboBox de empresas con los nombres de las empresas de la base
	// de datos
	private void llenarEmpresasComboBox() {
		try {
			String query = "SELECT nombreEmpresa FROM Empresa";
			PreparedStatement stmt = conexion.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				empresasComboBox.addItem(rs.getString("nombreEmpresa"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(seleccionarEmpresaJFrame,
					"Error al cargar empresas: " + e.getMessage());
		}
	}

	// Llena el ComboBox de servicios con los nombres de los servicios de la
	// base de datos
	private void llenarServiciosComboBox() {
		try {
			String query = "SELECT nombreServicio FROM Servicio";
			PreparedStatement stmt = conexion.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				serviciosComboBox.addItem(rs.getString("nombreServicio"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(agregarServicioJFrame,
					"Error al cargar servicios: " + e.getMessage());
		}
	}

	// Llena el ComboBox de proveedores con los nombres de los proveedores de la
	// base de datos
	private void llenarProveedoresComboBox() {
		try {
			String query = "SELECT nombreProveedor FROM Proveedor";
			PreparedStatement stmt = conexion.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				proveedoresComboBox.addItem(rs.getString("nombreProveedor"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(agregarServicioJFrame,
					"Error al cargar proveedores: " + e.getMessage());
		}
	}

	// Obtiene el ID del evento actual
	private int obtenerIdEvento() {
		// Consulta para obtener el ID del último evento insertado
		int idEvento = 0;
		try {
			String query = "SELECT LAST_INSERT_ROWID() AS idEvento";
			PreparedStatement stmt = conexion.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				idEvento = rs.getInt("idEvento");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(agregarServicioJFrame,
					"Error al obtener ID del evento: " + e.getMessage());
		}
		return idEvento;
	}

	// Obtiene el ID del servicio seleccionado
	private int obtenerIdServicio(String servicioSeleccionado) {
		int idServicio = 0;
		try {
			String query = "SELECT idServicio FROM Servicio WHERE nombreServicio = ?";
			PreparedStatement stmt = conexion.prepareStatement(query);
			stmt.setString(1, servicioSeleccionado);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				idServicio = rs.getInt("idServicio");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(agregarServicioJFrame,
					"Error al obtener ID del servicio: " + e.getMessage());
		}
		return idServicio;
	}

	// Obtiene el ID del cliente actual
	private int obtenerIdCliente() {
		String nombreCliente = (String) clientesComboBox.getSelectedItem(); // Obtener
																			// nombre
																			// del
																			// cliente
		int idCliente = 0;
		try {
			String query = "SELECT id FROM Cliente WHERE nombreCliente = ?";
			PreparedStatement stmt = conexion.prepareStatement(query);
			stmt.setString(1, nombreCliente);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				idCliente = rs.getInt("id");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(seleccionarClienteJFrame,
					"Error al obtener ID del cliente: " + e.getMessage());
		}
		return idCliente;
	}

	// Obtiene el ID de la empresa actual
	private int obtenerIdEmpresa() {
		String nombreEmpresa = (String) empresasComboBox.getSelectedItem(); // Obtener
																			// nombre
																			// de
																			// la
																			// empresa
		int idEmpresa = 0;
		try {
			String query = "SELECT idEmpresa FROM Empresa WHERE nombreEmpresa = ?";
			PreparedStatement stmt = conexion.prepareStatement(query);
			stmt.setString(1, nombreEmpresa);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				idEmpresa = rs.getInt("idEmpresa");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(seleccionarEmpresaJFrame,
					"Error al obtener ID de la empresa: " + e.getMessage());
		}
		return idEmpresa;
	}

	// Obtiene el nombre de la empresa actual
	private String obtenerNombreEmpresa() {
		String nombreEmpresa = (String) empresasComboBox.getSelectedItem(); // Obtener
																			// nombre
																			// de
																			// la
																			// empresa
		return nombreEmpresa;
	}

	// Obtiene el nombre del evento actual
	private String obtenerNombreEvento() {
		return nombreEventoTextField.getText();
	}

	// Obtiene la fecha actual como objeto Date
	private Date obtenerFechaActual() {
		Calendar calendario = Calendar.getInstance();
		java.sql.Date fechaSQL = new java.sql.Date(calendario.getTimeInMillis());
		return fechaSQL;
	}

	// Obtiene la fecha actual como String
	private String obtenerFechaActualString() {
		Calendar calendario = Calendar.getInstance();
		java.sql.Date fechaSQL = new java.sql.Date(calendario.getTimeInMillis());
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Define
																				// el
																				// formato
																				// deseado
		String fechaHoraFormateada = formato.format(fechaSQL);
		return fechaHoraFormateada;
	}

	// Obtiene los precios negociados
	private double obtenerPreciosNegociados() {
		double precioTotal = 0.0;
		List<ServicioEvento> servicios = obtenerServiciosEvento(); // Obtiene
																	// los
																	// servicios
																	// del
																	// evento

		for (ServicioEvento servicio : servicios) {
			try {
				String query = "SELECT precioUnitario FROM Servicio WHERE nombreServicio = ?";
				PreparedStatement stmt = conexion.prepareStatement(query);
				stmt.setString(1, servicio.getNombreServicio());
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					double precioUnitario = rs.getDouble("precioUnitario");
					precioTotal += precioUnitario * servicio.getCantidad(); // Calcula
																			// el
																			// precio
																			// total
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
						finalizarContratoJFrame,
						"Error al calcular precios negociados: "
								+ e.getMessage());
			}
		}

		return precioTotal;
	}

	// Obtiene la tasa de descuento
	private double obtenerTasaDescuento() {
		int idCliente = obtenerIdCliente(); // Obtiene el ID del cliente actual
		double tasaDescuento = 0.0;
		try {
			String query = "SELECT tasaDescuento FROM Contrato WHERE id = ?";
			PreparedStatement stmt = conexion.prepareStatement(query);
			stmt.setInt(1, idCliente);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				tasaDescuento = rs.getDouble("tasaDescuento");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(finalizarContratoJFrame,
					"Error al obtener tasa de descuento: " + e.getMessage());
		}
		return tasaDescuento;
	}

	// Actualiza la tabla de servicios con los servicios añadidos
	private void actualizarTablaServicios() {
		DefaultTableModel modeloTabla = (DefaultTableModel) serviciosTabla
				.getModel();
		modeloTabla.setRowCount(0); // Limpia la tabla

		// Obtener los servicios añadidos de la base de datos
		List<ServicioEvento> servicios = obtenerServiciosEvento();

		// Agregar los servicios a la tabla
		for (ServicioEvento servicio : servicios) {
			modeloTabla.addRow(new Object[] { servicio.getNombreServicio(),
					servicio.getProveedor(), servicio.getCantidad() });
		}
	}

	// Obtiene la lista de servicios añadidos para el evento
	private List<ServicioEvento> obtenerServiciosEvento() {
		List<ServicioEvento> servicios = new ArrayList<>();
		try {
			String query = "SELECT s.nombre AS nombreServicio, p.nombreProveedor AS proveedor, es.cantidad FROM Servicio s"
					+ " JOIN EventoServicio es ON s.idServicio = es.idServicio"
					+ " JOIN Proveedor p ON es.idProveedor = p.idProveedor"
					+ " WHERE es.idEvento = ?";
			PreparedStatement stmt = conexion.prepareStatement(query);
			stmt.setInt(1, obtenerIdEvento());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ServicioEvento servicio = new ServicioEvento(
						rs.getString("nombreServicio"),
						rs.getString("proveedor"), rs.getInt("cantidad"));
				servicios.add(servicio);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(agregarServicioJFrame,
					"Error al obtener servicios del evento: " + e.getMessage());
		}
		return servicios;
	}

	// Genera el informe del contrato
	private void generarInformeContrato() {
		JFrame informeJFrame = new JFrame("Informe de Contrato");
		informeJFrame.setSize(400, 300);
		informeJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		informeJFrame.setLayout(new GridLayout(7, 2));

		// Crear etiquetas para mostrar los detalles del contrato
		JLabel empresaLabel = new JLabel("Empresa:");
		JLabel fechaContratoLabel = new JLabel("Fecha Contrato:");
		JLabel preciosNegociadosLabel = new JLabel("Precios Negociados:");
		JLabel nombreEventoLabel = new JLabel("Nombre del Evento:");
		JLabel idClienteLabel = new JLabel("ID Cliente:");
		JLabel tasaDescuentoLabel = new JLabel("Tasa de Descuento:");
		JLabel serviciosLabel = new JLabel("Servicios:");

		// Crear una tabla para mostrar los servicios del contrato
		DefaultTableModel modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("Servicio");
		modeloTabla.addColumn("Cantidad");
		JTable serviciosTabla = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(serviciosTabla);

		// Agregar los detalles del contrato al JFrame
		informeJFrame.add(empresaLabel);
		informeJFrame.add(new JLabel(obtenerNombreEmpresa()));
		informeJFrame.add(fechaContratoLabel);
		informeJFrame.add(new JLabel(obtenerFechaActualString()));
		informeJFrame.add(preciosNegociadosLabel);
		informeJFrame
				.add(new JLabel(String.valueOf(obtenerPreciosNegociados())));
		informeJFrame.add(nombreEventoLabel);
		informeJFrame.add(new JLabel(obtenerNombreEvento()));
		informeJFrame.add(idClienteLabel);
		informeJFrame.add(new JLabel(String.valueOf(obtenerIdCliente())));
		informeJFrame.add(tasaDescuentoLabel);
		informeJFrame.add(new JLabel(String.valueOf(obtenerTasaDescuento())));
		informeJFrame.add(serviciosLabel);
		informeJFrame.add(scrollPane);

		// Agregar los servicios del contrato a la tabla
		List<ServicioEvento> servicios = obtenerServiciosEvento();
		for (ServicioEvento servicio : servicios) {
			modeloTabla.addRow(new Object[] { servicio.getNombreServicio(),
					servicio.getCantidad() });
		}

		// Mostrar el JFrame del informe
		informeJFrame.setVisible(true);
	}

	// Clase para representar un servicio del evento
	private class ServicioEvento {
		private String nombreServicio;
		private String proveedor;
		private int cantidad;

		public ServicioEvento(String nombreServicio, String proveedor,
				int cantidad) {
			this.nombreServicio = nombreServicio;
			this.proveedor = proveedor;
			this.cantidad = cantidad;
		}

		public String getNombreServicio() {
			return nombreServicio;
		}

		public String getProveedor() {
			return proveedor;
		}

		public int getCantidad() {
			return cantidad;
		}
	}

	public static void main(String[] args) {
		// Establecer la conexión a la base de datos
		final Conexion conexion = new Conexion();

		// Crear el JFrame principal
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new InsertarEventoJFrame(conexion.conectar());
			}
		});
	}
}