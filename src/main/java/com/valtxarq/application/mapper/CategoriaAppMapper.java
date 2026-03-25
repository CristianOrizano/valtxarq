package com.valtxarq.application.mapper;

import com.valtxarq.application.dto.categoria.*;
import com.valtxarq.domain.filters.CategoriaFilter;
import com.valtxarq.domain.model.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaAppMapper {
    CategoriaDto toDto(Categoria categoria);
    CategoriaDetalleDto toDetalleDto(Categoria categoria);
    Categoria toDomain (CategoriaSaveDto categoriaSaveDto);
    Categoria updateDomain(CategoriaUpdateDto categoriaUpdateDto, @MappingTarget Categoria categoria);
    CategoriaFilter toFilter(CategoriaFilterDto dto);
}
