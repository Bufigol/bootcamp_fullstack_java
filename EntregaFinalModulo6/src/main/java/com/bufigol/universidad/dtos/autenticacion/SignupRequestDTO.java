package com.bufigol.universidad.dtos.autenticacion;

import com.bufigol.universidad.utils.MensajesValidacion;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.bufigol.universidad.seguridad.constantes.ConstantesSeguridad.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {

    @NotBlank(message = MensajesValidacion.NOMBRE_REQUERIDO)
    @Size(min = MIN_NAME_LENGTH,
            max = MAX_NAME_LENGTH,
            message = MensajesValidacion.NOMBRE_LONGITUD)
    @Pattern(
            regexp = NAME_PATTERN,
            message = MensajesValidacion.NOMBRE_FORMATO
    )
    private String nombre;

    @NotBlank(message = MensajesValidacion.USERNAME_REQUERIDO)
    @Size(min = MIN_USERNAME_LENGTH,
            max = MAX_USERNAME_LENGTH,
            message = MensajesValidacion.USERNAME_LONGITUD)
    @Pattern(
            regexp = USERNAME_PATTERN,
            message = MensajesValidacion.USERNAME_FORMATO
    )
    private String username;

    @NotBlank(message = MensajesValidacion.EMAIL_REQUERIDO)
    @Email(message = MensajesValidacion.EMAIL_FORMATO)
    @Size(max = MAX_EMAIL_LENGTH,
            message = MensajesValidacion.EMAIL_LONGITUD)
    private String email;

    @NotBlank(message = MensajesValidacion.PASSWORD_REQUERIDO)
    @Size(min = MIN_PASSWORD_LENGTH,
            max = MAX_PASSWORD_LENGTH,
            message = MensajesValidacion.PASSWORD_LONGITUD)
    @Pattern(
            regexp = PASSWORD_PATTERN,
            message = MensajesValidacion.PASSWORD_FORMATO
    )
    private String password;

    @NotBlank(message = MensajesValidacion.CONFIRM_PASSWORD_REQUERIDO)
    private String confirmPassword;

    @NotEmpty(message = MensajesValidacion.ROLES_REQUERIDOS)
    private Set<String> roles;

    /**
     * Valida que los roles proporcionados sean válidos
     */
    @AssertTrue(message = MensajesValidacion.ROLES_INVALIDOS)
    public boolean isValidRoles() {
        return roles != null && roles.stream()
                .allMatch(role -> role.equals(ROLE_ADMIN) ||
                        role.equals(ROLE_CLIENT));
    }

    /**
     * Valida que la contraseña y su confirmación coincidan
     */
    @AssertTrue(message = MensajesValidacion.PASSWORDS_NO_COINCIDEN)
    private boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }

    /**
     * Valida que el tamaño de los roles no exceda el máximo permitido
     */
    @AssertTrue(message = MensajesValidacion.ROLES_MAXIMOS_EXCEDIDOS)
    private boolean isValidRolesSize() {
        return roles == null || roles.size() <= MAX_ROLES_PER_USER;
    }


    public void normalizeRoles() {
        if (roles != null) {
            roles = roles.stream()
                    .map(role -> !role.startsWith(ROLE_PREFIX) ? ROLE_PREFIX + role : role)
                    .collect(java.util.stream.Collectors.toSet());
        }
    }
}