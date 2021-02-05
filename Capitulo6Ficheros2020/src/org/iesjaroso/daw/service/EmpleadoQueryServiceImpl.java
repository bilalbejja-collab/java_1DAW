/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.service;

import org.iesjaroso.daw.dao.EmpleadoDAO;
import org.iesjaroso.daw.dao.IEmpleadoDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.iesjaroso.daw.model.Empleado;

/**
 *
 * @author franmatias
 */
public class EmpleadoQueryServiceImpl implements EmpleadoQueryService {

    IEmpleadoDAO daoEmpleado;
    private Predicate<Empleado> predicate;

    public EmpleadoQueryServiceImpl() throws IOException {
        daoEmpleado = EmpleadoDAO.getInstance();
        daoEmpleado.deleteAll();
        daoEmpleado.abrir("ficheros/empleados.csv");
        System.out.println(daoEmpleado.getAll().size()+ " datos léidos del fichero");
        // el predicate esta null pero cuando pornemos .exec() se tendrá valor
        predicate = null;
    }

    
    @Override
    public Collection<Empleado> exec() {

        return daoEmpleado.getAll()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public EmpleadoQueryService containsDNI(String... dni) {
        Predicate<Empleado> pAnyDNI = (empleado -> Arrays.stream(dni).allMatch(empleado.getDni()::contains));
        predicate = (predicate == null) ? pAnyDNI : predicate.and(pAnyDNI);
        return this;
    }

    @Override
    public EmpleadoQueryService containsSurname(String... surname) {
        Predicate<Empleado> pAnySurname = (empleado -> Arrays.stream(surname).allMatch(empleado.getApellidos()::contains));
        predicate = (predicate == null) ? pAnySurname : predicate.and(pAnySurname);

        return this;
    }

    @Override
    public EmpleadoQueryService containsName(String... name) {
        Predicate<Empleado> pAnyName = (empleado -> Arrays.stream(name).allMatch(empleado.getNombre()::contains));
        predicate = (predicate == null) ? pAnyName : predicate.and(pAnyName);

        return this;
    }

    @Override
    public EmpleadoQueryService betweenSurnameSize(int min, int max) {
        Predicate<Empleado> pBetweenSurnameSize = (empleado -> {
            return empleado.getApellidos().length() >= min
                    && empleado.getApellidos().length() <= max;
        });

        predicate = (predicate == null) ? pBetweenSurnameSize : predicate.and(pBetweenSurnameSize);

        return this;
    }

    @Override
    public EmpleadoQueryService betweenSueldo(double min, double max) {
        
        Predicate<Empleado> pBetweenSueldo = (empleado -> {
            return empleado.getSueldo() >= min && empleado.getSueldo() <= max;
        });
        predicate = (predicate == null) ? pBetweenSueldo : predicate.and(pBetweenSueldo);
        return this;
    }

    @Override
    public EmpleadoQueryService betweenFechaContratacion(LocalDate ini, LocalDate fin) {
        Predicate<Empleado> pBetweenDates = (empleado -> {

            return empleado.getFechaContratacion().isAfter(ini) && empleado.getFechaContratacion().isBefore(fin);
        });

        predicate = (predicate == null) ? pBetweenDates : predicate.and(pBetweenDates);

        return this;
    }
    
    @Override
    public void clearQuery(){
        predicate = null;
    }

}
