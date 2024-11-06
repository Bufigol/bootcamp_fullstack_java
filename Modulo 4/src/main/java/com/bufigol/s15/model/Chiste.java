package com.bufigol.s15.model;

public class Chiste {
    private String tipo;
    private String setUp;
    private String punchLine;
    private int id;

    public Chiste(String tipo, String setUp, String punchLine, int id) {
        this.tipo = tipo;
        this.setUp = setUp;
        this.punchLine = punchLine;
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSetUp() {
        return setUp;
    }

    public void setSetUp(String setUp) {
        this.setUp = setUp;
    }

    public String getPunchLine() {
        return punchLine;
    }

    public void setPunchLine(String punchLine) {
        this.punchLine = punchLine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
