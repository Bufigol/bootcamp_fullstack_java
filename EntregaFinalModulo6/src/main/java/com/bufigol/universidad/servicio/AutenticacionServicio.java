package com.bufigol.universidad.servicio;

import com.bufigol.universidad.utils.CustomPasswordEncoder;
import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.SignupRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.TokenResponseDTO;
import com.bufigol.universidad.excepciones.servicio.DuplicateResourceException;
import com.bufigol.universidad.interfaces.servicio.INT_AutenticacionServicio;
import com.bufigol.universidad.interfaces.servicio.INT_RoleServicio;
import com.bufigol.universidad.interfaces.servicio.INT_UsuarioServicio;
import com.bufigol.universidad.modelo.Role;
import com.bufigol.universidad.modelo.Usuario;
import com.bufigol.universidad.seguridad.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutenticacionServicio implements INT_AutenticacionServicio {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final INT_UsuarioServicio usuarioServicio;
    private final INT_RoleServicio roleServicio;
    private final CustomPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TokenResponseDTO signin(LoginRequestDTO loginRequest) {
        log.debug("Intentando autenticar usuario: {}", loginRequest.getUsername());

        try {
            // El AuthenticationManager usará internamente el CustomPasswordEncoder
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = tokenProvider.createToken(loginRequest.getUsername(), roles);

            log.info("Usuario {} autenticado exitosamente", loginRequest.getUsername());

            return new TokenResponseDTO(
                    token,
                    tokenProvider.getExpirationDateFromToken(token).getTime(),
                    loginRequest.getUsername(),
                    roles
            );
        } catch (Exception e) {
            log.error("Error durante la autenticación del usuario {}: {}",
                    loginRequest.getUsername(), e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public TokenResponseDTO signup(SignupRequestDTO signupRequest) {
        log.debug("Iniciando registro de nuevo usuario: {}", signupRequest.getUsername());

        // Validaciones de usuario existente
        if (usuarioServicio.existsByUsername(signupRequest.getUsername())) {
            log.warn("Intento de registro con nombre de usuario existente: {}",
                    signupRequest.getUsername());
            throw new DuplicateResourceException("Usuario", "username", signupRequest.getUsername());
        }

        if (usuarioServicio.existsByEmail(signupRequest.getEmail())) {
            log.warn("Intento de registro con email existente: {}", signupRequest.getEmail());
            throw new DuplicateResourceException("Usuario", "email", signupRequest.getEmail());
        }

        try {
            // Validar y obtener roles
            Set<Role> roles = new HashSet<>();
            for (String roleName : signupRequest.getRoles()) {
                Role role = roleServicio.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role no encontrado: " + roleName));
                roles.add(role);
            }

            // Crear nuevo usuario
            Usuario usuario = new Usuario();
            usuario.setName(signupRequest.getNombre());
            usuario.setUsername(signupRequest.getUsername());
            usuario.setEmail(signupRequest.getEmail());
            usuario.setPassword(signupRequest.getPassword()); // La encriptación se maneja en usuarioServicio
            usuario.setRoles(roles);

            // Guardar usuario (la encriptación de la contraseña se maneja en usuarioServicio.signup)
            Usuario savedUser = usuarioServicio.signup(usuario);
            log.info("Usuario registrado exitosamente: {}", savedUser.getUsername());

            // Iniciar sesión automáticamente después del registro
            return signin(new LoginRequestDTO(signupRequest.getUsername(), signupRequest.getPassword()));

        } catch (DuplicateResourceException e) {
            log.error("Error de duplicado durante el registro: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error durante el registro de usuario {}: {}",
                    signupRequest.getUsername(), e.getMessage());
            throw new RuntimeException("Error durante el registro de usuario", e);
        }
    }

    @Override
    public boolean validateToken(String token) {
        log.debug("Validando token JWT");
        try {
            return tokenProvider.validateToken(token);
        } catch (Exception e) {
            log.error("Error al validar token JWT: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        log.debug("Extrayendo username de token JWT");
        try {
            return tokenProvider.getUsername(token);
        } catch (Exception e) {
            log.error("Error al extraer username del token: {}", e.getMessage());
            throw e;
        }
    }
}