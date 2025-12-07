package GimnacioCliente;

import GimnacioMembresia.TipoMembresia;
import java.time.LocalDate;
/**
 *
 * @author Luisf
 */

public class ClienteDTO {

    private final String cedula;
    private final String nombreCompleto;
    private final String telefono;
    private final LocalDate fecha;
    private final TipoMembresia tipoMembresia;
    private final boolean activo;

    public ClienteDTO(String cedula,
            String nombreCompleto,
            String telefono,
            LocalDate fecha,
            TipoMembresia tipoMembresia,
            boolean activo) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.fecha = fecha;
        this.tipoMembresia = tipoMembresia;
        this.activo = activo;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public TipoMembresia getTipoMembresia() {
        return tipoMembresia;
    }

    public boolean isActivo() {
        return activo;
    }
}
