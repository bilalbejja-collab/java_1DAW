/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.view;

import org.iesjaroso.daw.dao.IEmpleadoDAO;
import java.util.stream.Stream;
import org.iesjaroso.daw.model.Empleado;

/**
 *
 * @author franmatias
 */
public class EmpleadoView {

    public void showEmpleado(Empleado empleado) {
        System.out.printf("%-10s %-20s %-15s %.2f€   %10s \n",
                empleado.getDni(),
                empleado.getApellidos(),
                empleado.getNombre(),
                empleado.getSueldo(),
                empleado.getFechaContratacion()
        );

    }

    public void showEmpleado(IEmpleadoDAO empleados) {
        System.out.printf("%-10s %-20s %-15s %-10s %10s \n",
                "DNI",
                "Apellidos",
                "Nombre",
                "Sueldo",
                "Fecha de Contratación"
        );
        Stream.iterate(0, n -> n+1)
              .limit(80)
              .forEach((n) -> System.out.print("-"));
        System.out.println("");
        empleados.getAll().forEach((Empleado empleado) -> {
            EmpleadoView vista = new EmpleadoView();
            vista.showEmpleado(empleado);
        });
    }
}
