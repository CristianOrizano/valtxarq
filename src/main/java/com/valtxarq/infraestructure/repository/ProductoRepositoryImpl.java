package com.valtxarq.infraestructure.repository;


import com.valtxarq.domain.model.Producto;
import com.valtxarq.domain.repository.IProductoRepository;
import com.valtxarq.infraestructure.entity.ProductoEntity;
import com.valtxarq.infraestructure.mapper.ProductoMapper;
import com.valtxarq.infraestructure.repository.jpa.JpaProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
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
    public Optional<Producto> findById(Long id) {
        return jpaProductoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Producto save(Producto producto) {
        ProductoEntity entity = mapper.toEntity(producto);
        return mapper.toDomain(jpaProductoRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        jpaProductoRepository.deleteById(id);
    }
}
