package com.bufigol.s2;

import java.util.Scanner;

public class EVM4S2 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Bienvenido\nPor favor, Ingresa la temperatura");
        temperatura(teclado.nextInt());
        ej2(teclado);
    }

    private static void ej2(Scanner teclado) {
        double saldo=1000;
        boolean continuar=true;
        do{
           System.out.println("""
                   Seleccione la opción:
                   1. Depositar dinero a mi cuenta
                   2. Retirar dinero de mi cuenta
                   3. Salir""");
           int opcion= teclado.nextInt();
           System.out.println("Su saldo incial es de: "+saldo);
           switch (opcion){
               case 1:
                   System.out.println("¿Cuanto dinero desea depositar?");
                   saldo+= teclado.nextDouble();
                   break;
               case 2:
                   System.out.println("¿Cuanto dinero desea retirar?");
                   double aux= teclado.nextDouble();
                   if(saldo< aux){
                       System.out.println("No tiene suficiente saldo");
                       break;
                   }else{
                       saldo-= aux;
                       System.out.println("Su nuevo saldo es de: "+(saldo));
                   }
                   break;
               case 3:
                   continuar=false;
                   break;
               default:
                   System.out.println("Opcion no valida");
                   break;
           }
        }while(continuar);
    }

    private static void temperatura(int temperatura) {
        if(temperatura<=10){
            System.out.println("Frio");
        }else{
            if(temperatura<20){
                System.out.println("Nublado");
            }else{
                if(temperatura<30){
                    System.out.println("Caluroso");
                }else{
                    System.out.println("Tropical");
                }
            }
        }
    }

}
