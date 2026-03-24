package com.valtxarq.application.usecase.categoria;

import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaSaveDto;
import com.valtxarq.application.mapper.CategoriaAppMapper;
import com.valtxarq.application.ports.categoria.UpdateCategoriaUseCase;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.domain.repository.ICategoriaRepository;
import com.valtxarq.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCategoriaUseCaseImpl implements UpdateCategoriaUseCase {

    private final ICategoriaRepository categoriaRepository;
    private final CategoriaAppMapper mapper;

    @Override
    public CategoriaDto execute(Long id, CategoriaSaveDto request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));
        mapper.updateDomain(request, categoria);
        return mapper.toDto(categoriaRepository.save(categoria));
    }
}
