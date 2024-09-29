package com.bufigol.servicios;

import java.util.List;

public class PromedioServicioImp {

    public double calcularPromedio(List<Float> notas) {
        if (notas == null || notas.isEmpty()) {
            throw new IllegalArgumentException("La lista de notas no puede ser nula o vacía");
        }

        double suma = 0;
        for (Float nota : notas) {
            suma += nota;
        }
        return suma / notas.size();
    }
}