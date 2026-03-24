package com.valtxarq.application.usecase.categoria;

import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaFilterDto;
import com.valtxarq.application.mapper.CategoriaAppMapper;
import com.valtxarq.application.ports.categoria.GetCategoriaUseCase;
import com.valtxarq.domain.filters.CategoriaFilter;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.domain.repository.ICategoriaRepository;
import com.valtxarq.shared.exception.ResourceNotFoundException;
import com.valtxarq.shared.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCategoriaUseCaseImpl implements GetCategoriaUseCase {

    private final ICategoriaRepository categoriaRepository;
    private final CategoriaAppMapper mapper;

    @Override
    public PageResponse<CategoriaDto> findAllPaginated(CategoriaFilterDto dto) {
        CategoriaFilter filter = mapper.toFilter(dto);
        PageResponse<Categoria> page = categoriaRepository.findAllPaginated(filter);

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
    public CategoriaDto findById(Long id) {
        return categoriaRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));
    }
}
