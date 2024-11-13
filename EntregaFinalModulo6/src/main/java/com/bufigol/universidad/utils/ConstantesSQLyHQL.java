package com.bufigol.universidad.utils;

public class ConstantesSQLyHQL {

    //MATERIA
    public static final String FIND_MATERIA_BY_ALUMNO_ID = "SELECT m FROM Materia m WHERE m.alumno.id = :alumnoId";
    public static final String FIND_MATERIA_BY_NOMBRE = "SELECT m FROM Materia m WHERE LOWER(m.nombre) LIKE LOWER(:nombre)";
    public static final String COUNT_MATERIA_BY_NOMBRE_AND_ALUMNO_ID = "SELECT COUNT(m) FROM Materia m WHERE LOWER(m.nombre) = LOWER(:nombre) AND m.alumno.id = :alumnoId";
    public static final String DELETE_MATERIA_BY_ID="DELETE FROM Materia m WHERE m.id IN :ids";
    public static final String DELETE_ALL_MATERIA ="DELETE FROM Materia";
    public static final String SELECT_ALL_MATERIA = "SELECT m FROM Materia m";
    public static final String SELECT_MATERIA_BY_ID = "SELECT m FROM Materia m WHERE m.id IN :ids";
    public static final String COUNT_ALL_MATERIA = "SELECT COUNT(m) FROM Materia m";

    //ALUMNO
    public static final String FIND_BY_RUT = "SELECT a FROM Alumno a WHERE a.rut = :rut";
    public static final String EXISTS_BY_RUT = "SELECT COUNT(a) > 0 FROM Alumno a WHERE a.rut = :rut";
    public static final String FIND_BY_NOMBRE = "SELECT a FROM Alumno a WHERE LOWER(a.nombre) LIKE LOWER(:nombre)";
    public static final String COUNT_ALL = "SELECT COUNT(a) FROM Alumno a";
    public static final String COUNT_BY_ID = "SELECT COUNT(a) FROM Alumno a WHERE a.id = :id";
    public static final String FIND_ALL = "SELECT a FROM Alumno a";
    public static final String FIND_BY_ID = "SELECT a FROM Alumno a WHERE a.id IN :ids";
    public static final String DELETE_ALL = "DELETE FROM Alumno a";
    public static final String DELETE_BY_IDS = "DELETE FROM Alumno a WHERE a.id IN :ids";
}
