import com.bufigol.s18.EVM4S18.Modelo.Mercado;
import com.bufigol.s18.EVM4S18.Modelo.Producto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class TestMercado {
    private Mercado mercado;
    private Producto producto;
    private List<Producto> listaProductos;


    @Before
    public void setUp() {
        this.mercado = new Mercado();
        this.producto = Producto.crearAviso("Computadora X", 111, "Nuevo", "$500.000");
        listaProductos = new ArrayList<>();
    }
    @Test
    public void testCrearAviso() {

        assertNotNull(producto);
        assertEquals("Computadora X", producto.getNombre());
        assertEquals(111, producto.getIdProducto());
        assertEquals("Nuevo", producto.getCondicion());
        assertEquals("$500.000", producto.getPrecio());
    }
    @Test
    public void testPublicarAviso() {
        List<Producto> listaProductosActualizada = mercado.publicarAviso(listaProductos, producto);
        assertNotNull(listaProductosActualizada);
        assertEquals(1, listaProductosActualizada.size());
        assertEquals(producto, listaProductosActualizada.getFirst());
    }
}
