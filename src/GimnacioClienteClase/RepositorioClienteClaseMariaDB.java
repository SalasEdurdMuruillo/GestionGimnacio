/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GimnacioClienteClase;

import GimnacioClienteClase.ClienteClase;
import GimnacioInfraestructura.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Eduard Salas Murillo
 */
public class RepositorioClienteClaseMariaDB implements RepositorioClienteClase {

    private final String INSERT = "INSERT INTO ClienteClase (CedulaCliente, CodigoClase, FechaInscripcion) VALUES (?, ?, CURDATE())";
    private final String SELECT_BY_CLIENTE = "SELECT Id, CedulaCliente, CodigoClase, FechaInscripcion FROM ClienteClase WHERE CedulaCliente = ?";
    private final String SELECT_EXISTENCE = "SELECT Id FROM ClienteClase WHERE CedulaCliente = ? AND CodigoClase = ?";
    private final String SELECT_BY_ID = "SELECT Id, CedulaCliente, CodigoClase, FechaInscripcion FROM ClienteClase WHERE Id = ?";

    @Override
    public long crear(ClienteClase clienteClase) {
        long idGenerado = -1;
        try (Connection cn = ConexionBD.getInstance().getConnection(); PreparedStatement ps = cn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, clienteClase.getCedulaCliente());
            ps.setInt(2, clienteClase.getCodigoClase());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getLong(1);
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error SQL al crear asignación de clase: " + ex.getMessage());
            throw new RuntimeException("Error al registrar la asignación de clase en la BD.", ex);
        }
        return idGenerado;
    }

    @Override
    public List<ClienteClase> buscarPorCedulaCliente(String cedulaCliente) {
        List<ClienteClase> asignaciones = new ArrayList<>();
        try (Connection cn = ConexionBD.getInstance().getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_BY_CLIENTE)) {

            ps.setString(1, cedulaCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asignaciones.add(crearClienteClaseDesdeResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error SQL al buscar clases asignadas: " + ex.getMessage());
            throw new RuntimeException("Error al buscar clases asignadas por cliente.", ex);
        }
        return asignaciones;
    }

    @Override
    public Optional<ClienteClase> buscarPorId(long id) {
        try (Connection cn = ConexionBD.getInstance().getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_BY_ID)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(crearClienteClaseDesdeResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error SQL al buscar asignación por ID: " + ex.getMessage());
            throw new RuntimeException("Error al buscar asignación por ID.", ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClienteClase> buscarPorCedulaYClase(String cedulaCliente, int codigoClase) {
        try (Connection cn = ConexionBD.getInstance().getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_EXISTENCE)) {

            ps.setString(1, cedulaCliente);
            ps.setInt(2, codigoClase);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClienteClase cc = new ClienteClase();
                    cc.setId(rs.getLong("Id"));
                    return Optional.of(cc);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error SQL al verificar existencia de asignación: " + ex.getMessage());
            throw new RuntimeException("Error al verificar la existencia de la asignación.", ex);
        }
        return Optional.empty();
    }

    private ClienteClase crearClienteClaseDesdeResultSet(ResultSet rs) throws SQLException {
        ClienteClase cc = new ClienteClase();
        cc.setId(rs.getLong("Id"));
        cc.setCedulaCliente(rs.getString("CedulaCliente"));
        cc.setCodigoClase(rs.getInt("CodigoClase"));
        Date sqlDate = rs.getDate("FechaInscripcion");
        if (sqlDate != null) {
            cc.setFechaInscripcion(sqlDate.toLocalDate());
        } else {
            cc.setFechaInscripcion(LocalDate.now());
        }
        return cc;
    }
}
