package com.bufigol.s8.modelo;

import java.util.ArrayList;
import java.util.Scanner;

public class Vendedor extends Persona implements TareasDeVendedor{
    private ArrayList<Producto> carrito;

    public Vendedor(String nombre, String identificador, ArrayList<Producto> carrito) {
        super(nombre, identificador);
        this.carrito = carrito;
    }

    public Vendedor(String nombre, String identificador) {
        super(nombre, identificador);
        this.carrito = new ArrayList<>();
    }

    public Vendedor() {
        super();
        this.carrito = new ArrayList<>();
    }

    @Override
    public void agregarProductoAlCarrito(Producto producto) {
        if(carrito == null) {
            carrito = new ArrayList<>();
        }
        int i=0;
        boolean encontrado = false;
        if(carrito.isEmpty()) {
            producto.setCantidad(1);
            carrito.add(producto);
        }else{
            do{
                if(carrito.get(i).getCodigo() == producto.getCodigo()) {
                    carrito.get(i).setCantidad(carrito.get(i).getCantidad() + 1);
                    encontrado = true;
                }
                i++;
            }while( i<carrito.size() && !encontrado);
            if(!encontrado) {
                producto.setCodigo(i);
                carrito.add(producto);
            }
        }

    }

    @Override
    public void vaciarCarrito() {
        if(carrito == null) {
            carrito = new ArrayList<>();
        }
        carrito.clear();
    }

    @Override
    public void mostrarCarrito() {
        for(Producto p : carrito) {
            if(p instanceof Vegetal v) {
                System.out.println(v.toString());
            }
            if(p instanceof Vestuario v) {
                System.out.println(v.toString());
            }
        }
    }

    @Override
    public void comprarCarrito() {
        float total=0;
        for(Producto p : carrito) {
            total += p.getPrecio();
        }
        System.out.println("El total de la compra es: " + total);
        String opc = pedirCadena("¿Quiere realizar la compra? (s/n)");
        if(opc.equals("s") || opc.equals("S") || opc.equals("n") || opc.equals("N")) {
            if (opc.equals("s") || opc.equals("S")) {
                vaciarCarrito();
                System.out.println("La compra ha sido realizada");
            }
            if (opc.equals("n") || opc.equals("N")) {
                System.out.println("La compra ha sido cancelada");
            }
        }else{
            System.out.println("Opcion no valida");
            comprarCarrito();
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
