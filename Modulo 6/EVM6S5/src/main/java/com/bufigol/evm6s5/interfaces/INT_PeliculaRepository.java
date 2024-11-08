package com.bufigol.evm6s5.interfaces;

import com.bufigol.evm6s5.modelo.Pelicula;

import java.util.List;

public interface INT_PeliculaRepository {
    void insertar(Pelicula pelicula);
    List<Pelicula> obtenerTodos();
    void actualizar(Pelicula pelicula);
    void eliminar(Long id);
}
