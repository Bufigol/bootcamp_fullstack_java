package com.bufigol.interfaces.servicios;

import com.bufigol.dtos.horoscopo.HoroscopoCreateDTO;
import com.bufigol.dtos.horoscopo.HoroscopoResponseDTO;
import com.bufigol.dtos.horoscopo.HoroscopoUpdateDTO;
import com.bufigol.modelo.Horoscopo;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface INT_HoroscopoServicio {
    List<HoroscopoResponseDTO> listarHoroscopos();
    Optional<HoroscopoResponseDTO> buscarHoroscopoPorId(int id);
    void crearHoroscopo(HoroscopoCreateDTO horoscopoCreateDTO);
    void actualizarHoroscopo(HoroscopoUpdateDTO horoscopoUpdateDTO);
    void eliminarHoroscopo(int id);
    Horoscopo buscarHoroscopoPorFecha(Date fecha);
}
