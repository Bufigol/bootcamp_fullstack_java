package com.bufigol.evm6s5.repositorio;

import com.bufigol.evm6s5.interfaces.INT_PeliculaRepositorio;
import com.bufigol.evm6s5.modelo.Pelicula;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PeliculaRepositorio implements INT_PeliculaRepositorio {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public void insertar(Pelicula pelicula) {

    }

    @Override
    public Pelicula buscarPorId(Long id) {
        return null;
    }

    @Override
    public List<Pelicula> buscarTodos() {
        return List.of();
    }

    @Override
    public void actualizar(Pelicula pelicula) {

    }

    @Override
    public void eliminar(Long id) {

    }
}
