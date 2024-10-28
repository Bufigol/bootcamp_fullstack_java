package com.bufigol.interfaces.repositorios;

import com.bufigol.modelo.Horoscopo;

import java.util.List;
import java.util.Optional;

public interface INT_HoroscopoRepository {
    List<Horoscopo> listarHoroscopos();
    void insertarHoroscopo(Horoscopo horoscopo);
    Optional<Horoscopo> buscarHoroscopo(int id);
    void actualizarHoroscopo(Horoscopo horoscopo);
    void eliminarHoroscopo(int id);
}
