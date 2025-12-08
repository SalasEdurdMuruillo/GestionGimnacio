package GimnacioClase;

import java.util.List;
import java.util.Optional;
import GimnacioClienteClase.ClienteClase;
import GimnacioClienteClase.RepositorioClienteClase;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author USER
 */
public class ServicioClase {

    private final RepositorioClases repositorioClases;

    private final RepositorioClienteClase repositorioClienteClase;

    public ServicioClase(RepositorioClases repositorioClases, RepositorioClienteClase repositorioClienteClase) {
        this.repositorioClases = repositorioClases;
        this.repositorioClienteClase = repositorioClienteClase;
    }

    public void guardar(ClaseGimnasio clase) {
        Optional<ClaseGimnasio> ex = repositorioClases.buscarPorId(clase.getCodigo());
        if (ex.isPresent()) {
            repositorioClases.actualizar(clase);
        } else {
            repositorioClases.crear(clase);
        }
    }

    public void eliminar(String codigo) {
        repositorioClases.eliminar(codigo);
    }

    public List<ClaseGimnasio> listarTodas() {
        return repositorioClases.buscarTodos();
    }

    public List<ClaseGimnasio> buscarPorFiltro(String filtro) {
        return repositorioClases.buscarPorFiltro(filtro);
    }

    public long asignarClaseACliente(ClienteClase nuevaAsignacion) {
        
        if (repositorioClienteClase.buscarPorCedulaYClase(
                nuevaAsignacion.getCedulaCliente(),
                nuevaAsignacion.getCodigoClase()).isPresent()) {

            throw new RuntimeException("Error: El cliente ya tiene asignada esta clase.");
        }

        return repositorioClienteClase.crear(nuevaAsignacion);
    }

    public boolean verificarAsignacionExistente(String cedulaCliente, int codigoClase) {
        return repositorioClienteClase.buscarPorCedulaYClase(cedulaCliente, codigoClase).isPresent();
    }

    public List<ClaseGimnasio> listarClasesAsignadasPorCliente(String cedulaCliente) {
        List<ClienteClase> asignaciones = repositorioClienteClase.buscarPorCedulaCliente(cedulaCliente);
        List<ClaseGimnasio> clasesAsignadas = new ArrayList<>();

        for (ClienteClase ac : asignaciones) {

            Optional<ClaseGimnasio> clase = repositorioClases.buscarPorId(String.valueOf(ac.getCodigoClase()));
            if (clase.isPresent()) {
                clasesAsignadas.add(clase.get());
            }
        }
        return clasesAsignadas;
    }

    public Optional<ClienteClase> buscarAsignacionPorId(long id) {
        return repositorioClienteClase.buscarPorId(id);
    }
}
