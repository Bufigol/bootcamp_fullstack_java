package com.bufigol.modelo;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Editorial {

    @NonNull
    @NotBlank(message = "El nombre de la editorial no puede estar vacío")
    private String nombre;

    @NonNull
    @NotBlank(message = "El país de la editorial no puede estar vacío")
    private String pais; // Corregido: 'país' a 'pais' para evitar problemas con caracteres especiales
}