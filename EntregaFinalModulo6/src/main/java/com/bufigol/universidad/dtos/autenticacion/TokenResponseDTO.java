package com.bufigol.universidad.dtos.autenticacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponseDTO {

    @JsonProperty("access_token")
    private String token;

    @JsonProperty("token_type")
    private String tokenType = "Bearer";

    @JsonProperty("expires_in")
    private Long expirationTime;

    @JsonProperty("issued_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issuedAt;

    @JsonProperty("expires_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiresAt;

    @JsonProperty("username")
    private String username;

    @JsonProperty("roles")
    private List<String> roles;


    public TokenResponseDTO(String token, Long expirationTime, String username, List<String> roles) {
        this.token = token;
        this.expirationTime = expirationTime;
        this.username = username;
        this.roles = roles;
        this.issuedAt = LocalDateTime.now();
        this.expiresAt = this.issuedAt.plusSeconds(expirationTime);
    }
}