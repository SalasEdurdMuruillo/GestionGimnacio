package Utils;

import GimnacioCliente.Cliente;
import GimnacioMembresia.TipoMembresia;
import java.time.LocalDate;
/**
 *
 * @author Sebastian
 */

public final class MembresiaUtil {

    private MembresiaUtil() {
    }

    public static LocalDate calcularFechaVencimiento(LocalDate fechaRegistro, TipoMembresia tipo) {
        if (fechaRegistro == null || tipo == null) {
            return null;
        }
        return fechaRegistro.plusMonths(tipo.getMeses());
    }

    public static boolean estaVencida(Cliente cliente, LocalDate hoy) {
        if (cliente == null || hoy == null) {
            return false;
        }
        LocalDate fechaRegistro = cliente.getFecha();
        if (fechaRegistro == null || cliente.getTipoMembresia() == null) {
            return false;
        }
        LocalDate vencimiento = calcularFechaVencimiento(fechaRegistro, cliente.getTipoMembresia());
        if (vencimiento == null) {
            return false;
        }
        return !vencimiento.isAfter(hoy);
    }

    public static boolean estaPorVencer(Cliente cliente, LocalDate hoy, int diasAviso) {
        if (cliente == null || hoy == null || diasAviso <= 0) {
            return false;
        }
        LocalDate fechaRegistro = cliente.getFecha();
        if (fechaRegistro == null || cliente.getTipoMembresia() == null) {
            return false;
        }
        LocalDate vencimiento = calcularFechaVencimiento(fechaRegistro, cliente.getTipoMembresia());
        if (vencimiento == null) {
            return false;
        }
        LocalDate limiteAviso = hoy.plusDays(diasAviso);
        return vencimiento.isAfter(hoy)
                && (vencimiento.isBefore(limiteAviso) || vencimiento.isEqual(limiteAviso));
    }
}
