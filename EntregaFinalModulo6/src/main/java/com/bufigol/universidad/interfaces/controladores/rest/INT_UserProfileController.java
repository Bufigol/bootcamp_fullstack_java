package com.bufigol.universidad.interfaces.controladores.rest;

import com.bufigol.universidad.dtos.modelo.ProfileUpdateDTO;
import com.bufigol.universidad.dtos.modelo.UsuarioResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

public interface INT_UserProfileController {

    @GetMapping("/api/users/profile")
    ResponseEntity<UsuarioResponseDTO> getCurrentUserProfile(Principal principal);

    @PostMapping("/api/users/profile")
    ResponseEntity<UsuarioResponseDTO> updateProfile(@RequestBody ProfileUpdateDTO updateDTO, Principal principal);
}