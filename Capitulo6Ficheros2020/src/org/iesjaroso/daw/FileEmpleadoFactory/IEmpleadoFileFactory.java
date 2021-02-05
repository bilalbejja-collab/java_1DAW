/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.FileEmpleadoFactory;

import java.util.List;

/**
 *
 * @author franmatias
 * @param <T>
 */
public interface IEmpleadoFileFactory<T> {
    public List<T> abrir(String fichero);
    public void guardar(String fichero, List<T> datos);
}
