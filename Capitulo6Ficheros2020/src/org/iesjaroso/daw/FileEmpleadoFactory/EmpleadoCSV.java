/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.FileEmpleadoFactory;

import org.iesjaroso.daw.model.Empleado;
import org.iesjaroso.daw.model.EmpleadoBuilder;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author franmatias
 */
public class EmpleadoCSV implements IEmpleadoFileFactory<Empleado> {

    private static EmpleadoCSV instance = null;

    public static EmpleadoCSV getInstance() throws IOException {
        if (instance == null) {
            instance = new EmpleadoCSV();
        }
        return instance;
    }

    private EmpleadoCSV() {
    }

    @Override
    public List<Empleado> abrir(String fichero) {
        return CSVToEmpleado(leer(fichero));
    }

    private static List<Empleado> CSVToEmpleado(List<String> datos) {
        List<Empleado> empleados = new ArrayList();
        try {
            datos.stream().map((registro) -> registro.split(";"))
                    .map((campos) -> {
                        if (campos.length != 5) {
                            return null;
                        }
                        String dni = campos[0];
                        String apellidos = campos[1];
                        String nombre = campos[2];
                        double sueldo = Double.parseDouble(campos[3]);
                        LocalDate fecha = LocalDate.parse(campos[4]);
                        Empleado empleado = new EmpleadoBuilder(dni)
                                .setApellidos(apellidos)
                                .setNombre(nombre)
                                .setSueldo(sueldo)
                                .setFechaContratacion(fecha)
                                .createEmpleado();

                        return empleado;

                    }).forEachOrdered((empleado) -> {
                if (empleado != null) {
                    empleados.add(empleado);
                }
            });
        } catch (NumberFormatException | DateTimeParseException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return empleados;
    }

    private static List<String> leer(String nombreFichero) {
        Path ruta = Paths.get(nombreFichero);
        Charset charset = Charset.forName("UTF-8");
        List<String> datos = null;
        try {
            datos = Files.readAllLines(ruta, charset);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            System.out.println(" Fichero " + ruta.getFileName() + " leído correctamente.");
        }

        return datos;
    }

    @Override
    public void guardar(String fichero, List<Empleado> empleados) {
        escribir(fichero, empleadoToCSV(empleados));
    }

    private static List<String> empleadoToCSV(List<Empleado> empleados) {
        StringBuilder sb = new StringBuilder();
        List<String> datos = new ArrayList();
        empleados.forEach((empleado) -> {
            sb.append(empleado.getDni()).append(";")
                    .append(empleado.getApellidos()).append(";")
                    .append(empleado.getNombre()).append(";")
                    .append(empleado.getSueldo()).append(";")
                    .append(empleado.getFechaContratacion()).append("\n");
        });
        
        sb.delete(sb.lastIndexOf("\n"), sb.length()); //Borra el último salto de línea
        datos.add(sb.toString());
        return datos;
    }

    private static void escribir(String nombreFichero, List<String> datos) {
        Path ruta = Paths.get(nombreFichero);
        Charset charset = Charset.forName("UTF-8");
        //datos.forEach(System.err::println);
        try {
            Files.write(ruta, datos, charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.println(" Fichero " + nombreFichero + " escrito correctamente. ");
    }

    public List<Empleado> leerStream(String nombreFichero) {
        List<Empleado> datos;
        try {
            datos = Files.lines(Paths.get(nombreFichero))
                    .skip(1)
                    .map((String line) -> {
                        String[] values = line.split(";");
                        return new EmpleadoBuilder(values[0])
                                .setApellidos(values[1])
                                .setNombre(values[2])
                                .setSueldo(Double.parseDouble(values[3]))
                                .setFechaContratacion(LocalDate.parse(values[4]))
                                .createEmpleado();

                    }).collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error de lectura del fichero de datos: " + nombreFichero);
            System.exit(-1);
        }

        return new ArrayList();
    }
}
