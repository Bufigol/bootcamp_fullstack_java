package com.bufigol.utils;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EntradaPorTeclado {
    private static Scanner scanner = null;

    public static int pedirEntero(String msg) {
        if (scanner == null) {
            scanner = new Scanner(System.in); // Open scanner only if it's null
        }
        System.out.println(msg);
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                scanner.nextLine(); // Limpiar el buffer
            }
        }
    }

    public static String pedirCadena(String msg) {
        if (scanner == null) {
            scanner = new Scanner(System.in); // Open scanner only if it's null
            scanner.nextLine(); // Limpiar el buffer
        }
        System.out.println(msg);
        String out=scanner.nextLine();
        if(out.isEmpty()){
            return pedirCadena(msg);
        }else{
            return out;
        }

    }

    public static void cerrarScanner() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}