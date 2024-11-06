package com.bufigol.s2;

import java.util.Scanner;

public class ECM4S2  {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Ingrese su nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("¿Cuantos productos va a llevar? Total Productos: ");
        int cantidadProductos = teclado.nextInt();
        double total = 0;
        Producto[] lista_productos = new Producto[cantidadProductos];

        for (int i=0; i<cantidadProductos; i++){
            System.out.print("Ingrese el precio unitario del producto " + (i+1) +": ");
            double precio = teclado.nextDouble();
            System.out.print("Ingrese la cantidad del producto " + (i+1) +": ");
            int cantidad = teclado.nextInt();
            lista_productos[i] = new Producto(precio, cantidad);
            total+=lista_productos[i].getTotal();
        }
        if ( total > 20000 ) {
            System.out.println(nombre + ", pagará con cheque el total de: " + total);
        } else if ( total > 10000 ) {
            System.out.println(nombre + ", pagará con tarjeta el total de: " + total);
        } else {
            System.out.println(nombre + ", pagará con efectivo el total de: " + total);

        }
    }
}
