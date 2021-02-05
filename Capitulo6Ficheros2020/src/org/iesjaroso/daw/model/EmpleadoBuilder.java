/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.model;

import java.time.LocalDate;


public class EmpleadoBuilder {

    private String dni;
    private String apellidos;
    private String nombre;
    private double sueldo;
    private LocalDate fechaContratacion;

    public EmpleadoBuilder(String dni) {
        this.dni = dni;
    }

    public EmpleadoBuilder setDni(String dni) {
        this.dni = dni;
        return this;
    }

    public EmpleadoBuilder setApellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public EmpleadoBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public EmpleadoBuilder setSueldo(double sueldo) {
        this.sueldo = sueldo;
        return this;
    }

    public EmpleadoBuilder setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
        return this;
    }

    public Empleado createEmpleado() {
        return new Empleado(dni, apellidos, nombre, sueldo, fechaContratacion);
    }
    
}
