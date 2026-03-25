package com.valtxarq.application.ports.producto;

import com.valtxarq.application.dto.producto.ProductoDto;
import com.valtxarq.application.dto.producto.ProductoFilterDto;
import com.valtxarq.shared.page.PageResponse;

import java.util.List;

public interface GetProductoUseCase {
    PageResponse<ProductoDto> findAllPaginated(ProductoFilterDto filter);
    List<ProductoDto> findAll();
    ProductoDto findById(Long id);
}
