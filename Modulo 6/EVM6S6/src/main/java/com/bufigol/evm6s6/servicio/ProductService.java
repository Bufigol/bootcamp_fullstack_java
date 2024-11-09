package com.bufigol.evm6s6.servicio;

import com.bufigol.evm6s6.interfaces.INT_ProductService;
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

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductService implements INT_ProductService {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByMarca(String marca) {
        log.debug("Buscando productos por marca: {}", marca);
        return productoRepository.findByMarca(marca);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByModelo(String modelo) {
        log.debug("Buscando productos por modelo: {}", modelo);
        return productoRepository.findByModelo(modelo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorDescripcion(String palabra) {
        log.debug("Buscando productos por descripción: {}", palabra);
        return productoRepository.buscarPorDescripcion(palabra);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> busquedaGeneral(String termino) {
        log.debug("Realizando búsqueda general con término: {}", termino);
        return productoRepository.busquedaGeneral(termino);
    }

    @Override
    public Producto save(Producto producto) {
        log.debug("Guardando nuevo producto: {}", producto);
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> saveAll(List<Producto> productos) {
        log.debug("Guardando lista de {} productos", productos.size());
        return productoRepository.saveAll(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findById(Long id) {
        log.debug("Buscando producto por ID: {}", id);
        return productoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        log.debug("Recuperando todos los productos");
        return productoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Eliminando producto con ID: {}", id);
        productoRepository.deleteById(id);
    }

    @Override
    public void delete(Producto producto) {
        log.debug("Eliminando producto: {}", producto);
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
    public Page<Producto> findAll(Pageable pageable) {
        log.debug("Recuperando página {} de productos", pageable.getPageNumber());
        return productoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll(Sort sort) {
        log.debug("Recuperando todos los productos ordenados");
        return productoRepository.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return productoRepository.count();
    }

    @Override
    public Producto update(Producto producto) {
        log.debug("Actualizando producto: {}", producto);
        if (!productoRepository.existsById(producto.getIdProducto())) {
            throw new RuntimeException("No se puede actualizar un producto que no existe");
        }
        return productoRepository.save(producto);
    }

    @Override
    public void deleteAll() {
        log.debug("Eliminando todos los productos");
        productoRepository.deleteAll();
    }

    @Override
    public void deleteAll(List<Producto> productos) {
        log.debug("Eliminando lista de {} productos", productos.size());
        productoRepository.deleteAll(productos);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        log.debug("Eliminando productos por IDs, cantidad: {}", ids.size());
        productoRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAllById(List<Long> ids) {
        log.debug("Buscando productos por lista de IDs, cantidad: {}", ids.size());
        return productoRepository.findAllById(ids);
    }
}