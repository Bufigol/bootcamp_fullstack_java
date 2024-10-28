package com.bufigol.servicios;

import com.bufigol.dtos.horoscopo.HoroscopoCreateDTO;
import com.bufigol.dtos.horoscopo.HoroscopoResponseDTO;
import com.bufigol.dtos.horoscopo.HoroscopoUpdateDTO;
import com.bufigol.interfaces.servicios.INT_HoroscopoServicio;
import com.bufigol.modelo.Horoscopo;
import com.bufigol.repositorio.HoroscopoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HoroscopoServicio implements INT_HoroscopoServicio {

    private final HoroscopoRepository horoscopoRepository;

    public HoroscopoServicio() {
        this.horoscopoRepository = new HoroscopoRepository();
    }

    @Override
    public List<HoroscopoResponseDTO> listarHoroscopos() {
        return horoscopoRepository.listarHoroscopos().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HoroscopoResponseDTO> buscarHoroscopoPorId(int id) {
        return horoscopoRepository.buscarHoroscopo(id).map(this::mapToDTO);
    }

    @Override
    public void crearHoroscopo(HoroscopoCreateDTO horoscopoCreateDTO) {
        Horoscopo horoscopo = new Horoscopo(
                horoscopoCreateDTO.getId(),
                horoscopoCreateDTO.getAnimalEnum(),
                horoscopoCreateDTO.getAnimal(),
                horoscopoCreateDTO.getFechaInicio(),
                horoscopoCreateDTO.getFechaFinal()
        );
        horoscopoRepository.insertarHoroscopo(horoscopo);
    }

    @Override
    public void actualizarHoroscopo(HoroscopoUpdateDTO horoscopoUpdateDTO) {
        Optional<Horoscopo> horoscopoOptional = horoscopoRepository.buscarHoroscopo(horoscopoUpdateDTO.getId());

        if (horoscopoOptional.isPresent()) {
            Horoscopo horoscopo = horoscopoOptional.get();

            horoscopo.setAnimalEnum(horoscopoUpdateDTO.getAnimalEnum());
            horoscopo.setAnimal(horoscopoUpdateDTO.getAnimal());
            horoscopo.setInicio(horoscopoUpdateDTO.getFechaInicio());
            horoscopo.setFin(horoscopoUpdateDTO.getFechaFinal());

            horoscopoRepository.actualizarHoroscopo(horoscopo);
        }
    }

    @Override
    public void eliminarHoroscopo(int id) {
        horoscopoRepository.eliminarHoroscopo(id);
    }

    private HoroscopoResponseDTO mapToDTO(Horoscopo horoscopo) {
        return new HoroscopoResponseDTO(
                horoscopo.getId(),
                horoscopo.getAnimal(),
                horoscopo.getAnimalEnum(),
                horoscopo.getInicio(),
                horoscopo.getFin()
        );
    }
}
