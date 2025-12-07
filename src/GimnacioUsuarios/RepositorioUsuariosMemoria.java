package GimnacioUsuarios;

import Utils.PasswordUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author Luisf
 */

public class RepositorioUsuariosMemoria implements RepositorioUsuarios {

    private static final List<Usuario> listaUsuarios = new ArrayList<>();

    static {
        listaUsuarios.add(new Usuario(1L, "Admin", PasswordUtils.hash("Admin123@"), RolUsuario.ADMINISTRADOR));
        listaUsuarios.add(new Usuario(2L, "Entrenador", PasswordUtils.hash("Entrenador123@"), RolUsuario.ENTRENADOR));
    }

    @Override
    public void crear(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return listaUsuarios.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public List<Usuario> buscarTodos() {
        return new ArrayList<>(listaUsuarios);
    }

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getId().equals(usuario.getId())) {
                listaUsuarios.set(i, usuario);
                return;
            }
        }
    }

    @Override
    public void eliminar(Long id) {
        listaUsuarios.removeIf(u -> u.getId().equals(id));
    }

    @Override
    public List<Usuario> buscarPorFiltro(String filtro) {
        List<Usuario> res = new ArrayList<>();
        String f = filtro.toLowerCase();
        for (Usuario u : listaUsuarios) {
            if (u.getNombreUsuario().toLowerCase().contains(f)) {
                res.add(u);
            }
        }
        return res;
    }

    @Override
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return listaUsuarios.stream()
                .filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))
                .findFirst();
    }
}
