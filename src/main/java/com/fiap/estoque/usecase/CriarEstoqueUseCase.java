package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.exception.EstoqueJaExisteException;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.springframework.stereotype.Service;

@Service
public class CriarEstoqueUseCase {
    
    private final EstoqueGateway gateway;
    
    public CriarEstoqueUseCase(EstoqueGateway gateway) {
        this.gateway = gateway;
    }
    
    public Estoque executar(Estoque estoque) {
        if (gateway.existePorIdProduto(estoque.getIdProduto())) {
            throw new EstoqueJaExisteException("Já existe estoque para o produto: " + estoque.getIdProduto());
        }
        
        return gateway.salvar(estoque);
    }
}
