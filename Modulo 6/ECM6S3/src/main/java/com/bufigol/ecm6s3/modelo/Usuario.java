package com.bufigol.ecm6s3.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private String email;

}
