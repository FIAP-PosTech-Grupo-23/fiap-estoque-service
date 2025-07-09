package com.fiap.estoque.gateway;

import com.fiap.estoque.domain.Estoque;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstoqueGateway {
    
    Estoque salvar(Estoque estoque);
    
    Optional<Estoque> buscarPorIdProduto(UUID idProduto);
    
    List<Estoque> buscarTodos();
    
    void removerPorIdProduto(UUID idProduto);
    
    boolean existePorIdProduto(UUID idProduto);
}
