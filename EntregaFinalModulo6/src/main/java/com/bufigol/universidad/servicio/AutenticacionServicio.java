package com.bufigol.universidad.servicio;

import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.SignupRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.TokenResponseDTO;
import com.bufigol.universidad.excepciones.seguridad.JwtAuthenticationException;
import com.bufigol.universidad.excepciones.servicio.DuplicateResourceException;
import com.bufigol.universidad.interfaces.seguridad.INT_TokenServicio;
import com.bufigol.universidad.interfaces.servicio.INT_AutenticacionServicio;
import com.bufigol.universidad.modelo.Role;
import com.bufigol.universidad.modelo.Usuario;
import com.bufigol.universidad.seguridad.servicios.RoleValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bufigol.universidad.seguridad.constantes.ConstantesSeguridad.INVALID_CREDENTIALS;
import static com.bufigol.universidad.utils.MensajesValidacion.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AutenticacionServicio implements INT_AutenticacionServicio {

    private final AuthenticationManager authenticationManager;
    private final INT_TokenServicio tokenService;
    private final RoleValidator roleValidator;
    private final UsuarioServicio usuarioServicio;

    @Override
    public TokenResponseDTO logIn(LoginRequestDTO loginRequest) {
        log.debug("Iniciando proceso de autenticación para usuario: {}", loginRequest.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = tokenService.generateToken(loginRequest.getUsername(), roles);

            Date expirationDate = tokenService.getExpirationDateFromToken(token);
            LocalDateTime expirationDateTime = LocalDateTime.ofInstant(
                    expirationDate.toInstant(),
                    ZoneId.systemDefault()
            );

            log.info("Usuario {} autenticado exitosamente", loginRequest.getUsername());

            return TokenResponseDTO.builder()
                    .token(token)
                    .tokenType("Bearer")
                    .username(loginRequest.getUsername())
                    .roles(roles)
                    .issuedAt(LocalDateTime.now())
                    .expiresAt(expirationDateTime)
                    .build();

        } catch (BadCredentialsException e) {
            log.error("Credenciales inválidas para usuario: {}", loginRequest.getUsername());
            throw new JwtAuthenticationException(INVALID_CREDENTIALS);
        } catch (Exception e) {
            log.error("Error durante la autenticación del usuario {}: {}",
                    loginRequest.getUsername(), e.getMessage());
            throw new JwtAuthenticationException("Error en el proceso de autenticación: " + e.getMessage());
        }
    }


    @Override
    @Transactional
    public TokenResponseDTO signup(SignupRequestDTO signupRequest) {
        log.debug("Iniciando proceso de registro para usuario: {}", signupRequest.getUsername());

        try {
            validateSignupRequest(signupRequest);
            Set<Role> roles = roleValidator.validateAndNormalizeRoles(signupRequest.getRoles());

            Usuario usuario = new Usuario();
            usuario.setName(signupRequest.getNombre());
            usuario.setUsername(signupRequest.getUsername());
            usuario.setEmail(signupRequest.getEmail());
            usuario.setPassword(signupRequest.getPassword());
            usuario.setRoles(roles);

            usuarioServicio.signup(usuario);
            log.info("Usuario registrado exitosamente: {}", usuario.getUsername());

            // Crear nuevo objeto LoginRequestDTO para el logIn
            LoginRequestDTO loginRequest = new LoginRequestDTO(
                    signupRequest.getUsername(),
                    signupRequest.getPassword()
            );

            return logIn(loginRequest);

        } catch (DuplicateResourceException | IllegalArgumentException e) {
            log.error("Error de validación durante el registro: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado durante el registro de usuario {}: {}",
                    signupRequest.getUsername(), e.getMessage());
            throw new JwtAuthenticationException("Error en el proceso de registro: " + e.getMessage());
        }
    }


    @Override
    public boolean validateToken(String token) {
        try {
            return tokenService.validateToken(token);
        } catch (Exception e) {
            log.error("Error validando token: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        try {
            return tokenService.getUsernameFromToken(token);
        } catch (Exception e) {
            log.error("Error extrayendo username del token: {}", e.getMessage());
            throw new JwtAuthenticationException("Error al procesar el token: " + e.getMessage());
        }
    }

    private void validateSignupRequest(SignupRequestDTO request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException(PASSWORDS_NO_COINCIDEN);
        }

        if (usuarioServicio.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Usuario", "username", request.getUsername());
        }

        if (usuarioServicio.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Usuario", "email", request.getEmail());
        }
    }

    private Usuario createNewUser(SignupRequestDTO request, Set<Role> roles) {
        Usuario usuario = new Usuario();
        usuario.setName(request.getNombre());
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setRoles(roles);
        return usuario;
    }
}