package com.bufigol.s11.modelo;

public class Efectivo  {

    public static String pagar() {
        return "Has pagado con Efectivo";
    }


    public static String pagar(int cantidad) {
        if(cantidad < 0){
            return "Se te ha devuelto la cantidad de " + cantidad + " CLP en efectivo";
        } else if (cantidad == 0) {
            return "No hay saldo que ajustar";

        }else{
            return "Has pagado con efectivo la cantidad de " + cantidad + " CLP en efectivo" ;
        }
    }
}
