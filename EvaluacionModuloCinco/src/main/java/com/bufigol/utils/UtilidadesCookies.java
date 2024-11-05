package com.bufigol.utils;

import com.bufigol.modelo.Usuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class UtilidadesCookies {

    /**
     * Convierte un ArrayList de cadenas en un objeto Cookie.
     *
     * Este método toma una lista de cadenas y las concatena en una sola cadena,
     * separando cada elemento por comas. Luego, crea una cookie con el nombre
     * proporcionado y el valor resultante de la concatenación de la lista.
     * La cookie tiene una duración de 1 hora y se establece en el path raíz.
     *
     * @param lista        Un ArrayList de cadenas que se desea convertir en una cookie.
     * @param nombreCookie El nombre que se asignará a la cookie.
     * @return Una instancia de Cookie con el nombre y valor especificados.
     */
    public static Cookie stringArrayListToCookie(ArrayList<String> lista, String nombreCookie) {
        // Convertir el ArrayList a una cadena separada por comas
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            stringBuilder.append(lista.get(i));
            if (i < lista.size() - 1) {
                stringBuilder.append(","); // Agregar una coma entre elementos
            }
        }

        // Crear la cookie con el nombre "myCookie" y el valor de la cadena
        Cookie cookie = new Cookie(nombreCookie, stringBuilder.toString());
        // Opcional: establecer la duración de la cookie (en segundos)
        cookie.setMaxAge(60 * 60); // 1 hora
        // Opcional: establecer el path de la cookie
        cookie.setPath("/");

        return cookie;
    }

    /**
     * Convierte una Cookie en un ArrayList de cadenas.
     *
     * Este método toma el valor de una cookie, lo divide en elementos
     * utilizando la coma como separador, y devuelve un ArrayList que
     * contiene esos elementos.
     *
     * @param cookie La cookie de la cual se desea extraer los valores.
     * @return Un ArrayList de cadenas que contiene los valores de la cookie.
     */
    public static ArrayList<String> cookieToStringArrayList(Cookie cookie) {
        ArrayList<String> lista = new ArrayList<>();

        // Obtener el valor de la cookie
        String valor = cookie.getValue();

        // Verificar si el valor no es nulo o vacío
        if (valor != null && !valor.isEmpty()) {
            // Dividir el valor de la cookie en elementos usando la coma como separador
            String[] elementos = valor.split(",");
            // Convertir el array en un ArrayList
            lista.addAll(Arrays.asList(elementos));
        }

        return lista;
    }

    public static String serializeToBase64(Usuario objeto)  {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(objeto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(byteStream.toByteArray());
    }

    public static Usuario deserializeFromBase64(String base64)  {
        byte[] data = Base64.getDecoder().decode(base64);
        try (ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (Usuario) objectStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void eliminarTodasLasCookies(HttpServletRequest request, HttpServletResponse response) {
        // Obtener todas las cookies de la solicitud
        Cookie[] cookies = request.getCookies();

        // Verificar si hay cookies
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Crear una nueva cookie con el mismo nombre
                Cookie cookieToDelete = new Cookie(cookie.getName(), null);
                // Establecer la ruta y el tiempo de vida a 0
                cookieToDelete.setPath(cookie.getPath());
                cookieToDelete.setMaxAge(0);
                // Agregar la cookie de eliminación a la respuesta
                response.addCookie(cookieToDelete);
            }
        }
    }
}
