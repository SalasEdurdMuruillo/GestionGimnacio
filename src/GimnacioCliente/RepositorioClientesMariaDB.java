package GimnacioCliente;

import GimnacioInfraestructura.ConexionBD;
import GimnacioMembresia.TipoMembresia;
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
 * @author Luisf
 */

public class RepositorioClientesMariaDB implements RepositorioClientes {

    private final ConexionBD conexionBD = ConexionBD.getInstance();

    private int mapearIdTipoMembresia(TipoMembresia tipo) {
        if (tipo == null) {
            return 1;
        }
        return switch (tipo) {
            case BASICA ->
                1;
            case PREMIUM ->
                2;
            case VIP ->
                3;
        };
    }

    private TipoMembresia mapearTipoMembresia(int idTipo) {
        return switch (idTipo) {
            case 2 ->
                TipoMembresia.PREMIUM;
            case 3 ->
                TipoMembresia.VIP;
            default ->
                TipoMembresia.BASICA;
        };
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setCedula(rs.getString("Cedula"));
        c.setNombreCompleto(rs.getString("Nombre"));
        c.setTelefono(rs.getString("Telefono"));

        Date fechaSql = rs.getDate("Fecha");
        if (fechaSql != null) {
            LocalDate fecha = fechaSql.toLocalDate();
            c.setFecha(fecha);
        }

        int idTipo = rs.getInt("IdTipoMembresia");
        if (!rs.wasNull()) {
            c.setTipoMembresia(mapearTipoMembresia(idTipo));
        }

        c.setActivo(rs.getBoolean("Activo"));
        return c;
    }

    @Override
    public void crear(Cliente entidad) {
        String sql = "INSERT INTO Cliente "
                + "(Cedula, Nombre, Telefono, Fecha, IdTipoMembresia, Activo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, entidad.getCedula());
            ps.setString(2, entidad.getNombreCompleto());
            ps.setString(3, entidad.getTelefono());

            LocalDate fecha = entidad.getFecha();
            if (fecha == null) {
                fecha = LocalDate.now();
            }
            ps.setDate(4, Date.valueOf(fecha));

            ps.setInt(5, mapearIdTipoMembresia(entidad.getTipoMembresia()));
            ps.setBoolean(6, entidad.isActivo());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al crear cliente", ex);
        }
    }

    @Override
    public Optional<Cliente> buscarPorId(String id) {
        String sql = "SELECT * FROM Cliente WHERE Cedula = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al buscar cliente por cédula", ex);
        }
        return Optional.empty();
    }

    @Override
    public List<Cliente> buscarTodos() {
        List<Cliente> res = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                res.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al listar clientes", ex);
        }
        return res;
    }

    @Override
    public void actualizar(Cliente entidad) {
        String sql = "UPDATE Cliente SET "
                + "Nombre = ?, Telefono = ?, Fecha = ?, IdTipoMembresia = ?, Activo = ? "
                + "WHERE Cedula = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombreCompleto());
            ps.setString(2, entidad.getTelefono());

            LocalDate fecha = entidad.getFecha();
            if (fecha == null) {
                fecha = LocalDate.now();
            }
            ps.setDate(3, Date.valueOf(fecha));

            ps.setInt(4, mapearIdTipoMembresia(entidad.getTipoMembresia()));
            ps.setBoolean(5, entidad.isActivo());
            ps.setString(6, entidad.getCedula());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al actualizar cliente", ex);
        }
    }

    @Override
    public void eliminar(String id) {
        String sql = "DELETE FROM Cliente WHERE Cedula = ?";
        try (Connection cn = conexionBD.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error al eliminar cliente", ex);
        }
    }

    @Override
    public List<Cliente> buscarPorFiltro(String filtro) {
        List<Cliente> res = new ArrayList<>();
        String f = "%" + filtro + "%";
        String sql = "SELECT * FROM Cliente "
                + "WHERE Cedula LIKE ? OR Nombre LIKE ? OR Telefono LIKE ?";
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
            throw new RuntimeException("Error al buscar clientes por filtro", ex);
        }
        return res;
    }
}
