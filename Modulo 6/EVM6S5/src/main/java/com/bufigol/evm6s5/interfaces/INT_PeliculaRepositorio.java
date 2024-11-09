package com.bufigol.evm6s5.interfaces;

import com.bufigol.evm6s5.modelo.Pelicula;

import java.util.List;

public interface INT_PeliculaRepositorio {
    void insertar(Pelicula pelicula);
    Pelicula buscarPorId(Long id);
    List<Pelicula> buscarTodos();
    void actualizar(Pelicula pelicula);
    void eliminar(Long id);
}
