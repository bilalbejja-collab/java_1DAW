/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capitulo8.Vistas;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author BILAL
 */
public class VistaMain {

    public static void main(String[] args) throws SQLException {
        int opc;
        Scanner teclado = new Scanner(System.in);

        do {
            System.out.println("- - - - - MENÚ PRINCIPAL - - - - -");
            System.out.println("1. Equipos");
            System.out.println("2. Jugadores");
            System.out.println("3. Salir");
            System.out.print(" ==> ");
            opc = teclado.nextInt();

            switch (opc) {
                case 1:
                    VistaEquipos.menuEquipos();
                    break;
                case 2:
                    VistaJugadores.menuJugadores();
                    break;
            }
        } while (opc != 3);
    }
}
