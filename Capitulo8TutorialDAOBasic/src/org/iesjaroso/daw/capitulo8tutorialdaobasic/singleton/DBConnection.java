/**
 * CLASE QUE IMPLEMENTA EL PATR�N SINGLETON PARA OBTENER LA CONSULTA A LA BASE DE DATOS
 */
package org.iesjaroso.daw.capitulo8tutorialdaobasic.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Openwebinars
 *
 */
public class DBConnection {

    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/jdbc";
    private static Connection instance = null;

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (instance == null) {
            Properties props = new Properties();
            props.put("user", "usuario");
            props.put("password", "usuario");
            System.out.println(JDBC_URL);
            instance = DriverManager.getConnection(JDBC_URL, props);
        }

        return instance;
    }

}
