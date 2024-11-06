package com.bufigol.s7.modelo;

public class Bicicleta  extends Vehiculo {
    private String tipoDeBicicleta;

    public Bicicleta(int numeroDeRuedas, int cantidadDeVentanas, String tipoDeBicicleta) {
        super(numeroDeRuedas, cantidadDeVentanas);
        this.tipoDeBicicleta = tipoDeBicicleta;
    }

    public Bicicleta(String tipoDeBicicleta) {
        super(2,0);
        this.tipoDeBicicleta = tipoDeBicicleta;
    }

    @Override
    public void encender() {
        if(!this.isEncendido()){
            this.setEncendido(true);
        }else {
            System.out.println("El vehiculo ya se encuentra encendido");
        }
    }

    @Override
    public void apagar() {
        if(this.isEncendido()){
            this.setEncendido(false);
        }else {
            System.out.println("El vehiculo ya se encuentra apagado");
        }
    }

    public String getTipoDeBicicleta() {
        return tipoDeBicicleta;
    }

    public void setTipoDeBicicleta(String tipoDeBicicleta) {
        this.tipoDeBicicleta = tipoDeBicicleta;
    }
}
