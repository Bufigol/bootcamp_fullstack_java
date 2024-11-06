package com.bufigol.s7.modelo;

import com.bufigol.s7.interfaces.InterfazVehiculo;

import java.util.Objects;

public class Auto extends Vehiculo implements InterfazVehiculo {
    private String color;
    private String modelo;
    private String tipoDeBencina;

    public Auto(int numeroDeRuedas, int cantidadDeVentanas, String color, String modelo) {
        super(numeroDeRuedas, cantidadDeVentanas);
        this.color = color;
        this.modelo = modelo;
    }

    public Auto(String color, String modelo) {
        super();
        this.color = color;
        this.modelo = modelo;
    }

    public Auto() {
        super();
    }

    @Override
    public void encender() {
        if(!this.isEncendido()){
            this.setEncendido(true);
        }else{
            System.out.println("El vehiculo ya se encuentra encendido");
        }
    }

    @Override
    public void apagar() {
        if(this.isEncendido()){
            this.setEncendido(false);
        }else{
            System.out.println("El vehiculo ya se encuentra apagado");
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


    @Override
    public String tipoDeBencina() {
        return Objects.requireNonNullElse(this.tipoDeBencina, "No definido");
    }

    @Override
    public void tipoDeBencina(String tipoDeBencina) {

        this.tipoDeBencina = tipoDeBencina;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "numeroDeRuedas=" + this.getNumeroDeRuedas() +
                ", cantidadDeVentanas=" + this.getCantidadDeVentanas() +
                ", encendido=" + this.isEncendido() +
                ", color='" + color + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tipoDeBencina='" + tipoDeBencina + '\'' +
                '}';
    }
}
