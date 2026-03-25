package com.valtxarq.domain.repository;

import com.valtxarq.domain.filters.ProductoFilter;
import com.valtxarq.domain.model.Producto;
import com.valtxarq.shared.page.PageRequest;
import com.valtxarq.shared.page.PageResponse;

import java.util.List;
import java.util.Optional;

public interface IProductoRepository {
    List<Producto> findAll();
    PageResponse<Producto> findAllPaginated(ProductoFilter filter, PageRequest pageRequest);
    Optional<Producto> findById(Long id);
    boolean existsByCategoriaId(Long categoriaId);
    List<Producto> findByCategoriaId(Long categoriaId);
    Producto save(Producto producto);
    List<Producto> saveAll(List<Producto> productos); // ← nuevo
    void deleteById(Long id);
}
