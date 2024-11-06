package S19;

import com.bufigol.s19.modelo.EquipoBasket;
import com.bufigol.s19.modelo.Jugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Test del Ejercicio de Comprobación Modulo 4 Session 19")
public class TestECM4S19 {

    @Mock
    private EquipoBasket equipoMock;

    @Mock
    private Jugador jugadorMock;

    private EquipoBasket equipoBasket;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        equipoBasket = EquipoBasket.crearEquipo("Real Madrid", "Madrid", new ArrayList<>());
    }

    @Test
    @DisplayName("Test de Getters Equipo Basket")
    public void testGettersEquipoBasket() {
        assertEquals("Real Madrid", equipoBasket.getNombre());
        assertEquals("Madrid", equipoBasket.getCiudad());
        assertEquals(new ArrayList<>(), equipoBasket.getListaJugadores());
    }

    @Test
    @DisplayName("Test de Setters Equipo Basket")
    public void testSettersEquipoBasket() {
        equipoBasket.setCiudad("Barcelona");
        assertEquals("Barcelona", equipoBasket.getCiudad());

        equipoBasket.setNombre("FC Barcelona");
        assertEquals("FC Barcelona", equipoBasket.getNombre());

        List<Jugador> nuevaLista = new ArrayList<>();
        nuevaLista.add(new Jugador());
        equipoBasket.setListaJugadores(nuevaLista);
        assertEquals(nuevaLista, equipoBasket.getListaJugadores());
    }

    @Test
    @DisplayName("Test crearEquipo con Mockito")
    public void testCrearEquipoMockito() {
        when(equipoMock.getNombre()).thenReturn("Equipo Mock");
        when(equipoMock.getCiudad()).thenReturn("Ciudad Mock");
        when(equipoMock.getListaJugadores()).thenReturn(new ArrayList<>());

        EquipoBasket equipo = EquipoBasket.crearEquipo("Equipo Mock", "Ciudad Mock", new ArrayList<>());

        assertEquals("Equipo Mock", equipo.getNombre());
        assertEquals("Ciudad Mock", equipo.getCiudad());
        assertTrue(equipo.getListaJugadores().isEmpty());

        verify(equipoMock, never()).setNombre(anyString());
        verify(equipoMock, never()).setCiudad(anyString());
        verify(equipoMock, never()).setListaJugadores(anyList());
    }

    @Test
    @DisplayName("Test crearEquipo con excepción")
    public void testCrearEquipoExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> EquipoBasket.crearEquipo("", "Ciudad", new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> EquipoBasket.crearEquipo("Nombre", "", new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> EquipoBasket.crearEquipo("Nombre", "Ciudad", null));
    }

    @Test
    @DisplayName("Test crearJugador con Mockito")
    public void testCrearJugadorMockito() {
        when(jugadorMock.getNombre()).thenReturn("Jugador Mock");
        when(jugadorMock.getEdad()).thenReturn(25);
        when(jugadorMock.getNumero()).thenReturn(10);

        Jugador jugador = Jugador.crearJugador("Jugador Mock", 25, 10);

        assertEquals("Jugador Mock", jugador.getNombre());
        assertEquals(25, jugador.getEdad());
        assertEquals(10, jugador.getNumero());

        verify(jugadorMock, never()).setNombre(anyString());
        verify(jugadorMock, never()).setEdad(anyInt());
        verify(jugadorMock, never()).setNumero(anyInt());
    }

    @Test
    @DisplayName("Test crearJugador validación")
    public void testCrearJugadorValidacion() {
        Jugador jugador = Jugador.crearJugador("Nombre", 20, 5);
        assertNotNull(jugador);
        assertEquals("Nombre", jugador.getNombre());
        assertEquals(20, jugador.getEdad());
        assertEquals(5, jugador.getNumero());
    }
}