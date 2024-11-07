package com.bufigol.modelo;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

@Repository
@Getter
@Setter
public class Bibloteca {

    private static final Logger logger = LoggerFactory.getLogger(Bibloteca.class);
    private List<Libro> libros;

    @PostConstruct
    private void init() {
        this.libros = new ArrayList<>();
        logger.info("Biblioteca inicializada");
    }

    public List<Libro> getLibros() {
        return Collections.unmodifiableList(libros);
    }

    public void agregarLibro(Libro libro) {
        if (libro == null) {
            logger.warn("Intento de agregar un libro nulo");
            return;
        }

        if (buscarPorId(libro.getId()) != null) {
            logger.warn("Ya existe un libro con el ID: {}", libro.getId());
            return;
        }

        this.libros.add(libro);
        logger.info("Libro agregado: {} (ID: {})", libro.getTitulo(), libro.getId());
    }

    public Libro buscarPorId(int id) {
        return this.libros.stream()
                .filter(libro -> libro.getId() == id)
                .findFirst()
                .orElseGet(() -> {
                    logger.warn("No se encontró libro con ID: {}", id);
                    return null;
                });
    }

    public List<Libro> buscarPorFrase(String frase) {
        if (frase == null || frase.trim().isEmpty()) {
            logger.warn("Búsqueda con frase vacía o nula");
            return Collections.emptyList();
        }

        String fraseMinuscula = frase.toLowerCase().trim();
        return this.libros.stream()
                .filter(libro ->
                        libro.getTitulo().toLowerCase().contains(fraseMinuscula) ||
                                libro.getAutor().getNombre().toLowerCase().contains(fraseMinuscula) ||
                                libro.getAutor().getApellido().toLowerCase().contains(fraseMinuscula) ||
                                (libro.getEditorial() != null &&
                                        libro.getEditorial().getNombre().toLowerCase().contains(fraseMinuscula))
                )
                .toList();
    }
}