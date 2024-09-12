package com.bufigol.s14.main;

import com.bufigol.s14.execpciones.MaxSociosException;
import com.bufigol.s14.execpciones.SocioExistenteException;
import com.bufigol.s14.modelo.Socio;
import com.bufigol.utils.EntradaPorTeclado;

public class EVM4S14 {

    public static void main(String[] args) {
        ejecucionPrograma();
    }

    private static void ejecucionPrograma() {
        int opc = 0;
        Socio[] socios = new Socio[7];
        do{
            try {
                opc = mostrarMenu();
                switch (opc) {
                    case 1:
                        socios = agregarSocio(socios);
                        break;
                    case 2:
                        System.out.println("Hasta pronto!");
                        EntradaPorTeclado.cerrarScanner();
                        break;
                    default:
                        System.out.println("Opcion no valida");
                        break;
                }
            } catch (MaxSociosException | SocioExistenteException e) {
                System.out.println(e.getMessage());
            }

        }while (opc != 2);
    }

    private static Socio[] agregarSocio(Socio[] socios) throws MaxSociosException, SocioExistenteException {
        int espacioDisponible = obtenerEspacioDisponible(socios);
        String nombre = EntradaPorTeclado.pedirCadena("Ingrese el Nombre");
        int edad = EntradaPorTeclado.pedirEntero("Ingrese la Edad");
        String cargo = EntradaPorTeclado.pedirCadena("Ingrese el Cargo");
        Socio socioNuevo = new Socio(nombre, edad, cargo);
        if( verificarSocioNuevo(socios,socioNuevo) ){
            socios[espacioDisponible] = socioNuevo;
        } else {
            throw new SocioExistenteException("Socio ya esta afiliado");
        }
        return socios;
    }

    private static boolean verificarSocioNuevo(Socio[] socios, Socio socioNuevo) {
        boolean out = true;
        int i = 0;
        while (out && i < socios.length) {
            if (socios[i] != null && socios[i].equals(socioNuevo)) {
                out = false;
            }
            i++;
        }
        return out;
    }

    private static int obtenerEspacioDisponible(Socio[] socios) throws MaxSociosException {
        boolean noHayEspacio = true;
        int i = 0;
        int espacioDisponible = 0;
        while (noHayEspacio && i < socios.length) {
            if (socios[i] == null) {
                noHayEspacio = false;
                espacioDisponible = i;
            } else {
                i++;
            }
        }
        if (noHayEspacio) {
            throw new MaxSociosException("El club ha llegado al numero maximo de socios");
        }else {
            return espacioDisponible;
        }
    }

    private static int mostrarMenu() {
        String menu = """
        1- Ingresar nuevo socio
        2- Cerrar la aplicación
        """;
        return EntradaPorTeclado.pedirEntero(menu);
    }
}