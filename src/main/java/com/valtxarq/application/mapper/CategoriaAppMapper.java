package com.valtxarq.application.mapper;

import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaFilterDto;
import com.valtxarq.application.dto.categoria.CategoriaSaveDto;
import com.valtxarq.domain.filters.CategoriaFilter;
import com.valtxarq.domain.model.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaAppMapper {
    CategoriaDto toDto(Categoria categoria);
    Categoria toDomain (CategoriaSaveDto categoriaSaveDto);
    Categoria updateDomain(CategoriaSaveDto categoriaSaveDto, @MappingTarget Categoria categoria);
    CategoriaFilter toFilter(CategoriaFilterDto dto);
}
