package com.bufigol.universidad.interfaces.seguridad;

import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.SignupRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.TokenResponseDTO;

public interface INT_AuthenticationServicio {
    TokenResponseDTO authenticate(LoginRequestDTO credentials);
    TokenResponseDTO register(SignupRequestDTO registrationData);
    void validateRegistrationData(SignupRequestDTO data);
}
