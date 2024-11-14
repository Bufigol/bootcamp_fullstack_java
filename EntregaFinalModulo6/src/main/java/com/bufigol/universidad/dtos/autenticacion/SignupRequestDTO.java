package com.bufigol.universidad.dtos.autenticacion;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",
            message = "El nombre solo puede contener letras y espacios"
    )
    private String nombre;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]{3,}$",
            message = "El nombre de usuario solo puede contener letras, números, puntos, guiones bajos y guiones medios"
    )
    private String username;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "Debe proporcionar un correo electrónico válido")
    @Size(max = 100, message = "El correo electrónico no puede exceder los 100 caracteres")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$",
            message = "La contraseña debe contener al menos un número, una letra mayúscula, una letra minúscula, " +
                    "un carácter especial y no debe contener espacios"
    )
    private String password;

    @NotBlank(message = "La confirmación de contraseña no puede estar vacía")
    private String confirmPassword;

    @NotEmpty(message = "Debe seleccionar al menos un rol")
    private Set<String> roles;

    // Método personalizado para validar que las contraseñas coincidan
    @AssertTrue(message = "Las contraseñas no coinciden")
    private boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }
}
