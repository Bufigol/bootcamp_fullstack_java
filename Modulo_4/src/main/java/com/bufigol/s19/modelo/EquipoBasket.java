package com.bufigol.s19.modelo;

import java.util.List;

public class EquipoBasket {

    private String nombre;
    private String ciudad;
    private List<Jugador> listaJugadores;

    public EquipoBasket() {
        this.listaJugadores = new java.util.ArrayList<>();
        this.ciudad = "";
        this.nombre = "";
    }

    public static EquipoBasket crearEquipo(String nombre, String ciudad, List<Jugador> listaJugadores) {
        EquipoBasket out = new EquipoBasket();
        if(nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser vacío");
        }else{
            out.setNombre(nombre);
        }

        if(ciudad == null || ciudad.isEmpty()) {
            throw new IllegalArgumentException("La ciudad no puede ser vacía");
        }else{
            out.setCiudad(ciudad);
        }

        if(listaJugadores == null) {
            throw new IllegalArgumentException("La lista de jugadores no puede ser nula");
        }else{
            out.setListaJugadores(listaJugadores);
        }
        return out;
    }

    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
