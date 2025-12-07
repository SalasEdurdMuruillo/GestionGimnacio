package GimnacioCliente;

import GimnacioMembresia.TipoMembresia;
import java.time.LocalDate;
/**
 *
 * @author Luisf
 */

public class Cliente {

    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private LocalDate fecha;
    private TipoMembresia tipoMembresia;
    private boolean activo;

    public Cliente() {
    }

    public Cliente(String cedula, String nombreCompleto, String telefono,
            LocalDate fecha, TipoMembresia tipoMembresia, boolean activo) {
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

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoMembresia getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(TipoMembresia tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
