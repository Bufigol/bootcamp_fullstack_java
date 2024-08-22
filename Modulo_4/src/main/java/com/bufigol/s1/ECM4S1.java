package com.bufigol.s1;

import java.util.Scanner;

public class ECM4S1 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese la altura del rectangulo en metros: ");
        int altura = teclado.nextInt();
        System.out.println("Ingrese la base del rectangulo en metros: ");
        int base = teclado.nextInt();
        System.out.println("El area del rectangulo es: " + (base * altura) + " metros cuadrados");
        System.out.println("El perimetro del rectangulo es: " + (2 * (base + altura)) + " metros");
    }
}
