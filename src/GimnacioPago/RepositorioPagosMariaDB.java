package GimnacioPago;

import GimnacioInfraestructura.ConexionBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Eduard Salas Murillo
 */
public class RepositorioPagosMariaDB implements RepositorioPagos {

    private final ConexionBD conexionBD = ConexionBD.getInstance();

    private Pago mapear(ResultSet rs) throws SQLException {
        Pago p = new Pago();
        p.setId(rs.getInt("Id"));
        p.setCedulaCliente(rs.getString("CedulaCliente"));
        p.setMonto(rs.getDouble("Monto"));
        java.sql.Timestamp ts = rs.getTimestamp("Fecha");
        if (ts != null) {
            p.setFecha(ts.toLocalDateTime().toLocalDate());
        }
        return p;
    }

    @Override
    public void crear(Pago pago) {
        String sql = "INSERT INTO Pagos (CedulaCliente, Monto, Fecha) VALUES (?, ?, ?)";

        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, pago.getCedulaCliente());

            ps.setDouble(2, pago.getMonto());

            LocalDate fecha = pago.getFecha();
            if (fecha == null) {
                ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            } else {
                ps.setTimestamp(3, java.sql.Timestamp.valueOf(fecha.atStartOfDay()));
            }

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al crear pago: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<Pago> buscarPorId(Integer id) {
        String sql = "SELECT * FROM Pagos WHERE Id = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al buscar pago por id", ex);
        }
        return Optional.empty();
    }

    @Override
    public List<Pago> buscarTodos() {
        List<Pago> res = new ArrayList<>();
        String sql = "SELECT * FROM Pagos";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                res.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al listar pagos", ex);
        }
        return res;
    }

    @Override
    public void actualizar(Pago pago) {
        String sql = "UPDATE Pagos SET CedulaCliente = ?, Monto = ?, Fecha = ? WHERE Id = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, pago.getCedulaCliente());
            ps.setDouble(2, pago.getMonto());

            LocalDate fecha = pago.getFecha();
            if (fecha == null) {
                ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            } else {
                ps.setTimestamp(3, java.sql.Timestamp.valueOf(fecha.atStartOfDay()));
            }
            ps.setInt(4, pago.getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al actualizar pago", ex);
        }
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM Pagos WHERE Id = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al eliminar pago", ex);
        }
    }

    @Override
    public List<Pago> buscarPorFiltro(String filtro) {
        List<Pago> res = new ArrayList<>();
        String f = "%" + filtro + "%";
        String sql = "SELECT * FROM Pagos WHERE CedulaCliente LIKE ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, f);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    res.add(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al buscar pagos por filtro", ex);
        }
        return res;
    }
}
