package com.valtxarq.application.ports.categoria;

import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaSaveDto;
import com.valtxarq.application.dto.categoria.CategoriaUpdateDto;

public interface UpdateCategoriaUseCase {
    CategoriaDto execute(Long id, CategoriaUpdateDto request);
}
