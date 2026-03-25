package com.valtxarq.application.usecase.categoria;

import com.valtxarq.application.dto.categoria.CategoriaDetalleDto;
import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaFilterDto;
import com.valtxarq.application.dto.producto.ProductoSaveSimpleDto;
import com.valtxarq.application.dto.producto.ProductoSimpleDto;
import com.valtxarq.application.mapper.CategoriaAppMapper;
import com.valtxarq.application.mapper.ProductoAppMapper;
import com.valtxarq.application.ports.categoria.GetCategoriaUseCase;
import com.valtxarq.domain.filters.CategoriaFilter;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.domain.repository.ICategoriaRepository;
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
public class GetCategoriaUseCaseImpl implements GetCategoriaUseCase {

    private final ICategoriaRepository categoriaRepository;
    private final IProductoRepository productoRepository;
    private final CategoriaAppMapper mapper;
    private final ProductoAppMapper productoAppMapper;

    @Override
    public PageResponse<CategoriaDto> findAllPaginated(CategoriaFilterDto dto) {
        CategoriaFilter filter = mapper.toFilter(dto);
        PageRequest pageRequest = PageRequest.builder()       // solo paginacion
                .page(dto.getPage())
                .size(dto.getSize())
                .sortBy(dto.getSortBy())
                .sortDir(dto.getSortDir())
                .build();
        PageResponse<Categoria> page = categoriaRepository.findAllPaginated(filter,pageRequest);

        return PageResponse.<CategoriaDto>builder()
                .content(page.getContent().stream().map(mapper::toDto).toList())
                .currentPage(page.getCurrentPage())
                .perPage(page.getPerPage())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }

    @Override
    public List<CategoriaDto> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CategoriaDetalleDto findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));

        List<ProductoSimpleDto> productos = productoRepository.findByCategoriaId(id)
                .stream().map(productoAppMapper::toSimpleDto)
                .toList();

        CategoriaDetalleDto dto = mapper.toDetalleDto(categoria);
        dto.setProductos(productos);
        return dto;
    }

    /*@Override
    public CategoriaDto findById(Long id) {
        return categoriaRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));
    } */
}
