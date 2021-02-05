/**
 * EjemploSelect.java, MODIFICADO Y AUMENTADO PARA EL USO DE PREPAREDSTATEMENT
 */
package org.iesjaroso.daw.capitulo8tutorialdaobasic.consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.iesjaroso.daw.capitulo8tutorialdaobasic.singleton.DBConnection;

public class EjemploPreparedStatement {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String sql = "SELECT * FROM empleados WHERE sueldo >= ? ORDER BY fecha_nacimiento";
        // Creamos el objeto para enviar sentencias
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            ps.setFloat(1, 1200.0f);

            // El procesamiento se hace igual que en el ejemplo anterior
            // Ejecutamos la sentencia y recogemos
            // datos de la consulta
            // El procesamiento se hace igual que en el ejemplo anterior
            while (rs.next()) {
                String nombre = rs.getString("nombre"); // equivalente a rs.getString(1);
                String apellidos = rs.getString("apellidos"); // equivalente a rs.getString(2);
                java.sql.Date fechaNacimiento = rs.getDate("fecha_nacimiento"); // equivalente a rs.getDate(3);
                LocalDate fechaNacimientoLD = fechaNacimiento.toLocalDate();
                float sueldo = rs.getFloat("sueldo"); // equivalente a rs.getFloat(4);
                System.out.printf("%s %s\t\t (%s) - %.2f� %n", nombre, apellidos,
                        fechaNacimientoLD.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)), sueldo);
            }
            // Cerramos ResultSet y Statement

        } catch (SQLException e) {
            e.getMessage();
        }

    }

}
