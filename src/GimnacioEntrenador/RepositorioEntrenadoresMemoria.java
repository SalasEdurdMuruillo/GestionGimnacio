package GimnacioEntrenador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Eduard Salas Murillo
 */
public class RepositorioEntrenadoresMemoria implements RepositorioEntrenadores {

    private static final List<Entrenador> lista = new ArrayList<>();

    @Override
    public void crear(Entrenador entrenador) {
        lista.add(entrenador);
    }

    @Override
    public Optional<Entrenador> buscarPorId(String cedula) {
        return lista.stream().filter(e -> e.getCedula().equalsIgnoreCase(cedula)).findFirst();
    }

    @Override
    public List<Entrenador> buscarTodos() {
        return new ArrayList<>(lista);
    }

    @Override
    public void actualizar(Entrenador entrenador) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCedula().equalsIgnoreCase(entrenador.getCedula())) {
                lista.set(i, entrenador);
                return;
            }
        }
    }

    @Override
    public void eliminar(String cedula) {
        lista.removeIf(e -> e.getCedula().equalsIgnoreCase(cedula));
    }

    @Override
    public List<Entrenador> buscarPorFiltro(String filtro) {
        List<Entrenador> res = new ArrayList<>();
        String f = filtro.toLowerCase();
        for (Entrenador e : lista) {
            if (e.getCedula().toLowerCase().contains(f)
                    || e.getNombreCompleto().toLowerCase().contains(f)
                    || e.getEspecialidad().toLowerCase().contains(f)) {
                res.add(e);
            }
        }
        return res;
    }
}
