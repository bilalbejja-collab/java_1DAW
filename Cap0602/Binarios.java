/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarios;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author franmatias
 */
public class Binarios {

    /**
     * EJEMPLOS JAVA DE ESCRITURA EN FICHEROS BINARIOS Ejemplo 1: Programa que
     * lee enteros por teclado y los escribe en el fichero binario1.dat La
     * lectura de datos acaba cuando se introduce -1
     */
    public static void binario1() {
        Scanner sc = new Scanner(System.in);
        FileOutputStream fos = null;
        DataOutputStream salida = null;
        int n;

        try {
            fos = new FileOutputStream("ficheros/binario.dat");
            salida = new DataOutputStream(fos);

            System.out.print("Introduce número entero. -1 para acabar: ");
            n = sc.nextInt();
            while (n != -1) {
                salida.writeInt(n); //se escribe el número entero en el fichero
                System.out.print("Introduce número entero. -1 para acabar: ");
                n = sc.nextInt();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (salida != null) {
                    salida.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void binario1Mejorado() {
        Scanner sc = new Scanner(System.in);

        try (DataOutputStream salida = new DataOutputStream(
                new FileOutputStream("ficheros/binario.dat"))) {

            System.out.print("Introduce número entero. -1 para acabar: ");
            int n;
            while ((n = sc.nextInt()) != -1) {
                salida.writeInt(n); //se escribe el número entero en el fichero
                System.out.print("Introduce número entero. -1 para acabar: ");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ejemplo 2: Programa Java que crea una matriz de elementos de tipo double
     * y lee por teclado el valor de sus elementos. A continuación escribe el
     * contenido de la matriz en un fichero. Al principio del fichero se
     * escriben dos enteros con los valores del número de filas y columnas de la
     * matriz.
     */
    public static void binario2() {
        Scanner sc = new Scanner(System.in);

        FileOutputStream fos = null;
        DataOutputStream salida = null;

        double[][] matriz;
        int filas, columnas, i, j;
        do {
            System.out.print("Número de filas: ");
            filas = sc.nextInt();
        } while (filas <= 0);
        do {
            System.out.print("Número de columnas: ");
            columnas = sc.nextInt();
        } while (columnas <= 0);

        matriz = new double[filas][columnas]; //se crea la matriz

        for (i = 0; i < filas; i++) {     //lectura de datos por teclado
            for (j = 0; j < columnas; j++) {
                System.out.print("matriz[" + i + "][" + j + "]: ");
                matriz[i][j] = sc.nextDouble();
            }
        }
        try {
            //crear el fichero de salida
            fos = new FileOutputStream("ficheros/matriz.dat");
            salida = new DataOutputStream(fos);

            //escribir el número de filas y columnas en el fichero
            salida.writeInt(filas);
            salida.writeInt(columnas);

            //escribir la matriz en el fichero
            for (i = 0; i < filas; i++) {
                for (j = 0; j < columnas; j++) {
                    salida.writeDouble(matriz[i][j]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (salida != null) {
                    salida.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static void binario2Mejorado() {
        Scanner sc = new Scanner(System.in);

        double[][] matriz;
        int filas, columnas, i, j;
        do {
            System.out.print("Número de filas: ");
            filas = sc.nextInt();
        } while (filas <= 0);
        do {
            System.out.print("Número de columnas: ");
            columnas = sc.nextInt();
        } while (columnas <= 0);

        matriz = new double[filas][columnas]; //se crea la matriz

        for (i = 0; i < filas; i++) {     //lectura de datos por teclado
            for (j = 0; j < columnas; j++) {
                System.out.print("matriz[" + i + "][" + j + "]: ");
                matriz[i][j] = sc.nextDouble();
            }
        }
        try(DataOutputStream salida = new DataOutputStream(
                new FileOutputStream("ficheros/matriz.dat"))) {
            //crear el fichero de salida

            //escribir el número de filas y columnas en el fichero
            salida.writeInt(filas);
            salida.writeInt(columnas);

            //escribir la matriz en el fichero
            for (i = 0; i < filas; i++) {
                for (j = 0; j < columnas; j++) {
                    salida.writeDouble(matriz[i][j]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } 
    }

    /**
     * EJEMPLOS JAVA DE LECTURA DE FICHEROS BINARIOS Ejemplo 3: Programa que lee
     * el contenido del fichero creado en el método binario1() Utilizaremos un
     * bucle infinito para leer los datos. Cuando se llega al final del fichero
     * se lanza la excepción EOFException que se utiliza para salir del bucle
     * while.
     */
    public static void binario3() {

        int n;
        try(DataInputStream entrada = new DataInputStream(
            new FileInputStream("ficheros/binario.dat"))) {
            
            while (true) {
                n = entrada.readInt();  //se lee  un entero del fichero
                System.out.println(n);  //se muestra en pantalla
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } 
    }

    /**
     * Ejemplo 4: Programa Java que lee el contenido del fichero creado en el
     * método binario2() y lo muestra por pantalla.
     */
    public static void binario4() {

        int filas, columnas, i, j;
        try(DataInputStream entrada = new DataInputStream(
            new FileInputStream("ficheros/binario.dat"))) {

            filas = entrada.readInt();  //se lee el primer entero del fichero
            columnas = entrada.readInt();//se lee el segundo entero del fichero
            for (i = 0; i < filas; i++) {
                for (j = 0; j < columnas; j++) {  // se leen los double y se muestran por pantalla
                    System.out.printf("%8.2f", entrada.readDouble());
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } 
    }

}
