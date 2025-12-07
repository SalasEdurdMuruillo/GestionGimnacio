package GimnacioClase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author USER
 */
public class RepositorioClasesMemoria implements RepositorioClases {

    private static final List<ClaseGimnasio> lista = new ArrayList<>();

    @Override
    public void crear(ClaseGimnasio clase) {
        lista.add(clase);
    }

    @Override
    public Optional<ClaseGimnasio> buscarPorId(String codigo) {
        return lista.stream().filter(c -> c.getCodigo().equalsIgnoreCase(codigo)).findFirst();
    }

    @Override
    public List<ClaseGimnasio> buscarTodos() {
        return new ArrayList<>(lista);
    }

    @Override
    public void actualizar(ClaseGimnasio clase) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equalsIgnoreCase(clase.getCodigo())) {
                lista.set(i, clase);
                return;
            }
        }
    }

    @Override
    public void eliminar(String codigo) {
        lista.removeIf(c -> c.getCodigo().equalsIgnoreCase(codigo));
    }

    @Override
    public List<ClaseGimnasio> buscarPorFiltro(String filtro) {
        List<ClaseGimnasio> res = new ArrayList<>();
        String f = filtro.toLowerCase();
        for (ClaseGimnasio c : lista) {
            if (c.getCodigo().toLowerCase().contains(f)
                    || c.getNombre().toLowerCase().contains(f)
                    || c.getHorario().toLowerCase().contains(f)) {
                res.add(c);
            }
        }
        return res;
    }
}
