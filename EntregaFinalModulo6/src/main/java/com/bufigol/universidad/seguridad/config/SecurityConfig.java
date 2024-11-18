package com.bufigol.universidad.seguridad.config;

import com.bufigol.universidad.seguridad.jwt.JwtAuthenticationFilter;
import com.bufigol.universidad.seguridad.jwt.JwtTokenProvider;
import com.bufigol.universidad.servicio.UsuarioServicio;
import com.bufigol.universidad.utils.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.bufigol.universidad.seguridad.constantes.ConstantesSeguridad.*;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final UsuarioServicio usuarioServicio;
    private final CustomPasswordEncoder customPasswordEncoder;
    public static final String[] STATIC_RESOURCES = {
            "/css/**",
            "/js/**",
            "/img/**",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)  // Mantener deshabilitado para la API REST
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas existentes
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        // Nuevas rutas para vistas públicas
                        .requestMatchers("/", "/login", "/registro", "/error").permitAll()
                        // Recursos estáticos
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**",
                                "/favicon.ico").permitAll()
                        // Documentación API
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Rutas de administrador
                        .requestMatchers("/api/admin/**", "/admin/**").hasRole("ADMIN")
                        // Otras rutas protegidas
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/api/auth/signin")  // URL para procesar el login via API
                        .defaultSuccessUrl("/home", true)        // true fuerza la redirección
                        .failureUrl("/login?error")             // URL en caso de error
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID", "JWT_TOKEN") // Limpia las cookies relevantes
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Para peticiones API
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.sendError(HttpStatus.UNAUTHORIZED.value(),
                                        "No autorizado");
                            } else {
                                // Para peticiones web
                                response.sendRedirect("/login");
                            }
                        })
                )
                .userDetailsService(usuarioServicio)
                .addFilterBefore(
                        new JwtAuthenticationFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioServicio);
        authProvider.setPasswordEncoder(customPasswordEncoder);
        return authProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(ALLOWED_ORIGINS));
        configuration.setAllowedMethods(List.of(ALLOWED_METHODS));
        configuration.setAllowedHeaders(List.of(ALLOWED_HEADERS));
        configuration.setExposedHeaders(List.of(EXPOSED_HEADERS));
        configuration.setMaxAge(MAX_AGE);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}