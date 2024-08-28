package com.bufigol.s6;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ECM4S6 {
    private static Map<String, Persona> personasMap;
    public static void main(String[] args) {
        cargarPersonas();
        if(ingresar()){
            int eleccion = pedirEntero(mostrarMenu());
            switch (eleccion){
                case 1:
                    raizCuadrada(pedirEntero("Ingrese un número: "));
                    break;
                case 2:
                    mostrarPi();
                    break;
                case 3:
                    potencia(pedirEntero("Ingrese la base: "),pedirEntero("Ingrese el exponente: "));
                    break;
                default:
                    System.out.println("Opcion incorrecta, hasta luego");
                    break;
            }
        }
    }

    private static void potencia(int base, int exponente) {
        System.out.println(base+" elevado a "+exponente+" es "+Math.pow(base,exponente));
    }

    private static void mostrarPi() {
        System.out.println("Pi es "+Math.PI);
    }

    private static void raizCuadrada(int i) {
        System.out.println("La raíz cuadrada de "+i+" es "+ Math.sqrt(i));
    }

    private static String mostrarMenu() {
        return """
                ¿Que desea realizar?
                1- Calcular la raíz cuadrada de un número
                2- Ver el valor de pi
                3- Calcular la potencia de un número a otro numero
                """;
    }

    private static boolean ingresar() {
        Persona personaEncontrada;
        do {
            String buscarNombre = pedirCadena("Ingrese su nombre de usuario: ");
            personaEncontrada = personasMap.get(buscarNombre);
            if (personaEncontrada == null) {
                System.out.println("Usuario no encontrado");
            }
        }while (personaEncontrada == null);

        System.out.println("Bienvenido "+personaEncontrada.getNombre());

        int i = 0;
        do{
            String password = pedirCadena("Ingrese su contraseña: ");
            if(!personaEncontrada.verifyPassword(password)){
                System.out.println("Contraseña incorrecta");
                i++;
            }else{
                System.out.println("Contraseña correcta");
                return true;
            }
        }while(i<3);
        return false;
    }

    private static void cargarPersonas() {
        personasMap = new HashMap<>();
        personasMap.put("juan", new Persona("Juan Pérez", "contraseña123","juan"));
        personasMap.put("maria", new Persona("María García", "clave456","maria"));
        personasMap.put("carlos", new Persona("Carlos Rodríguez", "segura789","carlos"));
        personasMap.put("ana", new Persona("Ana Martínez", "acceso000","ana"));
    }
    private static int pedirEntero(String msg) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    private static String pedirCadena(String msg) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
