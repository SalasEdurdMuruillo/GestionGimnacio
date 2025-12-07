package GimnacioCliente;

import GimnacioInfraestructura.RepositorioGenerico;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author Luisf
 */

public class RepositorioClientesMemoria implements RepositorioClientes {

    private static final List<Cliente> lista = new ArrayList<>();

    @Override
    public void crear(Cliente cliente) {
        lista.add(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(String cedula) {
        return lista.stream().filter(c -> c.getCedula().equalsIgnoreCase(cedula)).findFirst();
    }

    @Override
    public List<Cliente> buscarTodos() {
        return new ArrayList<>(lista);
    }

    @Override
    public void actualizar(Cliente cliente) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCedula().equalsIgnoreCase(cliente.getCedula())) {
                lista.set(i, cliente);
                return;
            }
        }
    }

    @Override
    public void eliminar(String cedula) {
        lista.removeIf(c -> c.getCedula().equalsIgnoreCase(cedula));
    }

    @Override
    public List<Cliente> buscarPorFiltro(String filtro) {
        List<Cliente> res = new ArrayList<>();
        String f = filtro.toLowerCase();
        for (Cliente c : lista) {
            if (c.getCedula().toLowerCase().contains(f)
                    || c.getNombreCompleto().toLowerCase().contains(f)
                    || (c.getFecha() != null && c.getFecha().toString().toLowerCase().contains(f.toLowerCase()))) {
                res.add(c);
            }
        }
        return res;
    }
}
