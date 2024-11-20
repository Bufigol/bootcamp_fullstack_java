package com.bufigol.universidad.controlador;

import com.bufigol.universidad.dtos.mappers.UsuarioMapper;
import com.bufigol.universidad.dtos.modelo.AlumnoRequestDTO;
import com.bufigol.universidad.dtos.modelo.AlumnoResponseDTO;
import com.bufigol.universidad.dtos.modelo.ProfileUpdateDTO;
import com.bufigol.universidad.dtos.modelo.UsuarioResponseDTO;
import com.bufigol.universidad.excepciones.servicio.ResourceNotFoundException;
import com.bufigol.universidad.interfaces.controladores.INT_UserProfileController;
import com.bufigol.universidad.interfaces.servicio.INT_AlumnoServicio;
import com.bufigol.universidad.interfaces.servicio.INT_UsuarioServicio;
import com.bufigol.universidad.modelo.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController implements INT_UserProfileController {

    private final INT_UsuarioServicio usuarioServicio;
    private final INT_AlumnoServicio alumnoServicio;
    private final UsuarioMapper usuarioMapper;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponseDTO> getCurrentUserProfile(Principal principal) {
        log.debug("Obteniendo perfil del usuario: {}", principal.getName());

        try {
            Usuario usuario = usuarioServicio.findByUsername(principal.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", principal.getName()));

            return ResponseEntity.ok(usuarioMapper.toDto(usuario));
        } catch (Exception e) {
            log.error("Error al obtener perfil de usuario: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public ResponseEntity<UsuarioResponseDTO> updateProfile(ProfileUpdateDTO updateDTO, Principal principal) {
        log.debug("Actualizando perfil del usuario: {}", principal.getName());

        try {
            Usuario usuario = usuarioServicio.findByUsername(principal.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", principal.getName()));

            usuario.setName(updateDTO.getName());
            usuario.setEmail(updateDTO.getEmail());

            if (updateDTO.getDireccion() != null) {
                Optional<AlumnoResponseDTO> alumnoOpt = alumnoServicio.findByRut(principal.getName());
                if (alumnoOpt.isPresent()) {
                    AlumnoRequestDTO alumnoRequest = new AlumnoRequestDTO();
                    alumnoRequest.setRut(principal.getName());
                    alumnoRequest.setNombre(updateDTO.getName());
                    alumnoRequest.setDireccion(updateDTO.getDireccion());
                    alumnoServicio.update(alumnoOpt.get().getId(), alumnoRequest);
                }
            }

            // Actualizar el usuario utilizando el servicio
            usuarioServicio.signup(usuario);

            log.info("Perfil actualizado exitosamente para usuario: {}", principal.getName());
            return ResponseEntity.ok(usuarioMapper.toDto(usuario));

        } catch (Exception e) {
            log.error("Error al actualizar perfil de usuario: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}