package GimnacioClase;

/**
 *
 * @author USER
 */
public class ClaseGimnasio implements Cloneable {

    private String codigo;
    private String nombre;
    private String horario;
    private String entrenadorCedula;
    private int cupoMaximo;

    public ClaseGimnasio() {
    }

    public ClaseGimnasio(String codigo, String nombre, String horario,
            String entrenadorCedula, int cupoMaximo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.horario = horario;
        this.entrenadorCedula = entrenadorCedula;
        this.cupoMaximo = cupoMaximo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEntrenadorCedula() {
        return entrenadorCedula;
    }

    public void setEntrenadorCedula(String entrenadorCedula) {
        this.entrenadorCedula = entrenadorCedula;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    @Override
    public ClaseGimnasio clone() {
        try {
            return (ClaseGimnasio) super.clone();
        } catch (CloneNotSupportedException ex) {
            ClaseGimnasio copia = new ClaseGimnasio();
            copia.setCodigo(this.codigo);
            copia.setNombre(this.nombre);
            copia.setHorario(this.horario);
            copia.setEntrenadorCedula(this.entrenadorCedula);
            copia.setCupoMaximo(this.cupoMaximo);
            return copia;
        }
    }

}
