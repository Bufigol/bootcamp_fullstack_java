package com.bufigol.universidad.dtos.modelo;

import com.bufigol.universidad.validacion.RutValido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoRequestDTO {

    @NotBlank(message = "El RUT no puede estar vacío")
    @Size(min = 8, max = 12, message = "El RUT debe tener entre 8 y 12 caracteres")
    @RutValido(message = "El RUT ingresado no es válido")
    private String rut;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    private String direccion;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String password;
}