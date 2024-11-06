package com.bufigol.s18.EVM4S18.Main;

import com.bufigol.s18.EVM4S18.Modelo.Mercado;
import com.bufigol.s18.EVM4S18.Modelo.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create a new market
        Mercado mercado = new Mercado();

        // Create a new list of products
        List<Producto> listaProductos = new ArrayList<>();

        // Create a scanner to input product details
        Scanner scanner = new Scanner(System.in);

        // Input product details
        System.out.print("Introduzca el nombre de su producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Introduzca el ID de su producto: ");
        int idProducto = scanner.nextInt();
        System.out.print("¿Cuál es la condición de su producto? ");
        String condicion = scanner.next();
        System.out.print("¿Cuál es el precio de su producto? ");
        String precio = scanner.next();

        // Create a new product
        Producto producto = Producto.crearAviso(nombre, idProducto, condicion, precio);

        // Add the product to the list
        listaProductos.add(producto);

        // Publish the product to the market
        listaProductos = mercado.publicarAviso(listaProductos, producto);

        // Print the list of products
        mercado.verTodoAviso(listaProductos);
    }
}