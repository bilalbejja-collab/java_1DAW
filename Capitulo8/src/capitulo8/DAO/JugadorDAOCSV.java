/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo8.DAO;

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
import static java.util.stream.Collectors.toList;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 *
 * @author BILAL
 */
public class JugadorDAOCSV implements IJugadorDAO {

    int player_id;
    int team_id;
    String nombre;
    int dorsal;
    int edad;
    Connection con;
    private final String SQLGET = "SELECT * FROM Jugadores WHERE player_id = ?";
    private final String SQLGETALL = "SELECT * FROM jugadores";
    private final String SQLSAVE = "INSERT INTO jugadores "
            + "(TEAM_ID, NOMBRE, DORSAL, EDAD)"
            + "VALUES (?, ?, ?, ?);";
    private final String SQLUPDATE = "UPDATE jugadores SET "
            + "team_id = ?, nombre = ?, dorsal = ?, edad = ? "
            + "WHERE player_id = ? ;";
    private final String SQLDELETE = "DELETE FROM jugadores WHERE player_id = ?";

    private Connection conexion = null;
    private static JugadorDAOCSV instance = null;

    public JugadorDAOCSV() throws SQLException {
        conexion = DBConection.getConnection();
    }

    public static JugadorDAOCSV getInstance() throws SQLException {
        if (instance == null) {
            instance = new JugadorDAOCSV();
        }
        return instance;
    }

    @Override
    public List<Jugador> leerJugadores(String fichero) {
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

    private static List<Jugador> CSVToJugador(List<String> datos) {
        List<Jugador> jugadores = new ArrayList();
        try {
            datos.remove(0);
            datos.stream().map((registro) -> registro.split(";"))
                    .map((campos) -> {
                        int player_id = Integer.parseInt(campos[0]);
                        int team_id = Integer.parseInt(campos[1]);
                        String nombre = campos[2];
                        int dorsal = Integer.parseInt(campos[3]);
                        int edad = Integer.parseInt(campos[4]);
                        Jugador j = new Jugador(player_id, team_id, nombre, dorsal, edad);
                        return j;
                    }).forEachOrdered((j) -> {
                if (j != null) {
                    jugadores.add(j);
                }
            });
        } catch (NumberFormatException | DateTimeParseException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return jugadores;
    }

    @Override
    public Optional get(int id) {
        Jugador jugador = null;
        try (PreparedStatement ps = conexion.prepareStatement(SQLGET)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    jugador = mapJugador(rs);
                }
            }
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
        return Optional.of(jugador);
    }

    private Jugador mapJugador(ResultSet rs) throws SQLException {
        Jugador jugador = new Jugador();
        jugador.setId(rs.getInt("player_id"));
        jugador.setIdEquipo(rs.getInt("TEAM_ID"));
        jugador.setNombre(rs.getString("nombre"));
        jugador.setDorsal(rs.getInt("dorsal"));
        jugador.setEdad(rs.getInt("edad"));
        return jugador;
    }

    public Optional<List<Jugador>> getAll(int teamID) {
        Optional<List<Jugador>> jugadores = getAll();
        return Optional.of(jugadores.get()
                .stream()
                .filter(jugador -> jugador.getIdEquipo() == teamID)
                .collect(toList())
        );
    }

    @Override
    public Optional getAll() {
        List<Jugador> jugadores = new ArrayList();
        try (PreparedStatement ps = conexion.prepareStatement(SQLGETALL);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                jugadores.add(mapJugador(rs));
            }

        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
        return Optional.of(jugadores);
    }

    @Override
    public boolean save(Jugador jugador) {
        try (PreparedStatement ps = conexion.prepareStatement(SQLSAVE)) {
            //(TEAM_ID, NOMBRE, DORSAL, EDAD)
            ps.setInt(1, jugador.getIdEquipo());
            ps.setString(2, jugador.getNombre());
            ps.setInt(3, jugador.getDorsal());
            ps.setInt(4, jugador.getEdad());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
        return false;
    }

    @Override
    public boolean update(Jugador jugador) {
        try (PreparedStatement ps = conexion.prepareStatement(SQLUPDATE)) {
            //(TEAM_ID, NOMBRE, DORSAL, EDAD)
            ps.setInt(1, jugador.getIdEquipo());
            ps.setString(2, jugador.getNombre());
            ps.setInt(3, jugador.getDorsal());
            ps.setInt(4, jugador.getEdad());
            ps.setInt(5, jugador.getId());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
        return false;
    }

    @Override
    public boolean delete(Jugador jugador) {
        try (PreparedStatement ps = conexion.prepareStatement(SQLDELETE)) {
            ps.setInt(1, jugador.getId());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
        return false;
    }

    /* 
    Uso de RowSet
     */
    public void actualizarEdad(int cuanto) {

        final String URL = DBConection.getURLDB() + DBConection.getDB();

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(URL);
            rowSet.setUsername(DBConection.getUSERDB());
            rowSet.setPassword(DBConection.getPASSWDDB());
            rowSet.setCommand(SQLGETALL);
            rowSet.execute();
            rowSet.beforeFirst();
            while (rowSet.next()) {
                rowSet.updateInt("edad", rowSet.getInt("edad") + cuanto);
                rowSet.updateRow();
            }
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
    }
}
