package com.bufigol.modelo;

import com.bufigol.interfaces.IAyudante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Ayudante implements IAyudante {

    private final Bibloteca bibloteca;

    @Autowired
    public Ayudante(Bibloteca bibloteca) {
        this.bibloteca = bibloteca;
    }

    @Override
    public List<Libro> buscarLibro(String frase) {
        if(frase != null && !frase.isEmpty()) {
            return bibloteca.buscarPorFrase(frase);
        }
        return List.of();
    }

    @Override
    public List<Libro> obtenerLibros() {
        return bibloteca.getLibros();
    }

    @Override
    public void agregarLibro(Libro libro) {
        if(libro != null && !bibloteca.getLibros().contains(libro)) {
            bibloteca.agregarLibro(libro);
        }
    }

    @Override
    public Libro cambiarDisponibilidad(int id) {
        Libro libro = bibloteca.buscarPorId(id);
        if(libro != null) {
            libro.setDisponible(!libro.isDisponible());
        }
        return libro;
    }
}