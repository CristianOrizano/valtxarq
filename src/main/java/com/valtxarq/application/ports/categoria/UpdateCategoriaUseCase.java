package com.valtxarq.application.ports.categoria;

import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaSaveDto;

public interface UpdateCategoriaUseCase {
    CategoriaDto execute(Long id, CategoriaSaveDto request);
}
