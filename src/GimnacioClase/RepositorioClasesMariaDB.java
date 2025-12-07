package GimnacioClase;

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
 * @author USER
 */
public class RepositorioClasesMariaDB implements RepositorioClases {

    private final ConexionBD conexionBD = ConexionBD.getInstance();

    private ClaseGimnasio mapear(ResultSet rs) throws SQLException {
        ClaseGimnasio c = new ClaseGimnasio();

        c.setCodigo(String.valueOf(rs.getInt("Codigo")));
        c.setNombre(rs.getString("Nombre"));
        c.setHorario(rs.getString("Horario"));
        c.setEntrenadorCedula(rs.getString("CedulaEntrenador"));
        c.setCupoMaximo(rs.getInt("CupoMaximo"));
        return c;
    }

    private int parseCodigo(String codigo) {
        try {
            return Integer.parseInt(codigo);
        } catch (NumberFormatException ex) {

            return 0;
        }
    }

    @Override
    public void crear(ClaseGimnasio clase) {
        String sql = "INSERT INTO Clases "
                + "(Nombre, Horario, CedulaEntrenador, CupoMaximo) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, clase.getNombre());
            ps.setString(2, clase.getHorario());
            ps.setString(3, clase.getEntrenadorCedula());
            ps.setInt(4, clase.getCupoMaximo());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al crear clase", ex);
        }
    }

    @Override
    public Optional<ClaseGimnasio> buscarPorId(String id) {
        String sql = "SELECT * FROM Clases WHERE Codigo = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, parseCodigo(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al buscar clase por código", ex);
        }
        return Optional.empty();
    }

    @Override
    public List<ClaseGimnasio> buscarTodos() {
        List<ClaseGimnasio> res = new ArrayList<>();
        String sql = "SELECT * FROM Clases";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                res.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al listar clases", ex);
        }
        return res;
    }

    @Override
    public void actualizar(ClaseGimnasio clase) {
        String sql = "UPDATE Clases SET "
                + "Nombre = ?, Horario = ?, CedulaEntrenador = ?, CupoMaximo = ? "
                + "WHERE Codigo = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, clase.getNombre());
            ps.setString(2, clase.getHorario());
            ps.setString(3, clase.getEntrenadorCedula());
            ps.setInt(4, clase.getCupoMaximo());
            ps.setInt(5, parseCodigo(clase.getCodigo()));

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al actualizar clase", ex);
        }
    }

    @Override
    public void eliminar(String id) {
        String sql = "DELETE FROM Clases WHERE Codigo = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, parseCodigo(id));
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al eliminar clase", ex);
        }
    }

    @Override
    public List<ClaseGimnasio> buscarPorFiltro(String filtro) {
        List<ClaseGimnasio> res = new ArrayList<>();
        String f = "%" + filtro + "%";
        String sql = "SELECT * FROM Clases "
                + "WHERE CAST(Codigo AS CHAR) LIKE ? OR Nombre LIKE ? OR Horario LIKE ?";
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
            throw new RuntimeException("Error al buscar clases por filtro", ex);
        }
        return res;
    }
}
