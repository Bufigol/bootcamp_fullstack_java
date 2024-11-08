package com.bufigol.evm6s6.utils;

public class SQLConstants {
    // Consultas HQL existentes
    public static final String HQL_FIND_BY_MODELO =
            "SELECT p FROM Producto p WHERE p.modelo = :modelo";

    public static final String HQL_BUSQUEDA_GENERAL =
            "SELECT p FROM Producto p WHERE " +
                    "LOWER(p.modelo) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
                    "LOWER(p.marca) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
                    "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :termino, '%'))";

    public static final String HQL_BUQUEDA_POR_MARCA =
            "SELECT p FROM Producto p WHERE p.marca = :marca";

    public static final String HQL_CONTAR_PRODUCTOS_SEGUN_MARCA_MODELO =
            "SELECT COUNT(p) FROM Producto p WHERE p.modelo = :modelo AND p.marca = :marca";

    public static final String HQL_COMIENZO_BUSQUEDA_GENERAL =
            "SELECT p FROM Producto p WHERE ";

    // Nuevas constantes necesarias
    public static final String HQL_FIND_ALL =
            "SELECT p FROM Producto p";

    public static final String HQL_COUNT_ALL =
            "SELECT COUNT(p) FROM Producto p";

    // Consultas SQL Nativas
    public static final String SQL_BUSCAR_POR_DESCRIPCION =
            "SELECT * FROM products WHERE descripcion LIKE %:palabra%";
}