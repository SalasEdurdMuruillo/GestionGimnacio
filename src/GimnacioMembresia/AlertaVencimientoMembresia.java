package GimnacioMembresia;

import GimnacioCliente.Cliente;
import GimnacioCliente.ServicioCliente;
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
    private final ServicioCliente servicioCliente;
    private volatile boolean correr = true;

    public AlertaVencimientoMembresia(List<Cliente> clientes, int diasAviso, long milisegundosEspera, Consumer<String> notificador, ServicioCliente servicioCliente) {
        this.clientes = clientes;
        this.diasAviso = diasAviso;
        this.milisegundosEspera = milisegundosEspera;
        this.notificador = notificador;
        this.servicioCliente = servicioCliente;
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
                String cedula = c.getCedula();

                if (c.isNotificacionVencimientoMostrada()) {
                    continue;
                }

                String mensaje = null;
                if (MembresiaUtil.estaVencida(c, hoy)) {
                    mensaje = "Cliente " + c.getNombreCompleto()
                            + " (" + cedula + ") tiene la membresía VENCIDA.";
                } else if (MembresiaUtil.estaPorVencer(c, hoy, diasAviso)) {
                    mensaje = "Cliente " + c.getNombreCompleto()
                            + " (" + cedula + ") tiene la membresía por vencer pronto.";
                }

                if (mensaje != null) {
                    notificador.accept(mensaje);
                    c.setNotificacionVencimientoMostrada(true);

                    servicioCliente.guardar(c);
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
