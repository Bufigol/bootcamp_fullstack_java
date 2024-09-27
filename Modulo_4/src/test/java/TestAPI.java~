import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bufigol.s18.ECS4M18.Modelo.API;
import com.bufigol.s18.ECS4M18.Modelo.Respuesta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

@DisplayName("Tests para la clase API")
class TestAPI {
    private API api;

    @BeforeEach
    public void setUp() {
        this.api = new API();
    }

    @Test
    @DisplayName("Test Conexion a la API")
    public void testConexionApi(){
        Respuesta output = this.api.conexionApi();
        assertEquals("success", output.getMessage());
        Assertions.assertTrue(output.getTimestamp() > 1727466256);
    }

    @Test
    @DisplayName("Test del status de la conexion a la Api")
    public void testStatusConexion() {
        boolean status = this.api.statusConexion();
        Assertions.assertTrue(status);
        System.out.println("Status conexión a servidores de la ISS:");
        System.out.println("success");
    }
    @Test
    @DisplayName("Test de la Latitud")
    public void testLatitud() {
        float lat = this.api.infoLat();
        Assertions.assertTrue( ( lat >= -90 ) && ( lat <= 90  ));
        System.out.println("Latitud:");
        System.out.println(lat);
    }
    @Test
    @DisplayName("Test de la Longitud")
    public void testLongitud() {
        float longi = this.api.infoLong();
        Assertions.assertTrue( ( longi >= -180 ) && ( longi <= 180  ) );
        System.out.println("Longitud:");
        System.out.println(longi);
    }

    @Test
    @DisplayName("Test del ID de la conexion")
    public void testId() {
        int id = this.api.idConexión();
        Assertions.assertTrue( id > 0 );
        System.out.println("ID:");
        System.out.println(id);
    }

}
