package com.bufigol.evm6s5;

import com.bufigol.evm6s5.modelo.Pelicula;
import com.bufigol.evm6s5.servicio.PeliculaServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class Evm6S5Application {

    public static void main(String[] args) {
        SpringApplication.run(Evm6S5Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(PeliculaServicio peliculaServicio) {
        return args -> {
            log.info("Iniciando pruebas de operaciones CRUD de películas");

            try {
                // Crear películas de prueba
                Pelicula pelicula1 = Pelicula.builder()
                        .id(1L)
                        .titulo("El Padrino")
                        .director("Francis Ford Coppola")
                        .anio(1972)
                        .duracion(175)
                        .build();

                Pelicula pelicula2 = Pelicula.builder()
                        .id(1L) // Mismo ID que pelicula1
                        .titulo("Pulp Fiction")
                        .director("Quentin Tarantino")
                        .anio(1994)
                        .duracion(154)
                        .build();

                // 1. Prueba: Insertar dos películas con la misma id
                log.info("1. Prueba - Insertando primera película");
                try {
                    peliculaServicio.agregarPelicula(pelicula1);
                    log.info("Primera película insertada exitosamente");
                } catch (Exception e) {
                    log.info("Resultado prueba 1.1: {}", e.getMessage());
                }

                log.info("1. Prueba - Insertando segunda película con mismo ID");
                try {
                    peliculaServicio.agregarPelicula(pelicula2);
                } catch (Exception e) {
                    log.info("Resultado prueba 1.2: {}", e.getMessage());
                }

                // 2. Prueba: Eliminar la misma película dos veces
                log.info("2. Prueba - Eliminando película por primera vez");
                try {
                    peliculaServicio.eliminarPelicula(1L);
                    log.info("Primera eliminación exitosa");
                } catch (Exception e) {
                    log.info("Resultado prueba 2.1: {}", e.getMessage());
                }

                log.info("2. Prueba - Eliminando la misma película por segunda vez");
                try {
                    peliculaServicio.eliminarPelicula(1L);
                } catch (Exception e) {
                    log.info("Resultado prueba 2.2: {}", e.getMessage());
                }

                // 3. Prueba: Actualizar los datos de una película inexistente
                Pelicula peliculaInexistente = Pelicula.builder()
                        .id(999L)
                        .titulo("Película Inexistente")
                        .director("Director Inexistente")
                        .anio(2024)
                        .duracion(120)
                        .build();

                log.info("3. Prueba - Actualizando película inexistente");
                try {
                    peliculaServicio.actualizarPelicula(peliculaInexistente);
                } catch (Exception e) {
                    log.info("Resultado prueba 3: {}", e.getMessage());
                }

                // 4. Prueba: Recuperar una película con una id no registrada
                log.info("4. Prueba - Recuperando película con ID no registrado");
                try {
                    Pelicula peliculaRecuperada = peliculaServicio.obtenerPelicula(999L);
                    log.info("Película recuperada: {}", peliculaRecuperada);
                } catch (Exception e) {
                    log.info("Resultado prueba 4: {}", e.getMessage());
                }

                // Mostrar todas las películas al final
                log.info("Lista final de películas en la base de datos:");
                try {
                    peliculaServicio.obtenerTodas().forEach(p ->
                            log.info("Película: {}", p)
                    );
                } catch (Exception e) {
                    log.warn("Error al listar películas: {}", e.getMessage());
                }

            } catch (Exception e) {
                log.error("Error general en las pruebas: {}", e.getMessage());
            }

            log.info("Finalización de pruebas de operaciones CRUD de películas");
        };
    }
}