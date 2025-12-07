package GimnacioMembresia;

import GimnacioCliente.Cliente;
import Utils.MembresiaUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
/**
 *
 * @author Sebastian
 */

public class AlertaVencimientoMembresia extends Thread {

    private final List<Cliente> clientes;
    private final int diasAviso;
    private final long milisegundosEspera;
    private final Consumer<String> notificador;
    private volatile boolean correr = true;

    public AlertaVencimientoMembresia(List<Cliente> clientes,
            int diasAviso,
            long milisegundosEspera,
            Consumer<String> notificador) {
        this.clientes = clientes;
        this.diasAviso = diasAviso;
        this.milisegundosEspera = milisegundosEspera;
        this.notificador = notificador;
        setName("AlertaVencimientoMembresiaThread");
        setDaemon(true);
    }

    public void detener() {
        correr = false;
    }

    @Override
    public void run() {
        while (correr) {
            LocalDate hoy = LocalDate.now();
            for (Cliente c : clientes) {
                if (MembresiaUtil.estaVencida(c, hoy)) {
                    notificador.accept("Cliente " + c.getNombreCompleto()
                            + " (" + c.getCedula() + ") tiene la membresía VENCIDA.");
                } else if (MembresiaUtil.estaPorVencer(c, hoy, diasAviso)) {
                    notificador.accept("Cliente " + c.getNombreCompleto()
                            + " (" + c.getCedula() + ") tiene la membresía por vencer pronto.");
                }
            }
            try {
                Thread.sleep(milisegundosEspera);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
