package com.bufigol.utils;

import com.bufigol.configuracion.DatabaseConnection;
import com.bufigol.constantes.ConstantesTablas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilidadesDDBB {

    public static int buscarIDDisponible(String nombreTabla, String id){
        int out = 1;
        DatabaseConnection ddbb = DatabaseConnection.getInstance();
        String sql = "SELECT MAX(" + id +  ") AS maximo FROM " + nombreTabla + ";";
        try {
            PreparedStatement pstm = ddbb.getConnection().prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            out = rs.getInt(1) + 1 ;
        } catch (SQLException | RuntimeException e) {

            return -1;
        } finally {
            ddbb.closeConnection();
        }
        return out;
    }
}
