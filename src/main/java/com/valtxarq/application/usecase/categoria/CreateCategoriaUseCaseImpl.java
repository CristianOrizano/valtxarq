package com.valtxarq.application.usecase.categoria;

import com.valtxarq.application.dto.categoria.CategoriaDto;
import com.valtxarq.application.dto.categoria.CategoriaSaveDto;
import com.valtxarq.application.mapper.CategoriaAppMapper;
import com.valtxarq.application.mapper.ProductoAppMapper;
import com.valtxarq.application.ports.categoria.CreateCategoriaUseCase;
import com.valtxarq.domain.model.Categoria;
import com.valtxarq.domain.model.Producto;
import com.valtxarq.domain.repository.ICategoriaRepository;
import com.valtxarq.domain.repository.IProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateCategoriaUseCaseImpl implements CreateCategoriaUseCase {

    private final ICategoriaRepository categoriaRepository;
    private final IProductoRepository productoRepository;
    private final CategoriaAppMapper categoriaMapper;
    private final ProductoAppMapper productoMapper;

    @Override
    public CategoriaDto execute(CategoriaSaveDto request) {
        Categoria categoria = categoriaMapper.toDomain(request);
        Categoria categoriaSaved = categoriaRepository.save(categoria);

        if (request.getProductos() != null && !request.getProductos().isEmpty()) {
            List<Producto> productos = request.getProductos().stream()
                    .map(dto -> {
                        Producto p = productoMapper.toDomain(dto);
                        p.setCategoria(categoriaSaved);
                        return p;
                    })
                    .toList();
            productoRepository.saveAll(productos);
        }
        return categoriaMapper.toDto(categoriaSaved);
    }
}
