package com.fiap.estoque.gateway;

import com.fiap.estoque.domain.Estoque;

import java.util.List;
import java.util.Optional;

public interface EstoqueGateway {
    
    Estoque salvar(Estoque estoque);
    
    Optional<Estoque> buscarPorIdProduto(Long idProduto);
    
    Estoque atualizarQuantidade(Long idProduto, Integer novaQuantidade);
    
    List<Estoque> buscarTodos();
    
    void removerPorIdProduto(Long idProduto);
    
    boolean existePorIdProduto(Long idProduto);
}
