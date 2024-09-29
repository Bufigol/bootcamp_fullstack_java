// MenuTemplate.java
package com.bufigol.modelo;

import com.bufigol.utils.EntradaPorTeclado;

import java.util.Scanner;

public abstract class MenuTemplate {

    public abstract void exportarDatos();
    public abstract void importarDatos();
    public abstract void crearAlummno();
    public abstract void agregarMateria();
    public abstract void agregarNotaPasoUno();
    public abstract void listarAlummnos();
    public abstract void terminarPrograma();

    public void iniciarMenu(Scanner scanner) {
        String menu = """
                MENU PRINCIPAL
                1. Crear Alumnos
                2. Listar Alumnos
                3. Agregar Materias
                4. Agregar Notas
                5. Exportar Datos
                6. Importar Datos
                7. Salir
                Selección:""";

        while (true) {
            int opcion = EntradaPorTeclado.pedirEntero(menu, scanner);

            switch (opcion) {
                case 1 -> crearAlummno();
                case 2 -> listarAlummnos();
                case 3 -> agregarMateria();
                case 4 -> agregarNotaPasoUno();
                case 5 -> exportarDatos();
                case 6 -> importarDatos();
                case 7 -> {
                    terminarPrograma();
                    return;
                }
                default -> System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
}