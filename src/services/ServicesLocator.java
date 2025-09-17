package services;

public class ServicesLocator {

    private static ServicesLocator instance;

    private ClienteServices clienteService;
    private ContratoServices contratoService;
    private EmpleadoServices empleadoService;
    private EmpresaServices empresaService;
    private EventoServices eventoService;
    private EventoServicioServices eventoServicioService;
    private HistorialEventoServicioServices historialEventoServicioService;
    private ProveedorServices proveedorService;
    private ServicioServices servicioService;
    private RolServices rolService;
    private UsuarioServices usuarioService;

    private ServicesLocator() {
        clienteService = new ClienteServices();
        contratoService = new ContratoServices();
        empleadoService = new EmpleadoServices();
        empresaService = new EmpresaServices();
        eventoService = new EventoServices();
        eventoServicioService = new EventoServicioServices();
        historialEventoServicioService = new HistorialEventoServicioServices();
        proveedorService = new ProveedorServices();
        servicioService = new ServicioServices();
        rolService = new RolServices();
        usuarioService = new UsuarioServices();
    }

    public static ServicesLocator getInstance() {
        if (instance == null) {
            instance = new ServicesLocator();
        }
        return instance;
    }

    public ClienteServices getClienteService() {
        return clienteService;
    }

    public ContratoServices getContratoService() {
        return contratoService;
    }

    public EmpleadoServices getEmpleadoService() {
        return empleadoService;
    }

    public EmpresaServices getEmpresaService() {
        return empresaService;
    }

    public EventoServices getEventoService() {
        return eventoService;
    }

    public EventoServicioServices getEventoServicioService() {
        return eventoServicioService;
    }

    public HistorialEventoServicioServices getHistorialEventoServicioService() {
        return historialEventoServicioService;
    }

    public ProveedorServices getProveedorService() {
        return proveedorService;
    }

    public ServicioServices getServicioService() {
        return servicioService;
    }
    
    public RolServices getRolService(){
    	return rolService;
    }
    
    public UsuarioServices getUsuarioService(){
    	return usuarioService;
    }
}

