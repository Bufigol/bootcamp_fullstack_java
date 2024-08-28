package com.bufigol.s4;

public class Auto {
    private String marca;
    private String modelo;
    private String color;
    private float velocidadMaxima;
    public float velocidadActual;
    private boolean encendido;

    public Auto() {
        this.encendido = false;
        this.velocidadActual = 0;
    }

    public Auto(String marca, String modelo, String color, float velocidadMaxima) {
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.velocidadMaxima = velocidadMaxima;
        this.velocidadActual = 0;
        this.encendido = false;

    }
    public void encender(){
        if(!this.encendido) {
            this.encendido = true;
            this.velocidadActual = 0;
            System.out.println("El auto ha sido encendido");
        }else{
            System.out.println("El auto ya estaba encendido");
        }
    }

    public void apagar(){
        if(this.encendido) {
            this.encendido = false;
            this.velocidadActual = 0;
            System.out.println("El auto ha sido apagado");
        }else {
            System.out.println("El auto ya estaba apagado");
        }
    }
    public void acelerar(){
        acelerar(10);
    }
    public void acelerar(int incremento){
        if(this.encendido) {
            if(this.velocidadActual + incremento > this.velocidadMaxima) {
                System.out.println("El auto no puede superar la velocidad maxima");
                this.velocidadActual = this.velocidadMaxima;
            }else {
                this.velocidadActual += incremento;
            }
        }else {
            System.out.println("El auto no esta encendido");
        }
    }
    public void frenar(){
        if(this.encendido) {
            if(this.velocidadActual - 10 < 0) {
                System.out.println("El auto ya no puede frenar mas, ya estas detenido.-");
                this.velocidadActual = 0;
            }else {
                this.velocidadActual -= 10;
            }
        }else {
            System.out.println("El auto no esta encendido");
        }
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public void setVelocidadMaxima(float velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }

    public boolean isEncendido() {
        return encendido;
    }

    public void setEncendido(boolean encendido) {
        this.encendido = encendido;
    }

    public float getVelocidadActual() {
        return velocidadActual;
    }

    public void setVelocidadActual(float velocidadActual) {
        this.velocidadActual = velocidadActual;
    }

    @Override
    public String toString() {
        return "Auto [" +
                "marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", color='" + color + '\'' +
                ", velocidadMaxima=" + velocidadMaxima +
                ", encendido=" + encendido +
                ']';
    }
}
