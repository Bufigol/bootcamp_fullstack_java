package com.bufigol.s8.modelo;

import java.time.Instant;
import java.util.Date;

public class Vegetal extends Producto{
    private Date fechaDeEnvasado;
    private Date fechaDeCaducidad;

    public Vegetal(String nombre, int codigo, float precio,int cantidad, Date fechaDeEnvasado, Date fechaDeCaducidad) {
        super(nombre, codigo, precio,cantidad);
        this.fechaDeEnvasado = fechaDeEnvasado;
        this.fechaDeCaducidad = fechaDeCaducidad;
    }

    public Vegetal(String nombre, int codigo, float precio, int cantidad) {
        super(nombre, codigo, precio,cantidad);
        this.fechaDeCaducidad = Date.from(Instant.now());
        this.fechaDeEnvasado = Date.from(Instant.now());
    }

    public Vegetal() {
        super();
    }

    public Date getFechaDeEnvasado() {
        return fechaDeEnvasado;
    }

    public void setFechaDeEnvasado(Date fechaDeEnvasado) {
        this.fechaDeEnvasado = fechaDeEnvasado;
    }

    public Date getFechaDeCaducidad() {
        return fechaDeCaducidad;
    }

    public void setFechaDeCaducidad(Date fechaDeCaducidad) {
        this.fechaDeCaducidad = fechaDeCaducidad;
    }

    @Override
    public String toString() {
        return "Vegetal { nombre = " + this.getNombre() +
                ", codigo = " + this.getCodigo() +
                ", precio = " + this.getPrecio() +
                ", cantidad = " + this.getCantidad() +
                ", fecha de envasado = " + this.getFechaDeEnvasado() +
                ", fecha de Caducidad = " + this.getFechaDeCaducidad() + " }";
    }
}
