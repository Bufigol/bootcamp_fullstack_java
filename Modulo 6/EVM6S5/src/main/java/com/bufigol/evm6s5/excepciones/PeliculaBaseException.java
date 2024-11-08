package com.bufigol.evm6s5.excepciones;

public abstract class PeliculaBaseException extends RuntimeException {
    private final String codigoError;
    private final String detallesTecnicos;
    private final int statusCode;

    protected PeliculaBaseException(String mensaje, String codigoError, String detallesTecnicos, int statusCode) {
        super(mensaje);
        this.codigoError = codigoError;
        this.detallesTecnicos = detallesTecnicos;
        this.statusCode = statusCode;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public String getDetallesTecnicos() {
        return detallesTecnicos;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return String.format("Error [%s]: %s%nDetalles técnicos: %s%nCódigo de estado: %d",
                codigoError, getMessage(), detallesTecnicos, statusCode);
    }

    public String getResumenError() {
        return String.format("Código: %s - Mensaje: %s", codigoError, getMessage());
    }

    public boolean esErrorCritico() {
        return statusCode >= 500;
    }
}