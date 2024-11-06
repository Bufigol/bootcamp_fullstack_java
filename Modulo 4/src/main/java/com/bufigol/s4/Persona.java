package com.bufigol.s4;

public class Persona {
    private String nombre;
    private int edad;
    private float peso;
    private float altura;

    public Persona(String nombre, int edad, float peso, float altura) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
    }
    public Persona() {
    }

    public float getImc() {
        return (peso / (altura * altura));
    }
    public String getNombre() {
        return nombre;
    }
    public String getCalificacionImc() {
        String out = "";
        float imc = getImc();
        if (imc < 16) {
            out = "Delgadez Severa";
        }else if(imc >= 16 && imc < 17) {
            out = "Delgadez moderada";
        }else if(imc >= 17 && imc < 18.5) {
            out = "Delgadez leve";
        }else if(imc >= 18.5 && imc < 25) {
            out = "Peso normal";
        }else if(imc >= 25 && imc < 30) {
            out = "Preobesidad";
        }else if(imc >= 30 && imc < 35) {
            out = "Obesidad leve";
        }else if(imc >= 35 && imc < 40) {
            out = "Obesidad media";
        }else if(imc >= 40) {
            out = "Obesidad morbida";
        }
        return out;
    }
    public void getImcInfo() {
        System.out.println("Calculando su IMC...");
        System.out.println("Su Indice de Masa Muscular es: " + getImc() + "\nUsted se encuentra con: " + getCalificacionImc());
    }
    public void esMayorDeEdad() {
        System.out.println("Su edad es: "+ getEdad());
        if(edad >= 18) {
            System.out.println("Usted es mayor de edad");
        }else {
            System.out.println("Usted es menor de edad");
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return "Persona[" +
                "nombre='" + nombre +
                ", edad=" + edad +
                ", peso=" + peso +
                ", altura=" + altura +
                ']';
    }
}
