package com.bufigol.modelo;

import com.bufigol.interfaces.IAyudante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Biblotecario {

    private final IAyudante ayudante;

    @Autowired
    public Biblotecario(IAyudante ayudante) {
        this.ayudante = ayudante;
    }

    public void solicitanTodosLosLibros() {
        List<Libro> libros = ayudante.obtenerLibros();
        if (libros.isEmpty()) {
            System.out.println("La biblioteca no tiene libros registrados");
            return;
        }
        System.out.println("Lista de todos los libros en la biblioteca:");
        libros.forEach(libro -> System.out.println(libro.toString()));
    }

    public void agregarUnLibro(Libro libro) {
        if (libro == null) {
            System.out.println("No se puede agregar un libro sin información");
            return;
        }

        ayudante.agregarLibro(libro);

        if (ayudante.obtenerLibros().contains(libro)) {
            System.out.println("El libro '" + libro.getTitulo() + "' fue agregado exitosamente a la biblioteca");
        } else {
            System.out.println("No se pudo agregar el libro '" + libro.getTitulo() + "' a la biblioteca");
        }
    }

    public void devolucionOPrestamoDeLibro(int id) {
        Libro libro = ayudante.cambiarDisponibilidad(id);

        if (libro == null) {
            System.out.println("No se encontró un libro con el ID: " + id);
            return;
        }

        String estado = libro.isDisponible() ? "disponible" : "prestado";
        System.out.println("El libro '" + libro.getTitulo() + "' ahora está " + estado);
    }

    public void buscarLibro(String frase) {
        if (frase == null || frase.trim().isEmpty()) {
            System.out.println("Por favor proporcione un término de búsqueda válido");
            return;
        }

        List<Libro> librosEncontrados = ayudante.buscarLibro(frase);

        if (librosEncontrados.isEmpty()) {
            System.out.println("No se encontraron libros que coincidan con: '" + frase + "'");
            return;
        }

        System.out.println("Libros encontrados con el término '" + frase + "':");
        librosEncontrados.forEach(libro -> System.out.println(libro.toString()));
    }
}