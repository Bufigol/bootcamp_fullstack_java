package com.bufigol.ecm6s4.servicio;

import com.bufigol.ecm6s4.interfaces.INT_UsuarioService;
import com.bufigol.ecm6s4.modelo.Usuario;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.regex.Pattern;

@Service
public class UsuarioService implements INT_UsuarioService {
    // Expresiones regulares
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>]).{10,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private ArrayList<Usuario> listaDeUsuarios;

    @PostConstruct
    public void init() {
        listaDeUsuarios = new ArrayList<>();
    }

    @Override
    public boolean validarNombre(Usuario usuario) {
        String nombre = usuario.getNombre();
        return comprobarString(nombre);
    }

    @Override
    public boolean validarApellido(Usuario usuario) {
        String apellido = usuario.getApellido();
        return comprobarString(apellido);
    }

    @Override
    public boolean validarNombreYApellido(Usuario usuario) {
        return (validarNombre(usuario) && validarApellido(usuario));
    }

    @Override
    public boolean validarUserName(Usuario usuario) {
        if (usuario == null || usuario.getUsername() == null || usuario.getUsername().isEmpty()) {
            return false;
        }

        String username = usuario.getUsername();

        // Verifica el formato usando regex
        if (!username.matches(USERNAME_REGEX)) {
            return false;
        }

        // Verifica duplicados (con protección null)
        return listaDeUsuarios == null ||
                listaDeUsuarios.stream()
                        .map(Usuario::getUsername)
                        .noneMatch(u -> u.equals(username));
    }

    @Override
    public boolean validarEmail(Usuario usuario) {
        String email = usuario.getEmail();
        return email != null && !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public boolean validarPassword(Usuario usuario) {
        String password = usuario.getPassword();
        return password != null &&
                password.length() >= 10 &&
                password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        if (usuario == null) {
            return false;
        }

        if (validarUsuario(usuario)) {
            if (listaDeUsuarios == null) {
                listaDeUsuarios = new ArrayList<>();
            }
            listaDeUsuarios.add(usuario);
            return true;
        }
        return false;
    }

    @Override
    public boolean validarUsuario(Usuario usuario) {
        return usuario != null &&
                validarNombreYApellido(usuario) &&
                validarUserName(usuario) &&
                validarEmail(usuario) &&
                validarPassword(usuario);
    }

    private boolean comprobarString(String entrada) {
        if (entrada == null || entrada.isEmpty()) {
            return false;
        }
        return entrada.chars().allMatch(Character::isLetter);
    }
}