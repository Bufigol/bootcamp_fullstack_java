package com.bufigol.modelo;

public class testeo {

    public static void main(String[] args) {
        System.out.println(AnimalesHoroscopoEnum.valueOf("SERPIENTE"));
        AnimalesHoroscopoEnum test = AnimalesHoroscopoEnum.valueOf("SERPIENTE");
        System.out.println(test.toString());
    }
}
