package com.valtxarq.application.dto.categoria;

import com.valtxarq.shared.page.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaFilterDto extends PageRequest {
    private String nombre;
    private String descripcion;
}

