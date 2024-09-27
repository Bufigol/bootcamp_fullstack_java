package com.bufigol.s18.EVM4S18.Modelo;

import java.util.List;

public class Mercado {
    private List<Producto> productos;

    public Mercado(List<Producto> productos) {
        this.productos = productos;
    }

    public Mercado() {
        this.productos = new java.util.ArrayList<>();
    }

    public List<Producto> publicarAviso(List<Producto> listaProductos, Producto producto) {
        listaProductos.add(producto);
        System.out.println("Su producto ha sido subido al mercado online.");
        return listaProductos;
    }

    public void verTodoAviso(List<Producto> listaProductos) {
        System.out.println("Los productos disponibles en el mercado ahora son:");
        for (Producto producto : listaProductos) {
            System.out.println(producto);
        }
    }
}
