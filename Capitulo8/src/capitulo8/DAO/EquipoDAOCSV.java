/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo8.DAO;

import capitulo8.Modelo.Equipo;
import capitulo8.Modelo.Jugador;
import capitulo8.conexion.DBConection;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author BILAL
 */
public class EquipoDAOCSV implements IEquipoDAO {

    private final String SQLGET = "SELECT * FROM equipo WHERE team_id = ?";
    private final String SQLGETALL = "SELECT * FROM Equipo";
    private final String SQLSAVE = "INSERT INTO equipo "
            + "(EQ_NOMBRE, ESTADIO, POBLACION, PROVINCIA, CP)"
            + "VALUES (?, ?, ?, ?, ?);";
    private final String SQLUPDATE = "UPDATE equipo "
            + "SET eq_nombre = ?, estadio = ?, poblacion = ?, provincia = ?, cp = ? "
            + "WHERE team_id = ? ;";
    private final String SQLDELETE = "DELETE FROM equipo WHERE team_id = ?";

    private Connection conexion = null;
    private static EquipoDAOCSV instance = null;

    public EquipoDAOCSV() throws SQLException {
        conexion = DBConection.getConnection();
    }

    public static EquipoDAOCSV getInstance() throws SQLException {
        if (instance == null) {
            instance = new EquipoDAOCSV();
        }
        return instance;
    }

    @Override
    public List<Equipo> leerEquipos(String fichero) {
        return CSVToJugador(leer(fichero));
    }

    private static List<String> leer(String nombreFichero) {
        Path ruta = Paths.get(nombreFichero);
        Charset charset = Charset.forName("UTF-8");
        List<String> datos = null;
        try {
            datos = Files.readAllLines(ruta, charset);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            System.out.println(" Fichero " + ruta.getFileName() + " leído correctamente.");
        }

        return datos;
    }

    private static List<Equipo> CSVToJugador(List<String> datos) {
        List<Equipo> equipos = new ArrayList();
        try {
            datos.remove(0);
            datos.stream().map((registro) -> registro.split(";"))
                    .map((campos) -> {
                        int id = Integer.parseInt(campos[0]);
                        String nombre = campos[1];
                        String estadio = campos[2];
                        String poblacion = campos[3];
                        String provincia = campos[4];
                        String codigoPostal = campos[5];
                        Equipo j = new Equipo(id, nombre, estadio, poblacion, provincia, codigoPostal);
                        return j;
                    }).forEachOrdered((e) -> {
                if (e != null) {
                    equipos.add(e);
                }
            });
        } catch (NumberFormatException | DateTimeParseException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return equipos;
    }

    private void addAllJugadoresToEquipo(Equipo equipo) {
        try {
            Optional<List<Jugador>> jugadores = JugadorDAOCSV.getInstance().getAll(equipo.getId());
            equipo.addJugadores(jugadores.get());
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
    }

    private Equipo mapEquipo(ResultSet rs) throws SQLException {
        // TEAM_ID, EQ_NOMBRE, ESTADIO, POBLACION, PROVINCIA, CP
        Equipo equipo = new Equipo();
        equipo.setId(rs.getInt("team_id"));
        equipo.setNombre(rs.getString("eq_nombre"));
        equipo.setEstadio(rs.getString("estadio"));
        equipo.setPoblacion(rs.getString("poblacion"));
        equipo.setProvincia(rs.getString("provincia"));
        equipo.setCodigoPostal(rs.getString("cp"));
        return equipo;
    }

    @Override
    public Optional get(int id) {
        Equipo equipo = null;

        try (PreparedStatement ps = conexion.prepareStatement(SQLGET)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    equipo = mapEquipo(rs);
                    addAllJugadoresToEquipo(equipo);
                }
            }
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
        return Optional.of(equipo);
    }

    @Override
    public Optional getAll() {
        List<Equipo> equipos = new ArrayList<>();

        try (PreparedStatement ps = conexion.prepareStatement(SQLGETALL);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                equipos.add(mapEquipo(rs));
            }
            equipos.forEach((equipo) -> {
                addAllJugadoresToEquipo(equipo);
            });

        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }

        return Optional.of(equipos);
    }

    @Override
    public boolean save(Equipo equipo) {
        try (PreparedStatement ps = conexion.prepareStatement(SQLSAVE)) {
            // EQ_NOMBRE, ESTADIO, POBLACION, PROVINCIA, CP

            ps.setString(1, equipo.getNombre());
            ps.setString(2, equipo.getEstadio());
            ps.setString(3, equipo.getPoblacion());
            ps.setString(4, equipo.getProvincia());
            ps.setString(5, equipo.getCodigoPostal());

            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
        return false;
    }

    @Override
    public boolean update(Equipo equipo) {
        try (PreparedStatement ps = conexion.prepareStatement(SQLUPDATE)) {
            //eq_nombre = ?, estadio = ?, poblacion = ?"+
            //"provincia = ?, cp = ? WHERE team_id = ? ;";

            ps.setString(1, equipo.getNombre());
            ps.setString(2, equipo.getEstadio());
            ps.setString(3, equipo.getPoblacion());
            ps.setString(4, equipo.getProvincia());
            ps.setString(5, equipo.getCodigoPostal());
            ps.setInt(6, equipo.getId());
            System.out.println(ps.toString());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
        return false;
    }

    @Override
    public boolean delete(Equipo equipo) {
        try (PreparedStatement ps = conexion.prepareStatement(SQLDELETE)) {
            ps.setInt(1, equipo.getId());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
        return false;
    }

}
