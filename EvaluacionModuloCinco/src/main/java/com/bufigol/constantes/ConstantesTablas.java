package com.bufigol.constantes;

public class ConstantesTablas {
    public static final String HOROSCOPO_TABLE = "horoscopo";
    public static final String[] HOROSCOPO_TABLE_COLUMNS = {"id", "animal","fecha_inicio" ,"fecha_fin"};

    public static final String USUARIOS_TABLE = "usuarios";
    public static final String[] USUARIOS_TABLE_COLUMNS = { "id" ,"nombre" , "username" , "email" ,
            "fecha_nacimiento" , "password" , "horoscopo_id"};

}
