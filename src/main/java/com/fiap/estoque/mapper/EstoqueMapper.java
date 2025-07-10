package com.fiap.estoque.mapper;

import com.fiap.estoque.controller.request.BaixaEstoqueRequest;
import com.fiap.estoque.controller.request.CriarEstoqueRequest;
import com.fiap.estoque.controller.response.EstoqueResponse;
import com.fiap.estoque.controller.response.StatusEstoque;
import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.repository.entity.EstoqueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstoqueMapper {
    
    Estoque toDomain(EstoqueEntity entity);
    
    @Mapping(target = "id", ignore = true)
    EstoqueEntity toEntity(Estoque domain);
    
    @Mapping(target = "status", ignore = true)
    EstoqueResponse toResponse(Estoque domain);
    
    Estoque toDomain(CriarEstoqueRequest request);
    
    Estoque toDomain(BaixaEstoqueRequest request);
    
    default EstoqueResponse toResponse(Estoque domain, StatusEstoque status) {
        return new EstoqueResponse(domain.getIdProduto(), domain.getQuantidade(), status);
    }
}
