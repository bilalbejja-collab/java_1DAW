/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo8.conexion;

import capitulo8.Utils.ConfigDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author BILAL
 */
public class DBConection {

    private static final String HOST;
    private static final String PORT;
    private static final String DB;
    private static final String JDBC_URL;
    
    private static Connection instance = null;
    private static final Properties PROPS;

    static {
        //Es necesario el fichero de configuración con los datos de la BD
        PROPS = ConfigDB.loadConfigFile("config.properties");
        PORT = PROPS.getProperty("port");
        DB = PROPS.getProperty("database");
        HOST = PROPS.getProperty("host");
        JDBC_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/";
    }

    private DBConection() {}

    public static Connection getConnection() {
        return getConnection(DBConection.DB);
    }

    public static Connection getConnection(String BD) {

        if (instance == null) {
            try {
                instance = DriverManager.getConnection(JDBC_URL + BD, PROPS);
//            } catch (ClassNotFoundException ex) {
//                System.err.println("Error cargando el Driver MySQL JDBC ... FAIL");
            } catch (SQLException ex) {
                printSQLException(ex);
            }
        }
        return instance;
    }

    /**
     * Mensaje de error personalizado y muy completo para conocer que ha fallado
     * en la SQLException
     *
     * @param ex
     */
    public static void printSQLException(SQLException ex) {
        ex.printStackTrace(System.err);
        System.err.println("SQLEstado: " + ex.getSQLState());
        System.err.println("Código de error: " + ex.getErrorCode());
        System.err.println("Mensaje: " + ex.getMessage());
        Throwable t = ex.getCause();
        while (t != null) {
            System.out.println("Causa: " + t);
            t = t.getCause();
        }
    }
    public static String getUSERDB(){
        return PROPS.getProperty("user");
    }
    
    public static String getPASSWDDB(){
        return PROPS.getProperty("password");
    }
    
    public static String getDB(){
        return PROPS.getProperty("database");
    }
    
    public static String getURLDB(){
        return JDBC_URL;
    }
}
