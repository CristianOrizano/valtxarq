package com.valtxarq.infraestructure.repository.jpa;

import com.valtxarq.infraestructure.entity.ProductoEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaProductoRepository extends JpaRepository<ProductoEntity, Long> {

    @EntityGraph(attributePaths = {"categoria"})
    List<ProductoEntity> findAll();

    @EntityGraph(attributePaths = {"categoria"})
    Optional<ProductoEntity> findById(Long id);
}
