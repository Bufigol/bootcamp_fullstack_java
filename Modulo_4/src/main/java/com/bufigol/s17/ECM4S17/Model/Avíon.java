package com.bufigol.s17.ECM4S17.Model;

import com.bufigol.utils.EntradaPorTeclado;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Avíon {

    private String marca;
    private String modelo;
    private boolean listoParaDespegar;
    private Alas alas;
    private Motores motores;
    private Ruedas ruedas;
    private Cabina cabina;


    public Avíon(String marca, String modelo, boolean listoParaDespegar, Alas alas, Motores motores, Ruedas ruedas, Cabina cabina) {
        this.marca = marca;
        this.modelo = modelo;
        this.listoParaDespegar = listoParaDespegar;
        this.alas = alas;
        this.motores = motores;
        this.ruedas = ruedas;
        this.cabina = cabina;
    }

    public Avíon(String marca, String modelo,String alaLargoEnMetros, boolean alaBuenEstado,
                 int cabinaCapacidadPasajeros, int cabinaNumeroPuesrtas, boolean cabinaPuertasCerradas, boolean cabinaBuenEstado,
                 boolean motoresTieneCombustible, boolean motoresBuenEstado,
                 boolean ruedasIinfladas, boolean ruedasBuenEstado) {
        this.marca = marca;
        this.modelo = modelo;
        this.listoParaDespegar = false;
        this.alas = new Alas(alaLargoEnMetros, alaBuenEstado);
        this.cabina = new Cabina(cabinaCapacidadPasajeros, cabinaNumeroPuesrtas, cabinaPuertasCerradas, cabinaBuenEstado);
        this.motores = new Motores(motoresTieneCombustible, motoresBuenEstado);
        this.ruedas = new Ruedas(ruedasIinfladas, ruedasBuenEstado);
    }

    /**
     * Default constructor
     */
    public Avíon() {
    }

    public void recibirPasajeros() {
        System.out.println("Se va a recibir pasajeros");
        int pasajerosARecibir = EntradaPorTeclado.pedirEntero("Número de pasajeros a recibir");
        if(pasajerosARecibir > 0 && pasajerosARecibir <= this.cabina.getCapacidadPasajeros()) {
            System.out.println("Se recibieron " + pasajerosARecibir + " pasajeros");
        }else {
            System.out.println("No se puede recibir " + pasajerosARecibir + " pasajeros");
            recibirPasajeros();
        }

    }

    /**
     * 
     */
    public void despegar() {
        if(this.listoParaDespegar) {
            System.out.println("Despegando");
        }else {
            comprobarAvion();
            despegar();
        }
    }

    public void comprobarAvion() {
        if(this.alas.isBuenEstado() &&
                this.motores.isBuenEstado() &&
                this.cabina.isBuenEstado() &&
                this.ruedas.isBuenEstado() &&
                this.motores.isTieneCombustible() &&
                this.cabina.isPuertasCerradas() &&
                this.isListoParaDespegar()
        ) {
            System.out.println("");
            this.listoParaDespegar = true;
        }else {
            System.out.println("Realizaremos un checkeo de seguridad");
            System.out.println("--- ALAS ---");
            if(!this.alas.isBuenEstado()) {
                this.alas.pruebaAlas();
            }else{
                System.out.println("Alas en buen estado");
            }

            System.out.println("--- MOTORES ---");
            if(!this.motores.isBuenEstado()) {
                if(!this.motores.isTieneCombustible()) {
                    System.out.println("No tiene combustible");
                    this.motores.cargarCombustible();
                    this.motores.setBuenEstado(true);
                    System.out.println("Combustible cargado y revisados");
                }
            }else {
                System.out.println("Motores en buen estado");
            }

            System.out.println("--- CABINA ---");
            if (!this.cabina.isBuenEstado()) {
                System.out.println("Cabina en mal estado");
                if(!this.cabina.isPuertasCerradas()) {
                    this.cabina.cerrarPuertas();
                    this.cabina.setBuenEstado(true);
                    System.out.println("Cabina revisada y cerrada");
                }
            }else {
                if (!this.cabina.isPuertasCerradas()) {
                    this.cabina.cerrarPuertas();
                    System.out.println("Cabina en buen estado y cerrada");
                }
            }

            System.out.println("--- RUEDAS ---");
            if(!this.ruedas.isBuenEstado()) {
                if(!this.ruedas.isInfladas()) {
                    System.out.println("Ruedas desinfladas");
                    this.ruedas.inflar();
                    this.ruedas.setBuenEstado(true);
                    System.out.println("Ruedas infladas y revisadas");
                }
            }else {
                System.out.println("Ruedas en buen estado");
            }
            this.listoParaDespegar = true;
            comprobarAvion();
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

    public boolean isListoParaDespegar() {
        return listoParaDespegar;
    }

    public void setListoParaDespegar(boolean listoParaDespegar) {
        this.listoParaDespegar = listoParaDespegar;
    }

    public Alas getAlas() {
        return alas;
    }

    public void setAlas(Alas alas) {
        this.alas = alas;
    }

    public Motores getMotores() {
        return motores;
    }

    public void setMotores(Motores motores) {
        this.motores = motores;
    }

    public Ruedas getRuedas() {
        return ruedas;
    }

    public void setRuedas(Ruedas ruedas) {
        this.ruedas = ruedas;
    }

    public Cabina getCabina() {
        return cabina;
    }

    public void setCabina(Cabina cabina) {
        this.cabina = cabina;
    }
}