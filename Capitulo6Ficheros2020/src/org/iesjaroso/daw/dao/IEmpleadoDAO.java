/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.dao;

import java.util.List;
import org.iesjaroso.daw.model.Empleado;

/**
 *
 * @author BILAL
 */
public interface IEmpleadoDAO extends DAO<String, Empleado> {

    public List<Empleado> abrir(String fichero);
    public void guardar(String fichero, List<Empleado> datos);
}
