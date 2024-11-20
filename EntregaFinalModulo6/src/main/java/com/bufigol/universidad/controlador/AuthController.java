package com.bufigol.universidad.controlador;

import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.SignupRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.TokenResponseDTO;
import com.bufigol.universidad.interfaces.controladores.INT_AuthController;
import com.bufigol.universidad.interfaces.servicio.INT_AutenticacionServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements INT_AuthController {

    private final INT_AutenticacionServicio autenticacionServicio;

    @Override
    @PostMapping("/logIn")
    public ResponseEntity<TokenResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.debug("Solicitud de autenticación para usuario: {}", loginRequest.getUsername());
        TokenResponseDTO tokenResponse = autenticacionServicio.logIn(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @Override
    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDTO> registerUser(@Valid @RequestBody SignupRequestDTO signupRequest) {
        log.debug("Solicitud de registro para usuario: {}", signupRequest.getUsername());
        TokenResponseDTO tokenResponse = autenticacionServicio.signup(signupRequest);
        return ResponseEntity.ok(tokenResponse);
    }
}