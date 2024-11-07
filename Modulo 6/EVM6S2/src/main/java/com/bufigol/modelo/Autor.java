package com.bufigol.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    @NotNull(message = "El nombre no puede ser null")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El apellido no puede ser null")
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;
}