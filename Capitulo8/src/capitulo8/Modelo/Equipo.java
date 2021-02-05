/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo8.Modelo;

import capitulo8.conexion.DBConection;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author BILAL
 */
public class Equipo implements Serializable {

    private int id;
    private String nombre;
    private String estadio;
    private String poblacion;
    private String provincia;
    private String codigoPostal;
    private static Equipo instance = null;

    private List<Jugador> jugadores;

    public Equipo() {
        jugadores = new ArrayList<>();
    }

    public Equipo(int id, String nombre, String estadio) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.estadio = estadio;
    }

    public Equipo(int id, String nombre, String estadio, String poblacion, String provincia, String codigoPostal) {
        this(id, nombre, estadio);
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
    }

    public Equipo(int id, String nombre, String estadio, String poblacion, String provincia, String codigoPostal, List<Jugador> jugadores) {
        this(id, nombre, estadio, poblacion, provincia, codigoPostal);
        this.jugadores = jugadores;
    }

    public static Equipo getInstance() throws SQLException {
        if (instance == null) {
            instance = new Equipo();
        }
        return instance;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the estadio
     */
    public String getEstadio() {
        return estadio;
    }

    /**
     * @param estadio the estadio to set
     */
    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    /**
     * @return the poblacion
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * @param poblacion the poblacion to set
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * @return the codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @param codigoPostal the codigoPostal to set
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * @return the jugadores
     */
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * @param jugadores the jugadores to set
     */
    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void addJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void addJugadores(List<Jugador> jugadores) {
        this.jugadores.addAll(jugadores);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Equipo other = (Equipo) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Equipo{" + "id=" + id + ", nombre=" + nombre + ", estadio=" + estadio + ", poblacion=" + poblacion + ", provincia=" + provincia + ", codigoPostal=" + codigoPostal + '}';
    }

    public void showEquipo() {
        System.out.println(getNombre());
        Iterator<Jugador> it = jugadores.iterator();
        while (it.hasNext()) {
            Jugador jugador = it.next();
            System.out.print("\t" + jugador.getDorsal() + "  " + jugador.getNombre() + "\n");
        }
    }

    public Optional buscarJugadores(String key, String valor) {
        Jugador jugador = null;
        try (PreparedStatement ps
                = DBConection.getConnection().prepareStatement("SELECT * FROM Jugadores WHERE " + key + " = ?")) {
            ps.setString(1, valor);
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
    
    public Optional buscarEquipos(String key, String valor) {
        Equipo equipo = null;
        try (PreparedStatement ps
                = DBConection.getConnection().prepareStatement("SELECT * FROM Equipo WHERE " + key + " = ?")) {
            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    equipo = mapEquipo(rs);
                }
            }
        } catch (SQLException ex) {
            DBConection.printSQLException(ex);
        }
        return Optional.of(equipo);
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

    private Equipo mapEquipo(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo();
        equipo.setId(rs.getInt("team_id"));
        equipo.setNombre(rs.getString("eq_nombre"));
        equipo.setEstadio(rs.getString("estadio"));
        equipo.setPoblacion(rs.getString("poblacion"));
        equipo.setProvincia(rs.getString("provincia"));
        equipo.setCodigoPostal(rs.getString("cp"));
        return equipo;
    }
}
