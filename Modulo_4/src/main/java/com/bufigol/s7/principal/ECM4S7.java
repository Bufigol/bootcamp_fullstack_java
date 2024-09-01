package com.bufigol.s7.principal;

import com.bufigol.s7.modelo.Auto;
import com.bufigol.s7.modelo.Bicicleta;

public class ECM4S7 {

    public static void main(String[] args){

        Bicicleta bicicleta = new Bicicleta(2,0,"urbana");
        Auto auto = new Auto(4,5,"rojo","GT-R");

        bicicleta.encender();
        bicicleta.encender();
        bicicleta.apagar();
        bicicleta.apagar();
        auto.encender();
        auto.encender();
        auto.apagar();
        auto.apagar();
        System.out.println(auto.tipoDeBencina());
        auto.tipoDeBencina("diesel");
        System.out.println(auto.tipoDeBencina());
        System.out.println(auto.toString());
        System.out.println(bicicleta.toString());

    }
}
