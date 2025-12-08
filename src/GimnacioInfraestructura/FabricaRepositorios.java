package GimnacioInfraestructura;

import GimnacioClase.RepositorioClases;
import GimnacioClase.RepositorioClasesMemoria;
import GimnacioClase.RepositorioClasesMariaDB;
import GimnacioCliente.RepositorioClientes;
import GimnacioCliente.RepositorioClientesMemoria;
import GimnacioCliente.RepositorioClientesMariaDB;
import GimnacioClienteClase.RepositorioClienteClase;
import GimnacioClienteClase.RepositorioClienteClaseMariaDB;
import GimnacioClienteClase.RepositorioClienteClaseMemoria;
import GimnacioEntrenador.RepositorioEntrenadores;
import GimnacioEntrenador.RepositorioEntrenadoresMemoria;
import GimnacioEntrenador.RepositorioEntrenadoresMariaDB;
import GimnacioPago.RepositorioPagos;
import GimnacioPago.RepositorioPagosMemoria;
import GimnacioPago.RepositorioPagosMariaDB;
import GimnacioUsuarios.RepositorioUsuarios;
import GimnacioUsuarios.RepositorioUsuariosMemoria;
import GimnacioUsuarios.RepositorioUsuariosMariaDB;

/**
 *
 * @author USER
 */
public class FabricaRepositorios {

    private final TipoRepositorio tipo;

    public FabricaRepositorios(TipoRepositorio tipo) {
        this.tipo = tipo;
    }

    public RepositorioClientes crearRepositorioClientes() {
        return switch (tipo) {
            case MYSQL ->
                new RepositorioClientesMariaDB();
            case MEMORIA ->
                new RepositorioClientesMemoria();
        };
    }

    public RepositorioClases crearRepositorioClases() {
        return switch (tipo) {
            case MYSQL ->
                new RepositorioClasesMariaDB();
            case MEMORIA ->
                new RepositorioClasesMemoria();
        };
    }

    public RepositorioEntrenadores crearRepositorioEntrenadores() {
        return switch (tipo) {
            case MYSQL ->
                new RepositorioEntrenadoresMariaDB();
            case MEMORIA ->
                new RepositorioEntrenadoresMemoria();
        };
    }

    public RepositorioPagos crearRepositorioPagos() {
        return switch (tipo) {
            case MYSQL ->
                new RepositorioPagosMariaDB();
            case MEMORIA ->
                new RepositorioPagosMemoria();
        };
    }

    public RepositorioUsuarios crearRepositorioUsuarios() {
        return switch (tipo) {
            case MYSQL ->
                new RepositorioUsuariosMariaDB();
            case MEMORIA ->
                new RepositorioUsuariosMemoria();
        };
    }

    public RepositorioClienteClase crearRepositorioClienteClase() {
        return switch (tipo) {
            case MYSQL ->
                new RepositorioClienteClaseMariaDB();
            case MEMORIA ->
                new RepositorioClienteClaseMemoria();
            default ->
                throw new IllegalArgumentException("Tipo de repositorio no soportado para ClienteClase: " + tipo);
        };
    }

}
