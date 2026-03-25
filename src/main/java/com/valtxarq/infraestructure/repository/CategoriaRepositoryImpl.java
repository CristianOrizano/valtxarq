package com.valtxarq.infraestructure.repository;

import com.valtxarq.domain.filters.CategoriaFilter;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.domain.repository.ICategoriaRepository;
import com.valtxarq.infraestructure.entity.CategoriaEntity;
import com.valtxarq.infraestructure.mapper.CategoriaMapper;
import com.valtxarq.infraestructure.repository.jpa.JpaCategoriaRepository;
import com.valtxarq.shared.page.PageRequest;
import com.valtxarq.shared.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoriaRepositoryImpl implements ICategoriaRepository {
    private final JpaCategoriaRepository jpaCategoriaRepository;
    private final CategoriaMapper mapper;

    @Override
    public List<Categoria> findAll() {
        return jpaCategoriaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public PageResponse<Categoria> findAllPaginated(CategoriaFilter filter, PageRequest pageRequest) {
        Sort sort = pageRequest.getSortDir().equalsIgnoreCase("desc")
                ? Sort.by(pageRequest.getSortBy()).descending()
                : Sort.by(pageRequest.getSortBy()).ascending();

        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1, pageRequest.getSize(), sort);

        Specification<CategoriaEntity> spec = Specification
                .<CategoriaEntity>where((root, query, cb) ->
                        (filter.nombre() == null || filter.nombre().isBlank()) ? null
                                : cb.like(cb.lower(root.get("nombre")), "%" + filter.nombre().toLowerCase() + "%"))
                .and((root, query, cb) ->
                        (filter.descripcion() == null || filter.descripcion().isBlank()) ? null
                                : cb.like(cb.lower(root.get("descripcion")), "%" + filter.descripcion().toLowerCase() + "%"));

        Page<Categoria> page = jpaCategoriaRepository.findAll(spec, pageable)
                .map(mapper::toDomain);

        return PageResponse.<Categoria>builder()
                .content(page.getContent())
                .currentPage(page.getNumber() + 1)
                .perPage(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return jpaCategoriaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return mapper.toDomain(jpaCategoriaRepository.save(mapper.toEntity(categoria)));
    }

    @Override
    public void deleteById(Long id) {
        jpaCategoriaRepository.deleteById(id);
    }
}
