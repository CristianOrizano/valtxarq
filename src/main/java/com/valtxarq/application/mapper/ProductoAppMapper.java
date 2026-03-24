package com.valtxarq.application.mapper;

import com.valtxarq.application.dto.categoria.CategoriaSaveDto;
import com.valtxarq.application.dto.producto.ProductoDto;
import com.valtxarq.application.dto.producto.ProductoSaveDto;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.domain.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoAppMapper {
    ProductoDto toDto(Producto producto);
    Producto toDomain (ProductoSaveDto productoSaveDto);
    void updateDomain(ProductoSaveDto dto, @MappingTarget Producto producto);
}
