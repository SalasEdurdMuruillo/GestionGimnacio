package GimnacioPago;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduard Salas Murillo
 */
public class ServicioPago {

    private CalculoPago estrategia = new PagoBasico();
    private final List<PagoNotificador> observers = new ArrayList<>();

    private final RepositorioPagos repositorioPagos;

    public ServicioPago(RepositorioPagos repositorioPagos) {
        this.repositorioPagos = repositorioPagos;
    }

    public void agregarObserver(PagoNotificador observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void quitarObserver(PagoNotificador observer) {
        observers.remove(observer);
    }

    private void notificarPagoRegistrado(Pago pago) {
        for (PagoNotificador obs : observers) {
            obs.onPagoRegistrado(pago);
        }
    }

    public void setEstrategia(CalculoPago estrategia) {
        if (estrategia != null) {
            this.estrategia = estrategia;
        }
    }

    public void registrar(Pago pagoBase, double montoBase) {
        double calculado = estrategia.calcularMonto(montoBase);
        pagoBase.setMonto(calculado);
        registrar(pagoBase);
    }

    public void registrar(Pago pago) {
        repositorioPagos.crear(pago);
        notificarPagoRegistrado(pago);
    }

    public void eliminar(int id) {
        repositorioPagos.eliminar(id);
    }

    public List<Pago> listarTodos() {
        return repositorioPagos.buscarTodos();
    }

    public List<Pago> buscarPorFiltro(String filtro) {
        return repositorioPagos.buscarPorFiltro(filtro);
    }
}
