package com.bufigol.s18.ECM4S18.Modelo;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

public class API {

    public Respuesta conexionApi() {
        Response response = getResponse();
        Respuesta output = null;
        if(response.getStatus() != 200) {
            return null;
        }
        String jsonString = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(jsonString);
        float latitude = getStringFromResponse( jsonObject.getJSONObject("iss_position").getString("latitude") );
        float longitude = getStringFromResponse(jsonObject.getJSONObject("iss_position").getString("longitude") ) ;
        String message = jsonObject.getString("message");
        int timestamp = jsonObject.getInt("timestamp");
        output = new Respuesta(latitude, longitude, message, timestamp);
        return output;
    }

    public boolean statusConexion(){
        Respuesta output = conexionApi();
        if( output == null ){
            return false;
        }else{
            if( output.getMessage().equals("success") ){
                return true;
            }else {
                return false;
            }
        }
    }

    public int idConexión(){
        Respuesta output = conexionApi();
        if( output == null ){
            return 0;
        }else{
            return output.getTimestamp();
        }
    }

    public float infoLat(){
        Respuesta output = conexionApi();
        if( output == null ){
            return 0;
        }else{
            return output.getLatitude();
        }
    }

    public float infoLong(){
        Respuesta output = conexionApi();
        if( output == null ){
            return 0;
        }else{
            return output.getLongitude();
        }
    }

    private static Response getResponse() {
        String url ="http://api.open-notify.org/iss-now.json";
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get();
    }
    private float getStringFromResponse(String in){
        in = in.replace("\"", "");
        return Float.parseFloat(in);
    }
}
