/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo8.Utils;

import capitulo8.conexion.DBConection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author BILAL
 */
public class UtilsBD {

    private static final String DB = "javadb";
    private Connection conexion = null;
    private static UtilsBD instance = null;

    public static UtilsBD getInstance() throws SQLException {
        if (instance == null) {
            instance = new UtilsBD();
        }
        return instance;
    }

    public UtilsBD() {

    }

    private final String CREARBD = "CREATE DATABASE " + DB;
    private final String USERBD = "USE " + DB;
    private final String CREARTABLAEQUIPO = "CREATE TABLE IF NOT EXISTS `EQUIPO` (\n"
            + "  `TEAM_ID` int(11) NOT NULL AUTO_INCREMENT,\n"
            + "  `EQ_NOMBRE` varchar(40) NOT NULL,\n"
            + "  `ESTADIO` varchar(50) NOT NULL,\n"
            + "  `POBLACION` varchar(40) NOT NULL,\n"
            + "  `PROVINCIA` varchar(40) NOT NULL,\n"
            + "  `CP` varchar(5) DEFAULT NULL,\n"
            + "   PRIMARY KEY (`TEAM_ID`)\n"
            + ");";

    private final String CREARTABLAJUGADOR = "CREATE TABLE IF NOT EXISTS `JUGADORES` (\n"
            + "  `PLAYER_ID` int(11) NOT NULL AUTO_INCREMENT,\n"
            + "  `TEAM_ID` int(11) NOT NULL,\n"
            + "  `NOMBRE` varchar(40) NOT NULL,\n"
            + "  `DORSAL` int(11) NOT NULL,\n"
            + "  `EDAD` int(11) NOT NULL,\n"
            + "  PRIMARY KEY (`PLAYER_ID`),\n"
            + "  KEY `jugadores_ibfk_1` (`TEAM_ID`)\n"
            + ");";

    private boolean ejecutaSQL(String SQL) {
        conexion = DBConection.getConnection(DB);
        try (PreparedStatement ps = conexion.prepareStatement(SQL)) {
            ps.execute();
            System.out.println("Consulta ejecutada: \n" + SQL);
            return true;

        } catch (SQLException e) {
            DBConection.printSQLException(e);
        }
        return false;
    }

    private void crearDB(String dataBase) {
        conexion = DBConection.getConnection("");
        try (PreparedStatement ps = conexion.prepareStatement(USERBD)) {
            ps.execute();
            System.out.println("Base de datos " + dataBase + " creada");
            ps.executeUpdate("use " + dataBase + ";");
            System.out.println("Usando BD: " + dataBase);

            ps.execute(CREARTABLAEQUIPO);
            System.out.println("Tabla equipo creada");
            ps.execute(CREARTABLAJUGADOR);
            System.out.println("Tabla jugadores creada");
//            ejecutaSQL(USERBD);
        } catch (SQLException e) {
            DBConection.printSQLException(e);
        }

    }

    public void init() {
        crearDB(DB); 
    }
}
