package com.bufigol.evm6s6;

import com.bufigol.evm6s6.dto.ProductoRequestDTO;
import com.bufigol.evm6s6.interfaces.INT_ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final INT_ProductService productService;

    @Override
    public void run(String... args) {
        log.info("Iniciando pruebas de funcionalidad...");

        // Probar búsqueda por marca
        log.info("Productos Coleman: {}",
                productService.findByMarca("Coleman"));

        // Probar búsqueda por modelo
        log.info("Productos modelo Tent-001: {}",
                productService.findByModelo("Tent-001"));

        // Probar búsqueda por descripción
        log.info("Productos con 'carpa' en descripción: {}",
                productService.buscarPorDescripcion("carpa"));

        // Probar creación
        ProductoRequestDTO newProduct = ProductoRequestDTO.builder()
                .modelo("Test-001")
                .marca("Test")
                .descripcion("Producto de prueba")
                .build();

        log.info("Nuevo producto creado: {}",
                productService.saveDTO(newProduct));

        log.info("Pruebas completadas.");
    }
}