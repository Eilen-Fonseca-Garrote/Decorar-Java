package visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FrameMenuPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnArchivo;
	private JMenu mnSeguridad;
	private JMenu mnReportes;
	private JMenu mnAdministracin;
	private JMenuItem mntmCambiarContrasea;
	private JMenuItem mntmUsuarios;
	private String usuarioLogueado;
	private int nivelAccesoLogueado;
	private JMenuItem mntmCerrarSesion;
	private JMenuItem mntmSalir;
	private JMenuItem mntmFichaEmpresas;
	private JMenuItem mntmFichaDeUn;
	private JMenuItem mntmFichaDeUn_1;
	private JMenuItem mntmReporteDeEventos;
	private JMenuItem mntmReporteDeServicios;
	private JMenuItem mntmReporteDeEmpleados;
	private JMenuItem mntmReporteDeContratos;
	private JMenuItem mntmReporteDeEstado;
	private JMenuItem mntmReporteConsolidadoDe;
	private JMenuItem mntmReporteDeModificaciones;
	private JMenuItem mntmGeneral;
	private JMenuItem mntmClientes;
	private JMenuItem mntmContratos;
	private JMenuItem mntmEmpleados;
	private JMenuItem mntmEmpresas;
	private JMenuItem mntmEventos;
	private JMenuItem mntmServiciosDeEventos;
	private JMenuItem mntmProveedor;
	private JMenuItem mntmServicio;

	public FrameMenuPrincipal(String usuarioLogueado,int nivelAccesoLogueado) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setJMenuBar(getMenuBar_2());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.usuarioLogueado = usuarioLogueado;
		this.nivelAccesoLogueado = nivelAccesoLogueado;
		setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana
		if(nivelAccesoLogueado <2){
			mntmUsuarios.setEnabled(false);
			mntmGeneral.setEnabled(false);
			mntmClientes.setEnabled(false);
			mntmContratos.setEnabled(false);
			mntmEmpleados.setEnabled(false);
			mntmEmpresas.setEnabled(false);
			mntmEventos.setEnabled(false);
			mntmServiciosDeEventos.setEnabled(false);
			mntmProveedor.setEnabled(false);
			mntmServicio.setEnabled(false);
		}
	}
	private JMenuBar getMenuBar_2() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnArchivo());
			menuBar.add(getMnReportes());
			menuBar.add(getMnAdministracin());
			menuBar.add(getMnSeguridad());
		}
		return menuBar;
	}
	private JMenu getMnArchivo() {
		if (mnArchivo == null) {
			mnArchivo = new JMenu("Archivo");
			mnArchivo.add(getMntmCerrarSesion());
			mnArchivo.add(getMntmSalir());
		}
		return mnArchivo;
	}
	private JMenu getMnSeguridad() {
		if (mnSeguridad == null) {
			mnSeguridad = new JMenu("Seguridad");
			mnSeguridad.add(getMntmCambiarContrasea());
		}
		return mnSeguridad;
	}
	private JMenu getMnReportes() {
		if (mnReportes == null) {
			mnReportes = new JMenu("Reportes");
			mnReportes.add(getMntmFichaEmpresas());
			mnReportes.add(getMntmFichaDeUn());
			mnReportes.add(getMntmFichaDeUn_1());
			mnReportes.add(getMntmReporteDeEventos());
			mnReportes.add(getMntmReporteDeServicios());
			mnReportes.add(getMntmReporteDeEmpleados());
			mnReportes.add(getMntmReporteDeContratos());
			mnReportes.add(getMntmReporteDeEstado());
			mnReportes.add(getMntmReporteConsolidadoDe());
			mnReportes.add(getMntmReporteDeModificaciones());
		}
		return mnReportes;
	}
	private JMenu getMnAdministracin() {
		if (mnAdministracin == null) {
			mnAdministracin = new JMenu("Administraci\u00F3n");
			mnAdministracin.add(getMntmUsuarios());
			mnAdministracin.add(getMntmGeneral());
			mnAdministracin.add(getMntmClientes());
			mnAdministracin.add(getMntmContratos());
			mnAdministracin.add(getMntmEmpleados());
			mnAdministracin.add(getMntmEmpresas());
			mnAdministracin.add(getMntmEventos());
			mnAdministracin.add(getMntmProveedor());
			mnAdministracin.add(getMntmServiciosDeEventos());
			mnAdministracin.add(getMntmServicio());
		}
		return mnAdministracin;
	}
	private JMenuItem getMntmCambiarContrasea() {
		if (mntmCambiarContrasea == null) {
			mntmCambiarContrasea = new JMenuItem("Cambiar Contrase\u00F1a");
			mntmCambiarContrasea.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					FrameCambiarContraseña f = new FrameCambiarContraseña(usuarioLogueado);
					setEnabled(false);
					f.setVisible(true);
					f.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            setEnabled(true);
                            setVisible(true);
                        }
                    });
				}
			});
		}
		return mntmCambiarContrasea;
	}
	private JMenuItem getMntmUsuarios() {
		if (mntmUsuarios == null) {
			mntmUsuarios = new JMenuItem("Usuarios");
			mntmUsuarios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (nivelAccesoLogueado == 3) {
	                    UsuarioJFrame f = new UsuarioJFrame();
	                    f.setVisible(true);
	                } else {
	                    JOptionPane.showMessageDialog(null, "No tienes permiso para acceder a esta sección.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
	                }
				}
			});
			mntmUsuarios.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return mntmUsuarios;
	}
	private JMenuItem getMntmCerrarSesion() {
		if (mntmCerrarSesion == null) {
			mntmCerrarSesion = new JMenuItem("Cerrar Sesion");
			mntmCerrarSesion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					FrameLogin f = new FrameLogin();
					f.setVisible(true);
					dispose();
				}
			});
		}
		return mntmCerrarSesion;
	}
	private JMenuItem getMntmSalir() {
		if (mntmSalir == null) {
			mntmSalir = new JMenuItem("Salir");
			mntmSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}
		return mntmSalir;
	}
	private JMenuItem getMntmFichaEmpresas() {
		if (mntmFichaEmpresas == null) {
			mntmFichaEmpresas = new JMenuItem("Ficha de las Empresas");
			mntmFichaEmpresas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return mntmFichaEmpresas;
	}
	private JMenuItem getMntmFichaDeUn() {
		if (mntmFichaDeUn == null) {
			mntmFichaDeUn = new JMenuItem("Ficha de un Cliente Determinado");
			mntmFichaDeUn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return mntmFichaDeUn;
	}
	private JMenuItem getMntmFichaDeUn_1() {
		if (mntmFichaDeUn_1 == null) {
			mntmFichaDeUn_1 = new JMenuItem("Ficha de un Proveedor Determinado");
			mntmFichaDeUn_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return mntmFichaDeUn_1;
	}
	private JMenuItem getMntmReporteDeEventos() {
		if (mntmReporteDeEventos == null) {
			mntmReporteDeEventos = new JMenuItem("Reporte de Eventos Realizados en un Per\u00EDodo de Tiempo");
			mntmReporteDeEventos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return mntmReporteDeEventos;
	}
	private JMenuItem getMntmReporteDeServicios() {
		if (mntmReporteDeServicios == null) {
			mntmReporteDeServicios = new JMenuItem("Reporte de Servicios y Productos Utilizados en un Evento Espec\u00EDfico");
			mntmReporteDeServicios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return mntmReporteDeServicios;
	}
	private JMenuItem getMntmReporteDeEmpleados() {
		if (mntmReporteDeEmpleados == null) {
			mntmReporteDeEmpleados = new JMenuItem("Reporte de Empleados Asignados a un Evento");
			mntmReporteDeEmpleados.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return mntmReporteDeEmpleados;
	}
	private JMenuItem getMntmReporteDeContratos() {
		if (mntmReporteDeContratos == null) {
			mntmReporteDeContratos = new JMenuItem("Reporte de Contratos por Cliente en un Per\u00EDodo de Tiempo");
			mntmReporteDeContratos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return mntmReporteDeContratos;
	}
	private JMenuItem getMntmReporteDeEstado() {
		if (mntmReporteDeEstado == null) {
			mntmReporteDeEstado = new JMenuItem("Reporte de Estado de los Proveedores");
			mntmReporteDeEstado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return mntmReporteDeEstado;
	}
	private JMenuItem getMntmReporteConsolidadoDe() {
		if (mntmReporteConsolidadoDe == null) {
			mntmReporteConsolidadoDe = new JMenuItem("Reporte Consolidado de los Servicios y Productos en un A\u00F1o Determinado");
			mntmReporteConsolidadoDe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return mntmReporteConsolidadoDe;
	}
	private JMenuItem getMntmReporteDeModificaciones() {
		if (mntmReporteDeModificaciones == null) {
			mntmReporteDeModificaciones = new JMenuItem("Reporte de Modificaciones en Contratos en un Per\u00EDodo de Tiempo");
			mntmReporteDeModificaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		}
		return mntmReporteDeModificaciones;
	}
	private JMenuItem getMntmGeneral() {
		if (mntmGeneral == null) {
			mntmGeneral = new JMenuItem("General");
			mntmGeneral.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (nivelAccesoLogueado >= 2) {
	                    Visual f = new Visual("Cliente");
	                    f.setVisible(true);
	                } else {
	                    JOptionPane.showMessageDialog(null, "No tienes permiso para acceder a esta sección.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
	                }
				}
			});
		}
		return mntmGeneral;
	}
	private JMenuItem getMntmClientes() {
		if (mntmClientes == null) {
			mntmClientes = new JMenuItem("Clientes");
			mntmClientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MultiVisual m = new MultiVisual("Cliente");
					m.setVisible(true);
				}
			});
		}
		return mntmClientes;
	}
	private JMenuItem getMntmContratos() {
		if (mntmContratos == null) {
			mntmContratos = new JMenuItem("Contratos");
			mntmContratos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MultiVisual m = new MultiVisual("Contrato");
					m.setVisible(true);
				}
			});
		}
		return mntmContratos;
	}
	private JMenuItem getMntmEmpleados() {
		if (mntmEmpleados == null) {
			mntmEmpleados = new JMenuItem("Empleados");
			mntmEmpleados.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MultiVisual m = new MultiVisual("Empleado");
					m.setVisible(true);
				}
			});
		}
		return mntmEmpleados;
	}
	private JMenuItem getMntmEmpresas() {
		if (mntmEmpresas == null) {
			mntmEmpresas = new JMenuItem("Empresas");
			mntmEmpresas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MultiVisual m = new MultiVisual("Empresa");
					m.setVisible(true);
				}
			});
		}
		return mntmEmpresas;
	}
	private JMenuItem getMntmEventos() {
		if (mntmEventos == null) {
			mntmEventos = new JMenuItem("Eventos");
			mntmEventos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MultiVisual m = new MultiVisual("Evento");
					m.setVisible(true);
				}
			});
		}
		return mntmEventos;
	}
	private JMenuItem getMntmServiciosDeEventos() {
		if (mntmServiciosDeEventos == null) {
			mntmServiciosDeEventos = new JMenuItem("Servicios de eventos");
			mntmServiciosDeEventos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MultiVisual m = new MultiVisual("EventoServicio");
					m.setVisible(true);
				}
			});
		}
		return mntmServiciosDeEventos;
	}
	private JMenuItem getMntmProveedor() {
		if (mntmProveedor == null) {
			mntmProveedor = new JMenuItem("Proveedores");
			mntmProveedor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MultiVisual m = new MultiVisual("Proveedor");
					m.setVisible(true);
				}
			});
		}
		return mntmProveedor;
	}
	private JMenuItem getMntmServicio() {
		if (mntmServicio == null) {
			mntmServicio = new JMenuItem("Servicios");
			mntmServicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MultiVisual m = new MultiVisual("Servicio");
					m.setVisible(true);
				}
			});
		}
		return mntmServicio;
	}
}
