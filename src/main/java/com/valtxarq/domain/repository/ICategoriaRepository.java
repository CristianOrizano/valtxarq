package com.valtxarq.domain.repository;
import com.valtxarq.domain.filters.CategoriaFilter;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.shared.page.PageResponse;

import java.util.List;
import java.util.Optional;

public interface ICategoriaRepository {
    List<Categoria> findAll();
    PageResponse<Categoria> findAllPaginated(CategoriaFilter filter);
    Optional<Categoria> findById(Long id);
    Categoria save(Categoria categoria);
    void deleteById(Long id);
}
