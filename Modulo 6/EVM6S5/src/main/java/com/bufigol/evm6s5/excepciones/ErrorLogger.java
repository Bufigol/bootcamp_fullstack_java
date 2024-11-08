package com.bufigol.evm6s5.excepciones;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLogger {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logError(PeliculaBaseException e) {
        StringBuilder log = new StringBuilder()
                .append("\n=== Error Log ===\n")
                .append("Timestamp: ").append(LocalDateTime.now().format(formatter)).append("\n")
                .append("Código de Error: ").append(e.getCodigoError()).append("\n")
                .append("Mensaje: ").append(e.getMessage()).append("\n")
                .append("Detalles Técnicos: ").append(e.getDetallesTecnicos()).append("\n")
                .append("Status Code: ").append(e.getStatusCode()).append("\n");

        if (e instanceof PeliculaIdExistenteException) {
            log.append("Sugerencia: ").append(((PeliculaIdExistenteException) e).getSugerencia()).append("\n");
        } else if (e instanceof PeliculaNoEncontradaException) {
            log.append("ID Buscado: ").append(((PeliculaNoEncontradaException) e).getIdBuscado()).append("\n")
                    .append("Sugerencia: ").append(((PeliculaNoEncontradaException) e).getSugerencia()).append("\n");
        } else if (e instanceof PeliculaIdNoRegistradaException) {
            log.append("ID Inválido: ").append(((PeliculaIdNoRegistradaException) e).getIdInvalido()).append("\n")
                    .append("Sugerencia: ").append(((PeliculaIdNoRegistradaException) e).getSugerencia()).append("\n");
        }

        log.append("Error Crítico: ").append(e.esErrorCritico()).append("\n")
                .append("================\n");

        System.err.println(log.toString());
    }
}