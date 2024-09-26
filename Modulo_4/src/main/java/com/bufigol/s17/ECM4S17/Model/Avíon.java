package com.bufigol.s17.ECM4S17.Model;

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

    /**
     * Default constructor
     */
    public Avíon() {
    }

    public void recibirPasajeros() {
        System.out.println("Se va a recibir pasajeros");
        this.cabina.
    }

    /**
     * 
     */
    public void despegar() {
        // TODO implement here
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