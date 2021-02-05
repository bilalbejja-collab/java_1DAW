/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.capitulo6ficheros20;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author franmatias
 */
public class EmpleadoFiles {

    public static void crearCarpeta(String nombre) {
        Path ruta = Paths.get(nombre);

        try {
            //Files.createDirectory(ruta);
            Files.createDirectories(ruta);
        } catch (IOException ex) {
            System.err.println("Ruta no existente " + ruta);
        }
    }

    public static void renombrarCarpeta(String origen, String destino) {
        Path rutaOrigen = Paths.get(origen);
        Path rutaDestino = Paths.get(destino);
        try {
            Files.move(rutaOrigen, rutaDestino, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.err.println("Ruta no existente ");
        }
    }

    public static void borrarCarpeta(String origen) {
        Path rutaOrigen = Paths.get(origen);
        try {
            //Files.delete(rutaOrigen);
            Files.deleteIfExists(rutaOrigen);
        } catch (DirectoryNotEmptyException ex) {
            System.out.println("Carpeta no vacía");
        } catch (IOException ex) {
            System.err.println("Ruta no existente ");
        }
    }

    public static void borrarRecursivo(String origen) {

    }

    public static void mostrarContenido(String origen) {
        Path rutaOrigen = Paths.get(origen);

        Stream<Path> contenido;
        try {
            contenido = Files.list(rutaOrigen);

            contenido.sorted()
                    .forEach(file -> {
                        try {
                            
//                    System.out.println(file.getParent());
                        if(Files.isDirectory(file)){
                            System.out.print("\t" + file.getFileName()+ "\n");
                        }else{
                            System.out.print("\t" + file.getFileName() + " -> " + Files.size(file) + " bytes \n");
                        }    
//                    System.out.println("es directorio? " + Files.isDirectory(file));
//                    System.out.println("es de lectura? " + Files.isReadable(file));
                            //System.out.println("Tamaño " + Files.size(file));

                        } catch (IOException ex) {
                            System.err.println("Ruta no existente ");
                        }
                    });

        } catch (IOException ex) {
            System.err.println("Ruta no existente ");
        }

    }

    public void recorridoRecursivo(String nombre) {
        Stream<Path> miStream;
        try {
            miStream = Files.walk(Paths.get(nombre));
            miStream.forEach(System.out::println);
        } catch (IOException ex) {
            System.err.println("Ruta no existente ");
        }
    }

    public static void recorridoArbol(String nombre) {
        try {
            Files.walkFileTree(Paths.get(nombre), new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    //System.out.println("Previsit" + dir);
                    return FileVisitResult.TERMINATE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                   //System.out.println("Ficheros y carpetas del nivel: " + file);
                   if(Files.isRegularFile(file)){
                       System.out.println("Eliminando: "+file.getFileName());
                       Files.delete(file);
                   }
                   return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.TERMINATE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    System.out.println("Eliminando carpeta: " + dir);
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
//            Files.walkFileTree(Paths.get(nombre), new FileVisitor<Path>() {
//                @Override
//                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
//                    System.out.println("Previsitando a " + dir);
//                    return FileVisitResult.CONTINUE;
//                }
//
//                @Override
//                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//
//                    System.out.println("Mostrando info de " + file);
//                    return FileVisitResult.CONTINUE;
//                }
//
//                @Override
//                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
//                    System.out.println("Mostrando info en caso de fallo de " + file);
//                    return FileVisitResult.CONTINUE;
//                }
//
//                @Override
//                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//                    System.out.println("Posvisitando a " + dir);
//                    return FileVisitResult.CONTINUE;
//                }
//            });
        } catch (IOException ex) {
            System.err.println("Ruta no existente ");
        }
    }

}
