package com.bufigol.s11.modelo;

public class Tarjeta  {

    public static String pagar() {
        return "";
    }


    public static String pagar(int cantidad) {
        if(cantidad < 0){
            return "Se te ha devuelto la cantidad de " + cantidad + " CLP a tu tarjeta";
        } else if (cantidad == 0) {
            return "No hay saldo que ajustar";

        }else{
            return "Has pagado con tarjeta la cantidad de " + cantidad + " CLP" ;
        }
    }
}
