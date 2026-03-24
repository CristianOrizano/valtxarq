package com.valtxarq.application.ports.categoria;

import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaFilterDto;
import com.valtxarq.shared.page.PageResponse;

import java.util.List;

public interface GetCategoriaUseCase {
    PageResponse<CategoriaDto> findAllPaginated(CategoriaFilterDto filter);
    List<CategoriaDto> findAll();
    CategoriaDto findById(Long id);
}
