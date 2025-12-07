package GimnacioEntrenador;

import GimnacioInfraestructura.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Eduard Salas Murillo
 */
public class RepositorioEntrenadoresMariaDB implements RepositorioEntrenadores {

    private final ConexionBD conexionBD = ConexionBD.getInstance();

    private Entrenador mapear(ResultSet rs) throws SQLException {
        Entrenador e = new Entrenador();
        e.setCedula(rs.getString("CedulaEntrenador"));
        e.setNombreCompleto(rs.getString("Nombre"));
        e.setTelefono(rs.getString("Telefono"));
        e.setEspecialidad(rs.getString("Especialidad"));
        e.setActivo(rs.getBoolean("Activo"));
        return e;
    }

    @Override
    public void crear(Entrenador entrenador) {
        String sql = "INSERT INTO Entrenador "
                + "(CedulaEntrenador, Nombre, Telefono, Especialidad, Activo) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, entrenador.getCedula());
            ps.setString(2, entrenador.getNombreCompleto());
            ps.setString(3, entrenador.getTelefono());
            ps.setString(4, entrenador.getEspecialidad());
            ps.setBoolean(5, entrenador.isActivo());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al crear entrenador", ex);
        }
    }

    @Override
    public Optional<Entrenador> buscarPorId(String id) {
        String sql = "SELECT * FROM Entrenador WHERE CedulaEntrenador = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al buscar entrenador por cédula", ex);
        }
        return Optional.empty();
    }

    @Override
    public List<Entrenador> buscarTodos() {
        List<Entrenador> res = new ArrayList<>();
        String sql = "SELECT * FROM Entrenador";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                res.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al listar entrenadores", ex);
        }
        return res;
    }

    @Override
    public void actualizar(Entrenador entrenador) {
        String sql = "UPDATE Entrenador SET "
                + "Nombre = ?, Telefono = ?, Especialidad = ?, Activo = ? "
                + "WHERE CedulaEntrenador = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, entrenador.getNombreCompleto());
            ps.setString(2, entrenador.getTelefono());
            ps.setString(3, entrenador.getEspecialidad());
            ps.setBoolean(4, entrenador.isActivo());
            ps.setString(5, entrenador.getCedula());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al actualizar entrenador", ex);
        }
    }

    @Override
    public void eliminar(String id) {
        String sql = "DELETE FROM Entrenador WHERE CedulaEntrenador = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al eliminar entrenador", ex);
        }
    }

    @Override
    public List<Entrenador> buscarPorFiltro(String filtro) {
        List<Entrenador> res = new ArrayList<>();
        String f = "%" + filtro + "%";
        String sql = "SELECT * FROM Entrenador "
                + "WHERE CedulaEntrenador LIKE ? OR Nombre LIKE ? OR Especialidad LIKE ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, f);
            ps.setString(2, f);
            ps.setString(3, f);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    res.add(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al buscar entrenadores por filtro", ex);
        }
        return res;
    }
}
