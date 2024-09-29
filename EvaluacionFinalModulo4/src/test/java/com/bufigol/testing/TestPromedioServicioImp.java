package com.bufigol.testing;

import com.bufigol.servicios.PromedioServicioImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestPromedioServicioImp {



    @InjectMocks
    private PromedioServicioImp promedioServicio;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCalcularPromedio_ConNotasValidas() {
        List<Float> notas = Arrays.asList(4.5f, 3.0f, 5.0f, 2.5f);
        double promedio = promedioServicio.calcularPromedio(notas);
        assertEquals(3.75, promedio, 0.001, "El promedio calculado no es correcto");
    }

    @Test
    void testCalcularPromedio_ConListaVacia() {
        List<Float> notasVacias = Collections.emptyList();
        assertThrows(IllegalArgumentException.class, () -> promedioServicio.calcularPromedio(notasVacias),
                "Debería lanzar una IllegalArgumentException cuando la lista está vacía");
    }

    @Test
    void testCalcularPromedio_ConUnicaNota() {
        List<Float> unicaNota = Collections.singletonList(4.0f);
        double promedio = promedioServicio.calcularPromedio(unicaNota);
        assertEquals(4.0, promedio, 0.001, "El promedio de una única nota debería ser la misma nota");
    }

    @Test
    void testCalcularPromedio_ConNotasNegativas() {
        List<Float> notasNegativas = Arrays.asList(-1.0f, -2.0f, -3.0f);
        double promedio = promedioServicio.calcularPromedio(notasNegativas);
        assertEquals(-2.0, promedio, 0.001, "El promedio de notas negativas no es correcto");
    }
    @Test
    void testCalcularPromedio_ConListaNula() {
        assertThrows(IllegalArgumentException.class, () -> promedioServicio.calcularPromedio(null),
                "Debería lanzar una IllegalArgumentException cuando la lista es nula");
    }
}
