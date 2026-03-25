package com.valtxarq.application.dto.categoria;

import com.valtxarq.application.dto.producto.ProductoSaveSimpleDto;
import com.valtxarq.application.dto.producto.ProductoSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDetalleDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<ProductoSimpleDto> productos;
}
