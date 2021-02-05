/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author franmatias
 */
public class Empleado implements Serializable {
    private String dni;
    private String apellidos;
    private String nombre;
    private double sueldo;
    private LocalDate fechaContratacion;

    public Empleado(){}
    
    public Empleado(String dni){
        this.dni=dni;
    }

    public Empleado(String dni, String apellidos, String nombre, double sueldo, LocalDate fechaContratacion) {
        this(dni);
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.fechaContratacion = fechaContratacion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.dni);
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
        final Empleado other = (Empleado) obj;
        return Objects.equals(this.dni, other.dni);
    }

    @Override
    public String toString() {
        return "Empleado{" + "dni=" + dni + ", apellidos=" + apellidos + ", nombre=" + nombre + ", sueldo=" + sueldo + ", fechaContratacion=" + fechaContratacion + '}';
    }
    
    
    
}
