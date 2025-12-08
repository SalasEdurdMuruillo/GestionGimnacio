package GimnacioCliente;

import GimnacioInfraestructura.RepositorioGenerico;
import java.util.List;
/**
 *
 * @author Luisf
 */

public interface RepositorioClientes extends RepositorioGenerico<Cliente, String> {
    List<Cliente> buscarPorFiltro(String filtro);
}

