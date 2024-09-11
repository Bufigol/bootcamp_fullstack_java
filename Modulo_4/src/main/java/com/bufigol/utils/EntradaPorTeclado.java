package com.bufigol.utils;

import java.util.Scanner;

public class EntradaPorTeclado {
    public static int pedirEntero(String msg) {
        try{
            System.out.println(msg);
            Scanner scanner = new Scanner(System.in);
            return scanner.nextInt();
        } catch (RuntimeException e) {
            return pedirEntero(msg);
        }
    }
    public static String pedirCadena(String msg) {
        try{
            System.out.println(msg);
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (RuntimeException e) {
            return pedirCadena(msg);
        }
    }
}
