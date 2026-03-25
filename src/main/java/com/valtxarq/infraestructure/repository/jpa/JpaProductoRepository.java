package com.valtxarq.infraestructure.repository.jpa;

import com.valtxarq.infraestructure.entity.ProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface JpaProductoRepository extends JpaRepository<ProductoEntity, Long>, JpaSpecificationExecutor<ProductoEntity> {

    @EntityGraph(attributePaths = {"categoria"})
    List<ProductoEntity> findAll();

    @EntityGraph(attributePaths = {"categoria"})
    Optional<ProductoEntity> findById(Long id);

    boolean existsByCategoriaId(Long categoriaId);
    List<ProductoEntity> findByCategoriaId(Long categoriaId);

    @EntityGraph(attributePaths = {"categoria"}) // ← esto faltaba
    Page<ProductoEntity> findAll(Specification<ProductoEntity> spec, Pageable pageable);
}
