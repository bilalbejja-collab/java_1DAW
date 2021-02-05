/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.utils;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import org.iesjaroso.daw.model.Empleado;

/**
 *
 * @author BILAL
 */
public class EmpleadoRandom {

    //Propiedades
    private static final String CONFIGURATIONFILE = "ficheros/empleadornd.properties";
    private final static List<String> nombresMujer = new LinkedList<>();
    private final static List<String> nombresHombre = new LinkedList<>();
    private final static List<String> apellidos = new LinkedList<>();
    private static int minSueldo;
    private static int maxSueldo;
    private static LocalDate fechaIni;
    private static LocalDate fechaFin;

    public static enum Sex {
        HOMBRE, MUJER
    }

    /**
     * leer el fichero .properties
     */
    static {
        Properties p = new Properties();
        try {
            p.load(new FileReader(CONFIGURATIONFILE));
            int numNombHombres = p.getProperty("nombresMasculinos").split(", ").length;
            int numNombMujeres = p.getProperty("nombresFemeninos").split(", ").length;
            int numApellidos = p.getProperty("apellidos").split(", ").length;

            nombresHombre.addAll(Arrays.asList(p.getProperty("nombresMasculinos").split(", ")));
            nombresMujer.addAll(Arrays.asList(p.getProperty("nombresFemeninos").split(", ")));
            apellidos.addAll(Arrays.asList(p.getProperty("apellidos").split(", ")));
            minSueldo = Integer.parseInt(p.getProperty("sueldoMin"));
            maxSueldo = Integer.parseInt(p.getProperty("sueldoMax"));
            fechaIni = LocalDate.parse(p.getProperty("fechaIni"));
            fechaFin = LocalDate.parse(p.getProperty("fechaFin"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param min
     * @param max
     * @return
     */
    private static int azar(int min, int max) {
        Random r = new Random();
        return (int) (min + (max - min) * r.nextDouble());
    }

    /**
     *
     * @param max
     * @return
     */
    private static int azar(int max) {
        Random r = new Random();
        return (int) (max * r.nextDouble());
    }

    /**
     *
     * @return
     */
    private static String nombreMasculinoRND() {
        return nombresHombre.get(azar(nombresHombre.size()));
    }

    private static String nombreFemeninoRND() {
        return nombresMujer.get(azar(nombresMujer.size()));
    }

    private static String nombre() {
        List<String> todosNombre = nombresHombre;
        todosNombre.addAll(nombresMujer);
        return todosNombre.get(azar(todosNombre.size()));
    }

    private static String apellidoRND() {
        return apellidos.get(azar(apellidos.size()));
    }

    private static int sueldoRND() {
        return azar(minSueldo, maxSueldo);
    }

    private static LocalDate fechaRND() {
        return LocalDate.of(
                azar(fechaIni.getYear(), fechaFin.getYear()),
                azar(fechaIni.getMonthValue(), fechaFin.getMonthValue()),
                azar(1, fechaFin.getDayOfMonth()));
    }

    private static String dniRND() {
        int num = azar(10000000, 99999999);
        char letra = "ABCDEFGHIJKLMNOPQRSTUVWXY".charAt(num % 23);
        return num + "" + (char) letra;
    }

    public static Empleado createEmpleado() {
        return new Empleado("", apellidoRND(), nombre(), sueldoRND(), fechaRND());
    }

    public static Empleado createEmpleado(Sex sexo) {
        switch (sexo) {
            case HOMBRE:
                return new Empleado("", apellidoRND(), nombreMasculinoRND(), sueldoRND(), fechaRND());
            case MUJER:
                return new Empleado("", apellidoRND(), nombreFemeninoRND(), sueldoRND(), fechaRND());
        }
        return null;
    }

    public static List<Empleado> createEmpleados(int cant) {
        List<Empleado> empleados = new LinkedList<>();
        for (int i = 0; i < cant; i++) {
            empleados.add(new Empleado(dniRND(), apellidoRND()+" "+apellidoRND(), nombre(), sueldoRND(), fechaRND()));
        }
        return empleados;
    }

    public static List<Empleado> createEmpleados(int cant, Sex sexo) {
        List<Empleado> empleados = new LinkedList<>();
        switch (sexo) {
            case HOMBRE:
                for (int i = 0; i < cant; i++) {
                    empleados.add(new Empleado(dniRND(), apellidoRND(), nombreMasculinoRND(), sueldoRND(), fechaRND()));
                }
                break;
            case MUJER:
                for (int i = 0; i < cant; i++) {
                    empleados.add(new Empleado(dniRND(), apellidoRND(), nombreFemeninoRND(), sueldoRND(), fechaRND()));
                }
                break;
        }

        return empleados;
    }

}
