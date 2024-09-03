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
        listaDeProductos.add(new Producto(Zapatos.class,"Adidas"));
        listaDeProductos.add(new Producto(Zapatos.class,"Puma"));
        listaDeProductos.add(new Producto(Zapatos.class,"Reebok"));
        listaDeProductos.add(new Producto(Poleras.class,"Rojo"));
        listaDeProductos.add(new Producto(Poleras.class,"Blanco"));
        listaDeProductos.add(new Producto(Poleras.class,"Azul"));
        return listaDeProductos;
    }


}
