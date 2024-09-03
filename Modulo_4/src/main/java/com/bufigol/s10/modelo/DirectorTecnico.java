package com.bufigol.s10.modelo;

public class DirectorTecnico extends Persona{
    private int añosEnActivo;

    public DirectorTecnico(String nombre, String apellidos, int edad, int añosEnActivo) {
        super(nombre, apellidos, edad);
        this.añosEnActivo = añosEnActivo;
    }

    public DirectorTecnico() {
        super();
    }

    @Override
    public void hablar(){
        System.out.println("Soy un Director Tecnico");
    }

    @Override
    public void celebrar(){
        System.out.println("Soy un jugador al que dirijo ha marcado un gol!");
    }

    public int getAñosEnActivo() {
        return añosEnActivo;
    }

    public void setAñosEnActivo(int añosEnActivo) {
        this.añosEnActivo = añosEnActivo;
    }

    @Override
    public String toString() {
        return "DirectorTecnico{" + "\n" +
                "Nombre: " + getNombre() + "\n" +
                ", Apellidos: " + getApellidos() + "\n" +
                ", Edad: " + getEdad() + "\n" +
                ", Años En Activo=" + añosEnActivo +
                '}';
    }
}