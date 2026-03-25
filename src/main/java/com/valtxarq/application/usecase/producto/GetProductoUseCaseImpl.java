package com.valtxarq.application.usecase.producto;

import com.valtxarq.application.dto.producto.ProductoDto;
import com.valtxarq.application.dto.producto.ProductoFilterDto;
import com.valtxarq.application.mapper.ProductoAppMapper;
import com.valtxarq.application.ports.producto.GetProductoUseCase;
import com.valtxarq.domain.filters.ProductoFilter;
import com.valtxarq.domain.model.Producto;
import com.valtxarq.domain.repository.IProductoRepository;
import com.valtxarq.shared.exception.ResourceNotFoundException;
import com.valtxarq.shared.page.PageRequest;
import com.valtxarq.shared.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetProductoUseCaseImpl implements GetProductoUseCase {

    private final IProductoRepository productoRepository;
    private final ProductoAppMapper mapper;

    @Override
    public PageResponse<ProductoDto> findAllPaginated(ProductoFilterDto dto) {
        ProductoFilter filter = mapper.toFilter(dto);
        PageRequest pageRequest = PageRequest.builder()
                .page(dto.getPage())
                .size(dto.getSize())
                .sortBy(dto.getSortBy())
                .sortDir(dto.getSortDir())
                .build();
        PageResponse<Producto> page = productoRepository.findAllPaginated(filter, pageRequest);

        return PageResponse.<ProductoDto>builder()
                .content(page.getContent().stream().map(mapper::toDto).toList())
                .currentPage(page.getCurrentPage())
                .perPage(page.getPerPage())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }

    @Override
    public List<ProductoDto> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ProductoDto findById(Long id) {
        return productoRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }
}
