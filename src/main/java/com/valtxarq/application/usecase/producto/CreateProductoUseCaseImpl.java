package com.valtxarq.application.usecase.producto;

import com.valtxarq.application.dto.producto.ProductoDto;
import com.valtxarq.application.dto.producto.ProductoSaveDto;
import com.valtxarq.application.mapper.ProductoAppMapper;
import com.valtxarq.application.ports.producto.CreateProductoUseCase;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.domain.model.Producto;
import com.valtxarq.domain.repository.ICategoriaRepository;
import com.valtxarq.domain.repository.IProductoRepository;
import com.valtxarq.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductoUseCaseImpl implements CreateProductoUseCase {

    private final IProductoRepository productoRepository;
    private final ICategoriaRepository categoriaRepository;
    private final ProductoAppMapper mapper;

    @Override
    public ProductoDto execute(ProductoSaveDto dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + dto.getCategoriaId()));
        Producto producto = mapper.toDomain(dto);
        producto.setCategoria(categoria);
        return mapper.toDto(productoRepository.save(producto));
    }
}
