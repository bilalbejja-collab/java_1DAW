package org.iesjaroso.daw.controller;

import org.iesjaroso.daw.dao.IEmpleadoDAO;
import org.iesjaroso.daw.view.EmpleadoView;


public class EmpleadosController {

    private IEmpleadoDAO modelo;
    private final EmpleadoView vista;

    public EmpleadosController(IEmpleadoDAO modelo, EmpleadoView vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public IEmpleadoDAO getModelo() {
        return modelo;
    }

    public void setModelo(IEmpleadoDAO modelo) {
        this.modelo = modelo;
    }
    
    public void actualizarVista() {
        vista.showEmpleado(modelo);
    }
}
