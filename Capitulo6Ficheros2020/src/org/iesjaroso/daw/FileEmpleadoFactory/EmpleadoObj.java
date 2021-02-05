/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.FileEmpleadoFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.iesjaroso.daw.model.Empleado;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Almacena un empleado en formato .obj usando objetos java. Empleado debe 
 * implementar la interface Serializable
*/
public class EmpleadoObj implements IEmpleadoFileFactory<Empleado> {

    //Patron Singleton 
    private static EmpleadoObj instance;

    public static EmpleadoObj getInstance() throws IOException {
        if (instance == null) {
            instance = new EmpleadoObj();
        }
        return instance;
    }

    private EmpleadoObj() {
    }

    /**
     * Almacena en binario objetos del tipo Lista de Empleados
     *
     * @param nombreFichero
     * @param datos
     */
    @Override
    public void guardar(String nombreFichero, List<Empleado> datos) {
        try (ObjectOutputStream salida
                = new ObjectOutputStream(
                        new FileOutputStream(nombreFichero))) {
            salida.writeObject(datos);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Abre un fichero binario con el contenido de una lista de empleados
     *
     * @param nombreFichero
     * @return
     */
    @Override
    public List<Empleado> abrir(String nombreFichero) {
        List<Empleado> datos = new ArrayList();
        try (ObjectInputStream entrada
                = new ObjectInputStream(
                        new FileInputStream(nombreFichero))) {
            datos = (List<Empleado>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return datos;
    }

}
