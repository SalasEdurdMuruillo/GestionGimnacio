package GimnacioUsuarios;
/**
 *
 * @author Luisf
 */

public class UsuarioDTO {

    private final Long id;
    private final String nombreUsuario;
    private final String contrasenia;
    private final RolUsuario rolUsuario;

    public UsuarioDTO(Long id,
            String nombreUsuario,
            String contrasenia,
            RolUsuario rolUsuario) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rolUsuario = rolUsuario;
    }

    public Long getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }
}
