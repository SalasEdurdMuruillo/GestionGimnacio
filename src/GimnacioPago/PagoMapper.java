package GimnacioPago;

/**
 *
 * @author Eduard Salas Murillo
 */
public class PagoMapper {

    public PagoDTO toDto(Pago pago) {
        if (pago == null) {
            return null;
        }

        return new PagoDTO(
                pago.getId(),
                pago.getCedulaCliente(),
                pago.getMonto(),
                pago.getFecha()
        );
    }

    public Pago toEntity(PagoDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Pago(
                dto.getId(),
                dto.getCedulaCliente(),
                dto.getMonto(),
                dto.getFecha()
        );
    }
}
