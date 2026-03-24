package com.valtxarq.application.usecase.categoria;

import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaSaveDto;
import com.valtxarq.application.mapper.CategoriaAppMapper;
import com.valtxarq.application.ports.categoria.CreateCategoriaUseCase;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.domain.repository.ICategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoriaUseCaseImpl implements CreateCategoriaUseCase {

    private final ICategoriaRepository categoriaRepository;
    private final CategoriaAppMapper mapper;

    @Override
    public CategoriaDto execute(CategoriaSaveDto request) {
        Categoria categoria = mapper.toDomain(request);
        return mapper.toDto(categoriaRepository.save(categoria));
    }
}
