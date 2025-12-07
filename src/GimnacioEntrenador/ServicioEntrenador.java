package GimnacioEntrenador;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Eduard Salas Murillo
 */
public class ServicioEntrenador {

    private final RepositorioEntrenadores repositorioEntrenadores;

    public ServicioEntrenador(RepositorioEntrenadores repositorioEntrenadores) {
        this.repositorioEntrenadores = repositorioEntrenadores;
    }

    public void guardar(Entrenador entrenador) {
        Optional<Entrenador> ex = repositorioEntrenadores.buscarPorId(entrenador.getCedula());
        if (ex.isPresent()) {
            repositorioEntrenadores.actualizar(entrenador);
        } else {
            repositorioEntrenadores.crear(entrenador);
        }
    }

    public void eliminar(String cedula) {
        repositorioEntrenadores.eliminar(cedula);
    }

    public List<Entrenador> listarTodos() {
        return repositorioEntrenadores.buscarTodos();
    }

    public List<Entrenador> buscarPorFiltro(String filtro) {
        return repositorioEntrenadores.buscarPorFiltro(filtro);
    }
}
