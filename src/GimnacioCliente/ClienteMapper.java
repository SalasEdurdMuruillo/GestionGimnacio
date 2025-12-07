package GimnacioCliente;
/**
 *
 * @author Luisf
 */

public class ClienteMapper {

    public ClienteDTO toDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDTO(
                cliente.getCedula(),
                cliente.getNombreCompleto(),
                cliente.getTelefono(),
                cliente.getFecha(),
                cliente.getTipoMembresia(),
                cliente.isActivo()
        );
    }

    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        return new ClienteConfigurador()
                .cedula(dto.getCedula())
                .nombreCompleto(dto.getNombreCompleto())
                .telefono(dto.getTelefono())
                .Fecha(dto.getFecha())
                .tipoMembresia(dto.getTipoMembresia())
                .activo(dto.isActivo())
                .build();
    }
}
