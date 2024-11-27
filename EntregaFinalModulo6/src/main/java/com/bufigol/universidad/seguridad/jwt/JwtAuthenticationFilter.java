package com.bufigol.universidad.seguridad.jwt;

import com.bufigol.universidad.excepciones.seguridad.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.bufigol.universidad.seguridad.constantes.ConstantesSeguridad.TOKEN_MISSING;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = tokenProvider.resolveToken(request);

            if (token != null) {
                if (tokenProvider.validateToken(token)) {
                    Authentication auth = tokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.debug("Token válido, estableciendo autenticación para usuario: {}",
                            auth.getName());
                }
            } else {
                log.debug(TOKEN_MISSING);
            }

            filterChain.doFilter(request, response);

        } catch (JwtAuthenticationException e) {
            log.error("Error de autenticación JWT: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }


    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.startsWith("/webjars/") ||
                path.equals("/") ||
                path.equals("/login") ||
                path.equals("/registro") ||
                path.equals("/error") ||
                path.startsWith("/api/auth/") ||
                path.startsWith("/api/public/") ||
                path.equals("/swagger-ui/") ||
                path.startsWith("/v3/api-docs/");
    }
}