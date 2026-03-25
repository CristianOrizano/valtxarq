package com.valtxarq.domain.filters;

public record ProductoFilter(
        String nombre,
        String descripcion,
        Long categoriaId
) {}
