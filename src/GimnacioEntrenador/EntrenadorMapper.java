package GimnacioEntrenador;

/**
 *
 * @author Eduard Salas Murillo
 */
public class EntrenadorMapper {

    public EntrenadorDTO toDto(Entrenador entrenador) {
        if (entrenador == null) {
            return null;
        }

        return new EntrenadorDTO(
                entrenador.getCedula(),
                entrenador.getNombreCompleto(),
                entrenador.getTelefono(),
                entrenador.getEspecialidad(),
                entrenador.isActivo()
        );
    }

    public Entrenador toEntity(EntrenadorDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Entrenador(
                dto.getCedula(),
                dto.getNombreCompleto(),
                dto.getTelefono(),
                dto.getEspecialidad(),
                dto.isActivo()
        );
    }
}
