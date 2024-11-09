package com.bufigol.evm6s6.controlador;

import com.bufigol.evm6s6.dto.ProductoRequestDTO;
import com.bufigol.evm6s6.dto.ProductoResponseDTO;
import com.bufigol.evm6s6.interfaces.INT_ProductService;
import com.bufigol.evm6s6.mapper.ProductoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProductController {

    private final INT_ProductService productService;

    // Endpoints de búsqueda
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> findAll() {
        log.debug("REST request para obtener todos los productos");
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id) {
        log.debug("REST request para obtener el producto con id: {}", id);
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<ProductoResponseDTO>> findByMarca(@PathVariable String marca) {
        log.debug("REST request para obtener productos por marca: {}", marca);
        return ResponseEntity.ok(productService.findByMarca(marca));
    }

    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<List<ProductoResponseDTO>> findByModelo(@PathVariable String modelo) {
        log.debug("REST request para obtener productos por modelo: {}", modelo);
        return ResponseEntity.ok(productService.findByModelo(modelo));
    }

    @GetMapping("/descripcion")
    public ResponseEntity<List<ProductoResponseDTO>> findByDescripcion(@RequestParam String palabra) {
        log.debug("REST request para obtener productos por descripción: {}", palabra);
        return ResponseEntity.ok(productService.buscarPorDescripcion(palabra));
    }

    @GetMapping("/busqueda")
    public ResponseEntity<List<ProductoResponseDTO>> busquedaGeneral(@RequestParam String termino) {
        log.debug("REST request para búsqueda general con término: {}", termino);
        return ResponseEntity.ok(productService.busquedaGeneral(termino));
    }

    // Endpoints de paginación y ordenamiento
    @GetMapping("/paginado")
    public ResponseEntity<Page<ProductoResponseDTO>> findAllPaginado(
            @PageableDefault(size = 10, sort = "idProducto", direction = Sort.Direction.ASC) Pageable pageable) {
        log.debug("REST request para obtener productos paginados");
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("/ordenado")
    public ResponseEntity<List<ProductoResponseDTO>> findAllOrdenado(@RequestParam(required = false) String[] sort) {
        log.debug("REST request para obtener productos ordenados");
        Sort ordering = sort != null && sort.length > 0 ?
                Sort.by(sort) : Sort.by("idProducto");
        return ResponseEntity.ok(productService.findAll(ordering));
    }

    // Endpoints CRUD
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO productoDTO) {
        log.debug("REST request para crear un nuevo producto: {}", productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.saveDTO(productoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO productoDTO) {
        log.debug("REST request para actualizar producto con id: {}", id);
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productService.update(id, productoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request para eliminar producto con id: {}", id);
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints para operaciones masivas
    @PostMapping("/batch")
    public ResponseEntity<List<ProductoResponseDTO>> createBatch(
            @Valid @RequestBody List<ProductoRequestDTO> productosDTO) {
        log.debug("REST request para crear múltiples productos");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.saveAllDTO(productosDTO));
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteBatch(@RequestBody List<Long> ids) {
        log.debug("REST request para eliminar múltiples productos");
        productService.deleteAllById(ids);
        return ResponseEntity.noContent().build();
    }

    // Endpoints de validación
    @GetMapping("/existe/modelo-marca")
    public ResponseEntity<Boolean> existsByModeloAndMarca(
            @RequestParam String modelo,
            @RequestParam String marca) {
        log.debug("REST request para verificar existencia por modelo y marca: {} - {}", modelo, marca);
        return ResponseEntity.ok(productService.existsByModeloAndMarca(modelo, marca));
    }

    // Endpoint para conteo
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        log.debug("REST request para contar todos los productos");
        return ResponseEntity.ok(productService.count());
    }
}