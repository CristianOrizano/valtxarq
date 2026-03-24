package com.valtxarq.infraestructure.mapper;

import com.valtxarq.domain.model.Categoria;
import com.valtxarq.infraestructure.entity.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {
    Categoria toDomain(CategoriaEntity entity);
    CategoriaEntity toEntity(Categoria domain);
}
