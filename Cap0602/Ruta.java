/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficheros;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author franmatias
 */
public class Ruta {

    /**
     *
     * @param rutaNombreFichero
     */
    public static void info(String rutaNombreFichero) {
        try{
            Path path = Paths.get(rutaNombreFichero);
            System.out.println("ruta = " + path);
            System.out.println("es absoluta? = " + path.isAbsolute());
            System.out.println("nombre abreviado = " + path.getFileName());
            System.out.println("padre  = " + path.getParent());
            System.out.println("uri = " + path.toUri());
            System.out.println("Sistema de ficheros = " + path.getFileSystem());
            System.out.println("Raíz = " + path.getRoot());
            System.out.println("Elementos = " + path.getNameCount());

            System.out.println("path = " + path);
            System.out.println("   existe = " + Files.exists(path));
            System.out.println("   Lectura  = " + Files.isReadable(path));
            System.out.println("   Escritura = " + Files.isWritable(path));
            System.out.println("   Ejecución = " + Files.isExecutable(path));
            System.out.println("Tamaño: " + Files.size(path) + " bytes");
        } catch (IOException ex) {
            Logger.getLogger(Ruta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param rutaDirectorio
     */
    public static void crearCarpeta(String rutaDirectorio) {
        try {
            Files.createDirectory(Paths.get(rutaDirectorio));
        } catch (IOException ex) {
            Logger.getLogger(Ruta.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Carpeta " + rutaDirectorio + " creada");
    }

    /**
     *
     * @param rutaDirectorio
     */
    public static void borrarCarpeta(String rutaDirectorio) {
        try {
            Files.delete(Paths.get(rutaDirectorio));
            System.out.println("Carpeta " + rutaDirectorio + " eliminado");
        } catch (NoSuchFileException ex) {
            System.out.println(ex.getMessage() + " no existe");
        } catch (IOException ex) {
            Logger.getLogger(Ruta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param rutaFichero
     */
    public static void borrarFichero(String rutaFichero) {
        Path path = Paths.get(rutaFichero);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            } else {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    /**
     *
     *
     */
    public static void conjuntoCaracteres() {
        // Codificación por defecto
        System.out.println(" Conjunto de caracteres por defecto = " + Charset.defaultCharset());
        // Establecer un tipo de codificación
        System.setProperty("file.encoding", "ISO-8859-1");
        System.out.println(" Codificación de fichero = " + System.getProperty("file.encoding"));
        // Capturamos un objeto Charset a través de su nombre
        Charset ascii = Charset.forName("US-ASCII");

        System.out.println(" Estandar charset en viejos Sistemas = " + ascii);
    }

    /**
     * método para el acceso a ficheros muy pequeños
     *
     * @param rutaFichero
     */
    public static void lectura(String rutaFichero) {
        Path ruta = Paths.get(rutaFichero);

        try {
            List<String> content = Files.readAllLines(ruta, Charset.forName("ISO-8859-1"));
            for (String contenido : content) {
                System.out.print(contenido);
            }
        } catch (IOException e) {
            System.err.println(" ERROR : " + e);
            System.exit(1);
        }
    }

    /**
     *
     * @param rutaFichero
     */
    public static void lecturaBuffer(String rutaFichero) {
        Path ruta = Paths.get(rutaFichero);
        try (BufferedReader reader = Files.newBufferedReader(ruta, Charset.defaultCharset())) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(" ERROR : " + e);
            System.exit(1);
        }
    }

    /**
     * 
     * @param ficheroEntrada
     * @param ficheroSalida 
     */
    public static void escrituraFichero(String ficheroEntrada, String ficheroSalida) {
        Path inputFile = Paths.get(ficheroEntrada);
        Path outputFile = Paths.get(ficheroSalida);

        try {
            List<String> contents = Files.readAllLines(inputFile);
            Files.write(outputFile, contents,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println(" ERROR : " + e);
            System.exit(1);
        }
    }
    
    /**
     * Recorre un direc
     * @param fichero 
     */
    public static void recorrerDirectorio(String fichero){
        try {
            Path ruta = Paths.get(fichero);
            Stream<Path> dirs= Files.list(ruta);
            Iterator<Path> it = dirs.iterator();
            while(it.hasNext()){
                Path dir = it.next();
                System.out.println("Directorio: "+ dir.getFileName());
                if(Files.isRegularFile(ruta)){
                    System.out.println("De solo lectura?: "+ Files.isReadable(ruta));
                    System.out.println("Oculto?: "+ Files.isHidden(ruta));
                    System.out.println("Escritura?: "+ Files.isWritable(ruta));
                    System.out.println("Fecha modificación: "+ Files.getLastModifiedTime(ruta, LinkOption.NOFOLLOW_LINKS));
                    System.out.println("Tamaño: "+ Files.size(ruta) + " bytes");
                }
                
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Ruta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
