package com.bufigol.evm6s5.constantes;

public class Constantes {
    // Constantes SQL
    public static final String SQL_INSERT = "INSERT INTO pelicula (id, titulo, director, anio, duracion) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_BY_ID = "SELECT id, titulo, director, anio, duracion FROM pelicula WHERE id = ?";
    public static final String SQL_SELECT_ALL = "SELECT id, titulo, director, anio, duracion FROM pelicula";
    public static final String SQL_UPDATE = "UPDATE pelicula SET titulo = ?, director = ?, anio = ?, duracion = ? WHERE id = ?";
    public static final String SQL_DELETE = "DELETE FROM pelicula WHERE id = ?";
    public static final String SQL_COUNT_BY_ID = "SELECT COUNT(*) FROM pelicula WHERE id = ?";
}
