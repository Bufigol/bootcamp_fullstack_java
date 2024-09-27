package com.bufigol.s15.main;

import com.bufigol.s15.model.Usuario;
import com.bufigol.utils.ComprobacionesVarias;
import com.bufigol.utils.EntradaPorTeclado;
import com.bufigol.utils.OtrosMetodos;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

public class EVM4S15 {
    public static void main(String[] args) {
        System.out.println("""
                Bienvenido!
                Para crear un usuario introduce los siguientes datos:""");
        String nombre = EntradaPorTeclado.pedirCadena("Nombre: ");
        String apellido = EntradaPorTeclado.pedirCadena("Apellido: ");
        String correo = EntradaPorTeclado.pedirCadena("Correo: ");
        while (!ComprobacionesVarias.isValidEmail(correo)) {
            correo = EntradaPorTeclado.pedirCadena("Correo: ");
        }
        String pwd = EntradaPorTeclado.generateSecurePassword(EntradaPorTeclado.pedirCadena("Contraseña: "));
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.ipbase.com/v2/info");
        target.property("ip", OtrosMetodos.getMyIPAddress());
        target.property("apikey", EntradaPorTeclado.pedirCadena("Api Key de ipbase.com: "));
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response respuesta = invocationBuilder.get();
        if (respuesta.getStatus() == 200) {
            String jsonString = respuesta.readEntity(String.class);
            JSONObject jsonObject = new JSONObject(jsonString);
            String[] data = getData(jsonString);

            Usuario usuario = new Usuario(
                    nombre,
                    apellido,
                    correo,
                    pwd,
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            );
            System.out.println("Gracias! Su usuario es:\n" +
                    "Nommbre Completo: " + usuario.getNombre() + " " + usuario.getApellido() + "\n" +
                    "Correo: " + usuario.getCorreo() + "\n" +
                    "Clave: " + usuario.getPassword() + "\n" +
                    "Región: " + usuario.getRegion() + "\n" +
                    "País: " + usuario.getPais() + "\n" +
                    "Continente: " + usuario.getContinente());
        }
    }

    private static String[] getData(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject data = jsonObject.getJSONObject("data");
        String[] result = new String[4];

        result[0] = data.getJSONObject("location").getJSONObject("city").getString("name_translated");
        result[1] = data.getJSONObject("location").getJSONObject("region").getString("name_translated");
        result[2] = data.getJSONObject("location").getJSONObject("country").getString("name_translated");
        result[3] = data.getJSONObject("location").getJSONObject("continent").getString("name_translated");

        return result;
    }
}
