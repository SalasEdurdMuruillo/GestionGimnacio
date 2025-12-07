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
/**
 *
 * @author Luisf
 */

public class RepositorioUsuariosMariaDB implements RepositorioUsuarios {

    private final ConexionBD conexionBD = ConexionBD.getInstance();

    private RolUsuario mapearRol(String nombreRol) {
        if (nombreRol == null) {
            return RolUsuario.RECEPCIONISTA;
        }
        try {
            return RolUsuario.valueOf(nombreRol.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return RolUsuario.RECEPCIONISTA;
        }
    }

    private String rolToNombre(RolUsuario rol) {
        if (rol == null) {
            return "RECEPCIONISTA";
        }
        return rol.name();
    }

    private Long obtenerIdRol(RolUsuario rol, Connection cn) throws SQLException {
        String nombreRol = rolToNombre(rol);
        String select = "SELECT Id_Rol FROM RolUsuario WHERE Nombre = ?";
        try (PreparedStatement ps = cn.prepareStatement(select)) {
            ps.setString(1, nombreRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("Id_Rol");
                }
            }
        }

        String insert = "INSERT INTO RolUsuario (Nombre) VALUES (?)";
        try (PreparedStatement ps = cn.prepareStatement(insert, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombreRol);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }

        try (PreparedStatement ps = cn.prepareStatement(select)) {
            ps.setString(1, nombreRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("Id_Rol");
                }
            }
        }
        throw new SQLException("No se pudo obtener ni crear el rol de usuario");
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
        String sql = "INSERT INTO Usuarios (NombreUsuario, Contra, Id_Rol, CedulaEntrenador) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            Long idRol = obtenerIdRol(usuario.getRolUsuario(), cn);

            ps.setString(1, usuario.getNombreUsuario());

            String hash = usuario.getContrasenia();
            if (hash == null || hash.length() < 40) {
                hash = PasswordUtils.hash(usuario.getContrasenia());
            }
            ps.setString(2, hash);
            ps.setLong(3, idRol);

            ps.setString(4, null);

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al crear usuario", ex);
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        String sql = "SELECT u.Id, u.NombreUsuario, u.Contra, r.Nombre "
                + "FROM Usuarios u "
                + "JOIN RolUsuario r ON u.Id_Rol = r.Id_Rol "
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
                + "JOIN RolUsuario r ON u.Id_Rol = r.Id_Rol";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                res.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al listar usuarios", ex);
        }
        return res;
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE Usuarios SET NombreUsuario = ?, Contra = ?, Id_Rol = ? "
                + "WHERE Id = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            Long idRol = obtenerIdRol(usuario.getRolUsuario(), cn);

            ps.setString(1, usuario.getNombreUsuario());
            String hash = usuario.getContrasenia();
            if (hash == null || hash.length() < 40) {
                hash = PasswordUtils.hash(usuario.getContrasenia());
            }
            ps.setString(2, hash);
            ps.setLong(3, idRol);
            ps.setLong(4, usuario.getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al actualizar usuario", ex);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM Usuarios WHERE Id = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al eliminar usuario", ex);
        }
    }

    @Override
    public List<Usuario> buscarPorFiltro(String filtro) {
        List<Usuario> res = new ArrayList<>();
        String f = "%" + filtro + "%";
        String sql = "SELECT u.Id, u.NombreUsuario, u.Contra, r.Nombre "
                + "FROM Usuarios u "
                + "JOIN RolUsuario r ON u.Id_Rol = r.Id_Rol "
                + "WHERE u.NombreUsuario LIKE ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, f);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    res.add(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al buscar usuarios por filtro", ex);
        }
        return res;
    }

    @Override
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        String sql = "SELECT u.Id, u.NombreUsuario, u.Contra, r.Nombre "
                + "FROM Usuarios u "
                + "JOIN RolUsuario r ON u.Id_Rol = r.Id_Rol "
                + "WHERE u.NombreUsuario = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al buscar usuario por nombre de usuario", ex);
        }
        return Optional.empty();
    }
}
