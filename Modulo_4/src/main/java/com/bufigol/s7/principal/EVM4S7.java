package com.bufigol.s7.principal;

import com.bufigol.s7.modelo.Cocina;
import com.bufigol.s7.modelo.Producto;
import com.bufigol.s7.modelo.Refrigerador;

import java.util.Scanner;

public class EVM4S7 {

    public static void main(String[] args) {
        Producto[] productos = cargarDatos();
        int op = mostrarMenu();
        do{
            switch (op){
                case 1:
                    productos = comprar(productos,1);
                    break;
                case 2:
                    productos = comprar(productos,2);
                    break;
                case 3:
                    System.out.println("Hasta pronto");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
            op = mostrarMenu();
        }while(op != 3);
    }

    private static Producto[] comprar(Producto[] productos, int i) {
        if(i == 1){
            return comprarCocina(productos);
        }else{
            return comprarRefrigerador(productos);
        }
    }

    private static Producto[] comprarRefrigerador(Producto[] productos) {
        Scanner sc = new Scanner(System.in);
        Refrigerador ref = (Refrigerador) productos[1];
        if(ref.getCantidad()>0){
            System.out.println("Existen "+ref.getCantidad()+" refrigeradores disponibles");
            System.out.println("Cuantos refrigeradores desea comprar?");
            int cant = sc.nextInt();
            if(cant > ref.getCantidad()){
                System.out.println("No hay suficientes refrigeradores");
                return productos;
            }else{
                if(confirmarCompra(ref)){

                    ref.setCantidad(ref.getCantidad()-cant);
                    System.out.println("Ha comprado "+cant+" refrigeradores");
                    productos[1] = ref;
                }
                return productos;
            }
        }else{
            System.out.println("No hay refrigeradores disponibles");
            return productos;
        }
    }

    private static boolean confirmarCompra(Producto ref) {
        boolean confirmar = false;
        Scanner sc = new Scanner(System.in);
        System.out.print("Usted va a comprar el siguiente producto: ");
        if(ref instanceof Cocina aux){
            System.out.println(aux.toString());
        }
        if(ref instanceof Refrigerador aux){
            System.out.println(aux.toString());
        }
        System.out.println("Desea comprar? (s/n)");
        if(sc.nextLine().equalsIgnoreCase("s")){
            confirmar = true;
        }
        return confirmar;
    }

    private static Producto[] comprarCocina(Producto[] productos) {
        Scanner sc = new Scanner(System.in);
        Cocina coc = (Cocina) productos[0];
        if(coc.getCantidad()>0){
            System.out.println("Existen "+coc.getCantidad()+" cocinas disponibles");
            System.out.println("Cuantas cocinas desea comprar?");
            int cant = sc.nextInt();
            if(cant > coc.getCantidad()){
                System.out.println("No hay suficientes cocinas");
                return productos;
            }else{
                productos[0] = gasOElectrico(productos[0]);
                if(confirmarCompra(coc)){
                    coc.setCantidad(coc.getCantidad()-cant);
                    System.out.println("Ha comprado "+cant+" cocinas");
                    productos[0] = coc;
                }
                return productos;
            }

        }else{
            System.out.println("No hay cocinas disponibles");
            return productos;
        }
    }

    private static Producto gasOElectrico(Producto producto) {
        Scanner sc = new Scanner(System.in);
        if(producto instanceof Cocina){
            System.out.println("Desea comprar gas o electrico? (g/e)");
            try{
                ((Cocina) producto).gas(sc.nextLine().equalsIgnoreCase("g"));
            }catch (Exception e){
                System.out.println("Opcion no valida");
                return gasOElectrico(producto);
            }

        }
        return producto;
    }


    private static Producto[] cargarDatos(){
        Producto[] productos = new Producto[2];
        productos[0] = new Cocina(250000, 10,4);
        productos[1] = new Refrigerador(270000, 20,'A',4);
        return productos;
    }
    private static int mostrarMenu(){
        int op = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println(
                """
                1. Comprar Cocina
                2. Comprar Refrigerador
                3. Salir
                """);
        try{
            op = sc.nextInt();
        }catch (Exception e){
            System.out.println("Opcion no valida");
            return mostrarMenu();
        }
        return op;
    }
}
