package com.bufigol.s11;

import com.bufigol.s11.modelo.Duegno;
import com.bufigol.s11.modelo.Pelicula;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ejecucion();
    }

    private static void ejecucion() {
        ArrayList<Pelicula> listaPeliculas = cargarPeliculas();
        Duegno duegno = Duegno.getInstance();
        int opcion;
        do {
            opcion = mostrarMenu();
            switch (opcion){
                case 1:
                    mostrarListaPeliculas(listaPeliculas,1);
                    break;
                case 2:
                    mostrarListaPeliculas(listaPeliculas,2);
                    break;
                case 3:
                    arrendarPeli(listaPeliculas);
                    duegno.getionarPago();
                    break;
                case 4:
                    devolverPeli(listaPeliculas);
                    break;
                case 5:
                    System.out.println("Hasta Pronto");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while (opcion != 5);
    }


    private static int[] indexNoDisponibles(ArrayList<Pelicula> listaPeliculas) {
        ArrayList<Integer> aux = new ArrayList<>();
        int b=0;
        for(Pelicula peli : listaPeliculas){
            if(peli.getArrendada()) {
                aux.add(b);
            }
            b++;
        }
        int[] out = new int[aux.size()];
        for (int i = 0; i < out.length; i++){
            out[i] = aux.get(i);
        }
        return out;
    }

    private static void arrendarPeli(ArrayList<Pelicula> listaPeliculas) {
        int opcPeli = pedirEntero("Ingresa el numero de la pelicula que deseas arrendar");
        mostrarListaPeliculas(listaPeliculas,3);
        int[] indexDisponible = indexDisponibles(listaPeliculas);
        if(comprobarPeliDisponible(opcPeli,indexDisponible)){
            listaPeliculas.get(opcPeli).setArrendada(true);
        }else{
            arrendarPeli(listaPeliculas);
        }

    }
    private static void devolverPeli(ArrayList<Pelicula> listaPeliculas) {
        int opcPeli = pedirEntero("Ingresa el numero de la pelicula que se va a devolver:");
        mostrarListaPeliculas(listaPeliculas,3);
        int[] indexDisponible = indexNoDisponibles(listaPeliculas);
        if(comprobarPeliDisponible(opcPeli,indexDisponible)){
            listaPeliculas.get(opcPeli).setArrendada(false);
        }else{
            devolverPeli(listaPeliculas);
        }

    }

    private static boolean comprobarPeliDisponible(int opcPeli, int[] indexDisponible) {
        boolean out = false;
        for (int j : indexDisponible) {
            out = (opcPeli == j);
        }
        return out;
    }

    private static int[] indexDisponibles(ArrayList<Pelicula> listaPeliculas) {
        ArrayList<Integer> aux = new ArrayList<>();
        int b=0;
        for(Pelicula peli : listaPeliculas){
            if(!peli.getArrendada()) {
                aux.add(b);
            }
            b++;
        }
        int[] out = new int[aux.size()];
        for (int i = 0; i < out.length; i++){
            out[i] = aux.get(i);
        }
        return out;
    }

    private static void mostrarListaPeliculas(ArrayList<Pelicula> listaPeliculas,int opc) {
        switch (opc){
            case 1:
                for(Pelicula peli : listaPeliculas){
                    System.out.println(peli.toString());
                }
                break;
            case 2:
                for(Pelicula peli : listaPeliculas){
                    if(!peli.getArrendada()) {
                        System.out.println(peli.toString());
                    }
                }
                break;
            case 3:
                int i=0;
                for(Pelicula peli : listaPeliculas){
                    if(!peli.getArrendada()) {
                        System.out.println( (i + 1 ) + peli.toString());
                    }
                }
        }


    }

    private static ArrayList<Pelicula> cargarPeliculas() {
        ArrayList<Pelicula> listaPeliculas = new ArrayList<>();
        Pelicula pelicula1 = new Pelicula("Star Wars: Episode IV - A New Hope", 1977);
        Pelicula pelicula2 = new Pelicula("Star Wars: Episode V - The Empire Strikes Back", 1980);
        Pelicula pelicula3 = new Pelicula("Star Wars: Episode VI - Return of the Jedi", 1983);
        listaPeliculas.add(pelicula1);
        listaPeliculas.add(pelicula2);
        listaPeliculas.add(pelicula3);
        return listaPeliculas;
    }
    private static int mostrarMenu(){

        int out = pedirEntero("""
                Selecciona la opcion que desees:
                1.- Ver lista de peliculas
                2.- Ver lista de peliculas disponibles.
                3.- Arrendar pelicula
                4.- Devolver PElicula
                5.- Cerrar Programa
                """);
        if( (out < 6 ) && ( out > 0 ) ){
            return out;
        } else {
            System.out.println("Opcion no valida.");
            return mostrarMenu();
        }
    }
    private static int pedirEntero(String msg) {
        try{
            System.out.println(msg);
            Scanner scanner = new Scanner(System.in);
            return scanner.nextInt();
        } catch (RuntimeException e) {
            return pedirEntero(msg);
        }
    }
}
