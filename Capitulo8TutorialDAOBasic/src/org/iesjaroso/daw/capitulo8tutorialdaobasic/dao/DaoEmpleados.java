/**
 * EJEMPLO DE IMPLEMENTACI�N DEL PATR�N DAO CON LA TABLA PERSONAS
 * OFRECEMOS LOS M�TODOS
 *
 * - insert
 * - findAll
 * - findByPk
 * - update
 * - delete
 *
 * TAMBI�N IMPLEMENTAMOS EL PATR�N SINGLETON
 *
 */
package org.iesjaroso.daw.capitulo8tutorialdaobasic.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.iesjaroso.daw.capitulo8tutorialdaobasic.singleton.*;

/**
 * @author Openwebinars
 *
 */
public class DaoEmpleados implements DAO<Empleado> {

    private Connection con = null;

    private static DaoEmpleados instance = null;

    private DaoEmpleados() throws SQLException {
        con = DBConnection.getConnection();
    }

    public static DaoEmpleados getInstance() throws SQLException {

        if (instance == null) {
            instance = new DaoEmpleados();
        }

        return instance;
    }

    /**
     *
     * @param e
     * @throws SQLException
     */
    @Override
    public void insert(Empleado e) throws SQLException {
        String sql = "INSERT INTO empleados (nombre, apellidos, fecha_nacimiento, sueldo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setDate(3, Date.valueOf(e.getFechaNacimiento()));
            ps.setFloat(4, e.getSueldo());

            ps.executeUpdate();
        }

    }


    /**
     *
     * @return @throws SQLException
     */
    @Override
    public List<Empleado> findAll() throws SQLException {

        List<Empleado> result;
        String sql = "SELECT * FROM empleados";

        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            System.out.println(ps);
            result = null;
            while (rs.next()) {
                if (result == null) {
                    result = new ArrayList<>();
                }

                result.add(new Empleado(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellidos"),
                        rs.getDate("fecha_nacimiento").toLocalDate(), rs.getFloat("sueldo")));
            }
        }

        return result;
    }

    /**
     * Busca un empleado por su id.
     *
     * @param id clave primaria de empleado
     * @return un empleado si coincide su clave primaria o null si no existe
     * @throws SQLException
     */
    @Override
    public Empleado findByPk(int id) throws SQLException {

        Empleado result;
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM empleados WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            result = null;
            if (rs.next()) {
                result = new Empleado(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellidos"),
                        rs.getDate("fecha_nacimiento").toLocalDate(), rs.getFloat("sueldo"));
            }
            rs.close();
        }

        return result;

    }

    /**
     * Elimina un empleado pasando un objeto
     *
     * @param e
     * @throws SQLException
     */
    @Override
    public void delete(Empleado e) throws SQLException {
        delete(e.getId());
    }

    /**
     * Elimina un empleado con su id
     *
     * @param id
     * @throws SQLException
     */
    public void delete(int id) throws SQLException {

        if (id <= 0) {
            return;
        }

        try (PreparedStatement ps = con.prepareStatement("DELETE FROM empleados WHERE id = ?")) {
            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }

    /**
     * Actualiza un empleado
     *
     * @param e
     * @throws SQLException
     */
    @Override
    public void update(Empleado e) throws SQLException {

        if (e.getId() == 0) {
            return;
        }
        String sql = "UPDATE empleados SET "
                + "nombre = ?, "
                + "apellidos = ?, "
                + "fecha_nacimiento = ?, "
                + "sueldo = ? "
                + "WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setDate(3, Date.valueOf(e.getFechaNacimiento()));
            ps.setFloat(4, e.getSueldo());
            ps.setInt(5, e.getId());

            ps.executeUpdate();
        }

    }

}
