package com.bufigol.universidad.interfaces.servicio;

import com.bufigol.universidad.modelo.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface INT_UsuarioServicio extends UserDetailsService {
    Usuario signup(Usuario usuario);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Usuario save(Usuario usuario);  // Añadido este método
}