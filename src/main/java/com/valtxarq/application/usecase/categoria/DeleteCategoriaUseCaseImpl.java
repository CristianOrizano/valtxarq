package com.valtxarq.application.usecase.categoria;

import com.valtxarq.application.ports.categoria.DeleteCategoriaUseCase;
import com.valtxarq.domain.repository.ICategoriaRepository;
import com.valtxarq.domain.repository.IProductoRepository;
import com.valtxarq.shared.exception.BusinessRuleException;
import com.valtxarq.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteCategoriaUseCaseImpl implements DeleteCategoriaUseCase {

    private final ICategoriaRepository categoriaRepository;
    private final IProductoRepository productoRepository;

    @Override
    public void execute(Long id) {
        categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));

        if (productoRepository.existsByCategoriaId(id)) {
            throw new BusinessRuleException(
                    "No se puede eliminar la categoría porque tiene productos asociados"
            );
        }

        categoriaRepository.deleteById(id);
    }
}
