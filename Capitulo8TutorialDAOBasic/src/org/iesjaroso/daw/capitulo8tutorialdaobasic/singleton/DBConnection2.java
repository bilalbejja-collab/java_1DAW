/**
 * CLASE QUE IMPLEMENTA EL PATR�N SINGLETON PARA OBTENER LA CONSULTA A LA BASE DE DATOS
 */
package org.iesjaroso.daw.capitulo8tutorialdaobasic.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection2 {

    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/jdbc";
    public static final String USERNAME = "usuario";
    public static final String PASSWORD = "usuario";

    private static Connection instance = null;

    private DBConnection2() {
    }

    public static Connection getConnection() throws SQLException {
        if (instance == null) {
            Properties props = new Properties();
            props.put("user", "usuario");
            props.put("password", "usuario");
            instance = DriverManager.getConnection(JDBC_URL, props);
            instance.setAutoCommit(false);
        }

        return instance;
    }

}
