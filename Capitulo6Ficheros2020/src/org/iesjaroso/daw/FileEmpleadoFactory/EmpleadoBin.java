package org.iesjaroso.daw.FileEmpleadoFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.iesjaroso.daw.model.Empleado;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.iesjaroso.daw.model.EmpleadoBuilder;

/**
 * Almacena un empleado en formato .bin en modo binario.
 */
public class EmpleadoBin implements IEmpleadoFileFactory<Empleado> {

    private static EmpleadoBin instance;

    public static EmpleadoBin getInstance() throws IOException {
        if (instance == null) {
            instance = new EmpleadoBin();
        }
        return instance;
    }

    private EmpleadoBin() {
    }

    /**
     * 
     * @param nombreFichero
     * @param datos 
     */
    @Override
    public void guardar(String nombreFichero, List<Empleado> datos) {
        try (DataOutputStream salida
                = new DataOutputStream(
                        new FileOutputStream(nombreFichero))) {
            datos.forEach((Empleado empleado) -> {
                try {
                    salida.writeUTF(empleado.getDni());
                    salida.writeUTF(empleado.getApellidos());
                    salida.writeUTF(empleado.getNombre());
                    salida.writeDouble(empleado.getSueldo());
                    salida.writeUTF(empleado.getFechaContratacion().toString());
                } catch (IOException ex) {
                    System.err.println("Error al escribir en el archivo " + nombreFichero);
                }
            });
        } catch (IOException ex) {
            System.err.println("Fichero no encontrado " + nombreFichero);
        }
    }

    /**
     * 
     * @param nombreFichero
     * @return 
     */
    @Override
    public List<Empleado> abrir(String nombreFichero) {
        List<Empleado> datos = new ArrayList();
        try (DataInputStream entrada
                = new DataInputStream(
                        new FileInputStream(nombreFichero))) {
            while (true) {
                Empleado empleado = new EmpleadoBuilder(entrada.readUTF())
                        .setApellidos(entrada.readUTF())
                        .setNombre(entrada.readUTF())
                        .setSueldo(entrada.readDouble())
                        .setFechaContratacion(LocalDate.parse(entrada.readUTF()))
                        .createEmpleado();
                datos.add(empleado);

            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (EOFException e) {
            System.out.println("Datos leidos: " + datos.size());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return datos;
    }

}
