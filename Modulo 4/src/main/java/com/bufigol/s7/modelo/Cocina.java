package com.bufigol.s7.modelo;

import com.bufigol.s7.interfaces.CocinaInterface;

public class Cocina  extends Producto implements CocinaInterface {
    private int cantidadQuemadores;
    private boolean gas;

    public Cocina(int precio, int cantidad, int cantidadQuemadores) {
        super(precio, cantidad);
        this.cantidadQuemadores = cantidadQuemadores;
    }

    public Cocina(int cantidadQuemadores) {
        super(0,0);
        this.cantidadQuemadores = cantidadQuemadores;
    }

    public int getCantidadQuemadores() {
        return cantidadQuemadores;
    }

    public void setCantidadQuemadores(int cantidadQuemadores) {
        this.cantidadQuemadores = cantidadQuemadores;
    }

    @Override
    public String toString() {
        if(this.gas) {
            return "Cocina{" +
                    "precio=" + this.getPrecio() +
                    ", cantidad=" + this.getCantidad() +
                    ", cantidadQuemadores=" + cantidadQuemadores +
                    ", a gas" +
                    '}';
        }else {
            return "Cocina{" +
                    "precio=" + this.getPrecio() +
                    ", cantidad=" + this.getCantidad() +
                    ", cantidadQuemadores=" + cantidadQuemadores +
                    ", electrica" +
                    '}';
        }
    }

    @Override
    public void gas(boolean gas) {
        this.gas = gas;
    }

    @Override
    public boolean gas() {
        return this.gas;
    }

    @Override
    public void cambiarPrecio(int precio) {
        this.setPrecio(precio);
    }

    @Override
    public int cantidadDisponible() {
        return this.getCantidad();
    }

    @Override
    public void cantidadDisponible(int disponible) {
        this.setCantidad(disponible);
    }
}
