package com.bufigol.evm6s5.repositorio;

import com.bufigol.evm6s5.modelo.Pelicula;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PeliculaRowMapper implements RowMapper<Pelicula> {

    @Override
    public Pelicula mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Pelicula.builder()
                .id(rs.getLong("id"))
                .titulo(rs.getString("titulo"))
                .director(rs.getString("director"))
                .anio(rs.getInt("anio"))
                .duracion(rs.getInt("duracion"))
                .build();
    }
}