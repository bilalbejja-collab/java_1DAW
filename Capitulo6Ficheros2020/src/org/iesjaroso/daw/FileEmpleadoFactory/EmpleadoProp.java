/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.FileEmpleadoFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.iesjaroso.daw.model.Empleado;
import org.iesjaroso.daw.model.EmpleadoBuilder;

/**
 *
 * @author BILAL
 */
public class EmpleadoProp implements IEmpleadoFileFactory<Empleado> {

    //Patron Singleton 
    private static EmpleadoProp instance;

    public static EmpleadoProp getInstance() throws IOException {
        if (instance == null) {
            instance = new EmpleadoProp();
        }
        return instance;
    }

    private EmpleadoProp() {
    }

    /**
     * Almacena en .properties objetos del tipo Lista de Empleados
     *
     * @param nombreFichero
     * @param datos
     */
    @Override
    public void guardar(String nombreFichero, List<Empleado> datos) {
        try (FileWriter writer = new FileWriter(nombreFichero)) {
            writer.append("empleados=");
            for (Empleado empeleado : datos) {
                writer.write(empeleado.getDni() + "," + empeleado.getApellidos() + ","
                        + empeleado.getNombre() + "," + empeleado.getSueldo() + ","
                        + empeleado.getFechaContratacion() + ";");
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Abre un fichero properties con el contenido de una lista de empleados
     *
     * @param nombreFichero
     * @return
     */
    @Override
    public List<Empleado> abrir(String nombreFichero) {
        List<Empleado> datos = new ArrayList();
        Properties p = new Properties();
        try {
            p.load(new FileReader(nombreFichero));
            String[] empleados = p.getProperty("empleados").split(";");
            int numEmpleados = empleados.length;
            for (int i = 0; i < numEmpleados; i++) {
                datos.add(new Empleado(empleados[i].split(",")[0],
                        empleados[i].split(",")[1],
                        empleados[i].split(",")[2],
                        Double.parseDouble(empleados[i].split(",")[3]),
                        LocalDate.parse(empleados[i].split(",")[4])
                ));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return datos;
    }
//    @Override
//    public List<Empleado> abrir(String fichero) {
//        return PROPERTIESToEmpleado(leer(fichero));
//    }
//
//    private static List<Empleado> PROPERTIESToEmpleado(List<String> datos) {
//        List<Empleado> empleados = new ArrayList();
//        Properties p = new Properties();
//
//        try {
//            datos.stream().map((e) -> e.split(","))
//                    .map((campos) -> {
////                        if (campos.length != 5) {
////                            return null;
////                        }
//                        String dni = campos[0];
//                        String apellidos = campos[1];
//                        String nombre = campos[2];
//                        double sueldo = Double.parseDouble(campos[3]);
//                        LocalDate fecha = LocalDate.parse(campos[4]);
//                        Empleado empleado = new EmpleadoBuilder(dni)
//                                .setApellidos(apellidos)
//                                .setNombre(nombre)
//                                .setSueldo(sueldo)
//                                .setFechaContratacion(fecha)
//                                .createEmpleado();
//
//                        return empleado;
//
//                    }).forEachOrdered((empleado) -> {
//                if (empleado != null) {
//                    empleados.add(empleado);
//                }
//            });
//        } catch (NumberFormatException | DateTimeParseException ex) {
//            System.out.println("Error " + ex.getMessage());
//        }
//        return empleados;
//    }
//
//    private static List<String> leer(String nombreFichero) {
//        List<String> datos = new ArrayList();
//        Properties p = new Properties();
//        try {
//            p.load(new FileReader(nombreFichero));
//            datos.addAll(Arrays.asList(p.getProperty("empleados").split(";")));
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return datos;
//    }
}
