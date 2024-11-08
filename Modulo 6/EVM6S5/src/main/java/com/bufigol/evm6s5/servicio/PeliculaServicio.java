package com.bufigol.evm6s5.servicio;

import com.bufigol.evm6s5.interfaces.INT_PeliculaServicio;
import com.bufigol.evm6s5.modelo.Pelicula;
import com.bufigol.evm6s5.repositorio.PeliculaRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PeliculaServicio implements INT_PeliculaServicio {

    private final PeliculaRepositorio peliculaRepositorio;

    @Override
    public void agregarPelicula(Pelicula pelicula) {

    }

    @Override
    public Pelicula obtenerPelicula(Long id) {
        return null;
    }

    @Override
    public List<Pelicula> obtenerTodas() {
        return List.of();
    }

    @Override
    public void actualizarPelicula(Pelicula pelicula) {

    }

    @Override
    public void eliminarPelicula(Long id) {

    }
}
