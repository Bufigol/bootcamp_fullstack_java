package com.bufigol.repositorio;

import com.bufigol.configuracion.DatabaseConnection;
import com.bufigol.constantes.Constantes;
import com.bufigol.interfaces.repositorios.INT_HoroscopoRepository;
import com.bufigol.modelo.Horoscopo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HoroscopoRepository implements INT_HoroscopoRepository {
    @Override
    public List<Horoscopo> listarHoroscopos() {
        ArrayList<Horoscopo> out = new ArrayList<>();
        DatabaseConnection db = DatabaseConnection.getInstance();
        try {
            PreparedStatement pstm = db.getConnection().prepareStatement(Constantes.BUSQUEDA_COMPLETA_HOROSCOPO_ODERNADA);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    @Override
    public void insertarHoroscopo(Horoscopo horoscopo) {

    }

    @Override
    public Optional<Horoscopo> buscarHoroscopo(int id) {
        return Optional.empty();
    }

    @Override
    public void actualizarHoroscopo(Horoscopo horoscopo) {

    }

    @Override
    public void eliminarHoroscopo(int id) {

    }
}
