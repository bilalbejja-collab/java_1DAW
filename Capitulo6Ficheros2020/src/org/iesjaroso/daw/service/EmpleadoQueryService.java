/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.service;

import java.time.LocalDate;
import java.util.Collection;
import org.iesjaroso.daw.model.Empleado;

/**
 * Establecemos todas las busquedas que deseamos ofrecer en el servicio. 
 * El servicio es un 
 */
public interface EmpleadoQueryService {

    public Collection<Empleado> exec(); //Ejecuta la búsqueda

    public EmpleadoQueryService containsDNI(String... dni);
    public EmpleadoQueryService containsSurname(String... surname);
    public EmpleadoQueryService containsName(String... name);
    public EmpleadoQueryService betweenSurnameSize(int min, int max);
    public EmpleadoQueryService betweenSueldo(double min, double max);
    public EmpleadoQueryService betweenFechaContratacion(LocalDate ini, LocalDate fin);
    public void clearQuery();

}
