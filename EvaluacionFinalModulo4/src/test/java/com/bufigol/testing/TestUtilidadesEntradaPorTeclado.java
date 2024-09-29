package com.bufigol.testing;

import com.bufigol.utils.EntradaPorTeclado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestUtilidadesEntradaPorTeclado {

    @Mock
    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPedirEntero() {
        when(mockScanner.nextInt()).thenReturn(5);
        int result = EntradaPorTeclado.pedirEntero("Ingrese un número:", mockScanner);
        assertEquals(5, result);
        verify(mockScanner, times(1)).nextInt();
    }

    @Test
    void testPedirEnteroConExcepcion() {
        when(mockScanner.nextInt())
                .thenThrow(new InputMismatchException())
                .thenReturn(10);
        when(mockScanner.nextLine()).thenReturn("");
        int result = EntradaPorTeclado.pedirEntero("Ingrese un número:", mockScanner);
        assertEquals(10, result);
        verify(mockScanner, times(2)).nextInt();
        verify(mockScanner, times(1)).nextLine();
    }

    @Test
    void testPedirCadena() {
        String input = "Test Input\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String result = EntradaPorTeclado.pedirCadena("Ingrese una cadena:");
        assertEquals("Test Input", result);
    }

    @Test
    void testGenerateSecurePassword() {
        String password = "miContraseña123";
        String securePassword = EntradaPorTeclado.generateSecurePassword(password);
        assertNotNull(securePassword);
        assertTrue(securePassword.contains("."));
        assertNotEquals(password, securePassword);
    }

    @Test
    void testVerifyPassword() {
        String password = "miContraseña123";
        String securePassword = EntradaPorTeclado.generateSecurePassword(password);
        assertTrue(EntradaPorTeclado.verifyPassword(password, securePassword),
                "Password verification should succeed with correct password");
        assertFalse(EntradaPorTeclado.verifyPassword("contraseñaIncorrecta", securePassword),
                "Password verification should fail with incorrect password");
    }
}