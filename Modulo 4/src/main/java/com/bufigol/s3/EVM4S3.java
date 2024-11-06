package com.bufigol.s3;

import java.util.Scanner;

public class EVM4S3 {
    public static void main(String[] args) {
        contarParesImpares(ingresarEntero());
        contarPostivosNegativosCeros();

    }

    private static int ingresarNumero(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese un numero: ");
        return sc.nextInt();
    }
    private static int ingresarEntero() {
        int output =  ingresarNumero();
        if(output < 0 || output > 100) {
            System.out.println("-------------Error-------------");
            output = ingresarEntero();

        }
        return output;
    }
    private static void contarParesImpares(int a) {
        int pares = 0;
        int impares = 0;
        for( int i = 1; i <= a; i++ ) {
            if( i%2==0 ) {
                pares++;
            }else {
                impares++;
            }
        }
        System.out.println( "el total de numeros pares es: " + pares );
        System.out.println( "el total de numeros impares es: " + impares );
        System.out.println( "-------------fin del programa-------------" );
    }
    private static void contarPostivosNegativosCeros() {
        int positivos=0;
        int negativos=0;
        int ceros=0;
        for(int i = 0; i <= 10; i++) {
            int aux=ingresarNumero();
            if(aux>0) {
                positivos++;
            } else if(aux<0) {
                negativos++;
            } else {
                ceros++;
            }
        }
        System.out.println("el numero de ceros introducidos es de: "+ceros);
        System.out.println("el numero de positivos introducidos es de "+positivos);
        System.out.println("el numero de negativos introducidos es de "+negativos);

        System.out.println("-------------fin del programa-------------");

    }
}
