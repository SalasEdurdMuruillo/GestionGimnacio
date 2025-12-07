package GimnacioInfraestructura;

import GimnacioClase.RepositorioClases;
import GimnacioClase.ServicioClase;
import GimnacioCliente.RepositorioClientes;
import GimnacioCliente.ServicioCliente;
import GimnacioEntrenador.RepositorioEntrenadores;
import GimnacioEntrenador.ServicioEntrenador;
import GimnacioPago.RepositorioPagos;
import GimnacioPago.ServicioPago;
import GimnacioUsuarios.RepositorioUsuarios;
import GimnacioUsuarios.ServicioAutenticacion;

/**
 *
 * @author USER
 */
public class FachadaGimnasio {

    private final ServicioCliente servicioCliente;
    private final ServicioEntrenador servicioEntrenador;
    private final ServicioClase servicioClase;
    private final ServicioPago servicioPago;
    private final ServicioAutenticacion servicioAutenticacion;
    private final RepositorioUsuarios repoUsuarios;

    public FachadaGimnasio(FabricaRepositorios fabrica) {
        RepositorioClientes repoClientes = fabrica.crearRepositorioClientes();
        RepositorioEntrenadores repoEntrenadores = fabrica.crearRepositorioEntrenadores();
        RepositorioClases repoClases = fabrica.crearRepositorioClases();
        RepositorioPagos repoPagos = fabrica.crearRepositorioPagos();

        RepositorioUsuarios repoUsuariosInstance = fabrica.crearRepositorioUsuarios();

        this.servicioCliente = new ServicioCliente(repoClientes);
        this.servicioEntrenador = new ServicioEntrenador(repoEntrenadores);
        this.servicioClase = new ServicioClase(repoClases);
        this.servicioPago = new ServicioPago(repoPagos);
        this.servicioAutenticacion = new ServicioAutenticacion(repoUsuariosInstance);
        this.repoUsuarios = repoUsuariosInstance;
    }

    public ServicioCliente getServicioCliente() {
        return servicioCliente;
    }

    public ServicioEntrenador getServicioEntrenador() {
        return servicioEntrenador;
    }

    public ServicioClase getServicioClase() {
        return servicioClase;
    }

    public ServicioPago getServicioPago() {
        return servicioPago;
    }

    public ServicioAutenticacion getServicioAutenticacion() {
        return servicioAutenticacion;
    }

    public RepositorioUsuarios getRepositorioUsuarios() {
        return repoUsuarios;
    }

    public Object getRepositorioFactory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
