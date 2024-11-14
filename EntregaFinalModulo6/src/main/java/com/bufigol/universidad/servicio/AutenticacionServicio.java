package com.bufigol.universidad.servicio;

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

    @Override
    @Transactional
    public TokenResponseDTO signin(LoginRequestDTO loginRequest) {
        log.debug("Intentando autenticar usuario: {}", loginRequest.getUsername());

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

        return new TokenResponseDTO(
                token,
                tokenProvider.getExpirationDateFromToken(token).getTime(),
                loginRequest.getUsername(),
                roles
        );
    }

    @Override
    @Transactional
    public TokenResponseDTO signup(SignupRequestDTO signupRequest) {
        log.debug("Intentando registrar nuevo usuario: {}", signupRequest.getUsername());

        if (usuarioServicio.existsByUsername(signupRequest.getUsername())) {
            throw new DuplicateResourceException("Usuario", "username", signupRequest.getUsername());
        }

        if (usuarioServicio.existsByEmail(signupRequest.getEmail())) {
            throw new DuplicateResourceException("Usuario", "email", signupRequest.getEmail());
        }

        Set<Role> roles = new HashSet<>();
        signupRequest.getRoles().forEach(roleName -> {
            Role role = roleServicio.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Error: Role " + roleName + " no encontrado"));
            roles.add(role);
        });

        Usuario usuario = new Usuario();
        usuario.setName(signupRequest.getNombre());
        usuario.setUsername(signupRequest.getUsername());
        usuario.setEmail(signupRequest.getEmail());
        usuario.setPassword(signupRequest.getPassword());
        usuario.setRoles(roles);

        usuarioServicio.signup(usuario);

        return signin(new LoginRequestDTO(signupRequest.getUsername(), signupRequest.getPassword()));
    }

    @Override
    public boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return tokenProvider.getUsername(token);
    }
}