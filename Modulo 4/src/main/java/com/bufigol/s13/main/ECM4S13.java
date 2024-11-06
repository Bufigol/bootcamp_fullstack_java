package com.bufigol.s13.main;

import com.bufigol.utils.ComprobacionesVarias;
import com.bufigol.utils.EntradaPorTeclado;

import java.util.ArrayList;
import java.util.Iterator;

public class ECM4S13 {
    public static void main(String[] args) {
        ArrayList<Integer> lista = leerValores();
        System.out.println("La suma de todos los numeros es: " + calcularSuma(lista));
        System.out.println(mostrarResultados(lista));
    }

    private static String mostrarResultados(ArrayList<Integer> lista) {
        int media = calcularSuma(lista) / lista.size();
        int mayores = 0;
        int menores = 0;
        int mediaIgual = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("La media es: ")
                .append(media)
                .append("\n");
        for (int num : lista) {
            if (num > media) {
                mayores++;
            }
            if (num < media) {
                menores++;
            }
            if (num == media) {
                mediaIgual++;
            }
        }
        sb.append("Los numeros mayores que la media son: ")
                .append(mayores)
                .append("\n");
        sb.append("Los numeros menores que la media son: ")
                .append(menores)
                .append("\n");
        sb.append("Los numeros iguales que la media son: ")
                .append(mediaIgual)
                .append("\n");
        return sb.toString();
    }

    private static int calcularSuma(ArrayList<Integer> lista) {
        Iterator<Integer> it = lista.iterator();
        int suma = 0;
        while(it.hasNext()){
            suma += it.next();
        }
        return suma;
    }


    private static ArrayList<Integer> leerValores() {
        int numeroEntrada;
        ArrayList<Integer> lista = new ArrayList<>();
        while ( ( numeroEntrada = EntradaPorTeclado.pedirEntero("Introduce un numero") ) != -99 ) {
            lista.add(numeroEntrada);
        };
        return lista;
    }


}
