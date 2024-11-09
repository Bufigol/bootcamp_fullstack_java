package com.bufigol.evm6s6.interfaces;

import com.bufigol.evm6s6.modelo.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface INT_ProductService {

    // Métodos de búsqueda
    List<Producto> findByMarca(String marca);
    List<Producto> findByModelo(String modelo);
    List<Producto> buscarPorDescripcion(String palabra);
    List<Producto> busquedaGeneral(String termino);

    // Métodos CRUD básicos
    Producto save(Producto producto);
    List<Producto> saveAll(List<Producto> productos);
    Optional<Producto> findById(Long id);
    List<Producto> findAll();
    void deleteById(Long id);
    void delete(Producto producto);

    // Métodos de validación
    boolean existsById(Long id);
    boolean existsByModeloAndMarca(String modelo, String marca);

    // Métodos de paginación y ordenamiento
    Page<Producto> findAll(Pageable pageable);
    List<Producto> findAll(Sort sort);

    // Métodos de conteo
    long count();

    // Métodos de actualización
    Producto update(Producto producto);

    // Métodos de eliminación masiva
    void deleteAll();
    void deleteAll(List<Producto> productos);
    void deleteAllById(List<Long> ids);

    // Métodos de gestión de inventario
    List<Producto> findAllById(List<Long> ids);
}
