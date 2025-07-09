package com.fiap.estoque.repository;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import com.fiap.estoque.mapper.EstoqueMapper;
import com.fiap.estoque.repository.entity.EstoqueEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EstoqueGatewayImpl implements EstoqueGateway {
    
    private final EstoqueRepository repository;
    private final EstoqueMapper mapper;
    
    public EstoqueGatewayImpl(EstoqueRepository repository, EstoqueMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    @Transactional
    public Estoque salvar(Estoque estoque) {
        EstoqueEntity entity = mapper.toEntity(estoque);
        EstoqueEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Estoque> buscarPorIdProduto(UUID idProduto) {
        return repository.findByIdProduto(idProduto)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Estoque> buscarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    @Transactional
    public void removerPorIdProduto(UUID idProduto) {
        repository.deleteByIdProduto(idProduto);
    }
    
    @Override
    public boolean existePorIdProduto(UUID idProduto) {
        return repository.existsByIdProduto(idProduto);
    }
}
