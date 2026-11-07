package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USUARIO = "system";
    private static final String PASSWORD = "Tapiero123";

    // Reemplazar por la contraseña del usuario de Oracle.
    //private static final String PASSWORD = "TU_PASSWORD";

    private ConexionOracle()
    {
        // EVITA INSTANCIAR esta clase de utilidad.
    }

    public static Connection conectar() throws SQLException
    {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}   
