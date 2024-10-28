package com.bufigol.dtos.horoscopo;

import com.bufigol.modelo.AnimalesHoroscopoEnum;

import java.sql.Date;

public class HoroscopoCreateDTO {
    private String animal;
    private AnimalesHoroscopoEnum animalEnum;
    private Date fechaInicio;
    private Date fechaFinal;

    public HoroscopoCreateDTO(String animal, AnimalesHoroscopoEnum animalEnum, Date fechaInicio, Date fechaFinal) {
        this.animal = animal;
        this.animalEnum = animalEnum;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public HoroscopoCreateDTO() {
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public AnimalesHoroscopoEnum getAnimalEnum() {
        return animalEnum;
    }

    public void setAnimalEnum(AnimalesHoroscopoEnum animalEnum) {
        this.animalEnum = animalEnum;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
}
