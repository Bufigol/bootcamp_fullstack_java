package com.bufigol.evm6s5.excepciones;

import lombok.Getter;

@Getter
public class PeliculaNoEncontradaException extends PeliculaBaseException {
    private final Long idBuscado;

    public PeliculaNoEncontradaException(Long id) {
        super(
                "No se ha encontrado la Película",
                "PELICULA_002",
                String.format("No se encontró registro con ID: %d en la tabla pelicula", id),
                404
        );
        this.idBuscado = id;
    }

    public String getSugerencia() {
        return "Verifique el ID proporcionado o consulte la lista de películas disponibles";
    }
}