/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo8.Modelo;

import java.io.Serializable;

/**
 *
 * @author BILAL
 */
public class Jugador implements Serializable {

    private int id;
    private String nombre;
    private int dorsal;
    private int edad;
    private int idEquipo;

    public Jugador() {
    }

    public Jugador(String nombre, int dorsal, int edad) {
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.edad = edad;
    }

    public Jugador(int id, String nombre, int dorsal, int edad) {
        this(nombre, dorsal, edad);
        this.id = id;
    }

    public Jugador(int id, int idEquipo, String nombre, int dorsal, int edad) {
        this(id, nombre, dorsal, edad);
        this.idEquipo = idEquipo;
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
     * @return the dorsal
     */
    public int getDorsal() {
        return dorsal;
    }

    /**
     * @param dorsal the dorsal to set
     */
    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    /**
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Jugador other = (Jugador) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Jugador{" + "id=" + id + ", idEquipo=" + idEquipo + ", nombre=" + nombre + ", dorsal=" + dorsal + ", edad=" + edad + '}';
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

}
