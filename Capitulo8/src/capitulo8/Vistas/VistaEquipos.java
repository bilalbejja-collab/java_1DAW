/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capitulo8.Vistas;

import capitulo8.DAO.EquipoDAOCSV;
import capitulo8.DAO.JugadorDAOCSV;
import capitulo8.Modelo.Equipo;
import capitulo8.Modelo.Jugador;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 *
 * @author BILAL
 */
public class VistaEquipos {

    public static void verEquipo(Optional<Equipo> equipo) {
        System.out.printf("%-7s %-24s %-48s %-27s %-23s %-10s \n",
                "ID",
                "Nombre",
                "Estadio",
                "Poblacion",
                "Provincia",
                "CodigoPostal"
        );
        Stream.iterate(0, n -> n + 1)
                .limit(146)
                .forEach((n) -> System.out.print("-"));
        System.out.println("");
        System.out.printf("%-7s %-24s %-48s %-27s %-23s %-10s \n",
                equipo.get().getId(),
                equipo.get().getNombre(),
                equipo.get().getEstadio(),
                equipo.get().getPoblacion(),
                equipo.get().getProvincia(),
                equipo.get().getCodigoPostal()
        );
    }

    public static void menuEquipos() throws SQLException {
        int opc;
        Scanner teclado = new Scanner(System.in);
        Equipo e = new Equipo();
        Optional<Equipo> equipo;
        Jugador j = new Jugador();
        String eq_nombre;
        String estadio;
        String poblacion;
        String provincia;
        String cp;
        int idEquipo;
        String nombre;
        int dorsal;
        int edad;

        System.out.println("- - - - - MENÚ con opciones - - - - -");
        System.out.println("1. Buscar equipo por id");
        System.out.println("2. Buscar equipo por nombre");
        System.out.println("3. Insertar equipo");
        System.out.println("4. Modificar equipo");
        System.out.println("5. Borrar equipo");
        System.out.println("6. Añadir jugador a equipo");
        System.out.println("7. Atrás");
        System.out.print(" ==> ");
        do {
            opc = teclado.nextInt();
            switch (opc) {
                case 1:
                    System.out.print("Introduzca el id: ");
                    equipo = Equipo.getInstance()
                            .buscarEquipos("team_id", teclado.next());
                    verEquipo(equipo);
                    System.out.print(" ==> ");
                    break;
                case 2:
                    System.out.print("Introduzca el nombre: ");
                    equipo = Equipo.getInstance()
                            .buscarEquipos("eq_nombre", teclado.next());
                    verEquipo(equipo);
                    System.out.print(" ==> ");
                    break;
                case 3:
                    System.out.print("Introduzca 'nombre' para el equipo: ");
                    eq_nombre = teclado.next();
                    System.out.print("Introduzca 'estadio' para el equipo: ");
                    estadio = teclado.next();
                    System.out.print("Introduzca 'población' para el equipo: ");
                    poblacion = teclado.next();
                    System.out.print("Introduzca 'provincia' para el equipo: ");
                    provincia = teclado.next();
                    System.out.print("Introduzca 'cp' para el equipo: ");
                    cp = teclado.next();
                    e.setNombre(eq_nombre);
                    e.setEstadio(estadio);
                    e.setPoblacion(poblacion);
                    e.setProvincia(provincia);
                    e.setCodigoPostal(cp);
                    EquipoDAOCSV.getInstance().save(e);
                    System.out.print(" ==> ");
                    break;
                case 4:
                    System.out.print("Introduzca el id del equipo que quieres modificar: ");
                    e = (Equipo) EquipoDAOCSV.getInstance().get(teclado.nextInt()).get();
                    System.out.print("Introduzca 'nombre' para el equipo: ");
                    eq_nombre = teclado.next();
                    System.out.print("Introduzca 'estadio' para el equipo: ");
                    estadio = teclado.next();
                    System.out.print("Introduzca 'población' para el equipo: ");
                    poblacion = teclado.next();
                    System.out.print("Introduzca 'provincia' para el equipo: ");
                    provincia = teclado.next();
                    System.out.print("Introduzca 'cp' para el equipo: ");
                    cp = teclado.next();
                    e.setNombre(eq_nombre);
                    e.setEstadio(estadio);
                    e.setPoblacion(poblacion);
                    e.setProvincia(provincia);
                    e.setCodigoPostal(cp);
                    EquipoDAOCSV.getInstance().update(e);
                    System.out.print(" ==> ");
                    break;
                case 5:
                    System.out.print("Introduzca el id del equipo que quieres eleminar: ");
                    e = (Equipo) EquipoDAOCSV.getInstance().get(teclado.nextInt()).get();
                    EquipoDAOCSV.getInstance().delete(e);
                    System.out.print(" ==> ");
                    break;
                case 6:
                    System.out.print("Introduzca 'id' de equipo al que quieres añadir el jugador: ");
                    idEquipo = teclado.nextInt();
                    System.out.print("Introduzca 'nombre' del jugador: ");
                    nombre = teclado.next();
                    System.out.print("Introduzca 'dorsal' del jugador: ");
                    dorsal = teclado.nextInt();
                    System.out.print("Introduzca 'edad' del jugador: ");
                    edad = teclado.nextInt();
                    j.setIdEquipo(idEquipo);
                    j.setNombre(nombre);
                    j.setDorsal(dorsal);
                    j.setEdad(edad);
                    JugadorDAOCSV.getInstance().save(j);
                    System.out.print(" ==> ");
                    break;
            }
        } while (opc != 7);
    }
}
