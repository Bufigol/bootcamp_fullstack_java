package com.bufigol.universidad.seguridad.jwt;

import com.bufigol.universidad.dtos.autenticacion.LoginRequestDTO;
import com.bufigol.universidad.dtos.autenticacion.TokenResponseDTO;
import com.bufigol.universidad.excepciones.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class ApiAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public ApiAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        setFilterProcessesUrl("/api/auth/signin");

        // Configurar handlers específicos para API
        setAuthenticationSuccessHandler((request, response, authentication) -> {
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = tokenProvider.createToken(
                    authentication.getName(),
                    roles
            );

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    TokenResponseDTO.builder()
                            .token(token)
                            .tokenType("Bearer")
                            .username(authentication.getName())
                            .roles(roles)
                            .build()
            ));
        });

        setAuthenticationFailureHandler((request, response, exception) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    new ErrorResponse()
            ));
        });
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDTO loginRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestDTO.class);

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Error al procesar la solicitud de autenticación", e);
        }
    }
}