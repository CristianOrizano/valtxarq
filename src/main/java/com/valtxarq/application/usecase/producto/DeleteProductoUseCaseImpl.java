package com.valtxarq.application.usecase.producto;

import com.valtxarq.application.ports.producto.DeleteProductoUseCase;
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
public class DeleteProductoUseCaseImpl implements DeleteProductoUseCase {

    private final IProductoRepository productoRepository;

    @Override
    public void execute(Long id) {
        log.info("Eliminando producto id: {}", id);
        productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
        productoRepository.deleteById(id);
        log.info("Producto id: {} eliminado", id);
    }
}
