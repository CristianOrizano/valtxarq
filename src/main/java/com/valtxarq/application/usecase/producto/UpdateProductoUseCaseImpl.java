package com.valtxarq.application.usecase.producto;

import com.valtxarq.application.dto.producto.ProductoDto;
import com.valtxarq.application.dto.producto.ProductoSaveDto;
import com.valtxarq.application.mapper.ProductoAppMapper;
import com.valtxarq.application.ports.producto.UpdateProductoUseCase;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.domain.model.Producto;
import com.valtxarq.domain.repository.ICategoriaRepository;
import com.valtxarq.domain.repository.IProductoRepository;
import com.valtxarq.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UpdateProductoUseCaseImpl implements UpdateProductoUseCase {

    private final IProductoRepository productoRepository;
    private final ICategoriaRepository categoriaRepository;
    private final ProductoAppMapper mapper;

    @Override
    public ProductoDto execute(Long id, ProductoSaveDto dto) {
        log.info("Actualizando producto id: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + dto.getCategoriaId()));
        mapper.updateDomain(dto, producto);
        producto.setCategoria(categoria);
        ProductoDto result = mapper.toDto(productoRepository.save(producto));
        log.info("Producto id: {} actualizado", id);
        return result;
    }
}
