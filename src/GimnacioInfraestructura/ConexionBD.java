package GimnacioInfraestructura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class ConexionBD {

    private static ConexionBD instance;
    private final String url = "jdbc:mariadb://localhost:3307/Gimnacio";
    private final String user = "Admin";
    private final String password = "Admin123@";

    private ConexionBD() {
    }

    public static synchronized ConexionBD getInstance() {
        if (instance == null) {
            instance = new ConexionBD();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
