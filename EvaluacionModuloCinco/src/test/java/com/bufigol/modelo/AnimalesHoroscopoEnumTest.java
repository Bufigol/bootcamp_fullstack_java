package com.bufigol.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class AnimalesHoroscopoEnumTest {

    private AnimalesHoroscopoEnum mono;
    private AnimalesHoroscopoEnum dragon;

    @BeforeEach
    void setUp() {
        mono = AnimalesHoroscopoEnum.MONO;
        dragon = AnimalesHoroscopoEnum.DRAGON;
    }

    @Test
    void getNombre() {
        assertEquals("Mono", mono.getNombre());
        assertEquals("Dragón", dragon.getNombre());
    }

    @Test
    void getCaracteristicas() {
        assertEquals("Inteligente, ingenioso y versátil", mono.getCaracteristicas());
        assertEquals("Fuerte, orgulloso y exitoso", dragon.getCaracteristicas());
    }

    @Test
    void testToString() {
        assertEquals("Mono", mono.toString());
        assertEquals("Dragón", dragon.toString());
    }

    @Test
    void getAnimalPorAño() {
        // Probamos varios años conocidos
        assertEquals(AnimalesHoroscopoEnum.MONO, AnimalesHoroscopoEnum.getAnimalPorAño(2016));
        assertEquals(AnimalesHoroscopoEnum.DRAGON, AnimalesHoroscopoEnum.getAnimalPorAño(2012));
        assertEquals(AnimalesHoroscopoEnum.RATA, AnimalesHoroscopoEnum.getAnimalPorAño(2020));

        // Probamos años negativos
        assertEquals(AnimalesHoroscopoEnum.getAnimalPorAño(2020),
                AnimalesHoroscopoEnum.getAnimalPorAño(-4)); // 2020 y -4 deben dar el mismo animal
    }

    @Test
    void getSiguienteAnimal() {
        assertEquals(AnimalesHoroscopoEnum.GALLO, mono.getSiguienteAnimal());
        assertEquals(AnimalesHoroscopoEnum.SERPIENTE, dragon.getSiguienteAnimal());
    }

    @Test
    void getAnimalAnterior() {
        assertEquals(AnimalesHoroscopoEnum.CABALLO, mono.getAnimalAnterior());
        assertEquals(AnimalesHoroscopoEnum.CONEJO, dragon.getAnimalAnterior());
    }

    @Test
    void calcularCompatibilidad() {
        // Prueba compatibilidad con el mismo animal
        assertEquals(5, mono.calcularCompatibilidad(mono));

        // Prueba alta compatibilidad
        assertEquals(5, mono.calcularCompatibilidad(AnimalesHoroscopoEnum.RATA));

        // Prueba compatibilidad media
        assertEquals(3, mono.calcularCompatibilidad(AnimalesHoroscopoEnum.TIGRE));

        // Prueba baja compatibilidad
        assertEquals(2, mono.calcularCompatibilidad(AnimalesHoroscopoEnum.GALLO));
    }

    @Test
    void getElemento() {
        assertEquals("Metal", mono.getElemento(1990));
        assertEquals("Tierra", dragon.getElemento(1989));
        assertEquals("Fuego", AnimalesHoroscopoEnum.SERPIENTE.getElemento(1996));
        assertEquals("Agua", AnimalesHoroscopoEnum.RATA.getElemento(1882));
        assertEquals("Madera", AnimalesHoroscopoEnum.TIGRE.getElemento(1554));
    }

    @Test
    void esAñoDeEsteAnimal() {
        assertTrue(mono.esAñoDeEsteAnimal(2016));
        assertFalse(mono.esAñoDeEsteAnimal(2017));
        assertTrue(dragon.esAñoDeEsteAnimal(2012));
        assertFalse(dragon.esAñoDeEsteAnimal(2013));
    }

    @Test
    void getAnimalesCompatibles() {
        AnimalesHoroscopoEnum[] compatiblesConMono = mono.getAnimalesCompatibles();

        // Verifica que los animales compatibles incluyen al mismo animal
        assertTrue(java.util.Arrays.asList(compatiblesConMono).contains(mono));

        // Verifica que todos los animales en la lista tienen compatibilidad >= 4
        for (AnimalesHoroscopoEnum animal : compatiblesConMono) {
            assertTrue(mono.calcularCompatibilidad(animal) >= 4);
        }
    }

    @Test
    void getAnimalOpuesto() {
        assertEquals(AnimalesHoroscopoEnum.TIGRE, mono.getAnimalOpuesto());
        assertEquals(AnimalesHoroscopoEnum.PERRO, dragon.getAnimalOpuesto());
    }

    @Test
    void getProximoAño() {
        int añoActual = 2024;

        // Para el mono (último año fue 2016, próximo será 2028)
        assertEquals(2028, mono.getProximoAño(añoActual));

        // Para el dragón (2024 es año del dragón, próximo será 2036)
        assertEquals(2036, dragon.getProximoAño(añoActual));
    }

    @Test
    void getDescripcionCompleta() {
        String descripcionMono = mono.getDescripcionCompleta(1990);

        // Verifica que la descripción contiene todos los elementos necesarios
        assertTrue(descripcionMono.contains("Mono"));
        assertTrue(descripcionMono.contains("Inteligente, ingenioso y versátil"));
        assertTrue(descripcionMono.contains("Metal"));
        assertTrue(descripcionMono.contains("Posición en el ciclo:"));
    }

    @Test
    void values() {
        AnimalesHoroscopoEnum[] valores = AnimalesHoroscopoEnum.values();

        // Verifica que hay exactamente 12 animales
        assertEquals(12, valores.length);

        // Verifica que todos los animales están presentes
        assertTrue(java.util.Arrays.asList(valores).contains(AnimalesHoroscopoEnum.MONO));
        assertTrue(java.util.Arrays.asList(valores).contains(AnimalesHoroscopoEnum.DRAGON));
        assertTrue(java.util.Arrays.asList(valores).contains(AnimalesHoroscopoEnum.RATA));
    }

    @Test
    void valueOf() {
        // Prueba conversión válida
        assertEquals(AnimalesHoroscopoEnum.MONO, AnimalesHoroscopoEnum.valueOf("MONO"));
        assertEquals(AnimalesHoroscopoEnum.DRAGON, AnimalesHoroscopoEnum.valueOf("DRAGON"));

        // Prueba conversión inválida
        assertThrows(IllegalArgumentException.class, () -> {
            AnimalesHoroscopoEnum.valueOf("INVALID_ANIMAL");
        });
    }

    @Test
    void ordenCorrecto() {
        AnimalesHoroscopoEnum[] valores = AnimalesHoroscopoEnum.values();

        // Verifica el orden específico de los animales
        assertEquals(AnimalesHoroscopoEnum.MONO, valores[0]);
        assertEquals(AnimalesHoroscopoEnum.GALLO, valores[1]);
        assertEquals(AnimalesHoroscopoEnum.PERRO, valores[2]);
        assertEquals(AnimalesHoroscopoEnum.CERDO, valores[3]);
        assertEquals(AnimalesHoroscopoEnum.RATA, valores[4]);
        assertEquals(AnimalesHoroscopoEnum.BUEY, valores[5]);
        assertEquals(AnimalesHoroscopoEnum.TIGRE, valores[6]);
        assertEquals(AnimalesHoroscopoEnum.CONEJO, valores[7]);
        assertEquals(AnimalesHoroscopoEnum.DRAGON, valores[8]);
        assertEquals(AnimalesHoroscopoEnum.SERPIENTE, valores[9]);
        assertEquals(AnimalesHoroscopoEnum.CABALLO, valores[10]);
        assertEquals(AnimalesHoroscopoEnum.CABRA, valores[11]);
    }
}