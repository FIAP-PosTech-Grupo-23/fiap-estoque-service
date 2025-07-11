package com.fiap.estoque.mapper;

import com.fiap.estoque.controller.request.BaixaEstoqueRequest;
import com.fiap.estoque.controller.request.CriarEstoqueRequest;
import com.fiap.estoque.controller.response.EstoqueResponse;
import com.fiap.estoque.controller.response.StatusEstoque;
import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.repository.entity.EstoqueEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueMapperTest {

    private final EstoqueMapper mapper = Mappers.getMapper(EstoqueMapper.class);

    @Test
    void deveConverterEntityParaDomain() {
        EstoqueEntity entity = new EstoqueEntity(1L, 10);
        entity.setId(1L);
        
        Estoque domain = mapper.toDomain(entity);
        
        assertEquals(entity.getIdProduto(), domain.getIdProduto());
        assertEquals(entity.getQuantidade(), domain.getQuantidade());
    }

    @Test
    void deveConverterDomainParaEntity() {
        Estoque domain = new Estoque(1L, 10);
        
        EstoqueEntity entity = mapper.toEntity(domain);
        
        assertNull(entity.getId());
        assertEquals(domain.getIdProduto(), entity.getIdProduto());
        assertEquals(domain.getQuantidade(), entity.getQuantidade());
    }

    @Test
    void deveConverterDomainParaResponse() {
        Estoque domain = new Estoque(1L, 10);
        
        EstoqueResponse response = mapper.toResponse(domain);
        
        assertEquals(domain.getIdProduto(), response.idProduto());
        assertEquals(domain.getQuantidade(), response.quantidade());
        assertNull(response.status());
    }

    @Test
    void deveConverterCriarRequestParaDomain() {
        CriarEstoqueRequest request = new CriarEstoqueRequest(1L, 10);
        
        Estoque domain = mapper.toDomain(request);
        
        assertEquals(request.idProduto(), domain.getIdProduto());
        assertEquals(request.quantidade(), domain.getQuantidade());
    }

    @Test
    void deveConverterBaixaRequestParaDomain() {
        BaixaEstoqueRequest request = new BaixaEstoqueRequest(1L, 5);
        
        Estoque domain = mapper.toDomain(request);
        
        assertEquals(request.idProduto(), domain.getIdProduto());
        assertEquals(request.quantidade(), domain.getQuantidade());
    }

    @Test
    void deveConverterDomainParaResponseComStatus() {
        Estoque domain = new Estoque(1L, 10);
        StatusEstoque status = StatusEstoque.DISPONIVEL;
        
        EstoqueResponse response = mapper.toResponse(domain, status);
        
        assertEquals(domain.getIdProduto(), response.idProduto());
        assertEquals(domain.getQuantidade(), response.quantidade());
        assertEquals(status, response.status());
    }
}
