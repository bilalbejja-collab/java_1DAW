/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.service;

//import dao.IEmpleadoDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import org.iesjaroso.daw.model.Empleado;

/**
 *
 * @author franmatias
 */
public class EmpleadoService {

    //IEmpleadoDAO empleadoDao;
    EmpleadoQueryService queryService;

    public EmpleadoService() throws IOException {
        queryService = new EmpleadoQueryServiceImpl();
    }

    public Collection<Empleado> findByDNI(String... dni) {
        Collection<Empleado> result = queryService.containsDNI(dni).exec();
        return result;
    }

    public Collection<Empleado> findBySurname(String... surname) {
        return queryService.containsSurname(surname).exec();
    }

    public Collection<Empleado> findByName(String... name) {
        return queryService.containsName(name).exec();
    }

    public Collection<Empleado> findBybetweenSurnameSize(int min, int max) {
        return queryService.betweenSurnameSize(min, max).exec();
    }

    public Collection<Empleado> findBySueldo(double min, double max) {
        return queryService.betweenSueldo(min, max).exec();
    }

    public Collection<Empleado> findByFechaContratacion(LocalDate ini, LocalDate fin) {
        return queryService.betweenFechaContratacion(ini, fin).exec();
    }

    public void cleanQuery() {
        queryService.clearQuery();
    }

}
