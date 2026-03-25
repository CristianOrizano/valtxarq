package com.valtxarq.application.mapper;

import com.valtxarq.application.dto.producto.*;
import com.valtxarq.domain.filters.ProductoFilter;
import com.valtxarq.domain.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoAppMapper {
    ProductoDto toDto(Producto producto);
    ProductoSimpleDto toSimpleDto(Producto producto);
    Producto toDomain (ProductoSaveDto productoSaveDto);
    void updateDomain(ProductoSaveDto dto, @MappingTarget Producto producto);
    Producto toDomain(ProductoSaveSimpleDto dto);
    ProductoFilter toFilter(ProductoFilterDto dto);
}
