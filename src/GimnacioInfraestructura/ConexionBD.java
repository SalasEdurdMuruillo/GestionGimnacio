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
    private final String url = "jdbc:mysql://localhost:3307/Gimnacio?useSSL=false&serverTimezone=UTC";
    private final String user = "Admin";
    private final String password = "Admin123@";

    private ConexionBD() {
          try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new RuntimeException("No se encontró el driver MySQL", e);
    }
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
