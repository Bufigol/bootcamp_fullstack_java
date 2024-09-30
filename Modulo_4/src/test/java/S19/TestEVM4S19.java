package S19;

import com.bufigol.s18.EVM4S18.Modelo.Mercado;
import com.bufigol.s18.EVM4S18.Modelo.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestEVM4S19 {

    @Mock
    private Producto productoMock;

    @Mock
    private Mercado mercadoMock;

    private List<Producto> listaProductos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listaProductos = new ArrayList<>();
    }

    @Test
    void testOrdenInvocaciones() {
        // Configuración
        when(productoMock.getNombre()).thenReturn("Producto de prueba");
        when(productoMock.getPrecio()).thenReturn("100");

        // Ejecución
        mercadoMock.publicarAviso(listaProductos, productoMock);
        productoMock.getNombre();
        productoMock.getPrecio();

        // Verificación
        InOrder inOrder = inOrder(mercadoMock, productoMock);
        inOrder.verify(mercadoMock).publicarAviso(listaProductos, productoMock);
        inOrder.verify(productoMock).getNombre();
        inOrder.verify(productoMock).getPrecio();
    }

    @Test
    void testCrearAviso() {
        // Ejecución
        Producto producto = Producto.crearAviso("Laptop", 1, "Nuevo", "1000");

        // Verificación
        assertNotNull(producto);
        assertEquals("Laptop", producto.getNombre());
        assertEquals(1, producto.getIdProducto());
        assertEquals("Nuevo", producto.getCondicion());
        assertEquals("1000", producto.getPrecio());
    }

    @Test
    void testPublicarAviso() {
        // Configuración
        Mercado mercadoReal = new Mercado();
        Producto producto = new Producto();
        producto.setNombre("Smartphone");

        // Ejecución
        List<Producto> resultado = mercadoReal.publicarAviso(listaProductos, producto);

        // Verificación
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(producto));
    }

    @Test
    void testVerTodoAviso() {
        // Configuración
        Mercado mercadoSpy = spy(new Mercado());
        Producto producto1 = new Producto();
        Producto producto2 = new Producto();
        listaProductos.add(producto1);
        listaProductos.add(producto2);

        // Ejecución
        mercadoSpy.verTodoAviso(listaProductos);

        // Verificación
        verify(mercadoSpy, times(1)).verTodoAviso(listaProductos);
    }
}