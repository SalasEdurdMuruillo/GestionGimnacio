package GimnacioClase;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author USER
 */
public class ServicioClase {

    private final RepositorioClases repositorioClases;

    public ServicioClase(RepositorioClases repositorioClases) {
        this.repositorioClases = repositorioClases;
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
}
