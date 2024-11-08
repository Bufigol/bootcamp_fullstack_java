package com.bufigol.evm6s5.servicio;

import com.bufigol.evm6s5.excepciones.ErrorLogger;
import com.bufigol.evm6s5.excepciones.PeliculaIdExistenteException;
import com.bufigol.evm6s5.excepciones.PeliculaIdNoRegistradaException;
import com.bufigol.evm6s5.excepciones.PeliculaNoEncontradaException;
import com.bufigol.evm6s5.interfaces.INT_PeliculaServicio;
import com.bufigol.evm6s5.modelo.*;
import com.bufigol.evm6s5.repositorio.PeliculaRepositorio;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class PeliculaServicio implements INT_PeliculaServicio {
    private final PeliculaRepositorio peliculaRepositorio;


    @Override
    public void agregarPelicula(Pelicula pelicula) {
        try {
            // Verificar si ya existe una película con ese ID
            if (existePeliculaPorId(pelicula.getId())) {
                PeliculaIdExistenteException ex = new PeliculaIdExistenteException();
                ErrorLogger.logError(ex);
                throw ex;
            }
            peliculaRepositorio.insertar(pelicula);
        } catch (DuplicateKeyException e) {
            PeliculaIdExistenteException ex = new PeliculaIdExistenteException();
            ErrorLogger.logError(ex);
            throw ex;
        } catch (DataAccessException e) {
            System.err.println("Error de acceso a datos al agregar película: " + e.getMessage());
            throw new RuntimeException("Error al procesar la operación", e);
        }
    }

    @Override
    public Pelicula obtenerPelicula(Long id) {
        try {
            Pelicula pelicula = peliculaRepositorio.buscarPorId(id);
            if (pelicula == null) {
                PeliculaNoEncontradaException ex = new PeliculaNoEncontradaException(id);
                ErrorLogger.logError(ex);
                throw ex;
            }
            return pelicula;
        } catch (EmptyResultDataAccessException e) {
            PeliculaNoEncontradaException ex = new PeliculaNoEncontradaException(id);
            ErrorLogger.logError(ex);
            throw ex;
        } catch (DataAccessException e) {
            System.err.println("Error de acceso a datos al obtener película: " + e.getMessage());
            throw new RuntimeException("Error al procesar la operación", e);
        }
    }

    @Override
    public List<Pelicula> obtenerTodas() {
        try {
            List<Pelicula> peliculas = peliculaRepositorio.buscarTodos();
            if (peliculas.isEmpty()) {
                System.out.println("No se encontraron películas en la base de datos");
                return Collections.emptyList();
            }
            return peliculas;
        } catch (DataAccessException e) {
            System.err.println("Error de acceso a datos al obtener todas las películas: " + e.getMessage());
            throw new RuntimeException("Error al procesar la operación", e);
        }
    }

    @Override
    public void actualizarPelicula(Pelicula pelicula) {
        try {
            if (!existePeliculaPorId(pelicula.getId())) {
                PeliculaIdNoRegistradaException ex = new PeliculaIdNoRegistradaException(pelicula.getId());
                ErrorLogger.logError(ex);
                throw ex;
            }
            peliculaRepositorio.actualizar(pelicula);
        } catch (EmptyResultDataAccessException e) {
            PeliculaIdNoRegistradaException ex = new PeliculaIdNoRegistradaException(pelicula.getId());
            ErrorLogger.logError(ex);
            throw ex;
        } catch (DataAccessException e) {
            System.err.println("Error de acceso a datos al actualizar película: " + e.getMessage());
            throw new RuntimeException("Error al procesar la operación", e);
        }
    }

    @Override
    public void eliminarPelicula(Long id) {
        try {
            if (!existePeliculaPorId(id)) {
                PeliculaIdNoRegistradaException ex = new PeliculaIdNoRegistradaException(id);
                ErrorLogger.logError(ex);
                throw ex;
            }
            peliculaRepositorio.eliminar(id);
        } catch (EmptyResultDataAccessException e) {
            PeliculaIdNoRegistradaException ex = new PeliculaIdNoRegistradaException(id);
            ErrorLogger.logError(ex);
            throw ex;
        } catch (DataAccessException e) {
            System.err.println("Error de acceso a datos al eliminar película: " + e.getMessage());
            throw new RuntimeException("Error al procesar la operación", e);
        }
    }

    @Override
    public void eliminarTodas() {
        try {
            List<Pelicula> peliculas = obtenerTodas();
            for (Pelicula pelicula : peliculas) {
                peliculaRepositorio.eliminar(pelicula.getId());
            }
        } catch (DataAccessException e) {
            System.err.println("Error al eliminar todas las películas: " + e.getMessage());
            throw new RuntimeException("Error al procesar la operación", e);
        }
    }


    private boolean existePeliculaPorId(Long id) {
        try {
            return peliculaRepositorio.buscarPorId(id) != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}