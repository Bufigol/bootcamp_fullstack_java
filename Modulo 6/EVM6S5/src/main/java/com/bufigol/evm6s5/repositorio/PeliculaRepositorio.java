package com.bufigol.evm6s5.repositorio;

import com.bufigol.evm6s5.constantes.Constantes;
import com.bufigol.evm6s5.interfaces.INT_PeliculaRepositorio;
import com.bufigol.evm6s5.modelo.Pelicula;
import com.bufigol.evm6s5.repositorio.PeliculaRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PeliculaRepositorio implements INT_PeliculaRepositorio {

    private final JdbcTemplate jdbcTemplate;
    private final PeliculaRowMapper peliculaRowMapper;



    @Override
    public void insertar(Pelicula pelicula) {
        log.debug("Insertando película: {}", pelicula);
        try {
            jdbcTemplate.update(Constantes.SQL_INSERT,
                    pelicula.getId(),
                    pelicula.getTitulo(),
                    pelicula.getDirector(),
                    pelicula.getAnio(),
                    pelicula.getDuracion());
            log.info("Película insertada exitosamente: {}", pelicula.getId());
        } catch (Exception e) {
            log.error("Error al insertar película: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Pelicula buscarPorId(Long id) {
        log.debug("Buscando película con ID: {}", id);
        try {
            Pelicula pelicula = jdbcTemplate.queryForObject(Constantes.SQL_SELECT_BY_ID, peliculaRowMapper, id);
            if (pelicula != null) {
                log.info("Película encontrada: {}", id);
            }
            return pelicula;
        } catch (EmptyResultDataAccessException e) {
            log.warn("No se encontró la película con ID: {}", id);
            return null;
        } catch (Exception e) {
            log.error("Error al buscar película por ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Pelicula> buscarTodos() {
        log.debug("Obteniendo todas las películas");
        try {
            List<Pelicula> peliculas = jdbcTemplate.query(Constantes.SQL_SELECT_ALL, peliculaRowMapper);
            log.info("Se encontraron {} películas", peliculas.size());
            return peliculas;
        } catch (Exception e) {
            log.error("Error al buscar todas las películas: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void actualizar(Pelicula pelicula) {
        log.debug("Actualizando película: {}", pelicula);
        try {
            int filasAfectadas = jdbcTemplate.update(Constantes.SQL_UPDATE,
                    pelicula.getTitulo(),
                    pelicula.getDirector(),
                    pelicula.getAnio(),
                    pelicula.getDuracion(),
                    pelicula.getId());

            if (filasAfectadas == 0) {
                log.warn("No se encontró la película con ID {} para actualizar", pelicula.getId());
            } else {
                log.info("Película actualizada exitosamente: {}", pelicula.getId());
            }
        } catch (Exception e) {
            log.error("Error al actualizar película: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Eliminando película con ID: {}", id);
        try {
            int filasAfectadas = jdbcTemplate.update(Constantes.SQL_DELETE, id);

            if (filasAfectadas == 0) {
                log.warn("No se encontró la película con ID {} para eliminar", id);
            } else {
                log.info("Película eliminada exitosamente: {}", id);
            }
        } catch (Exception e) {
            log.error("Error al eliminar película: {}", e.getMessage());
            throw e;
        }
    }

    // Métodos auxiliares
    public boolean existePorId(Long id) {
        log.debug("Verificando existencia de película con ID: {}", id);
        try {
            Integer count = jdbcTemplate.queryForObject(Constantes.SQL_COUNT_BY_ID, Integer.class, id);
            boolean existe = count != null && count > 0;
            log.debug("Película con ID {}: {}", id, existe ? "existe" : "no existe");
            return existe;
        } catch (Exception e) {
            log.error("Error al verificar existencia de película: {}", e.getMessage());
            throw e;
        }
    }
}