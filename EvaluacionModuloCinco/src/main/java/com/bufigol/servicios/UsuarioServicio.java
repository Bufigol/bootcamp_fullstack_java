package com.bufigol.servicios;

import com.bufigol.dtos.usuario.UsuarioCreateDto;
import com.bufigol.dtos.usuario.UsuarioResponseDTO;
import com.bufigol.dtos.usuario.UsuarioUpdateDTO;
import com.bufigol.interfaces.servicios.INT_UsuarioServicio;
import com.bufigol.modelo.Horoscopo;
import com.bufigol.modelo.Usuario;
import com.bufigol.repositorio.HoroscopoRepository;
import com.bufigol.repositorio.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioServicio implements INT_UsuarioServicio {
    private final UsuarioRepository usuarioRepository;
    private final HoroscopoRepository horoscopoRepository;

    public UsuarioServicio() {
        this.usuarioRepository = new UsuarioRepository();
        this.horoscopoRepository = new HoroscopoRepository();
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.listarUsuarios().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioResponseDTO> buscarUsuarioPorId(int id) {
        return usuarioRepository.buscarUsuario(id).map(this::mapToResponseDTO);
    }

    @Override
    public UsuarioResponseDTO buscarUsuarioPorUserName(String username) {
        UsuarioResponseDTO out;
        Usuario resultado = usuarioRepository.buscarUsuario(username);

        out = new UsuarioResponseDTO(
                resultado.getId(),
                resultado.getNombre(),
                resultado.getUserName(),
                resultado.getEmail(),
                resultado.getFechaNacimiento(),
                resultado.getPassword(),
                resultado.getHoroscopo()
        );
        return out;
    }

    @Override
    public void crearUsuario(UsuarioCreateDto usuarioCreateDTO) {

        Usuario usuario = new Usuario(
                usuarioCreateDTO.getId(),
                usuarioCreateDTO.getNombre(),
                usuarioCreateDTO.getUsername(),
                usuarioCreateDTO.getEmail(),
                usuarioCreateDTO.getFechaNacimiento(),
                usuarioCreateDTO.getPassword(),
                usuarioCreateDTO.getHoroscopo()
                );
        usuarioRepository.insertarUsuario(usuario);
    }

    @Override
    public void actualizarUsuario(UsuarioUpdateDTO usuarioUpdateDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.buscarUsuario(usuarioUpdateDTO.getId());

        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = getUsuarioExistente(usuarioUpdateDTO, usuarioOptional);

            // Llamar al repositorio para actualizar
            usuarioRepository.actualizarUsuario(usuarioExistente);
        } else {
            // Manejar el caso en que el usuario no existe
            throw new RuntimeException("No se encontró el usuario con ID: " + usuarioUpdateDTO.getId());
        }
    }

    private static Usuario getUsuarioExistente(UsuarioUpdateDTO usuarioUpdateDTO, Optional<Usuario> usuarioOptional) {
        Usuario usuarioExistente = usuarioOptional.get();

        // Actualizar los campos del usuario existente
        usuarioExistente.setNombre(usuarioUpdateDTO.getNombre());
        usuarioExistente.setUserName(usuarioUpdateDTO.getUsername());
        usuarioExistente.setEmail(usuarioUpdateDTO.getEmail());
        usuarioExistente.setFechaNacimiento(usuarioUpdateDTO.getFechaNacimiento());
        usuarioExistente.setPassword(usuarioUpdateDTO.getPassword());
        usuarioExistente.setHoroscopo(usuarioUpdateDTO.getHoroscopo());
        return usuarioExistente;
    }

    @Override
    public void eliminarUsuario(int id) {
        usuarioRepository.eliminarUsuario(id);
    }



    private UsuarioResponseDTO mapToResponseDTO(Usuario usuario) {
        Optional<Horoscopo> horoscopo = horoscopoRepository.buscarHoroscopo(usuario.getHoroscopo().getId());
        String horoscopoAnimal = horoscopo.map(Horoscopo::getAnimal).orElse("Desconocido");

        //UsuarioResponseDTO(int id, String nombre, String userName, String email, Date fechaNacimiento, String password, Horoscopo horoscopo)
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getUserName(),
                usuario.getEmail(),
                usuario.getFechaNacimiento(),
                usuario.getPassword(),
                usuario.getHoroscopo()
        );
    }
}
