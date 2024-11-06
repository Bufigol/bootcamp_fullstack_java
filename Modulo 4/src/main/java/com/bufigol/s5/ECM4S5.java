package com.bufigol.s5;

import java.util.Scanner;

public class ECM4S5 {

    public static void main(String[] args) {
        String[] asignaturas = new String[5];
        float[] notas = new float[asignaturas.length];
        Scanner scanner = new Scanner(System.in);
        for(int i=0; i<asignaturas.length; i++){
            System.out.println("Ingrese el nombre de la Asignatura: ");
            asignaturas[i] = scanner.nextLine();
            System.out.println("Ingrese el promedio de la asignatura "+asignaturas[i]+": ");
            String paso = scanner.nextLine();
            notas[i] = Float.parseFloat(paso);
        }
        mostrarNotas(asignaturas, notas);
        mostrarPromedio(notas);
    }

    private static void mostrarPromedio(float[] notas) {
        float total = 0;
        for (float nota : notas) {
            total += nota;
        }
        System.out.println("El promedio del estudiante es: "+total/notas.length);
    }

    private static void mostrarNotas(String[] asignaturas, float[] notas) {
        for(int i = 0; i< asignaturas.length; i++){
            System.out.println("El promedio de la asignatura "+ asignaturas[i]+" es: "+ notas[i]);
        }
    }
}
