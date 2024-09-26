package com.bufigol.s17.ECM4S17.Main;

import com.bufigol.s17.ECM4S17.Model.*;

public class Main {

    public static void main(String[] args) {

        Avíon avíon = new Avíon("Boeing 737", "Boeing 737", false,
                new Alas("10", false), new Motores(false, false),
                new Ruedas(false, true),
                new Cabina(100, 2, false, false));
        PistaAeropuerto pistaAeropuerto = new PistaAeropuerto(true);
        avíon.recibirPasajeros();
        avíon.comprobarAvion();
        nuevoDespegue(avíon, pistaAeropuerto);
    }

    private static void nuevoDespegue(Avíon avíon, PistaAeropuerto pistaAeropuerto) {
        if (avíon.isListoParaDespegar()) {
            if(pistaAeropuerto.isDispoible()) {
                pistaAeropuerto.actualizarDisponiblidad();
                avíon.despegar();
                pistaAeropuerto.actualizarDisponiblidad();
            }else {
                System.out.println("Pista no disponible");
                pistaAeropuerto.actualizarDisponiblidad();
                avíon.comprobarAvion();
                nuevoDespegue(avíon, pistaAeropuerto);
            }
        }
    }
}
