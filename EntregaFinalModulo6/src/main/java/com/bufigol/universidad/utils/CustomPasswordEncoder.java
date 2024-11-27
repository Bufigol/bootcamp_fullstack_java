package com.bufigol.universidad.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder  implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return PasswordUtils.generarPasswordSegura(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return PasswordUtils.comprobarPassWord(rawPassword.toString(), encodedPassword);
    }
    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}