package com.bufigol.evm6s6.servicio;

import com.bufigol.evm6s6.dto.ProductoRequestDTO;
import com.bufigol.evm6s6.dto.ProductoResponseDTO;
import com.bufigol.evm6s6.excepciones.ProductoDuplicadoException;
import com.bufigol.evm6s6.excepciones.ProductoNotFoundException;
import com.bufigol.evm6s6.interfaces.INT_ProductService;
import com.bufigol.evm6s6.mapper.ProductoMapper;
import com.bufigol.evm6s6.modelo.Producto;
import com.bufigol.evm6s6.repositorio.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductService implements INT_ProductService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findByMarca(String marca) {
        log.debug("Buscando productos por marca: {}", marca);
        return productoRepository.findByMarca(marca).stream()
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findByModelo(String modelo) {
        log.debug("Buscando productos por modelo: {}", modelo);
        return productoRepository.findByModelo(modelo).stream()
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarPorDescripcion(String palabra) {
        log.debug("Buscando productos por descripción: {}", palabra);
        return productoRepository.buscarPorDescripcion(palabra).stream()
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> busquedaGeneral(String termino) {
        log.debug("Realizando búsqueda general con término: {}", termino);
        return productoRepository.busquedaGeneral(termino).stream()
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO saveDTO(ProductoRequestDTO productoDTO) {
        log.debug("Guardando nuevo producto: {}", productoDTO);
        if (productoRepository.existsByModeloAndMarca(productoDTO.getModelo(), productoDTO.getMarca())) {
            throw new ProductoDuplicadoException(productoDTO.getModelo(), productoDTO.getMarca());
        }
        Producto producto = productoMapper.toEntity(productoDTO);
        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public List<ProductoResponseDTO> saveAllDTO(List<ProductoRequestDTO> productosDTO) {
        log.debug("Guardando lista de {} productos", productosDTO.size());
        List<Producto> productos = productosDTO.stream()
                .map(productoMapper::toEntity)
                .collect(Collectors.toList());
        return productoRepository.saveAll(productos).stream()
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoResponseDTO> findById(Long id) {
        log.debug("Buscando producto por ID: {}", id);
        return productoRepository.findById(id)
                .map(productoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findAll() {
        log.debug("Recuperando todos los productos");
        return productoRepository.findAll().stream()
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Eliminando producto con ID: {}", id);
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException(id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    public void deleteDTO(ProductoRequestDTO productoDTO) {
        log.debug("Eliminando producto: {}", productoDTO);
        Producto producto = productoMapper.toEntity(productoDTO);
        productoRepository.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return productoRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByModeloAndMarca(String modelo, String marca) {
        return productoRepository.existsByModeloAndMarca(modelo, marca);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponseDTO> findAll(Pageable pageable) {
        log.debug("Recuperando página {} de productos", pageable.getPageNumber());
        return productoRepository.findAll(pageable)
                .map(productoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findAll(Sort sort) {
        log.debug("Recuperando todos los productos ordenados");
        return productoRepository.findAll(sort).stream()
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return productoRepository.count();
    }

    @Override
    public ProductoResponseDTO update(Long id, ProductoRequestDTO productoDTO) {
        log.debug("Actualizando producto con ID {}: {}", id, productoDTO);
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException(id);
        }
        Producto producto = productoMapper.toEntity(productoDTO);
        producto.setIdProducto(id);
        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public void deleteAll() {
        log.debug("Eliminando todos los productos");
        productoRepository.deleteAll();
    }

    @Override
    public void deleteAllDTO(List<ProductoRequestDTO> productosDTO) {
        log.debug("Eliminando lista de {} productos", productosDTO.size());
        List<Producto> productos = productosDTO.stream()
                .map(productoMapper::toEntity)
                .collect(Collectors.toList());
        productoRepository.deleteAll(productos);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        log.debug("Eliminando productos por IDs, cantidad: {}", ids.size());
        productoRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findAllById(List<Long> ids) {
        log.debug("Buscando productos por lista de IDs, cantidad: {}", ids.size());
        return productoRepository.findAllById(ids).stream()
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }

    // Métodos auxiliares para mantener compatibilidad con el controlador
    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> saveAll(List<Producto> productos) {
        return productoRepository.saveAll(productos);
    }

    @Override
    public Optional<Producto> findByIdEntity(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto update(Producto producto) {
        if (!productoRepository.existsById(producto.getIdProducto())) {
            throw new ProductoNotFoundException(producto.getIdProducto());
        }
        return productoRepository.save(producto);
    }
}