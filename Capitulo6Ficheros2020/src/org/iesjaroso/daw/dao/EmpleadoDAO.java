/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.dao;

import org.iesjaroso.daw.FileEmpleadoFactory.EmpleadoFileFactory;
import org.iesjaroso.daw.FileEmpleadoFactory.EmpleadoFileFactory.TypeFile;
import java.io.IOException;
import org.iesjaroso.daw.model.Empleado;

import java.util.LinkedList;
import java.util.List;
import org.iesjaroso.daw.FileEmpleadoFactory.IEmpleadoFileFactory;
import java.util.Optional;

/**
 *
 * @author franmatias
 */
public class EmpleadoDAO implements IEmpleadoDAO {

    List<Empleado> empleados = new LinkedList();

    private static EmpleadoDAO instance = null;

    public static EmpleadoDAO getInstance() throws IOException {
        if (instance == null) {
            instance = new EmpleadoDAO();
        }
        return instance;
    }

    private EmpleadoDAO() {
    }

    @Override
    public Empleado get(String dni) {

        return empleados.stream()
                .filter((Empleado empleado) -> empleado.getDni().equals(dni))
                .findAny().get();

    }

    private int getIndex(String dni) {
        for (int i = 0; i < empleados.size(); i++) {
            Empleado empleado = empleados.get(i);
            if (empleado.getDni().equals(dni)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public List<Empleado> getAll() {
        return empleados;
    }

    @Override
    public boolean insert(Empleado empleado) {
        return empleados.add(empleado);
    }

    @Override
    public boolean update(Empleado empleado) {
        int index = getIndex(empleado.getDni());
        try {
            empleados.set(index, empleado);
            return true;
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    @Override
    public boolean delete(Empleado empleado) {
        return empleados.remove(empleado);
    }

    @Override
    public boolean deleteAll() {
        return empleados.removeAll(empleados);
    }

    /**
     *
     * @param fichero
     * @return
     */
    @Override
    public List<Empleado> abrir(String fichero) {

        try {
            //Aqui se obtiene la extension del fichero 
            TypeFile typeFile = TypeFile.valueOf(getExtensionFile(fichero).toUpperCase());
            //Aqui se verifica el tipo de fichero pasado como parametro 
            IEmpleadoFileFactory instanceFile = EmpleadoFileFactory.getEmpleadoFile(typeFile);
            //aqui llama al abrir(..) depende del tipo de istancia Bin, Json, Prop ...
            empleados.addAll(instanceFile.abrir(fichero));
        } catch (IllegalArgumentException ex) {
            System.err.println("Extensión no válida");
        } catch (IOException ex) {
            System.err.println("Fichero no encontrado");
        }
        return empleados;
    }

    /**
     *
     * @param fichero
     * @param datos
     */
    @Override
    public void guardar(String fichero, List<Empleado> datos) {

        try {
            //Aqui se obtiene la extension del fichero 
            TypeFile typeFile = TypeFile.valueOf(getExtensionFile(fichero).toUpperCase());
            //Aqui se verifica el tipo de fichero pasado como parametro 
            IEmpleadoFileFactory instanceFile = EmpleadoFileFactory.getEmpleadoFile(typeFile);
            //aqui llama al guardar(...) depende del tipo de istancia Bin, Json, Prop ...
            instanceFile.guardar(fichero, datos);
        } catch (IllegalArgumentException ex) {
            System.err.println("Extensión no válida");
        } catch (IOException ex) {
            System.err.println("Fichero no encontrado");
        }

    }

    /**
     *
     * @param fichero
     * @return
     */
    public String getExtensionFile(String fichero) {
        return Optional.ofNullable(fichero)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(fichero.lastIndexOf(".") + 1)).orElse("");
    }

}
