package com.bufigol.utils;

import com.bufigol.modelo.AnimalesHoroscopoEnum;

public class OtrasUtilidades {

    public static AnimalesHoroscopoEnum getAnimalEnumFromString(String animal) {
        AnimalesHoroscopoEnum animalEnum = null;
        int i = 0;
        while(animalEnum == null && i < AnimalesHoroscopoEnum.values().length) {
            if(AnimalesHoroscopoEnum.values()[i].getNombre().equalsIgnoreCase(animal)) {
                animalEnum = AnimalesHoroscopoEnum.values()[i];
            }
            i++;
        }
        return animalEnum;
    }
}
