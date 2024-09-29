import com.bufigol.utils.RealInetAddressWrapper;
import com.bufigol.utils.Validaciones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
public class TestValidaciones {

    private Validaciones validaciones;

    @BeforeEach
    void setUp() {
        validaciones = new Validaciones(new RealInetAddressWrapper());
    }

    @Test
    void testIsValidEmail_ValidEmail() {
        assertTrue(validaciones.isValidEmail("usuario@dominio.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "usuariodominio.com",
            "usuario@",
            "@dominio.com",
            "usuario@dominio",
            "usuario@dominio..com"
    })
    void testIsValidEmail_InvalidEmail(String email) {
        assertFalse(validaciones.isValidEmail(email));
    }

    @Test
    void testIsValidEmail_NullEmail() {
        assertFalse(validaciones.isValidEmail(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "12.345.678-5",
            "12345678-5",
            "1-9"
    })
    void testIsValidRut_ValidRut(String rut) {
        assertTrue(Validaciones.isValidRut(rut));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "12.345.678-0",
            "1234567-8",
            "12.345.678-K"
    })
    void testIsValidRut_InvalidRut(String rut) {
        assertFalse(Validaciones.isValidRut(rut));
    }

    @Test
    void testIsValidRut_NullRut() {
        assertFalse(Validaciones.isValidRut(null));
    }

    @Test
    void testIsValidRut_EmptyRut() {
        assertFalse(Validaciones.isValidRut(""));
    }
}