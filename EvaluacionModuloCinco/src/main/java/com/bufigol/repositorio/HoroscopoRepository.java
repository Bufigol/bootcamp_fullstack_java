package com.bufigol.repositorio;

import com.bufigol.configuracion.DatabaseConnection;
import com.bufigol.constantes.Constantes;
import com.bufigol.interfaces.repositorios.INT_HoroscopoRepository;
import com.bufigol.modelo.Horoscopo;
import com.bufigol.utils.OtrasUtilidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HoroscopoRepository implements INT_HoroscopoRepository {

    @Override
    public List<Horoscopo> listarHoroscopos() {
        ArrayList<Horoscopo> out = new ArrayList<>();
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            PreparedStatement pstm = db.getConnection().prepareStatement(Constantes.BUSQUEDA_COMPLETA_HOROSCOPO_ODERNADA);
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
            PreparedStatement pstm = con.prepareStatement(Constantes.INSERTAR_HOROSCOPO);
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
            PreparedStatement pstm = conn.prepareStatement(Constantes.BUSCAR_HOROSCOPO_POR_ID);
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
            PreparedStatement pstm = conn.prepareStatement(Constantes.ACTULIZAR_HOROSCOPO);
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
            PreparedStatement pstm = conn.prepareStatement(Constantes.ELIMINAR_HOROSCOPO);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("No es posible eliminar el horóscopo",e);
        }finally {
            db.closeConnection();
        }
    }
}
