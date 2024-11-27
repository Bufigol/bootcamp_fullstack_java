package com.bufigol.universidad.interfaces.controladores.rest;

import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.SignupRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.TokenResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface INT_AuthController {
    @PostMapping("/logIn")
    ResponseEntity<TokenResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest);

    @PostMapping("/signup")
    ResponseEntity<TokenResponseDTO> registerUser(@Valid @RequestBody SignupRequestDTO signupRequest);
}

