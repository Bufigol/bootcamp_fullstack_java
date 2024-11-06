package com.bufigol.s3;

import java.util.Scanner;

public class ECM4S3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int a = sc.nextInt();
        patronUno(a);
        patronDos(a);
    }

    private static void patronUno(int a) {
        char caracter=' ';
        System.out.println( "Patrón 1:" );
        for (int i = 0; i < a; i++) {
            if ( i%2 == 0 ){
                caracter = '*';
            } else {
                caracter = '.';
            }
            System.out.print( caracter );
        }
        System.out.println();
    }

    private static void patronDos (int a) {
        System.out.println( "Patrón 2:" );
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                if ( i==0 || i == a-1 ) {
                    System.out.print( "*" );
                } else if (j == 0 || j == a-1) {
                    System.out.print( "*" );
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
