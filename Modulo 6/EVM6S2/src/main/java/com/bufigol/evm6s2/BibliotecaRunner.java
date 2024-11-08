package com.bufigol.evm6s2;

import com.bufigol.modelo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BibliotecaRunner implements CommandLineRunner {

    @Autowired
    private Biblotecario bibliotecario;

    @Override
    public void run(String... args) {
        System.out.println("\n=== Iniciando Demo de Biblioteca ===\n");

        // Crear autores
        Autor autor1 = new Autor("Gabriel", "García Márquez");
        Autor autor2 = new Autor("Isabel", "Allende");
        Autor autor3 = new Autor("Jorge Luis", "Borges");
        Autor autor4 = new Autor("Pablo", "Neruda");

        // Crear editoriales
        Editorial editorial1 = new Editorial("Planeta", "España");
        Editorial editorial2 = new Editorial("Penguin", "Estados Unidos");
        Editorial editorial3 = new Editorial("Alfaguara", "México");

        // Crear y agregar 4 libros
        System.out.println("=== Agregando libros ===");
        Libro libro1 = new Libro(1, "Cien años de soledad", autor1, editorial1, 496, "Realismo mágico", true);
        Libro libro2 = new Libro(2, "La casa de los espíritus", autor2, editorial2, 442, "Novela", true);
        Libro libro3 = new Libro(3, "El Aleph", autor3, editorial3, 224, "Cuentos", true);
        Libro libro4 = new Libro(4, "Veinte poemas de amor", autor4, editorial1, 56, "Poesía", true);

        bibliotecario.agregarUnLibro(libro1);
        bibliotecario.agregarUnLibro(libro2);
        bibliotecario.agregarUnLibro(libro3);
        bibliotecario.agregarUnLibro(libro4);

        System.out.println("\n=== Realizando préstamos y devoluciones ===");
        // Realizar 2 devoluciones o préstamos
        bibliotecario.devolucionOPrestamoDeLibro(1); // Préstamo del primer libro
        bibliotecario.devolucionOPrestamoDeLibro(2); // Préstamo del segundo libro

        System.out.println("\n=== Mostrando todos los libros ===");
        // Solicitar todos los libros
        bibliotecario.solicitanTodosLosLibros();

        System.out.println("\n=== Buscando libros por frase ===");
        // Buscar libro por frase
        bibliotecario.buscarLibro("amor");

        System.out.println("\n=== Demo de Biblioteca finalizada ===\n");
    }
}