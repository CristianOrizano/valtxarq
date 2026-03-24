package com.valtxarq.application.ports.producto;

import com.valtxarq.application.dto.producto.ProductoDto;
import java.util.List;

public interface GetProductoUseCase {
    List<ProductoDto> findAll();
    ProductoDto findById(Long id);
}
