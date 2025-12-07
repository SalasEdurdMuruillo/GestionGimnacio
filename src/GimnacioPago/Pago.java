package GimnacioPago;

import java.time.LocalDate;

/**
 *
 * @author Eduard Salas Murillo
 */
public class Pago {

    private int id;
    private String cedulaCliente;
    private double monto;
    private LocalDate fecha;

    public Pago() {
    }

    public Pago(int id, String cedulaCliente, double monto, LocalDate fecha) {
        this.id = id;
        this.cedulaCliente = cedulaCliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
