package com.valtxarq.infraestructure.repository;


import com.valtxarq.domain.filters.ProductoFilter;
import com.valtxarq.domain.model.Producto;
import com.valtxarq.domain.repository.IProductoRepository;
import com.valtxarq.infraestructure.entity.ProductoEntity;
import com.valtxarq.infraestructure.mapper.ProductoMapper;
import com.valtxarq.infraestructure.repository.jpa.JpaProductoRepository;
import com.valtxarq.shared.page.PageRequest;
import com.valtxarq.shared.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductoRepositoryImpl implements IProductoRepository {

    private final JpaProductoRepository jpaProductoRepository;
    private final ProductoMapper mapper;

    @Override
    public List<Producto> findAll() {
        return jpaProductoRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public PageResponse<Producto> findAllPaginated(ProductoFilter filter, PageRequest pageRequest) {
        Sort sort = pageRequest.getSortDir().equalsIgnoreCase("desc")
                ? Sort.by(pageRequest.getSortBy()).descending()
                : Sort.by(pageRequest.getSortBy()).ascending();

        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1, pageRequest.getSize(), sort);

        Specification<ProductoEntity> spec = Specification
                .<ProductoEntity>where((root, query, cb) ->
                        (filter.nombre() == null || filter.nombre().isBlank()) ? null
                                : cb.like(cb.lower(root.get("nombre")), "%" + filter.nombre().toLowerCase() + "%"))
                .and((root, query, cb) ->
                        (filter.descripcion() == null || filter.descripcion().isBlank()) ? null
                                : cb.like(cb.lower(root.get("descripcion")), "%" + filter.descripcion().toLowerCase() + "%"))
                .and((root, query, cb) ->
                        filter.categoriaId() == null ? null
                                : cb.equal(root.get("categoria").get("id"), filter.categoriaId()));

        Page<Producto> page = jpaProductoRepository.findAll(spec, pageable)
                .map(mapper::toDomain);

        return PageResponse.<Producto>builder()
                .content(page.getContent())
                .currentPage(page.getNumber() + 1)
                .perPage(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return jpaProductoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByCategoriaId(Long categoriaId) {
        return jpaProductoRepository.existsByCategoriaId(categoriaId);
    }

    @Override
    public List<Producto> findByCategoriaId(Long categoriaId) {
        return jpaProductoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Producto save(Producto producto) {
        ProductoEntity entity = mapper.toEntity(producto);
        return mapper.toDomain(jpaProductoRepository.save(entity));
    }

    @Override
    public List<Producto> saveAll(List<Producto> productos) {
        List<ProductoEntity> entities = productos.stream()
                .map(mapper::toEntity)
                .toList();
        return jpaProductoRepository.saveAll(entities).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaProductoRepository.deleteById(id);
    }
}
