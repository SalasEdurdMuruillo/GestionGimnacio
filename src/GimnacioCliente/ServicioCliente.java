package GimnacioCliente;

import java.util.List;
import java.util.Optional;
/**
 *
 * @author Luisf
 */

public class ServicioCliente {

    private final RepositorioClientes repositorioClientes;

    public ServicioCliente(RepositorioClientes repositorioClientes) {
        this.repositorioClientes = repositorioClientes;
    }

    public void guardar(Cliente cliente) {
        Optional<Cliente> existente = repositorioClientes.buscarPorId(cliente.getCedula());
        if (existente.isPresent()) {
            repositorioClientes.actualizar(cliente);
        } else {
            repositorioClientes.crear(cliente);
        }
    }

    public void eliminar(String cedula) {
        repositorioClientes.eliminar(cedula);
    }

    public List<Cliente> listarTodos() {
        return repositorioClientes.buscarTodos();
    }

    public List<Cliente> buscarPorFiltro(String filtro) {
        return repositorioClientes.buscarPorFiltro(filtro);
    }

    public java.util.Optional<Cliente> buscarPorCedula(String cedula) {
        return repositorioClientes.buscarPorId(cedula);
    }
}
