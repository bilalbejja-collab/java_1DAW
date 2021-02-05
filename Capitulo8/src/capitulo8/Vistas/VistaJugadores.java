/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capitulo8.Vistas;

import capitulo8.DAO.JugadorDAOCSV;
import java.util.Scanner;
import capitulo8.Modelo.Equipo;
import capitulo8.Modelo.Jugador;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author BILAL
 */
public class VistaJugadores {

    public static void verJugador(Optional<Jugador> jugador) {
        System.out.printf("%-7s %-10s %-15s %-10s %-10s \n",
                "ID",
                "IDEquipo",
                "Nombre",
                "Edad",
                "Dorsal"
        );
        Stream.iterate(0, n -> n+1)
              .limit(52)
              .forEach((n) -> System.out.print("-"));
        System.out.println("");
        System.out.printf("%-7s %-10s %-15s %-10s %-10s \n",
                jugador.get().getId(),
                jugador.get().getIdEquipo(),
                jugador.get().getNombre(),
                jugador.get().getEdad(),
                jugador.get().getDorsal()
        );
    }

    public static void menuJugadores() throws SQLException {
        int opc;
        Scanner teclado = new Scanner(System.in);
        int idEquipo;
        String nombre;
        int dorsal;
        int edad;
        Jugador j = new Jugador();
        Optional<Jugador> jugador;

        System.out.println("- - - - - MENÚ con opciones - - - - -");
        System.out.println("1. Buscar jugador por id");
        System.out.println("2. Buscar jugador por nombre");
        System.out.println("3. Insertar jugador");
        System.out.println("4. Modificar jugador");
        System.out.println("5. Borrar jugador");
        System.out.println("6. Atrás");
        System.out.print(" ==> ");
        do {
            opc = teclado.nextInt();
            switch (opc) {
                case 1:
                    System.out.print("Introduzca el id: ");
                    jugador = Equipo.getInstance()
                            .buscarJugadores("player_id", teclado.next());
                    verJugador(jugador);
                    System.out.print(" ==> ");
                    break;
                case 2:
                    System.out.print("Introduzca el nombre: ");
                    jugador = Equipo.getInstance()
                            .buscarJugadores("nombre", teclado.next());
                    verJugador(jugador);
                    System.out.print(" ==> ");
                    break;
                case 3:
                    System.out.print("Introduzca 'idEquipo' para el jugador: ");
                    idEquipo = teclado.nextInt();
                    System.out.print("Introduzca 'nombre' para el jugador: ");
                    nombre = teclado.next();
                    System.out.print("Introduzca 'dorsal' para el jugador: ");
                    dorsal = teclado.nextInt();
                    System.out.print("Introduzca 'edad' para el jugador: ");
                    edad = teclado.nextInt();
                    j.setIdEquipo(idEquipo);
                    j.setNombre(nombre);
                    j.setDorsal(dorsal);
                    j.setEdad(edad);
                    JugadorDAOCSV.getInstance().save(j);
                    System.out.print(" ==> ");
                    break;
                case 4:
                    System.out.print("Introduzca el id del jugador que quieres modificar: ");
                    j = (Jugador) JugadorDAOCSV.getInstance().get(teclado.nextInt()).get();
                    System.out.print("Introduzca 'idEquipo': ");
                    idEquipo = teclado.nextInt();
                    System.out.print("Introduzca 'nombre': ");
                    nombre = teclado.next();
                    System.out.print("Introduzca 'dorsal': ");
                    dorsal = teclado.nextInt();
                    System.out.print("Introduzca 'edad': ");
                    edad = teclado.nextInt();
                    j.setIdEquipo(idEquipo);
                    j.setNombre(nombre);
                    j.setDorsal(dorsal);
                    j.setEdad(edad);
                    JugadorDAOCSV.getInstance().update(j);
                    System.out.print(" ==> ");
                    break;
                case 5:
                    System.out.print("Introduzca el id del jugador que quieres eleminar: ");
                    j = (Jugador) JugadorDAOCSV.getInstance().get(teclado.nextInt()).get();
                    JugadorDAOCSV.getInstance().delete(j);
                    System.out.print(" ==> ");
                    break;
            }
        } while (opc != 6);
    }

}
