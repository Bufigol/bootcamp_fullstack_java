package com.bufigol.s14;

public class ECM4S14 {
    public static void main(String[] args) {
        int[] numeradores = {4, 8, 16, 32, 64, 128, 256, 512};
        int[] denominadores = {2, 0, 4, 4, 0, 8};
        for(int i = 0; i < numeradores.length; i++) {
            try {
                dividir(numeradores[i], denominadores[i]);
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Fuera del limite de la matriz");
                System.out.println("Excepcion en el main, fin del programa");
            }
        }
    }

    private static void dividir(int numerador, int denominador) {
        try{
            int div = numerador / denominador;
            System.out.println("La divicion entre '" + numerador + "' y '" + denominador + "' es '" + div + "'");
        } catch (ArithmeticException e) {
            System.out.println("No se puede dividir por cero");
        }

    }


}
