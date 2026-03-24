package com.valtxarq.domain.repository;

import com.valtxarq.domain.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoRepository {
    List<Producto> findAll();
    Optional<Producto> findById(Long id);
    Producto save(Producto producto);
    void deleteById(Long id);
}
