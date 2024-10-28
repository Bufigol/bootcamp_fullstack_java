package com.bufigol.interfaces.repositorios;

import com.bufigol.modelo.Horoscopo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de acceso a datos para la gestión de horóscopos.
 * <p>
 * Esta interfaz proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre los horóscopos en el sistema. Los implementadores de esta interfaz son responsables de
 * definir la lógica específica para interactuar con la fuente de datos, que puede ser una base de datos,
 * un servicio web, o cualquier otra forma de almacenamiento.
 * </p>
 * <p>
 * Los métodos definidos en esta interfaz permiten listar todos los horóscopos, insertar nuevos horóscopos,
 * buscar horóscopos por su identificador, actualizar información existente y eliminar horóscopos del sistema.
 * </p>
 */
public interface INT_HoroscopoRepository {

    /**
     * Lista todos los horóscopos disponibles en el sistema.
     * <p>
     * Este método debe recuperar todos los horóscopos almacenados y devolverlos como una lista.
     * La lista puede estar vacía si no hay horóscopos disponibles.
     * </p>
     *
     * @return una lista de objetos {@link Horoscopo} que representan todos los horóscopos en el sistema.
     *         La lista nunca es nula, pero puede estar vacía si no hay horóscopos.
     */
    List<Horoscopo> listarHoroscopos();

    /**
     * Inserta un nuevo horóscopo en el sistema.
     * <p>
     * Este método toma un objeto {@link Horoscopo} y lo almacena en la fuente de datos.
     * Se espera que el objeto proporcionado contenga todos los datos necesarios para crear
     * un nuevo horóscopo. Si el horóscopo ya existe (por ejemplo, si tiene un identificador
     * duplicado), se debe lanzar una excepción o manejar el error de acuerdo a la implementación.
     * </p>
     *
     * @param horoscopo el objeto {@link Horoscopo} que se desea insertar en el sistema.
     *                  No debe ser nulo.
     */
    void insertarHoroscopo(Horoscopo horoscopo);

    /**
     * Busca un horóscopo por su identificador único.
     * <p>
     * Este método busca un horóscopo en la fuente de datos utilizando el identificador proporcionado.
     * Si se encuentra el horóscopo, se devuelve como un objeto {@link Optional}, que puede contener
     * el horóscopo encontrado o estar vacío si no se encuentra ningún horóscopo con el ID especificado.
     * </p>
     *
     * @param id el identificador único del horóscopo que se desea buscar.
     *           Este valor debe ser mayor que cero.
     * @return un objeto {@link Optional} que contiene el horóscopo encontrado, o vacío si no se encuentra.
     *
     */
    Optional<Horoscopo> buscarHoroscopo(int id);

    /**
     * Actualiza la información de un horóscopo existente.
     * <p>
     * Este método toma un objeto {@link Horoscopo} que debe contener el identificador del horóscopo
     * que se desea actualizar, así como los nuevos valores para sus atributos. La implementación debe
     * verificar que el horóscopo existe antes de realizar la actualización. Si el horóscopo no existe,
     * se debe lanzar una excepción o manejar el error de acuerdo a la implementación.
     * </p>
     *
     * @param horoscopo el objeto {@link Horoscopo} con la información actualizada.
     *                  No debe ser nulo y debe contener un ID válido.
     */
    void actualizarHoroscopo(Horoscopo horoscopo);

    /**
     * Elimina un horóscopo del sistema por su identificador único.
     * <p>
     * Este método busca el horóscopo correspondiente al ID proporcionado y lo elimina de la fuente de datos.
     * Si no se encuentra un horóscopo con el ID especificado, se debe lanzar una excepción o manejar el
     * error de acuerdo a la implementación.
     * </p>
     *
     * @param id el identificador único del horóscopo que se desea eliminar.
     *           Este valor debe ser mayor que cero.
     *
     */
    void eliminarHoroscopo(int id);
}