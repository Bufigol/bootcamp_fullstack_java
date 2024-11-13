package com.bufigol.universidad.interfaces.servicio;

import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.SignupRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.TokenResponseDTO;

public interface INT_AutenticacionServicio {

    TokenResponseDTO signin(LoginRequestDTO loginRequest);
    TokenResponseDTO signup(SignupRequestDTO signupRequest);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);


}
