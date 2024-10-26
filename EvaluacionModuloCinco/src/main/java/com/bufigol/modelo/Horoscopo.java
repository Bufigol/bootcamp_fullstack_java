package com.bufigol.modelo;

import java.sql.Date;
import java.util.Objects;

public class Horoscopo {
    private String animal;
    private Date inicio;
    private Date fin;

    public Horoscopo(String animal, Date inicio, Date fin) {
        this.animal = animal;
        this.inicio = inicio;
        this.fin = fin;
    }

    public Horoscopo() {
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Horoscopo{");
        sb.append("animal='").append(animal).append('\'');
        sb.append(", inicio=").append(inicio);
        sb.append(", fin=").append(fin);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Horoscopo horoscopo)) return false;
        return Objects.equals(getAnimal(), horoscopo.getAnimal()) && Objects.equals(getInicio(), horoscopo.getInicio()) && Objects.equals(getFin(), horoscopo.getFin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAnimal(), getInicio(), getFin());
    }
}
