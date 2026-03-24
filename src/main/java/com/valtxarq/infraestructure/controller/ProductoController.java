package com.valtxarq.infraestructure.controller;

import com.valtxarq.application.dto.producto.ProductoDto;
import com.valtxarq.application.dto.producto.ProductoSaveDto;
import com.valtxarq.application.ports.producto.CreateProductoUseCase;
import com.valtxarq.application.ports.producto.DeleteProductoUseCase;
import com.valtxarq.application.ports.producto.GetProductoUseCase;
import com.valtxarq.application.ports.producto.UpdateProductoUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
@Tag(name = "Producto")
public class ProductoController {

    private final CreateProductoUseCase createProductoUseCase;
    private final UpdateProductoUseCase updateProductoUseCase;
    private final DeleteProductoUseCase deleteProductoUseCase;
    private final GetProductoUseCase getProductoUseCase;

    @GetMapping
    public ResponseEntity<List<ProductoDto>> findAll() {
        return ResponseEntity.ok(getProductoUseCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(getProductoUseCase.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoDto> create(@RequestBody ProductoSaveDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createProductoUseCase.execute(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> update(@PathVariable Long id, @RequestBody ProductoSaveDto dto) {
        return ResponseEntity.ok(updateProductoUseCase.execute(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteProductoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
