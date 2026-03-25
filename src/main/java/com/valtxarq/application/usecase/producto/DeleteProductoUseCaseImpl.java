package com.valtxarq.application.usecase.producto;

import com.valtxarq.application.ports.producto.DeleteProductoUseCase;
import com.valtxarq.domain.repository.IProductoRepository;
import com.valtxarq.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteProductoUseCaseImpl implements DeleteProductoUseCase {

    private final IProductoRepository productoRepository;

    @Override
    public void execute(Long id) {
        productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
        productoRepository.deleteById(id);
    }
}
