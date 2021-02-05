/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.FileEmpleadoFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.iesjaroso.daw.model.Empleado;

/**
 * Almacena un empleado en formato json usando las librerías gson
 */
public class EmpleadoGson implements IEmpleadoFileFactory<Empleado> {

    private static Gson gson;
    private static EmpleadoGson instance = null;

    public static EmpleadoGson getInstance() throws IOException {
        if (instance == null) {
            instance = new EmpleadoGson();
        }
        return instance;
    }

    private EmpleadoGson() {
    }

    @Override
    public List<Empleado> abrir(String fichero) {
        Path ruta = Paths.get(fichero);
        try {
            //Obtenemos el fichero.json List<String> en un único String
            String jsonString = Files.readAllLines(
                    Paths.get(fichero), Charset.forName("UTF-8"))
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.joining());

            gson = new GsonBuilder().setPrettyPrinting().create();
            return Arrays.asList(gson.fromJson(jsonString, Empleado[].class));
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            System.out.println(" Fichero " + ruta.getFileName() + " leído correctamente.");
        }

        return null;
    }

    @Override
    public void guardar(String fichero, List<Empleado> datos) {
        gson = new GsonBuilder().setPrettyPrinting().create();

        List<String> datosString = new ArrayList<>();
        datosString.add(gson.toJson(datos)); //Convertir List<Empleado> -> List<String>
        try {
            Files.write(Paths.get(fichero), datosString);
        } catch (IOException ex) {
            System.out.println("Error el Fichero no encontrado " + fichero);
        }

    }

}
