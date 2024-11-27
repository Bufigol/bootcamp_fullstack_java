package com.bufigol.universidad.seguridad.servicios;

import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.SignupRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.TokenResponseDTO;
import com.bufigol.universidad.excepciones.seguridad.JwtAuthenticationException;
import com.bufigol.universidad.excepciones.servicio.DuplicateResourceException;
import com.bufigol.universidad.interfaces.seguridad.INT_AuthenticationServicio;
import com.bufigol.universidad.interfaces.seguridad.INT_TokenServicio;
import com.bufigol.universidad.interfaces.servicio.INT_UsuarioServicio;
import com.bufigol.universidad.modelo.Role;
import com.bufigol.universidad.modelo.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bufigol.universidad.seguridad.constantes.ConstantesSeguridad.INVALID_CREDENTIALS;
import static com.bufigol.universidad.utils.MensajesValidacion.PASSWORDS_NO_COINCIDEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServicio implements INT_AuthenticationServicio {

    private final AuthenticationManager authenticationManager;
    private final INT_TokenServicio tokenService;
    private final INT_UsuarioServicio usuarioServicio;
    private final RoleValidator roleValidator;

    @Override
    @Transactional(readOnly = true)
    public TokenResponseDTO authenticate(LoginRequestDTO credentials) {
        log.debug("Procesando autenticación para usuario: {}", credentials.getUsername());

        try {
            Authentication authentication = performAuthentication(credentials);
            List<String> roles = extractRoles(authentication);
            String token = tokenService.generateToken(credentials.getUsername(), roles);

            TokenResponseDTO response = buildTokenResponse(credentials.getUsername(), token, roles);

            log.info("Usuario {} autenticado exitosamente", credentials.getUsername());
            return response;

        } catch (BadCredentialsException e) {
            log.error("Credenciales inválidas para usuario: {}", credentials.getUsername());
            throw new JwtAuthenticationException(INVALID_CREDENTIALS);
        } catch (Exception e) {
            log.error("Error durante la autenticación del usuario {}: {}",
                    credentials.getUsername(), e.getMessage());
            throw new JwtAuthenticationException("Error en el proceso de autenticación");
        }
    }

    @Override
    @Transactional
    public TokenResponseDTO register(SignupRequestDTO registrationData) {
        log.debug("Iniciando registro para usuario: {}", registrationData.getUsername());

        try {
            validateRegistrationData(registrationData);
            Set<Role> roles = roleValidator.validateAndNormalizeRoles(registrationData.getRoles());

            Usuario usuario = createNewUser(registrationData, roles);
            usuarioServicio.signup(usuario);

            log.info("Usuario registrado exitosamente: {}", usuario.getUsername());

            return authenticate(new LoginRequestDTO(
                    registrationData.getUsername(),
                    registrationData.getPassword()
            ));

        } catch (DuplicateResourceException | IllegalArgumentException e) {
            log.error("Error de validación durante el registro: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado durante el registro de usuario {}: {}",
                    registrationData.getUsername(), e.getMessage());
            throw new RuntimeException("Error en el proceso de registro: " + e.getMessage());
        }
    }

    @Override
    public void validateRegistrationData(SignupRequestDTO data) {
        log.debug("Validando datos de registro para usuario: {}", data.getUsername());

        if (!data.getPassword().equals(data.getConfirmPassword())) {
            throw new IllegalArgumentException(PASSWORDS_NO_COINCIDEN);
        }

        if (usuarioServicio.existsByUsername(data.getUsername())) {
            throw new DuplicateResourceException("Usuario", "username", data.getUsername());
        }

        if (usuarioServicio.existsByEmail(data.getEmail())) {
            throw new DuplicateResourceException("Usuario", "email", data.getEmail());
        }
    }

    // Métodos privados auxiliares
    private Authentication performAuthentication(LoginRequestDTO credentials) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword()
                )
        );
    }

    private List<String> extractRoles(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    private TokenResponseDTO buildTokenResponse(String username, String token, List<String> roles) {
        return TokenResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .username(username)
                .roles(roles)
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(24)) // O usar la configuración real de expiración
                .build();
    }

    private Usuario createNewUser(SignupRequestDTO data, Set<Role> roles) {
        Usuario usuario = new Usuario();
        usuario.setName(data.getNombre());
        usuario.setUsername(data.getUsername());
        usuario.setEmail(data.getEmail());
        usuario.setPassword(data.getPassword());
        usuario.setRoles(roles);
        return usuario;
    }
}