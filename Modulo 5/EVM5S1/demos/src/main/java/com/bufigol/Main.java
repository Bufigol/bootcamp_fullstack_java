package com.bufigol;

import com.bufigol.modelo.Elefante;
import com.bufigol.modelo.Humano;
import com.bufigol.modelo.Mamifero;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Humano humano_variable = new Humano(2,85);
        Mamifero mamifero_variable = new Humano(4, 85);
        Elefante elefante_variable = new Elefante(4, 85);
        humano_variable.comer();
        mamifero_variable.comer();
        elefante_variable.comer();


    }
}