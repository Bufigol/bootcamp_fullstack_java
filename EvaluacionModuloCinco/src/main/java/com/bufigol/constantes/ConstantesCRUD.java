package com.bufigol.constantes;

public class ConstantesCRUD {
    // Horoscopo
    public static final String BUSQUEDA_COMPLETA_HOROSCOPO_ODERNADA = "SELECT id, animal, fecha_inicio, fecha_fin FROM horoscopo ORDER BY id ASC;";
    public static final String INSERTAR_HOROSCOPO = "INSERT INTO horoscopo (animal, fecha_inicio, fecha_fin) VALUES (?, ?, ?);";
    public static final String BUSCAR_HOROSCOPO_POR_ID ="SELECT id, animal, fecha_inicio, fecha_fin FROM horoscopo WHERE id = ?";
    public static final String ACTULIZAR_HOROSCOPO = "UPDATE horoscopo SET animal = ?, fecha_inicio = ?, fecha_fin = ? WHERE id = ?";
    public static final String ELIMINAR_HOROSCOPO = "DELETE FROM horoscopo WHERE id = ?";
    public static final String BUSCAR_HORSCOPO_POR_FECHA = "SELECT * FROM horoscopo WHERE ? >= fecha_inicio AND ? <= fecha_fin; ";

    //Usuarios
    public static final String BUSQUEDA_COMPLETA_USUARIOS_ORDENADA = "SELECT * FROM usuarios ORDER BY id ASC;";
    public static final String BUSQUEDA_COMPLETA_POR_USERNAME = "SELECT * FROM usuarios WHERE username = ?;";
    public static final String INSERTAR_USUARIO = "INSERT INTO usuarios (nombre, username, email, fecha_nacimiento, password, horoscopo_id)  VALUES (?, ?, ?, ?, ?, ?);";
    public static final String BUSCAR_USUARIO_POR_ID = "SELECT * FROM usuarios WHERE id = ?;";
    public static final String ACTUALIZAR_USUARIO = "UPDATE usuarios SET nombre = ?, username = ?, email = ?, fecha_nacimiento = ?, password = ?, animal = ?  WHERE id = ?;";
    public static final String ELIMINAR_USUARIO = "DELETE FROM usuarios WHERE id = ?;";

}
