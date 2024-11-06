package com.bufigol.s7.modelo;

import com.bufigol.s7.interfaces.Productointerface;

public class Refrigerador  extends Producto implements Productointerface {
    private char tipoGastoElectrico;
    private int cantidadDePuertas;


    public Refrigerador(int precio, int cantidad, char tipoGastoElectrico, int cantidadDePuertas) {
        super(precio, cantidad);
        this.tipoGastoElectrico = checkTipoDeGasto(tipoGastoElectrico);
        this.cantidadDePuertas = cantidadDePuertas;
    }



    public Refrigerador(char tipoGastoElectrico, int cantidadDePuertas) {
        super(0, 0);
        this.tipoGastoElectrico = tipoGastoElectrico;
        this.cantidadDePuertas = cantidadDePuertas;
    }

    public char getTipoGastoElectrico() {
        return tipoGastoElectrico;
    }

    public void setTipoGastoElectrico(char tipoGastoElectrico) {
        this.tipoGastoElectrico = checkTipoDeGasto(tipoGastoElectrico);
    }

    public int getCantidadDePuertas() {
        return cantidadDePuertas;
    }

    public void setCantidadDePuertas(int cantidadDePuertas) {
        this.cantidadDePuertas = cantidadDePuertas;
    }

    @Override
    public String toString() {
        return "Refrigerador{" +
                "precio=" + this.getPrecio() +
                ", cantidad=" + this.getCantidad() +
                ", tipoGastoElectrico=" + tipoGastoElectrico +
                ", cantidadDePuertas=" + cantidadDePuertas +
                '}';
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

    private char checkTipoDeGasto(Character tipoGastoElectrico) {
        char comparar = Character.toLowerCase(tipoGastoElectrico);
        if(comparar == 'a' || comparar == 'b' || comparar == 'c' || comparar == 'd' || comparar == 'e') {
            return Character.toUpperCase(tipoGastoElectrico);
        } else {
            return 'E';
        }
    }
}
