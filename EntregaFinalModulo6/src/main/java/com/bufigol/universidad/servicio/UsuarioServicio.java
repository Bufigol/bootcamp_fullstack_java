package com.bufigol.universidad.servicio;

import com.bufigol.universidad.configuracion.CustomPasswordEncoder;
import com.bufigol.universidad.excepciones.servicio.DuplicateResourceException;
import com.bufigol.universidad.interfaces.servicio.INT_UsuarioServicio;
import com.bufigol.universidad.modelo.Usuario;
import com.bufigol.universidad.repositorio.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServicio implements INT_UsuarioServicio {

    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Usuario signup(Usuario usuario) {
        Assert.notNull(usuario, "El usuario no puede ser nulo");
        Assert.hasText(usuario.getUsername(), "El nombre de usuario no puede estar vacío");
        Assert.hasText(usuario.getEmail(), "El email no puede estar vacío");
        Assert.hasText(usuario.getPassword(), "La contraseña no puede estar vacía");

        log.debug("Registrando nuevo usuario: {}", usuario.getUsername());

        // Verificar si el username ya existe
        if (existsByUsername(usuario.getUsername())) {
            throw new DuplicateResourceException("Usuario", "username", usuario.getUsername());
        }

        // Verificar si el email ya existe
        if (existsByEmail(usuario.getEmail())) {
            throw new DuplicateResourceException("Usuario", "email", usuario.getEmail());
        }

        try {
            // Encriptar la contraseña usando nuestro CustomPasswordEncoder
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            Usuario savedUser = userRepository.save(usuario);
            log.info("Usuario registrado exitosamente con ID: {}", savedUser.getId());

            return savedUser;
        } catch (Exception e) {
            log.error("Error al registrar usuario: {}", e.getMessage());
            throw new RuntimeException("Error al registrar el usuario", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByUsername(String username) {
        Assert.hasText(username, "El nombre de usuario no puede estar vacío");
        log.debug("Buscando usuario por username: {}", username);

        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            log.error("Error al buscar usuario por username {}: {}", username, e.getMessage());
            throw new RuntimeException("Error al buscar usuario por username", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        Assert.hasText(email, "El email no puede estar vacío");
        log.debug("Buscando usuario por email: {}", email);

        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            log.error("Error al buscar usuario por email {}: {}", email, e.getMessage());
            throw new RuntimeException("Error al buscar usuario por email", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        Assert.hasText(username, "El nombre de usuario no puede estar vacío");
        log.debug("Verificando existencia de usuario por username: {}", username);

        try {
            return userRepository.existsByUsername(username);
        } catch (Exception e) {
            log.error("Error al verificar existencia de usuario por username {}: {}",
                    username, e.getMessage());
            throw new RuntimeException("Error al verificar existencia de usuario", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        Assert.hasText(email, "El email no puede estar vacío");
        log.debug("Verificando existencia de usuario por email: {}", email);

        try {
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            log.error("Error al verificar existencia de usuario por email {}: {}",
                    email, e.getMessage());
            throw new RuntimeException("Error al verificar existencia de usuario", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.hasText(username, "El nombre de usuario no puede estar vacío");
        log.debug("Cargando usuario por username: {}", username);

        try {
            Usuario usuario = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            String.format("Usuario no encontrado con username: %s", username)));

            return new User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    usuario.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                            .collect(Collectors.toList())
            );
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al cargar usuario por username {}: {}", username, e.getMessage());
            throw new RuntimeException("Error al cargar usuario", e);
        }
    }
}