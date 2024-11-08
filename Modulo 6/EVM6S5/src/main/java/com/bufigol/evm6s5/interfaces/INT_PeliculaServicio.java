package com.bufigol.evm6s5.interfaces;

import com.bufigol.evm6s5.modelo.Pelicula;

import java.util.List;

public interface INT_PeliculaServicio {
    void agregarPelicula(Pelicula pelicula);
    Pelicula obtenerPelicula(Long id);
    List<Pelicula> obtenerTodas();
    void actualizarPelicula(Pelicula pelicula);
    void eliminarPelicula(Long id);
    void eliminarTodas();
}
