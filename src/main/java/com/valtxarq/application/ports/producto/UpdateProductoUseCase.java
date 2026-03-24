package com.valtxarq.application.ports.producto;

import com.valtxarq.application.dto.producto.ProductoDto;
import com.valtxarq.application.dto.producto.ProductoSaveDto;

public interface UpdateProductoUseCase {
    ProductoDto execute(Long id, ProductoSaveDto dto);
}
