package GimnacioUsuarios;

import GimnacioInfraestructura.RepositorioGenerico;
import java.util.Optional;
/**
 *
 * @author Luisf
 */

public interface RepositorioUsuarios extends RepositorioGenerico<Usuario, Long> {

    Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario);
}
