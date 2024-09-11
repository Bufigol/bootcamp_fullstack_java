package com.bufigol.s12.main;

import com.bufigol.utils.EntradaPorTeclado;
import com.bufigol.utils.MetodosMatematicos;

import java.io.*;
import java.util.ArrayList;

public class ECM4S12 {

    public static void main(String[] args) {
        int opc;
        String rutaArchivo = "src/main/resources/participantes_M4S12.txt";
        do {
            opc = mostrarMenu();
            switch (opc) {
                case 1:
                    agregarParticipante(rutaArchivo,EntradaPorTeclado.pedirCadena("Nombre del participante"));
                    break;
                case 2:
                    mostrarParticipantes(rutaArchivo);
                    break;
                case 3:
                    elegirGanador(rutaArchivo);
                    break;
                case 4:
                    System.out.println("Hasta Luego!");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        } while (opc != 4);
    }

    private static void elegirGanador(String rutaArchivo) {
        ArrayList<String> participantes = new ArrayList<>();
        try {
            FileReader fr = new FileReader(rutaArchivo);
            BufferedReader br = new BufferedReader(fr);
            String data = br.readLine();
            while (data != null) {
                if(!data.isEmpty() || !data.isBlank()) {
                    participantes.add(data);
                }
                data = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Excepcion leyendo fichero " + rutaArchivo +
                    ": " + e);
        }finally {
            if(!participantes.isEmpty()){
                System.out.println("El ganador es: " + participantes.get(MetodosMatematicos.generateRandomInt(0,participantes.size()-1)));
            }
        }
    }

    private static void mostrarParticipantes(String rutaArchivo) {
        try {
            FileReader fr = new FileReader(rutaArchivo);
            BufferedReader br = new BufferedReader(fr);
            String data = br.readLine();
            while (data != null) {
                if(!data.isEmpty() || !data.isBlank()) {
                    System.out.println(data);
                }
                data = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Excepcion leyendo fichero " + rutaArchivo +
                    ": " + e);
        }

    }

    private static void agregarParticipante(String rutaArchivo, String nombreDelParticipante) {
        try {
            FileWriter archivo = new FileWriter(rutaArchivo, true);
            PrintWriter pw = new PrintWriter(archivo);
            pw.println("\n" + nombreDelParticipante);
            pw.close();
            archivo.close();
        } catch (Exception e) {
            System.out.println("Problemas en ejecucion" + e);
        }
    }

    private static int mostrarMenu() {
        String menu = """
                      1- Agegar participante
                      2- Ver Lista de participantes
                      3- Seleccionar un ganador
                      4- Salir
                      Seleccione una opción:
                      """;
        return EntradaPorTeclado.pedirEntero(menu);
    }
}