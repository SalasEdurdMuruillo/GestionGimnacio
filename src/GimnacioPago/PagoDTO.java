package GimnacioPago;

import java.time.LocalDate;

/**
 *
 * @author Eduard Salas Murillo
 */
public class PagoDTO {

    private final int id;
    private final String cedulaCliente;
    private final double monto;
    private final LocalDate fecha;

    public PagoDTO(int id,
            String cedulaCliente,
            double monto,
            LocalDate fecha) {
        this.id = id;
        this.cedulaCliente = cedulaCliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
