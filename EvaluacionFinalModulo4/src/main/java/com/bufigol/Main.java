package com.bufigol;

import com.bufigol.vistas.Menu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        menu.iniciarMenu(scanner);
    }
}