/**
 *
 */
package org.iesjaroso.daw.capitulo8tutorialdaobasic.consultas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.iesjaroso.daw.capitulo8tutorialdaobasic.singleton.DBConnection;

public class EjemploSelect {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try (Connection con = DBConnection.getConnection()) {
            // Enviamos una sentencia para rescatar todos los
            // datos de una tabla
            String sql = "SELECT * FROM empleados";
            // Creamos el objeto para enviar sentencias
            // Enviamos una sentencia para rescatar todos los datos de una tabla
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    String nombre = rs.getString("nombre"); //equivalente a rs.getString(1);
                    String apellidos = rs.getString("apellidos"); // equivalente a rs.getString(2);
                    java.sql.Date fechaNacimiento = rs.getDate("fecha_nacimiento"); //equivalente a rs.getDate(3);
                    LocalDate fechaNacimientoLD = fechaNacimiento.toLocalDate();
                    float sueldo = rs.getFloat("sueldo"); //equivalente a rs.getFloat(4);
                    System.out.printf("%s %s\t\t (%s) - %.2f� %n", nombre, apellidos,
                            fechaNacimientoLD.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                            sueldo);
                }

            }

        } catch (SQLException e) {
            e.getMessage();
        }

    }

}
