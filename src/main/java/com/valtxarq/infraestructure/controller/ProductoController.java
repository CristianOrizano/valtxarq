package com.valtxarq.infraestructure.controller;

import com.valtxarq.application.dto.producto.ProductoDto;
import com.valtxarq.application.dto.producto.ProductoFilterDto;
import com.valtxarq.application.dto.producto.ProductoSaveDto;
import com.valtxarq.application.ports.producto.CreateProductoUseCase;
import com.valtxarq.application.ports.producto.DeleteProductoUseCase;
import com.valtxarq.application.ports.producto.GetProductoUseCase;
import com.valtxarq.application.ports.producto.UpdateProductoUseCase;
import com.valtxarq.shared.page.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @GetMapping("paginated")
    public ResponseEntity<PageResponse<ProductoDto>> findAllPaginated(
            @RequestParam(defaultValue = "1")   int page,
            @RequestParam(defaultValue = "10")  int size,
            @RequestParam(defaultValue = "id")  String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false)     String nombre,
            @RequestParam(required = false)     String descripcion,
            @RequestParam(required = false)     Long categoriaId) {

        ProductoFilterDto filter = ProductoFilterDto.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .nombre(nombre)
                .descripcion(descripcion)
                .categoriaId(categoriaId)
                .build();

        return ResponseEntity.ok(getProductoUseCase.findAllPaginated(filter));
    }

    @PostMapping
    public ResponseEntity<ProductoDto> create(@Valid @RequestBody ProductoSaveDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createProductoUseCase.execute(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> update(@PathVariable Long id, @Valid @RequestBody ProductoSaveDto dto) {
        return ResponseEntity.ok(updateProductoUseCase.execute(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteProductoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
