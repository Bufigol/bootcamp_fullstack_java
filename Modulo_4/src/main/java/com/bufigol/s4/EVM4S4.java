package com.bufigol.s4;

import java.util.Scanner;

public class EVM4S4 {
    public static void main(String[] args) {

        start(new Auto("Lamborghini",
                "Urus", "Blanco", 305));
        int [][] números;
    }

    private static void start(Auto auto) {
        System.out.println("Felicidades por su nuevo "+auto.toString());
        boolean seguir = true;
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    auto.encender();
                    break;
                case 2:
                    auto.apagar();
                    break;
                case 3:
                    auto.acelerar();
                    if (auto.isEncendido() && auto.getVelocidadActual() > 0 && auto.getVelocidadActual() < auto.getVelocidadMaxima()) {
                        System.out.println("Velocidad actual: " + auto.getVelocidadActual());
                    }
                    break;
                case 4:
                    auto.frenar();
                    if(auto.isEncendido() && auto.getVelocidadActual() > 0 && auto.getVelocidadActual() < auto.getVelocidadMaxima()) {
                        System.out.println("Velocidad actual: " + auto.getVelocidadActual());
                    }
                    break;
                case 5:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        } while (seguir);

    }

    private static void mostrarMenu(){
        System.out.println("Seleccione lo que desea hacer:");
        System.out.println("1. Encender el auto");
        System.out.println("2. Apagar el auto");
        System.out.println("3. Acelerar el auto");
        System.out.println("4. Frenar el auto");
        System.out.println("5. Salir");
    }
}
