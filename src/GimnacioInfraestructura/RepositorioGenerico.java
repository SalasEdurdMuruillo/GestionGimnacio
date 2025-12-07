package GimnacioInfraestructura;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author USER
 */
public interface RepositorioGenerico<T, ID> {

    void crear(T entidad);

    Optional<T> buscarPorId(ID id);

    List<T> buscarTodos();

    void actualizar(T entidad);

    void eliminar(ID id);

    List<T> buscarPorFiltro(String filtro);
}
