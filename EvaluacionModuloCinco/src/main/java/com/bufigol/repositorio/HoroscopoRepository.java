package com.bufigol.repositorio;

import com.bufigol.configuracion.DatabaseConnection;
import com.bufigol.constantes.ConstantesCRUD;
import com.bufigol.constantes.ConstantesTablas;
import com.bufigol.interfaces.repositorios.INT_HoroscopoRepository;
import com.bufigol.modelo.AnimalesHoroscopoEnum;
import com.bufigol.modelo.Horoscopo;
import com.bufigol.utils.OtrasUtilidades;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HoroscopoRepository implements INT_HoroscopoRepository {

    @Override
    public List<Horoscopo> listarHoroscopos() {
        ArrayList<Horoscopo> out = new ArrayList<>();
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            PreparedStatement pstm = db.getConnection().prepareStatement(ConstantesCRUD.BUSQUEDA_COMPLETA_HOROSCOPO_ODERNADA);
            ResultSet rs = pstm.executeQuery();
            if( rs.getFetchSize() == 0 ) {
                pstm.close();
                db.closeConnection();
                return out;
            }
            while (rs.next()) {
                // (int id, AnimalesHoroscopoEnum animalEnum, String animal, Date inicio, Date fin)
                Horoscopo horoscopo = new Horoscopo(
                        rs.getInt("id"),
                        OtrasUtilidades.getAnimalEnumFromString(rs.getString("animal")),
                        rs.getString("animal"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_final"));
                out.add(horoscopo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.closeConnection();
        }
        return out;
    }

    @Override
    public void insertarHoroscopo(Horoscopo horoscopo) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            Connection con = db.getConnection();
            PreparedStatement pstm = con.prepareStatement(ConstantesCRUD.INSERTAR_HOROSCOPO);
            pstm.setString(1, horoscopo.getAnimal());
            pstm.setDate(2, horoscopo.getInicio());
            pstm.setDate(3, horoscopo.getFin());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el horóscopo",e);
        } finally {
            db.closeConnection();
        }
    }

    @Override
    public Optional<Horoscopo> buscarHoroscopo(int id) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            Connection conn = db.getConnection();
            PreparedStatement pstm = conn.prepareStatement(ConstantesCRUD.BUSCAR_HOROSCOPO_POR_ID);
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Horoscopo horoscopo = new Horoscopo(
                            rs.getInt("id"),
                            OtrasUtilidades.getAnimalEnumFromString(rs.getString("animal")),
                            rs.getString("animal"),
                            rs.getDate("fecha_inicio"),
                            rs.getDate("fecha_final")
                    );
                    return Optional.of(horoscopo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el horóscopo por ID", e);
        }finally {
            db.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public void actualizarHoroscopo(Horoscopo horoscopo) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            Connection conn = db.getConnection();
            PreparedStatement pstm = conn.prepareStatement(ConstantesCRUD.ACTULIZAR_HOROSCOPO);
            pstm.setString(1, horoscopo.getAnimal());
            pstm.setDate(2, horoscopo.getInicio());
            pstm.setDate(3, horoscopo.getFin());
            pstm.setInt(4, horoscopo.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el horóscopo",e);
        }finally {
            db.closeConnection();
        }
    }

    @Override
    public void eliminarHoroscopo(int id) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        try{
            Connection conn = db.getConnection();
            PreparedStatement pstm = conn.prepareStatement(ConstantesCRUD.ELIMINAR_HOROSCOPO);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("No es posible eliminar el horóscopo",e);
        }finally {
            db.closeConnection();
        }
    }

    @Override
    public  Horoscopo buscarHoroscopoPorFecha(Date fecha) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        try{
            Connection conn = db.getConnection();
            String sql = ConstantesCRUD.BUSCAR_HORSCOPO_POR_FECHA;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaString = "'" + sdf.format(fecha) + "'";
            sql = sql.replace("?", fechaString);

            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                //Horoscopo(int id, AnimalesHoroscopoEnum animalEnum, String animal, Date inicio, Date fin)
                return new Horoscopo(
                        rs.getInt(ConstantesTablas.HOROSCOPO_TABLE_COLUMNS[0]),
                        AnimalesHoroscopoEnum.valueOf( rs.getString(ConstantesTablas.HOROSCOPO_TABLE_COLUMNS[1]) ),
                        rs.getString(ConstantesTablas.HOROSCOPO_TABLE_COLUMNS[1]),
                        rs.getDate(ConstantesTablas.HOROSCOPO_TABLE_COLUMNS[2]),
                        rs.getDate(ConstantesTablas.HOROSCOPO_TABLE_COLUMNS[3])
                );
            }else{
                throw new RuntimeException();
            }
        } catch (SQLException |RuntimeException e) {
            throw new RuntimeException("No es posible encontrar el horóscopo",e);
        }finally {
            db.closeConnection();
        }
    }
}
