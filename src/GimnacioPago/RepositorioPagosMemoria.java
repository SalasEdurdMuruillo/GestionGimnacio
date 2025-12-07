package GimnacioPago;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Eduard Salas Murillo
 */
public class RepositorioPagosMemoria implements RepositorioPagos {

    private static final List<Pago> lista = new ArrayList<>();
    private static int siguienteId = 1;

    @Override
    public void crear(Pago pago) {
        pago.setId(siguienteId++);
        lista.add(pago);
    }

    @Override
    public Optional<Pago> buscarPorId(Integer id) {
        return lista.stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public List<Pago> buscarTodos() {
        return new ArrayList<>(lista);
    }

    @Override
    public void actualizar(Pago pago) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == pago.getId()) {
                lista.set(i, pago);
                return;
            }
        }
    }

    @Override
    public void eliminar(Integer id) {
        lista.removeIf(p -> p.getId() == id);
    }

    @Override
    public List<Pago> buscarPorFiltro(String filtro) {
        List<Pago> res = new ArrayList<>();
        String f = filtro.toLowerCase();
        for (Pago p : lista) {
            if (p.getCedulaCliente().toLowerCase().contains(f)) {
                res.add(p);
            }
        }
        return res;
    }
}
