package com.bufigol.modelo;

import java.util.Scanner;

public abstract class MenuTemplate {

    public abstract void exportarDatos();

    public abstract void crearAlummno();

    public abstract void agregarMateria();

    public abstract void agregarNotaPasoUno();

    public abstract void listarAlummnos();

    public abstract void terminarPrograma();

    public void iniciarMenu(Scanner scanner) {

    }

}
