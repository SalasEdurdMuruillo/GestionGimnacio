package GimnacioUsuarios;

import Utils.PasswordUtils;
import java.util.Optional;
/**
 *
 * @author Luisf
 */

/**
 * Servicio de autenticación que compara contraseñas usando hash.
 */
public class ServicioAutenticacion {

    private final RepositorioUsuarios repositorioUsuarios;

    public ServicioAutenticacion(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public Optional<Usuario> iniciarSesion(String nombreUsuario, String contraseniaPlano) {
        Optional<Usuario> usuarioOpt = repositorioUsuarios.buscarPorNombreUsuario(nombreUsuario);
        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }
        Usuario u = usuarioOpt.get();
        if (PasswordUtils.verificar(contraseniaPlano, u.getContrasenia())) {
            return Optional.of(u);
        }
        return Optional.empty();
    }
}
