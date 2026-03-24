package com.valtxarq.domain.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record CategoriaFilter(
        String nombre,
        String descripcion,
        int page,
        int size,
        String sortBy,
        String sortDir
) {}
