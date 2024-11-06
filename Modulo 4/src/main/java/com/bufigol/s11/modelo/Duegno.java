package com.bufigol.s11.modelo;

import java.util.Scanner;

public class Duegno {
    private static Duegno duegno;

    private Duegno() {
    }

    public static Duegno getInstance() {
        if (duegno == null) {
            duegno = new Duegno();
            return getInstance();
        }else{
            return duegno;
        }

    }

    public void getionarPago() {
        System.out.println(
                """
                Seleccione el metodo de pago:
                1. Tarjeta
                2. Efectivo
                """);
        int metodo = new Scanner(System.in).nextInt();

        switch (metodo) {
            case 1:
                System.out.println(Tarjeta.pagar());
                break;
            case 2:
                System.out.println(Efectivo.pagar());
                break;
            default:
                getionarPago();
                break;
        }
    }
}
