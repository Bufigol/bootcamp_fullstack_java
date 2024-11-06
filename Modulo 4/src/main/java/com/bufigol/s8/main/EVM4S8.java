package com.bufigol.s8.main;

import com.bufigol.s8.modelo.Producto;
import com.bufigol.s8.modelo.Vegetal;
import com.bufigol.s8.modelo.Vendedor;
import com.bufigol.s8.modelo.Vestuario;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class EVM4S8 {

    public static void main(String[] args) {
        Vendedor vendedor = identificarVendedor();
        ArrayList<Producto> productosAVender = agregarListaDeProductosDisponibles();
        ArrayList<Producto> carrito = new ArrayList<Producto>();
        int opc;
        do{
            opc = mostrarMenu();
            switch (opc){
                case 1:
                    mostrarPoductos(productosAVender);
                    break;
                case 2:
                    mostrarPoductos(productosAVender);
                    int productoAComprar = pedirEntero("¿Que producto desea comprar? ") - 1;
                    carrito.add(productosAVender.get(productoAComprar));
                    int indexCarrito = 0;
                    for(int i = 0; i < carrito.size(); i++){
                        if(carrito.get(i).getCodigo() == productosAVender.get(productoAComprar).getCodigo()){
                            indexCarrito = i;
                        }
                    }
                    vendedor.agregarProductoAlCarrito(carrito.get(indexCarrito));
                    productosAVender.get(productoAComprar).setCantidad(productosAVender.get(productoAComprar).getCantidad() - 1);
                    break;
                case 3:
                    vendedor.mostrarCarrito();
                    break;
                case 4:
                    vendedor.comprarCarrito();
                    break;
                case 5:
                    System.out.println("Hasta pronto");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        }while(opc != 5);
    }

    private static void mostrarPoductos(ArrayList<Producto> productosAVender) {
        for(int i = 0; i < productosAVender.size(); i++){
            if(productosAVender.get(i).getCantidad()>0){
                System.out.println( ( i + 1 ) + ". " + productosAVender.get(i).toString());
            }
        }
    }

    private static ArrayList<Producto> agregarListaDeProductosDisponibles() {
        ArrayList<Producto> output = new ArrayList<Producto>();
        output.add(new Vegetal("Tomate", 1, 1580.0f, 10, new Date (1722541817000L), new Date (1725220217000L)));;
        output.add(new Vegetal("Lechuga", 2, 2000.0f, 15, new Date (1721073017000L), new Date (1725220217000L)));
        output.add(new Vegetal("Champiñon", 3, 1956.0f, 20, new Date (1721505017000L),new Date (1725220217000L)));

        output.add(new Vestuario("Polera",4,21000f, 5, "L", "Azul"));
        output.add(new Vestuario("Poleron",5,15000f, 5, "XL", "Rojo"));
        output.add(new Vestuario("Pantalon",6,39500f, 5, "M", "Verde"));

        return output;
    }

    private static Vendedor identificarVendedor() {
        return new Vendedor(pedirCadena("Ingrese el nombre del vendedor"),
                pedirCadena("Ingrese el identificador del vendedor"),
                new ArrayList<Producto>());
    }

    private static int mostrarMenu(){
        System.out.println(
                """
                ¿Que desea hacer?
                1. Mostrar todos los productos
                2. Comprar un producto
                3. Ver el carrito
                4. Pagar
                5. Salir
                """);
        int opc = pedirEntero("Elija una opción: ");
        if( (opc<1) || (opc>5) ){
             return mostrarMenu();
        }else{
            return opc;
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
    private static String pedirCadena(String msg) {
        try{
            System.out.println(msg);
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (RuntimeException e) {
            return pedirCadena(msg);
        }
    }
}
