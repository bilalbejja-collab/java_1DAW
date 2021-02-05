package org.iesjaroso.daw.controller;

import java.time.LocalDate;
import org.iesjaroso.daw.model.Empleado;
import org.iesjaroso.daw.view.EmpleadoView;

/**
 *
 * @author franmatias
 */
public class EmpleadoController {

    Empleado modelo;
    EmpleadoView vista;
    
    public EmpleadoController(Empleado modelo, EmpleadoView vista) {
        this.modelo = modelo;
        this.vista = vista;
    }
    
    public String getDNI() {
        return modelo.getDni();
    }
    
    public String getNombre() {
        return modelo.getNombre();
    }
    
    public String getApellidos() {
        return modelo.getApellidos();
    }
    
    public double getSueldo() {
        return modelo.getSueldo();
    }
    
    public LocalDate getFechaContratacion() {
        return modelo.getFechaContratacion();
    }
    
    public void setDni(String dni) {
        modelo.setDni(dni);
    }

    public void setNombre(String nombre) {
        modelo.setNombre(nombre);
    }

    public void setApellidos(String apellidos) {
        modelo.setApellidos(apellidos);
    }
    
    public void setSueldo(double sueldo){
        modelo.setSueldo(sueldo);
    }
    
    public void setFechaContratacion(LocalDate fecha){
        modelo.setFechaContratacion(fecha);
    }
    
    public void actualizarVista(){
        vista.showEmpleado(modelo);
    }
}
