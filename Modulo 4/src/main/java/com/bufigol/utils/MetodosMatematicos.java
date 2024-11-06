package com.bufigol.utils;

import java.util.Random;

public class MetodosMatematicos {
    public static int generateRandomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
