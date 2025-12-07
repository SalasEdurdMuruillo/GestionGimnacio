package GimnacioCliente;

import GimnacioMembresia.TipoMembresia;
import java.time.LocalDate;
/**
 *
 * @author Luisf
 */

public class ClienteConfigurador {

    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private LocalDate fecha;
    private TipoMembresia tipoMembresia;
    private boolean activo;

    public ClienteConfigurador cedula(String cedula) {
        this.cedula = cedula;
        return this;
    }

    public ClienteConfigurador nombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        return this;
    }

    public ClienteConfigurador telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public ClienteConfigurador Fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public ClienteConfigurador tipoMembresia(TipoMembresia tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
        return this;
    }

    public ClienteConfigurador activo(boolean activo) {
        this.activo = activo;
        return this;
    }

    public Cliente build() {
        Cliente c = new Cliente();
        c.setCedula(cedula);
        c.setNombreCompleto(nombreCompleto);
        c.setTelefono(telefono);
        c.setFecha(fecha);
        c.setTipoMembresia(tipoMembresia);
        c.setActivo(activo);
        return c;
    }
}
