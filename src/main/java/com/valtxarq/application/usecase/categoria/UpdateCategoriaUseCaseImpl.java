package com.valtxarq.application.usecase.categoria;

import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaSaveDto;
import com.valtxarq.application.dto.categoria.CategoriaUpdateDto;
import com.valtxarq.application.mapper.CategoriaAppMapper;
import com.valtxarq.application.ports.categoria.UpdateCategoriaUseCase;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.domain.repository.ICategoriaRepository;
import com.valtxarq.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UpdateCategoriaUseCaseImpl implements UpdateCategoriaUseCase {

    private final ICategoriaRepository categoriaRepository;
    private final CategoriaAppMapper mapper;

    @Override
    public CategoriaDto execute(Long id, CategoriaUpdateDto request) {
        log.info("Actualizando categoria id: {}", id);
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));
        mapper.updateDomain(request, categoria);
        CategoriaDto result = mapper.toDto(categoriaRepository.save(categoria));
        log.info("Categoria id: {} actualizada", id);
        return result;
    }
}
