package com.bufigol.s5;

import java.util.ArrayList;
import java.util.Scanner;

public class EVM4S5 {

    public static void main(String[] args) {
        ArrayList<Producto> listaDeProductos =  primeraCargaDeProductos();
        int eleccion = 0;
        while (eleccion !=4){
            mostrarMenu();
            eleccion= pedirEntero();
            switch(eleccion){
                case 1:
                    mostrar(listaDeProductos);
                    break;
                case 2:
                    listaDeProductos = agregar(listaDeProductos);
                    break;
                case 3:
                    listaDeProductos = modificar(listaDeProductos);
                    break;
                case 4:
                   System.out.println("Hasta pronto");
                   break;
                default:
                   System.out.println("Opcion incorrecta, intente de nuevo");
                   break;
           }
       };
    }

    private static ArrayList<Producto>  modificar(ArrayList<Producto> productos) {
        for(int i = 0; i < productos.size(); i++) {
            System.out.println( (i + 1) + "- " + productos.get(i).toString());
        }
        System.out.print("Elija el producto a modificar: ");
        int o = pedirEntero();
        o -= 1;
        System.out.println("Ingrese el nombre  modificado del producto: ");
        String aux = pedirCadena();
        productos.get(o).setNombre(aux);
        System.out.println("El valor modificado del  producto: ");
        productos.get(o).setValor(pedirEntero());
        System.out.println("La descripción modificada del producto: ");
        aux = pedirCadena();
        productos.get(o).setDescripcion(aux);
        System.out.println("Se ha modificado el producto, y ha quedado de la siguiente manera:\n"+productos.get(o));
        
        return productos;
    }

    private static ArrayList<Producto> agregar(ArrayList<Producto> productos) {
        Producto nuevo= new Producto();
        if(productos.size() < 7){
            System.out.println("Ingrese el nombre del nuevo producto: ");
            String aux = pedirCadena();
            nuevo.setNombre(aux);
            System.out.println("El valor del nuevo producto: ");
            nuevo.setValor(pedirEntero());
            System.out.println("La descripción del nuevo producto: ");
            aux = pedirCadena();
            nuevo.setDescripcion(aux);
            productos.add(nuevo);
        }else{
           System.out.println("Error, ya se tiene el maximo numero de productos");
        }
        return productos;
    }

    private static void mostrar(ArrayList<Producto>productos) {
        for (Producto producto : productos) {
            System.out.println(producto.toString());
        }
    }

    private static void mostrarMenu() {
        System.out.println(
        """
        Elija una opción:
        1- Ver productos
        2- Agregar producto
        3- Modificar producto
        4- Salir
        """
        );
    }
    private static ArrayList<Producto> primeraCargaDeProductos(){
        ArrayList<Producto> out = new ArrayList<>();
        out.add(new Producto("chocolate", 500 ,"Chocolate amargo, 500 gr."));
        out.add(new Producto("leche", 800, "Leche entera, 1 litro."));
        out.add(new Producto("arroz", 900, "Arroz grado 2, 1 kilo."));
        return out;
    }
    private static int pedirEntero() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    private static String pedirCadena() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
