package com.valtxarq.infraestructure.mapper;

import com.valtxarq.domain.model.Producto;
import com.valtxarq.infraestructure.entity.ProductoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoMapper {
    Producto toDomain(ProductoEntity entity);

    ProductoEntity toEntity(Producto domain);
}
