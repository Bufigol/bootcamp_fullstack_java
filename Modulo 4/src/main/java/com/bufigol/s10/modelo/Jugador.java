package com.bufigol.s10.modelo;

public class Jugador extends Persona {
    private int numeroCamiseta;
    private String posicion;

    public Jugador(String nombre, String apellidos, int edad, int numeroCamiseta, String posicion) {
        super(nombre, apellidos, edad);
        this.numeroCamiseta = numeroCamiseta;
        this.posicion = posicion;
    }

    public Jugador() {
        super();
    }

    @Override
    public void hablar(){
        System.out.println("Soy un jugador");
    }

    @Override
    public void celebrar(){
        System.out.println("Soy un jugador que ha marcado un gol!");
    }

    public void correr(){
        System.out.println("Soy un jugador que corre");
    }

    public void saltar(){

    }

    public void quitarBalon(){

    }

    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }

    public void setNumeroCamiseta(int numeroCamiseta) {
        this.numeroCamiseta = numeroCamiseta;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Jugador{").append("\n");
        sb.append("Nombre: ").append(getNombre()).append("\n");
        sb.append(", Apellidos: ").append(getApellidos()).append("\n");
        sb.append(", Edad: ").append(getEdad()).append("\n");
        sb.append("numeroCamiseta=").append(numeroCamiseta).append("\n");
        sb.append(", posicion='").append(posicion).append("\n");
        sb.append('}');
        return sb.toString();
    }
}
