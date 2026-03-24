package com.valtxarq.application.usecase.producto;

import com.valtxarq.application.dto.producto.ProductoDto;
import com.valtxarq.application.mapper.ProductoAppMapper;
import com.valtxarq.application.ports.producto.GetProductoUseCase;
import com.valtxarq.domain.repository.IProductoRepository;
import com.valtxarq.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetProductoUseCaseImpl implements GetProductoUseCase {

    private final IProductoRepository productoRepository;
    private final ProductoAppMapper mapper;

    @Override
    public List<ProductoDto> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ProductoDto findById(Long id) {
        return productoRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }
}
