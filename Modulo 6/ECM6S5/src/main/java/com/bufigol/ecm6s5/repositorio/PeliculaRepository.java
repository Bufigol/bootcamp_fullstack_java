package com.bufigol.ecm6s5.repositorio;

import com.bufigol.ecm6s5.interfaces.INT_PeliculaRepository;
import com.bufigol.ecm6s5.modelo.Pelicula;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PeliculaRepository implements INT_PeliculaRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Pelicula> peliculaRowMapper = (rs, rowNum) -> new Pelicula(
            rs.getLong("id"),
            rs.getString("titulo"),
            rs.getString("director"),
            rs.getInt("anio"),
            rs.getInt("duracion")
    );

    public PeliculaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void insertar(Pelicula pelicula) {
        String sql = "INSERT INTO pelicula (titulo, director, anio, duracion) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                pelicula.getTitulo(),
                pelicula.getDirector(),
                pelicula.getAnio(),
                pelicula.getDuracion()
        );
    }

    @Override
    public List<Pelicula> obtenerTodos() {
        return jdbcTemplate.query("SELECT * FROM pelicula", peliculaRowMapper);
    }

    @Override
    public void actualizar(Pelicula pelicula) {
        String sql = "UPDATE pelicula SET titulo=?, director=?, anio=?, duracion=? WHERE id=?";
        jdbcTemplate.update(sql,
                pelicula.getTitulo(),
                pelicula.getDirector(),
                pelicula.getAnio(),
                pelicula.getDuracion(),
                pelicula.getId()
        );
    }

    @Override
    public void eliminar(Long id) {
        jdbcTemplate.update("DELETE FROM pelicula WHERE id=?", id);
    }
}
