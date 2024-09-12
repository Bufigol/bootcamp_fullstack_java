package com.bufigol.s15.main;

import com.bufigol.s15.model.Chiste;
import com.bufigol.utils.EntradaPorTeclado;
import com.bufigol.utils.JsonHandler;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class ECM4S15 {

    public static void main(String[] args) {
        int opc = 0;
        do{
            opc = mostrarMenu();
            switch (opc){
                case 1:
                    oneRandomJoke();
                    break;
                case 2:
                    tenRandomJokes();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    EntradaPorTeclado.cerrarScanner();
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }while(opc != 3);
    }

    private static void tenRandomJokes() {
        Response respuesta = getResponse("https://official-joke-api.appspot.com/random_ten");
        if(respuesta.getStatus() == 200){
            String jsonString = respuesta.readEntity(String.class);
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, String> joke = JsonHandler.getJsonMap(jsonObject.toString());
                Chiste chiste = new Chiste(
                        joke.get("type"),
                        joke.get("setup"),
                        joke.get("punchline"),
                        joke.get("id").isEmpty() ? 0 : Integer.parseInt(joke.get("id"))
                );
                imprimirChiste(chiste);
            }
        }
    }

    private static void oneRandomJoke() {
        Response respuesta = getResponse("https://official-joke-api.appspot.com/random_joke");
        if(respuesta.getStatus() == 200){
            Map<String, String> joke = JsonHandler.getJsonMap(
                    respuesta.readEntity(new GenericType<>(){}).toString()
            );
            Chiste chiste = new Chiste(
                    joke.get("type"),
                    joke.get("setup"),
                    joke.get("punchline"),
                    joke.get("id").isEmpty() ? 0 : Integer.parseInt( joke.get("id") )
            );
            imprimirChiste(chiste);
        }
    }

    private static Response getResponse(String url) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get();
    }

    private static void imprimirChiste(Chiste chiste) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(chiste.getSetUp()).append("\n");
        sb.append(chiste.getPunchLine());
        sb.append("\n");
        System.out.println(sb);
    }

    private static int mostrarMenu(){
        System.out.println("1. One random joke");
        System.out.println("2. 10 radom jokes");
        System.out.println("3. Exit Program");
        return EntradaPorTeclado.pedirEntero("Select an option: ");
    }
}
