/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.FileEmpleadoFactory;

import java.io.IOException;

/**
 * Factoria para leer y escribir ficheros de tipo empleado
 */
public class EmpleadoFileFactory {

    public enum TypeFile {
        CSV, JSON, BIN, OBJ, PROPERTIES
    }

    public static IEmpleadoFileFactory getEmpleadoFile(TypeFile type) throws IOException {
        switch (type) {
            case BIN:
                return EmpleadoBin.getInstance();
            case OBJ:
                return EmpleadoObj.getInstance();
            case CSV:
                return EmpleadoCSV.getInstance();
            case JSON:
                return EmpleadoGson.getInstance();
            case PROPERTIES:
                return EmpleadoProp.getInstance();
            default:

        }
        throw new IllegalArgumentException("La extensión del fichero no es válida");

    }

}
