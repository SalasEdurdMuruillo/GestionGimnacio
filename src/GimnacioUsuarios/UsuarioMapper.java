package GimnacioUsuarios;
/**
 *
 * @author Luisf
 */

public class UsuarioMapper {

    public UsuarioDTO toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getContrasenia(),
                usuario.getRolUsuario()
        );
    }

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Usuario(
                dto.getId(),
                dto.getNombreUsuario(),
                dto.getContrasenia(),
                dto.getRolUsuario()
        );
    }
}
