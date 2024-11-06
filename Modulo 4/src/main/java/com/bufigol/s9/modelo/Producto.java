package com.bufigol.s9.modelo;

public class Producto<T> {
    private T tipoProducto;

    public Producto(T tipoProducto, String dato) {
        super();
        this.tipoProducto = tipoProducto;
        if (tipoProducto instanceof Zapatos) {
            ((Zapatos) tipoProducto).setMarca(dato);
        }
        if (tipoProducto instanceof Poleras) {
            ((Poleras) tipoProducto).setColor(dato);
        }
    }
    public Producto() {
        super();
    }

    public T getTipoProducto() {
        return tipoProducto;
    }
    public void setTipoProducto(T tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Producto{");
        if (tipoProducto instanceof Zapatos) {
            sb.append("marca = ");
            sb.append( ((Zapatos) tipoProducto).getMarca() );
        }
        if (tipoProducto instanceof Poleras) {
            sb.append("color = ");
            sb.append(((Poleras) tipoProducto).getColor());
        }
        sb.append('}');
        return sb.toString();
    }
}
