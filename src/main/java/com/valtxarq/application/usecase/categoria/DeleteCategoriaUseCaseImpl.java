package com.valtxarq.application.usecase.categoria;

import com.valtxarq.application.ports.categoria.DeleteCategoriaUseCase;
import com.valtxarq.domain.repository.ICategoriaRepository;
import com.valtxarq.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCategoriaUseCaseImpl implements DeleteCategoriaUseCase {

    private final ICategoriaRepository categoriaRepository;

    @Override
    public void execute(Long id) {
        categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));
        categoriaRepository.deleteById(id);
    }
}
