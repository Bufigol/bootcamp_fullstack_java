package com.bufigol.ecm6s5;

import com.bufigol.ecm6s5.modelo.Pelicula;
import com.bufigol.ecm6s5.repositorio.PeliculaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrincipalApplication implements CommandLineRunner {
    private final PeliculaRepository peliculaRepository;

    public PrincipalApplication(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PrincipalApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Insertar 3 películas
        Pelicula pelicula1 = new Pelicula(null, "El Padrino", "Francis Ford Coppola", 1972, 175);
        Pelicula pelicula2 = new Pelicula(null, "Pulp Fiction", "Quentin Tarantino", 1994, 154);
        Pelicula pelicula3 = new Pelicula(null, "Matrix", "Hermanas Wachowski", 1999, 136);

        peliculaRepository.insertar(pelicula1);
        peliculaRepository.insertar(pelicula2);
        peliculaRepository.insertar(pelicula3);

        System.out.println("Películas insertadas:");
        mostrarPeliculas();

        // Actualizar una película
        Pelicula peliculaActualizar = new Pelicula(1L, "El Padrino", "Francis Ford Coppola", 1972, 180);
        peliculaRepository.actualizar(peliculaActualizar);

        System.out.println("\nDespués de actualizar:");
        mostrarPeliculas();

        // Eliminar una película
        peliculaRepository.eliminar(2L);

        System.out.println("\nDespués de eliminar:");
        mostrarPeliculas();
    }

    private void mostrarPeliculas() {
        peliculaRepository.obtenerTodos().forEach(p ->
                System.out.printf("ID: %d, Título: %s, Director: %s, Año: %d, Duración: %d min%n",
                        p.getId(), p.getTitulo(), p.getDirector(), p.getAnio(), p.getDuracion())
        );
    }
}