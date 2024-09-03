package com.bufigol.s9.main;

import com.bufigol.s9.modelo.Poleras;
import com.bufigol.s9.modelo.Producto;
import com.bufigol.s9.modelo.Zapatos;

import java.util.ArrayList;

public class EVM4S9 {

    public static void main(String[] args) {

        ArrayList<Producto> listaDeProductos = cargarDatos();
        mostrarDetalles(listaDeProductos);

    }

    private static void mostrarDetalles(ArrayList<Producto> listaDeProductos) {
        for (Producto producto : listaDeProductos) {
            System.out.println(producto.toString());
        }
    }
    private static ArrayList<Producto> cargarDatos() {
        ArrayList<Producto> listaDeProductos = new ArrayList<>();
        listaDeProductos.add(new Producto(new Zapatos(),"Adidas"));
        listaDeProductos.add(new Producto(new Zapatos(),"Puma"));
        listaDeProductos.add(new Producto(new Zapatos(),"Reebok"));
        listaDeProductos.add(new Producto(new Poleras(),"Rojo"));
        listaDeProductos.add(new Producto(new Poleras(),"Blanco"));
        listaDeProductos.add(new Producto(new Poleras(),"Azul"));
        return listaDeProductos;
    }


}
