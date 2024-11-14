package com.bufigol.universidad.configuracion;

import com.bufigol.universidad.utils.PasswordUtils;
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
}