package GimnacioClase;

/**
 *
 * @author USER
 */
public class ClaseGimnasioDTO {

    private final String codigo;
    private final String nombre;
    private final String horario;
    private final String entrenadorCedula;
    private final int cupoMaximo;

    public ClaseGimnasioDTO(String codigo,
            String nombre,
            String horario,
            String entrenadorCedula,
            int cupoMaximo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.horario = horario;
        this.entrenadorCedula = entrenadorCedula;
        this.cupoMaximo = cupoMaximo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHorario() {
        return horario;
    }

    public String getEntrenadorCedula() {
        return entrenadorCedula;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }
}
