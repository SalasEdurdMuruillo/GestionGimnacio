package GimnacioClase;

/**
 *
 * @author USER
 */
public class ClaseGimnasioMapper {

    public ClaseGimnasioDTO toDto(ClaseGimnasio clase) {
        if (clase == null) {
            return null;
        }

        return new ClaseGimnasioDTO(
                clase.getCodigo(),
                clase.getNombre(),
                clase.getHorario(),
                clase.getEntrenadorCedula(),
                clase.getCupoMaximo()
        );
    }

    public ClaseGimnasio toEntity(ClaseGimnasioDTO dto) {
        if (dto == null) {
            return null;
        }

        return new ClaseGimnasio(
                dto.getCodigo(),
                dto.getNombre(),
                dto.getHorario(),
                dto.getEntrenadorCedula(),
                dto.getCupoMaximo()
        );
    }
}
