package com.bufigol.universidad.seguridad.jwt;

import com.bufigol.universidad.excepciones.seguridad.JwtExpiredTokenException;
import com.bufigol.universidad.excepciones.seguridad.JwtInvalidTokenException;
import com.bufigol.universidad.seguridad.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bufigol.universidad.seguridad.constantes.ConstantesSeguridad.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private SecretKey secretKey;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(CLAIM_ROLES, roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getExpiration());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .setIssuer(jwtProperties.getIssuer())
                .setAudience(jwtProperties.getAudience())
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        List<SimpleGrantedAuthority> authorities = getRolesFromClaims(claims);

        UserDetails userDetails = User.builder()
                .username(getUsername(token))
                .password("")
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Firma JWT inválida: {}", e.getMessage());
            throw new JwtInvalidTokenException(token, "Firma inválida");
        } catch (MalformedJwtException e) {
            log.error("Token JWT malformado: {}", e.getMessage());
            throw new JwtInvalidTokenException(token, "Token malformado");
        } catch (ExpiredJwtException e) {
            log.error("Token JWT expirado: {}", e.getMessage());
            throw new JwtExpiredTokenException(token);
        } catch (UnsupportedJwtException e) {
            log.error("Token JWT no soportado: {}", e.getMessage());
            throw new JwtInvalidTokenException(token, "Token no soportado");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string está vacío: {}", e.getMessage());
            throw new JwtInvalidTokenException(token, "Claims vacíos");
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredTokenException(token);
        } catch (JwtException e) {
            throw new JwtInvalidTokenException(token, "Error al procesar el token");
        }
    }

    @SuppressWarnings("unchecked")
    private List<SimpleGrantedAuthority> getRolesFromClaims(Claims claims) {
        List<String> roles = (List<String>) claims.get(CLAIM_ROLES);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}