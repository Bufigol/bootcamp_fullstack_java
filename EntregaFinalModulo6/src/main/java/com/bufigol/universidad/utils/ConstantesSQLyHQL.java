package com.bufigol.universidad.utils;

public class ConstantesSQLyHQL {
    public static final String FIND_MATERIA_BY_ALUMNO_ID = "SELECT m FROM Materia m WHERE m.alumno.id = :alumnoId";
    public static final String FIND_MATERIA_BY_NOMBRE = "SELECT m FROM Materia m WHERE LOWER(m.nombre) LIKE LOWER(:nombre)";
    public static final String COUNT_MATERIA_BY_NOMBRE_AND_ALUMNO_ID = "SELECT COUNT(m) FROM Materia m WHERE LOWER(m.nombre) = LOWER(:nombre) AND m.alumno.id = :alumnoId";
    public static final String DELETE_MATERIA_BY_ID="DELETE FROM Materia m WHERE m.id IN :ids";
    public static final String DELETE_ALL_MATERIA ="DELETE FROM Materia";
    public static final String SELECT_ALL_MATERIA = "SELECT m FROM Materia m";
    public static final String SELECT_MATERIA_BY_ID = "SELECT m FROM Materia m WHERE m.id IN :ids";
    public static final String COUNT_ALL_MATERIA = "SELECT COUNT(m) FROM Materia m";
}
