package com.valtxarq.infraestructure.controller;

import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaFilterDto;
import com.valtxarq.application.dto.categoria.CategoriaSaveDto;
import com.valtxarq.application.ports.categoria.CreateCategoriaUseCase;
import com.valtxarq.application.ports.categoria.DeleteCategoriaUseCase;
import com.valtxarq.application.ports.categoria.GetCategoriaUseCase;
import com.valtxarq.application.ports.categoria.UpdateCategoriaUseCase;
import com.valtxarq.shared.page.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categoria")
@Tag(name = "Categoria")
public class CategoriaController {
    private final CreateCategoriaUseCase createCategoriaUseCase;
    private final UpdateCategoriaUseCase updateCategoriaUseCase;
    private final GetCategoriaUseCase getCategoriaUseCase;
    private final DeleteCategoriaUseCase deleteCategoriaUseCase;

    @GetMapping()
    public ResponseEntity<List<CategoriaDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(getCategoriaUseCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(getCategoriaUseCase.findById(id));
    }

    @GetMapping("paginated")
    public ResponseEntity<PageResponse<CategoriaDto>> findAllPaginated(
            @RequestParam(defaultValue = "1")   int page,
            @RequestParam(defaultValue = "10")  int size,
            @RequestParam(defaultValue = "id")  String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false)     String nombre,
            @RequestParam(required = false)     String descripcion) {

        CategoriaFilterDto filter = CategoriaFilterDto.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .nombre(nombre)
                .descripcion(descripcion)
                .build();

        return ResponseEntity.ok(getCategoriaUseCase.findAllPaginated(filter));
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> create(@RequestBody CategoriaSaveDto categoriaSaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createCategoriaUseCase.execute(categoriaSaveDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> update(@RequestBody CategoriaSaveDto categoriaSaveDto,@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(updateCategoriaUseCase.execute(id,categoriaSaveDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteCategoriaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}
