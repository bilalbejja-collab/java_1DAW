/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.capitulo6ficheros20;

import org.iesjaroso.daw.model.Empleado;
import org.iesjaroso.daw.model.EmpleadoBuilder;
import org.iesjaroso.daw.model.EmpleadoAdvancedBuilder;
import org.iesjaroso.daw.controller.EmpleadoController;
import org.iesjaroso.daw.dao.EmpleadoDAO;
import org.iesjaroso.daw.controller.EmpleadosController;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.iesjaroso.daw.service.EmpleadoQueryService;
import org.iesjaroso.daw.service.EmpleadoQueryServiceImpl;
import org.iesjaroso.daw.service.EmpleadoService;
import org.iesjaroso.daw.utils.EmpleadoRandom;
import org.iesjaroso.daw.view.EmpleadoView;

/**
 *
 * @author franmatias
 */
public class Capitulo6Ficheros20 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        //Uso de la capa MVC    
        EmpleadoView view = new EmpleadoView();
        Empleado model = new EmpleadoBuilder("97772313A")
                .setApellidos("Gutierrez Aguas")
                .setNombre("Ruben")
                .setSueldo(1500)
                .setFechaContratacion(LocalDate.of(2018, 1, 24))
                .createEmpleado();

        EmpleadoController controlador = new EmpleadoController(model, view);
        controlador.actualizarVista();
        // modificar el sueldo y los apellidos de model
        controlador.setSueldo(model.getSueldo() * 1.13d);
        controlador.setApellidos("Martinez Guirado");
        // solo nos muestra las informaciones del empleado porque es Empleado
        //no es IEmpleadoDAO
        controlador.actualizarVista();

        System.out.println("--------------------------------------------------");
        System.out.println("Controlador más avanzado");
//        Uso de la capa DAO. Objetivo independizar el modelo de las fuentes 
//        de datos o datasources
//         Inyectando más potencia a nuestro proyecto
        EmpleadosController controller = new EmpleadosController(
                EmpleadoDAO.getInstance(), view);
        //INSERTAR 1
        controller.getModelo().insert(model);
        //INSERTAR 2
        controller.getModelo().insert(
                new EmpleadoBuilder("23423434X")
                        .setApellidos("Bejja Annouh")
                        .setNombre("Bilal")
                        .setSueldo(2110)
                        .setFechaContratacion(LocalDate.of(2000, 12, 21))
                        .createEmpleado()
        );
        //INSERTAR 3
        controller.getModelo().insert(
                new EmpleadoAdvancedBuilder()
                        .with(e -> {
                            e.setDni("23800342F");
                            e.setApellidos("Martin López");
                            e.setNombre("Susana");
                            e.setSueldo(2900);
                        })
                        .with(e -> e.setFechaContratacion(LocalDate.of(2010, 4, 14)))
                        .createEmpleado()
        );

        controller.actualizarVista();

        //Leyendo ficheros para la entrada de datos a través de disco.
        System.out.println("\n\nLeyendo información en un formato: ");
//        controller.getModelo().deleteAll();
//        controller.getModelo().abrir("ficheros/empleados.json");
//        controller.actualizarVista();
//
//        controller.getModelo().deleteAll();
//        controller.getModelo().abrir("ficheros/empleados.csv");
//        controller.actualizarVista();
//
//        controller.getModelo().deleteAll();
//        controller.getModelo().abrir("ficheros/empleados.obj");
//        controller.actualizarVista();
//
//        controller.getModelo().deleteAll();
//        controller.getModelo().abrir("ficheros/empleados.bin");
//        controller.actualizarVista();
//
//        System.out.println("\n\nAlmacenar datos en formato csv: ");
//        controller.getModelo().guardar("ficheros/empleados.csv", controller.getModelo().getAll());
//
        System.out.println("\n\nUsando servicios: ");
        System.out.println("Un servicio es otra capa más que se coloca entre"
                + " el usuario y la capa DAO facilitando las consultas.");
//
        System.out.println("\nRealizando búsquedas por el servicio de 1er nivel: ");
        EmpleadoService servicio = new EmpleadoService();
        System.out.println("Búsqueda por DNI: ");
        servicio.findByDNI("23").forEach(System.out::println);
//
        servicio.cleanQuery();
        System.out.println("Búsqueda por apellidos: ");
        servicio.findBySurname("Martin").forEach(System.out::println);
//
        servicio.cleanQuery();
        System.out.println("Búsqueda por sueldo: ");
        servicio.findBySueldo(950, 2000).forEach(System.out::println);
//
        servicio.cleanQuery();
        System.out.println("Búsqueda por fecha de contratación: ");
        servicio.findByFechaContratacion(
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2010, 12, 31))
                .forEach(System.out::println);
//
        System.out.println("\nRealizando búsquedas por el servicio de 2nd nivel: ");
        System.out.println("Esto permite buscar anidando críterios");
        EmpleadoQueryService query = new EmpleadoQueryServiceImpl();
        query.containsDNI("234")
                .containsName("Bi")
                .exec()
                .forEach(System.out::println);
//
        query.clearQuery();
//
        System.out.println("\nOtra búsqueda: ");
        query.betweenFechaContratacion(LocalDate.of(2009, 1, 1), LocalDate.of(2022, 1, 1))
                .betweenSueldo(1000, 2000)
                .betweenSurnameSize(6, 20)
                .exec()
                .forEach(System.out::println);
//
        System.out.println("El servicio debería estar contenido en el controlador");
//
////        Trabajando con el sistema de ficheros. Ficheros y carpetas. 
////        String origen  = "ficheros/carpeta1";
////        String destino = "ficheros/carpeta3"; 
////        EmpleadoFiles.crearCarpeta("ficheros/carpeta3/tmp");
////        EmpleadoFiles.renombrarCarpeta(origen, destino);
////        EmpleadoFiles.borrarCarpeta(destino);
////        EmpleadoFiles.mostrarContenido("ficheros");
////       EmpleadoFiles.recorridoArbol("ficheros/Tu Carpeta");
//
        EmpleadoRandom er = new EmpleadoRandom();
        EmpleadoRandom.createEmpleados(2, EmpleadoRandom.Sex.MUJER).forEach(System.out::println);
//        
        System.out.println("------------------------------Prueba Bilal-----------------------------------");
//        
//        controller.getModelo().guardar("ficheros/empleados.properties", EmpleadoRandom.createEmpleados(7));
//    
        controller.getModelo().deleteAll();
        controller.getModelo().abrir("ficheros/empleados.properties");
        controller.actualizarVista();

        List<Integer> list = Arrays.asList(7, 2, 3, 4, 5);
        System.out.println(list.stream().findAny());
        System.out.println(list.stream().findFirst());
        Optional<Integer> result = list
                .stream().parallel()
                .filter(num -> num < 4).findAny();
        System.out.println(result);

        List<Integer> list2 = Arrays.asList(1, 10, 3, 7, 5);
        int a = list2.stream()
                .peek(num -> System.out.println("will filter " + num))
                .filter(x -> x == 7)
                .findFirst()
                .get();
        System.out.println(a);

        String frase = "bilal-bejja.2016@Tayeb";
        String[] plbs = frase.split("[-.@]");
        System.out.println(Arrays.toString(plbs));

        Pattern pat = Pattern.compile("^bil.*");
        Matcher mat = pat.matcher(frase);
        if (mat.find()) {
            System.out.println("Válido");
        } else {
            System.out.println("No Válido");
        }

    }

}
