package com.bufigol.evm6s6.interfaces;

import com.bufigol.evm6s6.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bufigol.evm6s6.utils.SQLConstants;

import java.util.List;

@Repository
public interface INT_ProductoRepository extends JpaRepository<Producto, Long> {

    // Método que usa keywords para filtrar por marca
    List<Producto> findByMarca(String marca);

    // Método usando HQL para filtrar por modelo
    @Query(SQLConstants.HQL_FIND_BY_MODELO)
    List<Producto> findByModelo(@Param("modelo") String modelo);

    // Método usando SQL nativo para buscar en la descripción
    @Query(value = SQLConstants.SQL_BUSCAR_POR_DESCRIPCION, nativeQuery = true)
    List<Producto> buscarPorDescripcion(@Param("palabra") String palabra);

    // Métodos adicionales útiles para la lógica de negocio
    boolean existsByModeloAndMarca(String modelo, String marca);

    @Query(SQLConstants.HQL_BUSQUEDA_GENERAL)
    List<Producto> busquedaGeneral(@Param("termino") String termino);
}