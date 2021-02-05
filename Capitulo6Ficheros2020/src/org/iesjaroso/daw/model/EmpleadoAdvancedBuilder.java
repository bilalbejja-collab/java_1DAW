/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.model;

import java.time.LocalDate;
import java.util.function.Consumer;

/**
 *
 * @author franmatias
 */
public class EmpleadoAdvancedBuilder {

    private String dni;
    private String apellidos;
    private String nombre;
    private double sueldo;
    private LocalDate fechaContratacion;

    /*
    Forma de uso:
    Empleado empleado = new EmpleadoAdvancedBuilder()
            .with($ -> {
                $.dni = "238003424F";
                $.apellidos = "Martin López";
                $.nombre = "Ana";
                $.sueldo = 2500;
                $.fechaContratacion = LocalDate.of(2010, 4, 14);
            }).createEmpleado();
     */
    public EmpleadoAdvancedBuilder with(
            Consumer<EmpleadoAdvancedBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public Empleado createEmpleado() {
        return new Empleado(dni,
                apellidos,
                nombre,
                sueldo,
                fechaContratacion);
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
    
    

}
