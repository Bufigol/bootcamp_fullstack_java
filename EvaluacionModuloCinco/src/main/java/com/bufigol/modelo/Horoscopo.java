package com.bufigol.modelo;

import java.sql.Date;
import java.util.Objects;

public class Horoscopo {
    private int id;
    private AnimalesHoroscopoEnum animalEnum;
    private String animal;
    private Date inicio;
    private Date fin;

    public Horoscopo(int id, AnimalesHoroscopoEnum animalEnum, String animal, Date inicio, Date fin) {
        this.id = id;
        this.animalEnum = animalEnum;
        this.animal = animal;
        this.inicio = inicio;
        this.fin = fin;
    }

    public Horoscopo() {
    }

    public AnimalesHoroscopoEnum getAnimalEnum() {
        return animalEnum;
    }

    public void setAnimalEnum(AnimalesHoroscopoEnum animalEnum) {
        this.animalEnum = animalEnum;
        this.animal = animalEnum != null ? animalEnum.getNombre() : null;
    }

    public String getAnimal() {
        return animal;
    }

    /**
     * Setter para animal que actualiza tanto la versión String como el Enum
     * @param animal nombre del animal a establecer
     * @throws IllegalArgumentException si el nombre no corresponde a ningún animal del horóscopo
     */
    public void setAnimal(String animal) {
        this.animal = animal;
        if (animal != null) {
            try {
                this.animalEnum = java.util.Arrays.stream(AnimalesHoroscopoEnum.values())
                        .filter(a -> a.getNombre().equalsIgnoreCase(animal))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Animal no válido: " + animal));
            } catch (IllegalArgumentException e) {
                // Si no se encuentra el animal, se mantiene el valor actual de animalEnum
                throw new IllegalArgumentException("Animal no válido: " + animal);
            }
        } else {
            this.animalEnum = null;
        }
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    /**
     * Obtiene la descripción completa del animal del horóscopo
     * @return String con la información detallada del animal
     */
    public String getDescripcionAnimal() {
        return animalEnum != null ? animalEnum.getDescripcionCompleta() : "Animal no especificado";
    }

    /**
     * Obtiene el elemento asociado al animal del horóscopo
     * @return String con el elemento correspondiente
     */
    public String getElemento() {
        return animalEnum != null ? animalEnum.getElemento() : "Elemento no disponible";
    }

    /**
     * Calcula la compatibilidad con otro horóscopo
     * @param otroHoroscopo El horóscopo con el que se quiere comparar
     * @return Un nivel de compatibilidad del 1 al 5, 0 si alguno de los animales es null
     */
    public int calcularCompatibilidad(Horoscopo otroHoroscopo) {
        if (this.animalEnum == null || otroHoroscopo == null || otroHoroscopo.getAnimalEnum() == null) {
            return 0;
        }
        return this.animalEnum.calcularCompatibilidad(otroHoroscopo.getAnimalEnum());
    }

    /**
     * Verifica si las fechas del horóscopo están dentro del año del animal correspondiente
     * @return true si las fechas corresponden al año del animal
     */
    public boolean verificarCorrespondenciaFechas() {
        if (inicio == null || fin == null || animalEnum == null) {
            return false;
        }

        int añoInicio = inicio.toLocalDate().getYear();
        int añoFin = fin.toLocalDate().getYear();

        return animalEnum.esAñoDeEsteAnimal(añoInicio) && animalEnum.esAñoDeEsteAnimal(añoFin);
    }

    /**
     * Sincroniza el animal String con el Enum en caso de inconsistencias
     * @return true si se realizó alguna sincronización
     */
    public boolean sincronizarAnimal() {
        if (animalEnum != null && !animalEnum.getNombre().equals(animal)) {
            animal = animalEnum.getNombre();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Horoscopo{");
        sb.append("animalEnum=").append(animalEnum);
        sb.append(", animal='").append(animal).append('\'');
        sb.append(", inicio=").append(inicio);
        sb.append(", fin=").append(fin);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Horoscopo horoscopo)) return false;
        return Objects.equals(getAnimalEnum(), horoscopo.getAnimalEnum()) &&
                Objects.equals(getAnimal(), horoscopo.getAnimal()) &&
                Objects.equals(getInicio(), horoscopo.getInicio()) &&
                Objects.equals(getFin(), horoscopo.getFin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAnimalEnum(), getAnimal(), getInicio(), getFin());
    }
}