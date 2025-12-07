package GimnacioEntrenador;

/**
 *
 * @author Eduard Salas Murillo
 */
public class EntrenadorDTO {

    private final String cedula;
    private final String nombreCompleto;
    private final String telefono;
    private final String especialidad;
    private final boolean activo;

    public EntrenadorDTO(String cedula,
            String nombreCompleto,
            String telefono,
            String especialidad,
            boolean activo) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.especialidad = especialidad;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public boolean isActivo() {
        return activo;
    }
}
