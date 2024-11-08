package com.bufigol.evm6s4.servicio;

import com.bufigol.evm6s4.interfaces.INT_UsuarioServicio;
import com.bufigol.evm6s4.modelo.Usuario;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UsuarioServicio implements INT_UsuarioServicio {
    private List<Usuario> listaDeUsuarios;

    // Expresiones regulares para comprobaciones
    private static final String WORD_REGEX = "^[a-zA-Z]+$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!¡¿?*])(?=\\S+$).{10,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9]+([\\.\\-\\_]?[a-zA-Z0-9]+)*@[a-zA-Z0-9]+([\\.\\-]?[a-zA-Z0-9]+)*\\.[a-zA-Z]{2,}$";

    private static final Pattern WORD_PATTERN = Pattern.compile(WORD_REGEX);
    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @PostConstruct
    public void init() {
        listaDeUsuarios = new ArrayList<>();
    }

    @Override
    public boolean agregarUsuario(Usuario usuario) {
        // Verificar que el username no exista antes de agregar
        if(validarDatos(usuario) && !usernameExiste(usuario.getUsername())){
            listaDeUsuarios.add(usuario);
            return true;
        }
        return false;
    }


    @Override
    public boolean editarUsuario(Usuario usuario) {
        // Si el usuario es null o no existe, retornamos false
        if (usuario == null) return false;

        // Buscamos el índice del usuario existente
        int indice = -1;
        for (int i = 0; i < listaDeUsuarios.size(); i++) {
            if (listaDeUsuarios.get(i).getUsername().equals(usuario.getUsername())) {
                indice = i;
                break;
            }
        }

        // Si no encontramos el usuario, retornamos false
        if (indice == -1) return false;

        // Validamos los nuevos datos
        if (validarNombre(usuario.getNombre()) &&
                validarApellido(usuario.getApellido()) &&
                validarPassword(usuario.getPassword()) &&
                validarEmail(usuario.getEmail())) {

            // Actualizamos el usuario en el índice encontrado
            listaDeUsuarios.set(indice, usuario);
            return true;
        }

        return false;
    }

    @Override
    public boolean eliminarUsuario(String username) {
        Usuario usuario = buscarPorUsername(username);
        if (usuario != null && !usuario.equals(new Usuario())) {
            return listaDeUsuarios.remove(usuario);
        }
        return false;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(listaDeUsuarios);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        if(!listaDeUsuarios.isEmpty() && username != null){
            return listaDeUsuarios.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(new Usuario());
        }
        return new Usuario();
    }

    @Override
    public boolean validarDatos(Usuario usuario) {
        if(usuario == null) return false;

        return validarNombre(usuario.getNombre()) &&
                validarApellido(usuario.getApellido()) &&
                validarUsername(usuario.getUsername()) &&
                validarPassword(usuario.getPassword()) &&
                validarEmail(usuario.getEmail());
    }

    @Override
    public boolean validarUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        // Verificar que no contenga espacios y siga el patrón
        if (username.contains(" ") || !USERNAME_PATTERN.matcher(username).matches()) {
            return false;
        }

        return true;  // Solo validamos el formato
    }

    // Método para verificar si un username ya existe
    private boolean usernameExiste(String username) {
        return listaDeUsuarios.stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }

    @Override
    public boolean validarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return false;
        }
        return WORD_PATTERN.matcher(nombre).matches();
    }

    @Override
    public boolean validarApellido(String apellido) {
        if (apellido == null || apellido.isEmpty()) {
            return false;
        }
        return WORD_PATTERN.matcher(apellido).matches();
    }

    @Override
    public boolean validarPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        // Validar longitud mínima, mayúscula, número y símbolo
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    @Override
    public boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Validar formato de email
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return false;
        }

        // Verificar que solo haya un @
        long countArroba = email.chars().filter(ch -> ch == '@').count();
        if (countArroba != 1) {
            return false;
        }

        // Verificar que no contenga símbolos extraños
        String localPart = email.split("@")[0];
        String domainPart = email.split("@")[1];

        // Solo permitir letras, números, puntos, guiones y guiones bajos
        return localPart.matches("^[a-zA-Z0-9\\.\\-\\_]+$") &&
                domainPart.matches("^[a-zA-Z0-9\\.\\-]+$");
    }
}