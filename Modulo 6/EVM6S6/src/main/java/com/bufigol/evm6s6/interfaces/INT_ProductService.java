package com.bufigol.evm6s6.interfaces;

import com.bufigol.evm6s6.dto.ProductoRequestDTO;
import com.bufigol.evm6s6.dto.ProductoResponseDTO;
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
    List<ProductoResponseDTO> findByMarca(String marca);
    List<ProductoResponseDTO> findByModelo(String modelo);
    List<ProductoResponseDTO> buscarPorDescripcion(String palabra);
    List<ProductoResponseDTO> busquedaGeneral(String termino);

    // Métodos CRUD básicos con DTOs
    ProductoResponseDTO saveDTO(ProductoRequestDTO productoDTO);
    List<ProductoResponseDTO> saveAllDTO(List<ProductoRequestDTO> productosDTO);
    Optional<ProductoResponseDTO> findById(Long id);
    List<ProductoResponseDTO> findAll();
    void deleteById(Long id);
    void deleteDTO(ProductoRequestDTO productoDTO);

    // Métodos de validación
    boolean existsById(Long id);
    boolean existsByModeloAndMarca(String modelo, String marca);

    // Métodos de paginación y ordenamiento
    Page<ProductoResponseDTO> findAll(Pageable pageable);
    List<ProductoResponseDTO> findAll(Sort sort);

    // Métodos de conteo
    long count();

    // Métodos de actualización
    ProductoResponseDTO update(Long id, ProductoRequestDTO productoDTO);

    // Métodos de eliminación masiva
    void deleteAll();
    void deleteAllDTO(List<ProductoRequestDTO> productosDTO);
    void deleteAllById(List<Long> ids);

    // Métodos de gestión de inventario
    List<ProductoResponseDTO> findAllById(List<Long> ids);

    // Métodos para entidades (compatibilidad con controlador)
    Producto save(Producto producto);
    List<Producto> saveAll(List<Producto> productos);
    Optional<Producto> findByIdEntity(Long id);
    Producto update(Producto producto);
}