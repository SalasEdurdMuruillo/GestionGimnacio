package GimnacioPago;

/**
 *
 * @author Eduard Salas Murillo
 */
public class PagoBasico implements CalculoPago {

    @Override
    public double calcularMonto(double montoBase) {
        return montoBase;
    }
}
