package GimnacioUsuarios;

import GimnacioInfraestructura.ConexionBD;
import Utils.PasswordUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.List;

import java.util.List;

import java.util.ArrayList;

/**
 *
 * @author Luisf
 */
public class RepositorioUsuariosMariaDB implements RepositorioUsuarios {

    private final ConexionBD conexionBD = ConexionBD.getInstance();

    private RolUsuario mapearRol(String nombreRol) {
        if (nombreRol == null) {
            return RolUsuario.ADMINISTRADOR;
        }
        try {
            return RolUsuario.valueOf(nombreRol.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return RolUsuario.ADMINISTRADOR;
        }
    }

    private String rolToNombre(RolUsuario rol) {
        if (rol == null) {
            return "ADMINISTRADOR";
        }
        return rol.name();
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("Id"));
        u.setNombreUsuario(rs.getString("NombreUsuario"));
        u.setContrasenia(rs.getString("Contra"));

        String nombreRol = rs.getString("Nombre");
        u.setRolUsuario(mapearRol(nombreRol));
        return u;
    }

    @Override
    public void crear(Usuario usuario) {
        Optional<Usuario> existente = buscarPorNombreUsuario(usuario.getNombreUsuario());

        String sql = "INSERT INTO Usuarios (NombreUsuario, Contra, RolUsuario) "
                + "VALUES (?, ?, ?)";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            String nombreRol = rolToNombre(usuario.getRolUsuario());

            ps.setString(1, usuario.getNombreUsuario());

            String hash = usuario.getContrasenia();
            if (hash == null || hash.length() < 40) {
                hash = PasswordUtils.hash(usuario.getContrasenia());
            }
            ps.setString(2, hash);

            ps.setString(3, nombreRol);

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al crear usuario", ex);
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        String sql = "SELECT u.Id, u.NombreUsuario, u.Contra, r.Nombre "
                + "FROM Usuarios u "
                + "JOIN RolUsuario r ON u.RolUsuario = r.RolUsuario "
                + "WHERE u.Id = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al buscar usuario por id", ex);
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> buscarTodos() {
        List<Usuario> res = new ArrayList<>();
        String sql = "SELECT u.Id, u.NombreUsuario, u.Contra, r.Nombre "
                + "FROM Usuarios u "
                + "JOIN RolUsuario r ON u.RolUsuario = r.RolUsuario";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                res.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("", ex);
        }
        return res;
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE Usuarios SET NombreUsuario = ?, Contra = ?, RolUsuario = ? "
                + "WHERE Id = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            String nombreRol = rolToNombre(usuario.getRolUsuario());

            ps.setString(1, usuario.getNombreUsuario());
            String hash = usuario.getContrasenia();
            if (hash == null || hash.length() < 40) {
                hash = PasswordUtils.hash(usuario.getContrasenia());
            }
            ps.setString(2, hash);

            ps.setString(3, nombreRol);
            ps.setLong(4, usuario.getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("", ex);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM Usuarios WHERE Id = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("", ex);
        }
    }

    @Override
    public List<Usuario> buscarPorFiltro(String filtro) {
        List<Usuario> res = new ArrayList<>();
        String f = "%" + filtro + "%";
        String sql = "SELECT u.Id, u.NombreUsuario, u.Contra, r.Nombre "
                + "FROM Usuarios u "
                + "JOIN RolUsuario r ON u.RolUsuario = r.RolUsuario "
                + "WHERE u.NombreUsuario LIKE ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, f);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    res.add(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("", ex);
        }
        return res;
    }

    @Override
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        String sql = "SELECT u.Id, u.NombreUsuario, u.Contra, r.Nombre "
                + "FROM Usuarios u "
                + "JOIN RolUsuario r ON u.RolUsuario = r.RolUsuario "
                + "WHERE u.NombreUsuario = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("", ex);
        }
        return Optional.empty();
    }
}
